package connect_four_client_server;

/*
 * http://stackoverflow.com/questions/19844649/java-read-file-and-store-text-in-an-array
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	//Create a public static PORT and IP for the server.
    final static int PORT = 80; //port number
    public static Socket client1; //connection to server
    public static Socket client2; //connection to server
    public static ServerSocket server; //server waits for connection
    public static PrintWriter outClient1;
    public static PrintWriter outClient2;
    public static DataOutputStream client1writer;
    public static DataOutputStream client2writer;
    public static BufferedReader inputClient1;
    public static BufferedReader inputClient2;
    public static boolean c = true; //loop variable
    public static String line = null;
    public static List<String> temps = new ArrayList<String>(); //temp list array for the board
    public static String [][] board = new String[6][7]; //board array
    
    
    public static void main(String[] args) throws IOException{
    	
        System.out.println("Connecting to client../."); //update message
        server = new ServerSocket(PORT);
        
        //connect four layout
		BufferedReader bufferedReader = 
				new BufferedReader(new FileReader("CONNECT_FOUR/connect_four_board.txt"));
        
        try{
			client1 = server.accept(); //accepts connection to client1, player1
			System.out.println("Connection successful to PLAYER1"); //update message
            client1writer = new DataOutputStream(client1.getOutputStream());
          
			client2 = server.accept(); //accepts connection to client2, player2
			System.out.println("Connection successful to PLAYER2"); //update message
			client2writer = new DataOutputStream(client2.getOutputStream());
			
			//for reading and writing
			outClient1 = new PrintWriter(client1.getOutputStream(), true); //to write to the client
			//inputClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream())); //to read from client
			
			//for reading and writing
			outClient2 = new PrintWriter(client2.getOutputStream(), true); //to write to the client
			//inputClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream())); //to read from client
			
			//assigning values to players
			String PL1 = "X";
			String PL2 = "O";
			
			//representing each client as players
			client1writer.writeChars(PL1); client2writer.writeChars(PL2);
			//display when players have connected
			outClient1.println(PL1 + " Player 1 has connected"); outClient2.println(PL2 + " Player 2 has connected");
			

			
			//output the c4 board
			while((line = bufferedReader.readLine()) != null) {temps.add(line);} 
			
            bufferedReader.close(); //
            
            //String array to display the board
            String[] tempsArray = temps.toArray(new String[0]); 
            
            for(int i = 0;i<1;i++){ //rows
	            for (int j = 0;j<5;j++){ //cols
	                board[i][j] = tempsArray[j]; 
	                outClient1.println(board[i][j]);
	            }
            }
            
            
			//populate the board with '-'
			for (int row = 0; row < board.length; row++) { 
				for (int col = 0; col < board[row].length; col++) { 
					board[row][col] = tempsArray[col]; 
					outClient1.println(board[row][col]);} }
            
            
//            for (String s : tempsArray) {
//              outClient1.println(s);
//              outClient2.println(s);
//            }
			
			//String input = inputClient1.readLine(); //client input
			while(c){}
			

     //Close both sockets when execution is finished.
    }catch (IOException IOex){
    	System.err.println("Something went wrong"); //output
        //client.close(); server.close(); outClient1.close(); inputClient.close(); System.exit(0); //closing everything
    }
        
    }

}
