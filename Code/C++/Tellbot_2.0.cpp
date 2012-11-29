//#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <assert.h>
#include <unistd.h>
#include <netdb.h>
#include <iostream>;
#include <cstdio>;
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <getopt.h>
using namespace std;

int main(int argc, char *argv[]) {

	// open sockets/ports
	// load handranker
	// start loop
	//		-> parser (input is string, "output" is updated object
	//		-> MatchState , the decision maker, returns char,
	//			which is our action
	// if we're acting, flush the stream to the server

	int count; // counter for string
	int sock, len, r; // socket, length, r??
	int32_t min, max;
	uint16_t port;
	struct sockaddr_in addr;
	struct hostent *hostent;
	FILE *toServer, *fromServer; //
	char line[ 256 ]; // was "max length", which was 256.

	port = 48777; //hard coded for now.
	sock = socket(AF_INET, SOCK_STREAM,0);
	if (sock < 0){
	 printf("Error opening socket");
		  exit(0);
	  }

	  hostent = gethostbyname("127.0.0.1");
	  if(hostent == NULL){
		    printf("ERROR: could not look up address for 127.0.0.1");
		    exit( EXIT_FAILURE );
	  }

	  addr.sin_family = AF_INET;
	  addr.sin_port = htons( port );
	  memcpy( &addr.sin_addr, hostent->h_addr_list[ 0 ], hostent->h_length );

	  if( connect( sock, (struct sockaddr *)&addr, sizeof( addr ) ) < 0 ) {
	   printf("ERROR: could not open connect.");
	    exit( EXIT_FAILURE );
	  }



	  toServer = fdopen( sock, "w" );
	  fromServer = fdopen( sock, "r" );
	  if( toServer == NULL || fromServer == NULL ) {
	    fprintf( stderr, "ERROR: could not get socket streams\n" );
	    exit( EXIT_FAILURE );
	  }

	  // try and send version string to server
	  if( fprintf( toServer, "VERSION:2.0.0\n" ) != 14 ) {
	    fprintf( stderr, "ERROR: could not get send version to server\n" );
	    exit( EXIT_FAILURE );
	  }
	  fflush( toServer );

	  while( fgets( line, 256, fromServer ) ) {
		  count = 0;
		  while (line[count] != '\n'){
			  count++;
		  }
		  printf("Received string is\n%s\n",line); //THIS IS THE BETSTREAM
		  len = count-1; // get it to point at the index we want to deal with


	    /* add a colon (guaranteed to fit because we read a new-line in fgets) */
	    line[ len ] = ':';
	    ++len;
	    line[ len ] = 'c';////////////////////////////////////////////////////////////////////////////THIS IS THE PART WHERE WE SEND THE THINGS
	    ++len;
	    line[ len ] = '\n';
	    ++len;
	    printf("String sent to server is\n%s", line);

	    if( fwrite( line, 1, len, toServer ) != len ) {
	      printf("ERROR: could not get send response to server\n" );
	      exit( 0 );
	    }
	    fflush( toServer );
	  }

	  return 0;


}

