/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package twitterserver;

/**
 *
 * @author trong
 */
public class Message {
    private String sender;
    private String message;
    private String hashtag;
    
    
    public Message (String sender, String message, String hashtag){
        this.sender = sender;
        this.message = message;
        this.hashtag = hashtag;
    }
    
    public String constructMessage (){
        return this.sender + ": " + this.message + " #" + this.hashtag;
    }
}
