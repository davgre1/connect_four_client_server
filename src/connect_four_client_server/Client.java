package connect_four_client_server;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

	public static Socket client; //players
    public static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); //track user input
    public static PrintWriter outServer; //to write
    public static DataInputStream serverInput;
    public static DataOutputStream serverOutput;
    public static final String HOST = "localhost"; //localhost
    public static final int PORT = 80; //port number
    public static int input; //takes user input
    public static String is; //input receiver
    public static Boolean x;
    public static char[] symbol = new char[] { 'X', 'O' };
    public static Scanner scan = new Scanner(System.in);
    
    
public static void main(String[] args){
	
        System.out.println("Connecting to server..."); //update message
        try{
			client = new Socket(HOST, PORT); //IP and Port number
			serverInput = new DataInputStream(client.getInputStream()); //read input from server
			serverOutput = new DataOutputStream(client.getOutputStream());
			
			System.out.println("Connection successful to SERVER!");
			System.out.println("Connect Four");//column message
			
			int player = serverInput.readInt();
			
			Server.board(); //calls board method in Server class
			System.out.println("Player " + player); //read player
			
			  String move;		// # of column to drop piece into
		      int col;			// Value of move as an int
		      int currentPlayer;	// Current player's turn
		      boolean valid;		// Determines if the input from the user can be a valid move
		        
			
//			//DISPLAY BOARD
//			for(int i = 0; i < 6; i++){
//				for(int c = 0; c < 7; c++){
//					
//					System.out.print(serverInput.readChar()); //displays input from server
//				}
//				System.out.println("");
//			}
		      
		      
		      for (int i=0; i<42; i++) {
	            	// Determine's who's turn it is (passed in from server)
	            	currentPlayer = serverInput.readInt();
	            	
	            	// Takes a turn
	            	if (currentPlayer == player) {
	            		valid = false;	// Makes valid false at the beginning of each turn
	            		while (valid == false) {
	                		System.out.println("Enter a column  0-6: ");
	                		move = scan.nextLine();		// Gets input from user as a String
	                		if (move.matches("[1-7]{1}") == true) {		// Checks to see if input is a valid column
	                			col = Integer.parseInt(move)-1;
	                			if (Server.columnHeight(col) < 6) {	// Checks to see if column entered is full
	                				Server.update(col, 'X');		// Updates current player's board
	                				serverOutput.writeInt(col);			// Sends move to server to update other player's board
	                				System.out.println("Sending move to server.");
	                				valid = true;	// Makes valid true to end loop
	                			} else {
	                				System.out.println("Column " + (col+1) + " is full.");
	                				valid = false;	// Makes valid false to loop until a legal move is entered
	                			}
	                		} else {
	                			System.out.println("Enter a column between 1 and 7.");
	            				valid = false;		// Makes valid false to loop until a legal move is entered
	                		}
	                    }  //loops while move is invalid	
	            	} else {
	            		// Lets other player go
	            		System.out.println("Waiting for other player");
	            		col = serverInput.readInt();
	            		Server.update(col, 'O');
	            		System.out.println("Other player moved in column " + (col+1) + ".");
	            	}           	
	            	Server.board();	// Prints board after a move by either player is made
	            }
		      
		      
		      
		      
		      
		      
//			try{
//				do{
//					System.out.println("Player " + symbol);
//				    input = keyboard.read(); //input from user
//				    
//				    if((input >=0 && input <=6)){
//				    	System.out.println("THIS IS NOT A VAaaLID MOVE");
//				    	continue;}
//				    
//				    for(int i = 0; i < 6; i++){
//						for(int c = 0; c < 7; c++){
//							System.out.print(serverInput.readChar()); //displays input from server
//						}
//						System.out.println("");
//					}
//				    
//				}while((input < 1 || input > 6));
//				//method();
//			}catch(InputMismatchException e){
//		        System.out.println("THIS IS NOT A VALID MOVE"); //if letter or special characters are input
//		        //keyboard.readLine();
//		    }
            
        }catch(IOException IOex){
            System.err.println("Error connecting to server."); //if can not connect to the server
        }
    }

		
}
