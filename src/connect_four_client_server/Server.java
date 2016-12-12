package connect_four_client_server;

/*
 * 12/11/2016
 * David F Greene
 * Connect Four, Server class
 */

import java.io.*;
import java.net.*;

public class Server {
	
	//create a public static PORT and IP for the server.
    final static int PORT = 80; //port number
    public static Socket client1; //connection to server
    public static Socket client2; //connection to server
    public static ServerSocket server; //server waits for connection
    public static DataOutputStream client1writer;
    public static DataOutputStream client2writer;
    public static DataInputStream client1reader;
    public static DataInputStream client2reader;
    //global variables
    public static int ROWS = 6;
    public static int COLS = 7;
    public static char[][] board = new char[ROWS][COLS];
	public static int[] columnHeight = new int[COLS]; //height of column
	
    
    public static void main(String[] args) throws IOException{
    	
        System.out.println("Connecting to client..."); //update message
        server = new ServerSocket(PORT); //server socket
        
        try{
			client1 = server.accept(); //accepts connection to client1, player1
			System.out.println("Connection successful to PLAYER1"); //update message
            client1writer = new DataOutputStream(client1.getOutputStream()); //to write to Client
            client1reader = new DataInputStream(client1.getInputStream()); //to read from Client
            
			client2 = server.accept(); //accepts connection to client2, player2
			System.out.println("Connection successful to PLAYER2"); //update message
			client2writer = new DataOutputStream(client2.getOutputStream()); //to write to Client
			client2reader = new DataInputStream(client2.getInputStream()); //to read from Client
			
			//assign players
			int PL1 = 1, PL2 = 2;
			client1writer.writeInt(PL1); client2writer.writeInt(PL2);
			
			int col, playerCount = 0;
			
			for (int i=0; i<(ROWS*COLS); i++) { //loops 42 times for every slot on the board 6*7
				//using mod to determine which player is next
            	client1writer.writeInt((playerCount % 2) + 1); 
            	client2writer.writeInt((playerCount % 2) + 1);
            	
            	//server output for which player is waiting and what move they make
            	if (playerCount%2 == 0) {
            		col = client1reader.readInt(); //reads input for column         		
            		update(col, 'X'); //calls the update method
            		System.out.println("Player 1 chooses Column " +(col+1));
            		client2writer.writeInt(col); //updates player 2 board
            	} else {
            		col = client2reader.readInt(); //reads input for column
            		update(col, 'O'); //calls the update method
            		System.out.println("Player 2 chooses Column " + (col+1));
            		client1writer.writeInt(col); //updates player 1 boards
            	}
            	playerCount++; //increments players turn
            }
			
    }catch (IOException IOex){
    	System.err.println("Something went wrong");
    	//closing everything
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
