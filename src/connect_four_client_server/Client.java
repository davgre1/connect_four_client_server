package connect_four_client_server;

/*
 * 12/11/2016
 * David F Greene
 * Connect Four, Client class
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	public static Socket client; //players
    public static DataInputStream serverInput;
    public static DataOutputStream serverOutput;
    public static final String HOST = "localhost"; //localhost
    public static final int PORT = 80; //port number
    
    public static String numColumn; //which column the players choose
    public static int col; //place holder for numColumn after parsed
    public static int nextPlayer; //use to compare the player to the nextPlayer
    public static boolean validation; //used to valid the players moves
    public static Scanner keyboard = new Scanner(System.in);
    
    
public static void main(String[] args){
	
        System.out.println("Connecting to server..."); //update message
        try{
			client = new Socket(HOST, PORT); //IP and Port number
			serverInput = new DataInputStream(client.getInputStream()); //to read from Server
			serverOutput = new DataOutputStream(client.getOutputStream()); //to write to Server
			
			System.out.println("Connection successful to SERVER!"); //connection is true
			System.out.println("Connect Four"); //message
			
			int player = serverInput.readInt(); //first player connected
			
			Server.board(); //calls board method in Server
			System.out.println("Player " + player); //read player in
		        
			
//			//DISPLAY BOARD
//			for(int i = 0; i < 6; i++){
//				for(int c = 0; c < 7; c++){
//					
//					System.out.print(serverInput.readChar()); //displays input from server
//				}
//				System.out.println("");
//			}
		      
		      
		      for (int i=0; i<42; i++) { //loops 42 times for every slot on the board 6*7
	            	
	            	nextPlayer = serverInput.readInt(); //Server passes who's turn it is
	            	
	            	if (nextPlayer == player) {
	            		validation = false;	//validation is false at the start
	            		while (validation == false) {
	                		System.out.print("Enter a column  0-6: ");
	                		numColumn = keyboard.nextLine(); //gets column number
	                		if (numColumn.matches("[1-7]{1}") == true) { //regular expression for validation of input
	                			col = Integer.parseInt(numColumn)-1; //changes String to int
	                			if (Server.columnHeight(col) < 6) {	//check column height
	                				Server.update(col, 'X'); //calls update method
	                				serverOutput.writeInt(col); //send column number to Server
	                				System.out.println("Sending to server.");
	                				validation = true;	//validation is true at the end
	                			} else {
	                				System.out.print("Column " + (col+1) + " is full, ");
	                				validation = false;	//if column is full
	                			}
	                		} else {
	                			System.out.print("Incorrect value,");
	            				validation = false; //if not a valid number
	                		}
	                    }	
	            	} else {
	            		//while other players are waiting
	            		System.out.println("Waiting on other player");
	            		col = serverInput.readInt(); //reads from Server
	            		Server.update(col, 'O'); //calls update method
	            		System.out.println("Other player choose Column " + (col+1));
	            	}           	
	            	Server.board(); //prints new updated board
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
            keyboard.close(); client.close();//closing everything
        }catch(IOException IOex){
            System.err.println("Error connecting to server."); //cannot connect to the server
        }
    }
}
