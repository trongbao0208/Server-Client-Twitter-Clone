/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author trong
 */
public class Server {
    private UserList userList;
    static int ADD_PORT_CLIENT = 2003;
    static Socket sockClient;
    static Scanner inClient;
    static PrintWriter outClient;
    public Server(){
        userList = new UserList();
    }
    
    public String numberOfMessage(String username){
        return userList.numberOfMessage(username);
    }
    
    public int getNumberOfUnreadMessages(String username){
        return userList.getNumberOfUnreadMessages(username);
    }
    
    public boolean checkUserExist(String username){
        return userList.checkUserExist(username);
    }
    
    public String getIPAddress(String username){
        return userList.getIPAddress(username);
    }
    
    public String[] searchHashtag(String hashtag){
        return userList.searchHashtag(hashtag);
    }
    
    public String[] retrieveMessages(String username){
        return userList.retrieveMessages(username);
    }
    
    public String sendMessage(String username, String message, String hashtag){
        if(userList.sendMessage(username, message, hashtag)){
            return "You have successfully sent a message";
        }else{
            return "Error: Couldn't send the message. Either you are not logged in or your account is invalid";
        }
    }
    
    
    public String unfollow(String myUsername, String otherUsername){
        if(userList.unfollow(myUsername, otherUsername)){
            return "Succeeded";
        }else{
            return "Failed";
        }
    }
    
    public String retrievePeopleWhoIFollow(String username){
        return userList.retrievePeopleWhoIFollow(username);
    }
    
    public String retrievePeopleWhoFollowMe (String username){
        return userList.retrievePeopleWhoFollowMe(username);
    }
    
    public String follow(String myUsername, String otherUsername){
        if(userList.follow(myUsername, otherUsername)){
            return "Succeeded";
        }else{
            return "Failed"; 
        }
    }
    
    public String registerUser(String username, String password){
        if (userList.registerUser(username, password)){
            return "Congratulation, the account for " + username + " has been successfully created." ;
        }else{
            return "Sorry, the " + username + " username has been taken. Please try again.";
        }
    }
    
    public String logIn(String username, String password, String ip){
        if(userList.logIn(username, password, ip)){
            return "Succeeded";
        }else{
            return "Failed";
        }
    }
    
    public String logOff(String username){
        if(userList.logOff(username)){
            return "Succeeded";
        }else{
            return "Failed";
        }
    }
    
