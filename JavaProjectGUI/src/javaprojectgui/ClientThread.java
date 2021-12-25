/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaprojectgui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author trong
 */
public class ClientThread implements Runnable{
    private boolean threadRunning;
    MainPageJframe jframe;
    
    public ClientThread(MainPageJframe jframe){
        this.threadRunning = true;
        this.jframe = jframe;
    }
    
    @Override
    public void run() {
        try{
            ServerSocket listen = new ServerSocket(2003);
            System.out.println("NotifyFollowUnfollowThread is running");
            String result = "";
            while (this.threadRunning == true){
                Socket client = listen.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                Scanner in = new Scanner(client.getInputStream());
                String line = in.nextLine();
                
                if(line.equals("Notify follow")){
                    String follower = in.nextLine();
                    out.println("Notify successfully");
                    jframe.setLabel(follower + " followed you.");
                    jframe.updateListModelWhoFollowMe(follower);
                    JOptionPane.showMessageDialog(new javax.swing.JFrame(),follower + " followed you." ,"Follow Notification", JOptionPane.INFORMATION_MESSAGE);
                }else if(line.equals("Notify unfollow")){
                    String follower = in.nextLine();
                    out.println("Notify successfully");
                    jframe.setLabel(follower + " unfollowed you.");
                    jframe.removeElementFromListModelWhoFollowMe(follower);
                    JOptionPane.showMessageDialog(new javax.swing.JFrame(),follower + " unfollowed you." ,"Unfollow Notification", JOptionPane.INFORMATION_MESSAGE);
                }else if (line.equals("Send private message")){
                    String message = in.nextLine();
                    jframe.setjTextAreaPrivateMessage(message);
                    out.println("Message sent successfully");
                }else if (line.equals("Stop thread")){
                    this.threadRunning = false;
                    System.out.println("Thread stopped");
                }else if (line.equals("Notify new message")){
                    result = in.nextLine();
                    jframe.setjLabelMessageNotification(Integer.toString(Integer.parseInt(result)));
                }
                out.close();
		client.close();
            }
        }catch(IOException e){
            System.out.println("Coudn't stop the thread");
            System.err.println( e.getMessage());
        }
    }
    
}
