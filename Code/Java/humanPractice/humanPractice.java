
//package Bot;
import java.io.*;
//import java.util.*;
import java.net.*;

//import tools.HandEvaluator;
//import tools.HandPotential;

public class humanPractice {
	
	public static void main(String args[]) throws IOException,SocketException{
//		 String serverAddress;
		 int PORT = 48777;
		 PrintWriter outStream = null;
		 BufferedReader inStream = null;
		 Socket socket = null;
		 String input = null;
//		PrintWriter textStream;	
		
		if(args.length > 1)
		{
			PORT = Integer.parseInt(args[1]);
		}
		
		// load hand ranker into memory
		//HandEvaluator.loadHandRanks();
		//MatchState.buildHashMap();
		
		// TCP set-up

		socket = new Socket("127.0.0.1",PORT);
		outStream = new PrintWriter(socket.getOutputStream(),true);
		inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outStream.println("VERSION:2.0.0"); // MUST BE FIRST STRING SENT TO SERVER
		outStream.flush();
		
		System.out.println("Connected");
		matchState test = new matchState();
	
		while((input = inStream.readLine()) != null){	
			String move = IOHelper.getString("Input your hand (c) or (r) : ");//":c"; 
			if(move.contains("c"))
				move = ":c";
			else
				move = ":r";
			String out = input + move;
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
