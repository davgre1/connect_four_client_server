package connect_four_client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;

public class Client {

	public static Socket client; //players
    public static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); //track user input
    public static PrintWriter outServer; //to write
    public static BufferedReader inputServer; //to read
    public static final String HOST = "localhost"; //localhost
    public static final int PORT = 80; //port number
    public static String input; //takes user input
    public static String is; //input receiver
    public static Boolean x;
    public static int pl1num = 0;
	
public static void main(String[] args){
    	
        System.out.println("Connecting to server..."); //update message
        try{
			client = new Socket(HOST, PORT); //IP and Port number
			inputServer = new BufferedReader(new InputStreamReader(client.getInputStream())); //read input from server
			outServer = new PrintWriter(client.getOutputStream(), true); //if connections exists, write to server
			System.out.println("Connection successful to SERVER!");
			
			board();
			method();
			
            //client.close();
            
        }catch(Exception e){
            System.err.println("Error connecting to server."); //if can not connect to the server
        }
    }
public static void board () throws IOException {
	do{
        is = inputServer.readLine(); //receives input from server
        System.out.println(is); //displays input from server
//        System.out.print("Enter your move: ");
//        
//        input = keyboard.readLine(); //gathers input from user
	}while(pl1num==0);
	//method();
}

public static void method () throws IOException {
    
	try{
		do{
			System.out.print("Enter yyyour move: ");
	        
	        input = keyboard.readLine(); //gathers input from user
	        outServer.println(input); //send user input to server
	        //is = inputServer.readLine(); //receives input from server
	        //System.out.println(is); //displays input from server
		}while(true); 
        }catch(InputMismatchException e){
            System.out.println("THIS IS NOT A VALID MOVE"); //if letter or special characters are input
            //keyboard.readLine();
            }   
	}
	
}
