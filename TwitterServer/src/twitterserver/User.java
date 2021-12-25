/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterserver;

import java.util.ArrayList;

/**
 *
 * @author trong
 */
public class User {
    private String username;
    private String password;
    private String ipAddress;
    private boolean LoggedInStatus;
    private ArrayList<String> peopleWhoIFollow;
    private ArrayList<String> peopleWhoFollowMe;
   //Constructor
    public User(String username, String password){
    this.username = username;
    this.password = password;
    this.LoggedInStatus = false;
    peopleWhoIFollow = new ArrayList<>();
    peopleWhoFollowMe = new ArrayList<>();
    this.ipAddress = "";
    }
    
    public String returnIPAddress(){
        return this.ipAddress;
    }
    
    //Assign ip address to user
    public void setIPAddress(String ip){
        this.ipAddress = ip;
    }
    
    //Clear ip address when user logged out
    public void clearIPAdress(){
        this.ipAddress = "";
    }
    
    
    //Retrieve peopleWhoIFollow list
    public String getPeopleWhoIFollow(){
        return peopleWhoIFollow.toString().substring(1, peopleWhoIFollow.toString().length()-1 );
    }
    
    //Retrieve peopleWhoFollowMe list
    public String getPeopleWhoFollowMe(){
        return peopleWhoFollowMe.toString().substring(1, peopleWhoFollowMe.toString().length()-1 );
    }
            
    //add the people I follow to peopleWhoIFollow list
    public void addToFollowingList(String username){
        peopleWhoIFollow.add(username);
    }
    
    //check if the user is in the peopleWhoIFollow list
    public boolean checkUserInPeopleWhoIFollow(String username){
        if(peopleWhoIFollow.contains(username)){
            return true;
        }else{
            return false;
        }
    }
    
    //remove user in peopleWhoIFollow
    public void removeUserInPeopleWhoIFollow(String username){
        peopleWhoIFollow.remove(username);
    }
    
    //remove user in peopleWhoFollowMe
    public void removeUserInPeopleWhoFollowMe (String username){
        peopleWhoFollowMe.remove(username);
    }
    
    //add other people to my follower list
    public void addToFollowerList(String username){
        peopleWhoFollowMe.add(username);
    }
    
    //Check if I already followed that user
    public boolean checkAlreadyFollow (String username){
        if(peopleWhoIFollow.contains(username)){
            return true;
        }else{
            return false;
        }
    }
    
    public void changeToOffline(){
        LoggedInStatus = false;
    }
    
    public void changeToOnline(){
        LoggedInStatus = true;
    }
    
    public boolean checkPassword(String password){
        if(password.equals(this.password)){
            return true;
        }else{
            return false;
        }
    }
}
