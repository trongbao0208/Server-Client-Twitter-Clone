/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterserver;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
/**
 *
 * @author trong
 */
public class UserList {
    private HashMap <String, User> theUsers;
    private ArrayList<String> currentLoggedInList;
    private HashMap<String, ArrayList<Message>> messagesByHashtag;
    private HashMap<String, ArrayList<Message>> messagesByUser;
    //Constructor
    public UserList(){
        theUsers = new HashMap<>();
        currentLoggedInList = new ArrayList<>();
        messagesByHashtag = new HashMap<String, ArrayList<Message>>();
        messagesByUser = new HashMap<String, ArrayList<Message>>();
    }
    //Get number of unread messages
    public int getNumberOfUnreadMessages(String username){
        if(messagesByUser.get(username) == null){
             return 0;
        }else{
            return messagesByUser.get(username).size();
        }
    }
    
    
    //Check if the user is in the list
    public boolean checkUserExist(String username){
        return theUsers.containsKey(username);
    }
    
    //Get IP Address of a user by username;
    public String getIPAddress (String username){
        User u = theUsers.get(username);
        return u.returnIPAddress();
    }
    
    
    //Assign an ip address to that user when logged in
    public void assignIPAddress(String username, String ipAddress){
        User currentUser = theUsers.get(username);
        currentUser.setIPAddress(ipAddress);
    }
    
    //Clear IP address of user when logged out;
    public void clearIPAddress(String username){
        User currentUser = theUsers.get(username);
        currentUser.clearIPAdress();
    }
    
    //Send a message
    public boolean sendMessage(String username, String message, String hashtag){
        //Check if the user is online or the user is in the userList
        if(currentLoggedInList.contains(username) == false || theUsers.containsKey(username) == false){
            return false;
        }else{
            //Add the message to hashtag list
            if(messagesByHashtag.containsKey(hashtag) == false){
                messagesByHashtag.put(hashtag, new ArrayList<Message>());
                Message sentMessage = new Message(username, message, hashtag);
                messagesByHashtag.get(hashtag).add(sentMessage);
            } else{
                Message sentMessage = new Message(username, message, hashtag);
                messagesByHashtag.get(hashtag).add(sentMessage);
            }
            //Add the message to messageByUser list
            User me = theUsers.get(username);
            String peopleWhoFollowMe = me.getPeopleWhoFollowMe();
            String[] arrayPeopleWhoFollowMe = peopleWhoFollowMe.split(", ");
            for (String user : arrayPeopleWhoFollowMe){
                if(messagesByUser.containsKey(user) == false){
                    messagesByUser.put(user, new ArrayList<Message>());
                    Message sentMessage = new Message(username, message, hashtag);
                    messagesByUser.get(user).add(sentMessage);
                }else{
                    Message sentMessage = new Message(username, message, hashtag);
                    messagesByUser.get(user).add(sentMessage);
                }
            }
            return true;
        }
    }
    
    //Search messages by hashtag
    public String[] searchHashtag(String hashtag){
        //Return null if there is no hashtag that match
        if (messagesByHashtag.containsKey(hashtag) == false){
            return null;
        }else{
            ArrayList<String> hashtagArrayList = new ArrayList<>();
            for(Message message: messagesByHashtag.get(hashtag)){
                hashtagArrayList.add(message.constructMessage());
            }
            return hashtagArrayList.toArray(String[]::new);
        }
    }
    
    //Number of Message
    public String numberOfMessage(String username){
        if (messagesByUser.containsKey(username) == false){
            return null;
        }else{
            return Integer.toString(messagesByUser.get(username).size());
        }
    }
    //Retrieve messages by user
    public String[] retrieveMessages(String username){
        if (messagesByUser.containsKey(username) == false){
            return null;
        }else{
            ArrayList<String> userArrayList = new ArrayList<>();
            for(Message message: messagesByUser.get(username)){
                userArrayList.add(message.constructMessage());
            }
            String[] result = userArrayList.toArray(String[]::new);
            messagesByUser.remove(username);
            return result;
        }
    }
    
    
    
    
    //Follow
    public boolean follow(String myUsername, String otherUsername){
        if (!theUsers.containsKey(otherUsername)){
            return false;
        }else {
            User me = theUsers.get(myUsername);
            if(me.checkAlreadyFollow(otherUsername)){
                return false;
            }else{
                me.addToFollowingList(otherUsername);
                User other = theUsers.get(otherUsername);
                other.addToFollowerList(myUsername);
                return true;
            }
        }
    }
    
    //Unfollow
    public boolean unfollow(String myUsername, String otherUsername){
        User me = theUsers.get(myUsername);
        if (me.checkUserInPeopleWhoIFollow(otherUsername)){
            User other = theUsers.get(otherUsername);
            me.removeUserInPeopleWhoIFollow(otherUsername);
            other.removeUserInPeopleWhoFollowMe(myUsername);
            return true;
        }else{
            return false;
        }
    }
    //Retrieve peopleWhoIFollowList
    public String retrievePeopleWhoIFollow(String username){
        User me = theUsers.get(username);
        return me.getPeopleWhoIFollow();
    }
    
    //Retrieve peopleWhoFollowMe List
    public String retrievePeopleWhoFollowMe (String username){
        User me = theUsers.get(username);
        return me.getPeopleWhoFollowMe();
    }
  
    
    //registerUser method
    public boolean registerUser(String username, String password){
        if (theUsers.containsKey(username)){
            return false;
        }else{
            User newUser = new User(username, password);
            theUsers.put(username, newUser);
            return true;
        }
        
    }
    
    //Log off method
    public boolean logOff(String username){
        //Check if the user is in the list of users
        if(!theUsers.containsKey(username)){
            return false;
        //Check if the user is offline
        }else if (!currentLoggedInList.contains(username)){
            return false;
        }
        else{
            User u = theUsers.get(username);
            u.changeToOffline();
            u.clearIPAdress();
            currentLoggedInList.remove(username);
            
            return true;
        }
    }
    
    //Login method
    public boolean logIn(String username, String password, String ip){
        if(currentLoggedInList.contains(username)){
            return false;
        }else{
            //Check if the username is in the list of users
            if(!theUsers.containsKey(username)){
                return false;
            }
            User u = theUsers.get(username);
            //Check if the password match
            if(!u.checkPassword(password)){
                return false;
            }
            u.changeToOnline();
            currentLoggedInList.add(username);
            u.setIPAddress(ip);
            return true;
        }
    }
   
}
