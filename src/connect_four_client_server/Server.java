package connect_four_client_server;

/*
 * http://stackoverflow.com/questions/19844649/java-read-file-and-store-text-in-an-array
 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	
	//Create a public static PORT and IP for the server.
	
    final static int PORT = 80; //port number
    public static Socket client1; //connection to server
    public static Socket client2; //connection to server
    public static ServerSocket server; //server waits for connection
    public static DataOutputStream client1writer;
    public static DataOutputStream client2writer;
    public static DataInputStream client1reader;
    public static DataInputStream client2reader;
    
    public static int ROWS = 6;
    public static int lastCol = -1, lastTop = -1;
    public static int COLS = 7;
    public static char[][] board = new char[ROWS][COLS];
	public static int[] columnHeight = new int[COLS]; //height of column
	
    
    public static void main(String[] args) throws IOException{
    	
        System.out.println("Connecting to client../."); //update message
        server = new ServerSocket(PORT); //server socket
        
        try{
			client1 = server.accept(); //accepts connection to client1, player1
			System.out.println("Connection successful to PLAYER1"); //update message
            client1writer = new DataOutputStream(client1.getOutputStream()); //to write to client
            client1reader = new DataInputStream(client1.getInputStream()); //to read from client
            
			client2 = server.accept(); //accepts connection to client2, player2
			System.out.println("Connection successful to PLAYER2"); //update message
			client2writer = new DataOutputStream(client2.getOutputStream()); //to write to client
			client2reader = new DataInputStream(client2.getInputStream()); //to read from client
			
			//assign players
			int PL1 = 1, PL2 = 2;
			client1writer.writeInt(PL1); client2writer.writeInt(PL2);
			
			int col, playerCount = 0;
			
			for (int i=0; i<42; i++) {
				//using mod to determine which player is next
            	client1writer.writeInt((playerCount % 2) + 1); 
            	client2writer.writeInt((playerCount % 2) + 1);
            	
            	//Server output for which player is waiting and what move they make
            	if (playerCount%2 == 0) {
            		System.out.println("Waiting for player 1 to make a move...");
            		col = client1reader.readInt(); //reads input for column         		
            		update(col, 'X'); //calls the update method
            		System.out.println("Player 1 moved to " +(col+1));
            		client2writer.writeInt(col); //updates player 2 board
            	} else {
            		System.out.println("Waiting for player 2 to make a move...");
            		col = client2reader.readInt(); //reads input for column
            		update(col, 'O'); //calls the update method
            		System.out.println("Player 2 moved to " + (col+1));
            		client1writer.writeInt(col); //updates player 1 boards
            	}
            	playerCount++; //increments players turn
            }
			
    }catch (IOException IOex){
    	System.err.println("Something went wrong");
    	//Closing everything
        client1.close(); client2.close(); server.close(); client1writer.close(); 
        client2writer.close(); client1reader.close(); client2reader.close(); System.exit(0); 
    }
        
}
    
    //****************************************
	/*WHEN BOARD IS POPULATED AND DISPLAYED*/
	//****************************************
    public static void board () {
//    	for (int row = 0; row < 6; row++) {
//        	for (int col = 0; col < 7; col++) {
//        		board[row][col] = '-';
//                client1writer.writeChar(board[row][col]);
//                client2writer.writeChar(board[row][col]);
//  			}
//        }
    	
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				System.out.print("-" + board[row][col]);
			}
			System.out.println();
		}
		for (int col = 0; col < COLS; col++)
			System.out.print("--");
		System.out.println();
		System.out.println(" 1|2|3|4|5|6|7 ");
    }
    
    //****************************************
  	/*UPDATES BOARD AFTER MOVE IS MADE*/
  	//****************************************
 	public static void update (int input, char symbol) throws IOException {
 		board[(ROWS-1)-columnHeight[input]][input] = symbol; //decrements through ROWS by input(column)
 		columnHeight[input]++; //increments column height
 	}
 	
 	//****************************************
 	/*RETURNS THE HEIGHT OF EACH COLUMN*/
 	//****************************************
 	public static int columnHeight (int input) {return columnHeight[input];}

}