    public static void main(String[] args){
        Server myServer = new Server();
        myServer.registerUser("trong1", "");
        myServer.registerUser("trong2", "");
        myServer.registerUser("trong3", "");
        
       // myServer.logIn("trong1", "password");
       // myServer.logIn("trong2", "password");
       
     
        try{
            //Create server socket
            ServerSocket listen = new ServerSocket(2001);
            System.out.println("Running");
            String result = "";
            while (true){
                Socket client = listen.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                Scanner in = new Scanner(client.getInputStream());
                String line = in.nextLine();
                
                //Server register account
                if (line.equals("Register")){
                    String username = in.nextLine();
                    String password = in.nextLine();
                    result = myServer.registerUser(username, password);
                    out.println(result);
                } else if(line.equals("Log in")){
                    String username = in.nextLine();
                    String password = in.nextLine();
                    String ip = client.getInetAddress().getHostAddress();
                    result = myServer.logIn(username, password, ip);
                    out.println(result);
                    
                } else if(line.equals("Log off")){
                    String username = in.nextLine();
                    result = myServer.logOff(username);
                    out.println(result);
                } else if(line.equals("Follow")){
                    String myUsername = in.nextLine();
                    String otherUsername = in.nextLine();
                    result = myServer.follow(myUsername, otherUsername);
                    out.println(result);
                    
                    //Notify otherUsername that myUsername just follow.
             
                    if(result.equals("Succeeded") && !myServer.getIPAddress(otherUsername).isEmpty()){
                        String otherUsernameIPAddress = myServer.getIPAddress(otherUsername);
                        try{
                            sockClient = new Socket(otherUsernameIPAddress, ADD_PORT_CLIENT);
                            inClient = new Scanner(sockClient.getInputStream());
                            outClient = new PrintWriter(sockClient.getOutputStream(),true);
                            outClient.println("Notify follow");
                            outClient.println(myUsername);
                            String notifyStatus = inClient.nextLine();
                            System.out.println(notifyStatus);
                            inClient.close();
                            sockClient.close();
                        }
                        catch ( UnknownHostException e ) {
                            System.err.println( "DayTimeClient:  no such host" );
                        }
                        catch ( IOException e ) {
                            System.err.println("IOEXCEPTION");
                            System.err.println( e.getMessage() );
                        }
                    }
                } else if(line.equals("Get peopleWhoIFollow list")){
                    String username = in.nextLine();
                    result = myServer.retrievePeopleWhoIFollow(username);
                    out.println(result);
                    
                } else if(line.equals("Unfollow")){
                    String myUsername = in.nextLine();
                    String otherUsername = in.nextLine();
                    result = myServer.unfollow(myUsername, otherUsername);
                    out.println(result);
                    
                    //Notify otherUsername that myUsername just unfollow
                    if(result.equals("Succeeded") && !myServer.getIPAddress(otherUsername).isEmpty()){
                        String otherUsernameIPAddress = myServer.getIPAddress(otherUsername);
                        try{
                            sockClient = new Socket(otherUsernameIPAddress, ADD_PORT_CLIENT);
                            inClient = new Scanner(sockClient.getInputStream());
                            outClient = new PrintWriter(sockClient.getOutputStream(),true);
                            outClient.println("Notify unfollow");
                            outClient.println(myUsername);
                            String notifyStatus = inClient.nextLine();
                            System.out.println(notifyStatus);
                            inClient.close();
                            sockClient.close();
                        }
                        catch ( UnknownHostException e ) {
                            System.err.println( "DayTimeClient:  no such host" );
                        }
                        catch ( IOException e ) {
                            System.err.println("IOEXCEPTION");
                            System.err.println( e.getMessage() );
                        }
                    }
                } else if (line.equals("Get peopleWhoFollowMe list")){
                    String username = in.nextLine();
                    result = myServer.retrievePeopleWhoFollowMe(username);
                    out.println(result);
                } else if (line.equals("Send message")){
                    String myUsername = in.nextLine();
                    String message = in.nextLine();
                    String hashtag = in.nextLine();
                    result = myServer.sendMessage(myUsername, message, hashtag);
                    out.println(result);
                    if(result.equals("You have successfully sent a message")){
                        String peopleWhoFollowMe = myServer.retrievePeopleWhoFollowMe(myUsername);
                        String[] arrayPeopleWhoFollowMe = peopleWhoFollowMe.split(", ");
                        for(int i = 0; i < arrayPeopleWhoFollowMe.length; i++){
                            String personIPAddress = myServer.getIPAddress(arrayPeopleWhoFollowMe[i]);
                            if(personIPAddress.isEmpty() == false){
                                try{
                                    sockClient = new Socket(personIPAddress, ADD_PORT_CLIENT);
                                    inClient = new Scanner(sockClient.getInputStream());
                                    outClient = new PrintWriter(sockClient.getOutputStream(),true);
                                    outClient.println("Notify new message");
                                    if(myServer.numberOfMessage(arrayPeopleWhoFollowMe[i]) == null){
                                        outClient.println("0");
                                    }else{
                                        outClient.println(myServer.numberOfMessage(arrayPeopleWhoFollowMe[i]));
                                    }
                                    inClient.close();
                                    sockClient.close();
                                }
                                catch ( UnknownHostException e ) {
                                    System.err.println( "DayTimeClient:  no such host" );
                                }
                                catch ( IOException e ) {
                                    System.err.println("IOEXCEPTION");
                                    System.err.println( e.getMessage() );
                                }
                            }
                        }
                    }
                } else if (line.equals("Retrieve messages")){
                    String myUsername = in.nextLine();
                    String[] resultArray = myServer.retrieveMessages(myUsername);
                    if(resultArray == null){
                        out.println("You have no new messages");
                    }else{
                        out.println("Have new messages");
                        out.println(resultArray.length);
                        for (int i = 0; i < resultArray.length; i++){
                            out.println(resultArray[i]);
                        }
                    }
                } else if (line.equals("Search hashtag")){
                    String hashtag = in.nextLine();
                    String[] resultArray = myServer.searchHashtag(hashtag);
                    if(resultArray == null){
                        out.println("There is no message with this hashtag");
                    }else{
                        out.println("Messages are");
                        out.println(resultArray.length);
                        for (int i = 0; i < resultArray.length; i++){
                            out.println(resultArray[i]);
                        }
                    }
                }else if (line.equals("Get IP address")){
                    String otherUsername = in.nextLine();
                    boolean userExistStatus = myServer.checkUserExist(otherUsername);
                    if (userExistStatus == true){
                        String otherIPAddress = myServer.getIPAddress(otherUsername);
                        out.println(otherIPAddress);
                    }else{
                        out.println("The user does not exist");
                    }
                }else if(line.equals("Show number of unread messages")){
                    String username = in.nextLine();
                    String numberOfUnread = Integer.toString(myServer.getNumberOfUnreadMessages(username));
                    out.println(numberOfUnread);
                }
                
                out.close();
		client.close();
            }
        }catch (IOException e){
            System.err.println( e.getMessage());
        }
        
    }
    
}
