
//package Bot;
import java.io.*;
//import java.util.*;
import java.net.*;

//import tools.HandEvaluator;
//import tools.HandPotential;

public class TCP_LOOP {
	
		public static void main(String args[]) throws IOException,SocketException{

			 int PORT = 16177;
			 PrintWriter outStream = null;
			 BufferedReader inStream = null;
			 Socket socket = null;
			 String input = null;

			
			if(args.length > 1)
			{
				PORT = Integer.parseInt(args[1]);
			}
			
			
			// TCP set-up
	
			socket = new Socket("127.0.0.1",PORT);
			outStream = new PrintWriter(socket.getOutputStream(),true);
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream.println("VERSION:2.0.0"); // MUST BE FIRST STRING SENT TO SERVER
			outStream.flush();
			
			System.out.println("Connected");
			matchState test = new matchState();
		
			while((input = inStream.readLine()) != null){	
				String out = input +  ":" + rattata.decision(input);
				System.out.println(input);
				if (out == null){
					continue;
				}
				
				
				
				test.updateState(input);
				
				if(test.getOurTurn()){	
						System.out.println("Output" + out);
						outStream.println(out);
						outStream.flush();
				}
			}
			
			inStream.close();
			outStream.close();
			socket.close();
			
			System.out.println("Game Finished.");


		}
		
}
