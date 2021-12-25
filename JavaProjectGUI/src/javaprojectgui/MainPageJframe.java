/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectgui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import static javaprojectgui.RegisterJDialog.ADD_PORT;
import static javaprojectgui.RegisterJDialog.host;
import static javaprojectgui.RegisterJDialog.in;
import static javaprojectgui.RegisterJDialog.out;
import static javaprojectgui.RegisterJDialog.sock;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;


/**
 *
 * @author trong
 */

public class MainPageJframe extends javax.swing.JFrame {

    /**
     * Creates new form MainPageJframe
     */
   
    
    //Logged in messages
    private ArrayList<String> loggedInMessages;
    
    //List Model for peopleWhoIFollow List
    private DefaultListModel<String> listModel;
    
    //List Model for peopleWhoFollowMe List
    private DefaultListModel<String> listModelWhoFollowMe;
    
    private LoginJdialog loginJdialog;
    
    //Set variable for current username
    public String username;
    
    //Set variable for user to unfollow
   
    static int ADD_PORT = 2001;
    public static String host = "10.21.53.113";
    static Socket sock;
    static Scanner in;
    static PrintWriter out;
    public MainPageJframe() {
        initComponents();
        
        //Create loginJdialog before showing the mainframe
        loginJdialog = new LoginJdialog(this, true);
        loginJdialog.setVisible(true);
        jButtonViewSent.setText("<html>View sent messages <br>since logged in </html>");
        //Assign the username to the username that logged in
        username = loginJdialog.getUsername();
        
        //Create logged in messages
        loggedInMessages = new ArrayList<>();
        
        //
        jTextAreaMessages.setEditable(false);
        //Create DefaultListModel for jList
        listModel = new DefaultListModel<>();
        listModelWhoFollowMe = new DefaultListModel<>();
        
        //Set jList to listModel
        jListFollowing.setModel(listModel);
        jListFollower.setModel(listModelWhoFollowMe);
        
        //Disable unfollow button when no value is selected
        jButtonUnfollow.setEnabled(false);
        
        //Set jLabelFollowNotification to empty
        jLabelFollowNotification.setText("");
        
        //Log off on exit
        this.addWindowListener(new WindowHandler());
        
        //show number of unread messages at start
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Show number of unread messages");
            out.println(this.username);
            String result = in.nextLine();
            jLabelMessageNotification.setText("You have " + result + " unread messages");
            
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
       
        //Show following list at start
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            
            //Get peopleWhoIFollow list
            out.println("Get peopleWhoIFollow list");
            out.println(this.username);
            String result = in.nextLine();
            String[] arrayResult = result.split(", ");
            for(String user : arrayResult){
                listModel.addElement(user);
            }
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
        
        //Showing Follower list at start
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            
            //Get peopleWhoFollowMe list
            out.println("Get peopleWhoFollowMe list");
            out.println(this.username);
            String result2 = in.nextLine();
            String[] arrayResult2 = result2.split(", ");
            for(String user : arrayResult2){
                listModelWhoFollowMe.addElement(user);
            }
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
        this.jLabel4.setText("Welcome " + this.username);
        jListFollowing.addMouseListener(new ListMouseListener());
        
         //Create thread to listen follow notification
     
         
         
    }
    //set jLabelMessageNotification
    public void setjLabelMessageNotification(String number){
        jLabelMessageNotification.setText("You have " + number + " new messages.");
        jLabelMessageNotification.setForeground(Color.BLUE);
    }
   
    
    //Set jTextAreaPrivateMessage
    public void setjTextAreaPrivateMessage(String message){
        jTextAreaPrivateMessage.append(message + "\n");
    }
    
    
    //remove element from listModelWhoFollowMe when someone unfollow you
    public void removeElementFromListModelWhoFollowMe (String follower){
        listModelWhoFollowMe.removeElement(follower);
    }
    
    //update listModelWhoFollowMe
    public void updateListModelWhoFollowMe (String newFollower){
        listModelWhoFollowMe.addElement(newFollower);
    }
    
    //set follow notification label
    public void setLabel(String text) {
        jLabelFollowNotification.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonLogOff = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButtonViewUnread = new javax.swing.JButton();
        jButtonViewSent = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldHashtag = new javax.swing.JTextField();
        jButtonSearchHashtag = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaMessages = new javax.swing.JTextArea();
        jLabelMessageNotification = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldPrivateMessage = new javax.swing.JTextField();
        jButtonSendPrivateMessage = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaPrivateMessage = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldPrivateUser = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldMessageSend = new javax.swing.JTextField();
        jTextFieldHashtagSend = new javax.swing.JTextField();
        jButtonSendMessage = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListFollowing = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jButtonFollow = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListFollower = new javax.swing.JList<>();
        jButtonUnfollow = new javax.swing.JButton();
        jLabelFollowNotification = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 800));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));

        jLabel4.setBackground(new java.awt.Color(0, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel4.setText("Welcome username");
        jLabel4.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(506, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(476, 476, 476))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButtonLogOff.setText("Log off");
        jButtonLogOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogOffActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonViewUnread.setText("View unread messages");
        jButtonViewUnread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewUnreadActionPerformed(evt);
            }
        });

        jButtonViewSent.setText("View sent messages since logged in");
        jButtonViewSent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewSentActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonSearchHashtag.setText("Search");
        jButtonSearchHashtag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchHashtagActionPerformed(evt);
            }
        });

        jLabel6.setText("Search message by hashtag:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldHashtag, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSearchHashtag, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldHashtag, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearchHashtag)
                .addContainerGap())
        );

        jTextAreaMessages.setColumns(20);
        jTextAreaMessages.setRows(5);
        jScrollPane5.setViewportView(jTextAreaMessages);

        jLabelMessageNotification.setText("Message notification");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonViewSent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonViewUnread, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMessageNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMessageNotification)
                    .addComponent(jButtonViewUnread))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButtonViewSent, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Send message:");

        jButtonSendPrivateMessage.setText("Send");
        jButtonSendPrivateMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendPrivateMessageActionPerformed(evt);
            }
        });

        jLabel10.setText("Private messages");

        jTextAreaPrivateMessage.setColumns(20);
        jTextAreaPrivateMessage.setRows(5);
        jScrollPane4.setViewportView(jTextAreaPrivateMessage);

        jLabel11.setText("To");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonSendPrivateMessage)
                .addGap(241, 241, 241))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPrivateMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPrivateUser)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPrivateMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldPrivateUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSendPrivateMessage)
                .addGap(42, 42, 42))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("What's on your mind?");

        jLabel2.setText("HashTag:");

        jTextFieldHashtagSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHashtagSendActionPerformed(evt);
            }
        });

        jButtonSendMessage.setText("Send Message");
        jButtonSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldHashtagSend, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addComponent(jTextFieldMessageSend))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSendMessage)
                .addGap(198, 198, 198))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMessageSend, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldHashtagSend, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSendMessage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(533, 533, 533))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(jButtonLogOff)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLogOff)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 204, 204));

        jScrollPane2.setViewportView(jListFollowing);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Follower");

        jButtonFollow.setText("Follow");
        jButtonFollow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFollowActionPerformed(evt);
            }
        });

        jLabel5.setText("Type in a username to follow");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Following");

        jListFollower.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListFollower);

        jButtonUnfollow.setText("Unfollow");
        jButtonUnfollow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnfollowActionPerformed(evt);
            }
        });

        jLabelFollowNotification.setText("follow notification");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jButtonUnfollow)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFollowNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonFollow))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonUnfollow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFollow, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFollowNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFollowActionPerformed
        // TODO add your handling code here:
        if(this.username.equals(jTextField3.getText())){
            JOptionPane.showMessageDialog(new javax.swing.JFrame(), "You can't follow yourself." ,"Failed to follow", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Follow");
            out.println(this.username);
            out.println(jTextField3.getText());
            String result = in.nextLine();
            if (result.equals("Failed")){
                jTextField3.setText("");
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Failed to follow" +jTextField3.getText() +" because the user does not exist or you have already followed this user." ,"Failed to follow", JOptionPane.WARNING_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Successfully followed " + jTextField3.getText()+ ".", "Follow successfully", JOptionPane.PLAIN_MESSAGE);
                listModel.addElement(jTextField3.getText());
                jTextField3.setText("");
            }
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
    }//GEN-LAST:event_jButtonFollowActionPerformed

    private void jButtonUnfollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnfollowActionPerformed
        // TODO add your handling code here:
        int index = jListFollowing.getSelectedIndex();
        String userToUnfollow = (String)listModel.get(index);
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream());
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Unfollow");
            out.println(this.username);
            out.println(userToUnfollow);
            String result = in.nextLine();
            if(result.equals("Failed")){
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Failed to unfollow because you didn't follow this user","Failed to unfollow", JOptionPane.WARNING_MESSAGE);
            }else{
                listModel.remove(index);
                if (jListFollowing.getSelectedIndex() == -1){
                    jButtonUnfollow.setEnabled(false);
                }else{
                    jButtonUnfollow.setEnabled(true);
                }
            }
            
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
    }//GEN-LAST:event_jButtonUnfollowActionPerformed

    private void jButtonSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendMessageActionPerformed
        // TODO add your handling code here:
        if(jTextFieldMessageSend.getText().equals("")){
            JOptionPane.showMessageDialog(new javax.swing.JFrame(), "You can't send a blank message","Failed to send message", JOptionPane.WARNING_MESSAGE);
            return;
        }
        else if(jTextFieldHashtagSend.getText().contains(" ")){
            JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Hashtag cannot contain whitespace","Failed to send message", JOptionPane.WARNING_MESSAGE);
            return;
        }else{

            String text = jTextFieldMessageSend.getText();
            String hashtag = jTextFieldHashtagSend.getText();

            //Add to current logged in messages
            this.loggedInMessages.add(text + " #" + hashtag);

            try{
                sock = new Socket( host, ADD_PORT );
                // Create the IO streams from the socket
                in = new Scanner( sock.getInputStream());
                out = new PrintWriter( sock.getOutputStream(), true );
                out.println("Send message");
                out.println(this.username);
                out.println(text);
                out.println(hashtag);
                String result = in.nextLine();
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), result, "Sent message status", JOptionPane.PLAIN_MESSAGE);

                jTextFieldMessageSend.setText("");
                jTextFieldHashtagSend.setText("");
                in.close();
                sock.close();
            }
            catch ( UnknownHostException e ) {
                System.err.println( "DayTimeClient:  no such host" );
            }
            catch ( IOException e ) {
                System.err.println("IOEXCEPTION");
                System.err.println( e.getMessage() );
            }
        }

    }//GEN-LAST:event_jButtonSendMessageActionPerformed

    private void jTextFieldHashtagSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHashtagSendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHashtagSendActionPerformed

    private void jButtonSendPrivateMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendPrivateMessageActionPerformed
        // TODO add your handling code here:
        if(jTextFieldPrivateMessage.getText().isEmpty() || jTextFieldPrivateUser.getText().isEmpty()){
            JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Message or user fields can't be blank","Send private message failed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            sock = new Socket(host, ADD_PORT);
            // Create the IO streams from the socket
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream(), true );
            out.println("Get IP address");
            out.println(jTextFieldPrivateUser.getText());
            String result = in.nextLine();

            if (result.equals("The user does not exist")){
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Can't send a private message because the user does not exist","Send private message failed", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (result.isEmpty()){
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Can't send a private message because the user is not online","Send private message failed", JOptionPane.INFORMATION_MESSAGE);
            }else{
                in.close();
                sock.close();
                try{
                    sock = new Socket(result, 2003);
                    // Create the IO streams from the socket
                    in = new Scanner(sock.getInputStream());
                    out = new PrintWriter(sock.getOutputStream(), true );
                    out.println("Send private message");
                    String message = this.username + ": " + jTextFieldPrivateMessage.getText() + "\n";
                    out.println(message);
                    String status = in.nextLine();
                    this.setjTextAreaPrivateMessage("Private message sent successfully to " + jTextFieldPrivateUser.getText() + ": " + jTextFieldPrivateMessage.getText());
                }
                catch ( UnknownHostException e ) {
                    System.err.println( "DayTimeClient:  no such host" );
                }
                catch ( IOException e ) {
                    System.err.println("IOEXCEPTION");
                    System.err.println( e.getMessage() );
                }
            }

            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
        jTextFieldPrivateMessage.setText("");
        jTextFieldPrivateUser.setText("");

    }//GEN-LAST:event_jButtonSendPrivateMessageActionPerformed

    private void jButtonSearchHashtagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchHashtagActionPerformed
        // TODO add your handling code here:
        String hashtag = jTextFieldHashtag.getText();
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream());
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Search hashtag");
            out.println(hashtag);
            String result = in.nextLine();
            if(result.equals("There is no message with this hashtag")){
                jTextAreaMessages.setText(result);
            }else{
                String arrayLength = in.nextLine();
                String messages = "";
                for (int i = 0; i < Integer.parseInt(arrayLength); i++){
                    messages += in.nextLine() + "\n";
                }
                jTextAreaMessages.setText(messages);
            }
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }
    }//GEN-LAST:event_jButtonSearchHashtagActionPerformed

    private void jButtonViewSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewSentActionPerformed
        // TODO add your handling code here:
        if (loggedInMessages.isEmpty()){
            jTextAreaMessages.setText("You have not sent any messages since logged in.");
        }else{
            String allMessages = "";
            for(String message : loggedInMessages){
                allMessages += message +"\n";
            }
            jTextAreaMessages.setText(allMessages);
        }

    }//GEN-LAST:event_jButtonViewSentActionPerformed

    private void jButtonViewUnreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewUnreadActionPerformed
        // TODO add your handling code here:
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream());
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Retrieve messages");
            out.println(this.username);
            String result = in.nextLine();
            if(result.equals("You have no new messages")){
                jTextAreaMessages.setText(result);
            }else{
                String arrayLength = in.nextLine();
                String messages = "";
                for (int i = 0; i < Integer.parseInt(arrayLength); i++){
                    messages += in.nextLine() + "\n";
                }
                jTextAreaMessages.setText(messages);
                jLabelMessageNotification.setText("You have no unread message");
            }
            in.close();
            sock.close();
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }

    }//GEN-LAST:event_jButtonViewUnreadActionPerformed

    private void jButtonLogOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogOffActionPerformed
        // TODO add your handling code here:
        try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Log off");
            out.println(this.username);
            String result = in.nextLine();
            in.close();
            sock.close();
            if(result.equals("Failed")){
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Logoff failed","Logoff failed", JOptionPane.WARNING_MESSAGE);
            }else{
                //Connect to the thread to stop it
                try{
                    Socket sockClient = new Socket(InetAddress.getLocalHost(), 2003 );
//                    // Create the IO streams from the socket
                    Scanner inClient = new Scanner( sockClient.getInputStream() );
                    PrintWriter outClient = new PrintWriter( sockClient.getOutputStream(), true );
                    outClient.println("Stop thread");
                    inClient.close();
                    sockClient.close();

                    //Dispose the current Jframe class
                    
                }
                catch (UnknownHostException e ) {
                    System.err.println( "DayTimeClient:  no such host" );
                }
                catch ( IOException e ) {
                    System.err.println("IOEXCEPTION");
                    System.err.println( e.getMessage() );
                }

            }
            this.dispose();
            //Create a new Jframe class
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            MainPageJframe frame = new MainPageJframe();
                            frame.setVisible(true);
                            ClientThread thread = new ClientThread(frame);
                            Thread clientThread = new Thread(thread);
                            clientThread.start();
                        }
                    });
            
        }
        catch ( UnknownHostException e ) {
            System.err.println( "DayTimeClient:  no such host" );
        }
        catch ( IOException e ) {
            System.err.println("IOEXCEPTION");
            System.err.println( e.getMessage() );
        }

    }//GEN-LAST:event_jButtonLogOffActionPerformed
   //Log the user out when selecting the close icon
    private class WindowHandler extends WindowAdapter{
        public void windowClosing(java.awt.event.WindowEvent a) {
            try{
            sock = new Socket( host, ADD_PORT );
            // Create the IO streams from the socket
            in = new Scanner( sock.getInputStream() );
            out = new PrintWriter( sock.getOutputStream(), true );
            out.println("Log off");
            out.println(username);
            String result = in.nextLine();
            in.close();
            sock.close();
            if(result.equals("Failed")){
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Logoff failed","Logoff failed", JOptionPane.WARNING_MESSAGE);
                
            }else{
           
                //Connect to the thread to stop it
                try{
                    Socket sockClient = new Socket(InetAddress.getLocalHost(), 2003 );
                    // Create the IO streams from the socket
                    Scanner inClient = new Scanner( sockClient.getInputStream() );
                    PrintWriter out = new PrintWriter( sockClient.getOutputStream(), true );
                    out.println("Stop thread");
                    inClient.close();
                    sockClient.close();
                }
                catch (UnknownHostException e ) {
                    System.err.println( "DayTimeClient:  no such host" );
                }
                catch ( IOException e ) {
                    System.err.println("IOEXCEPTION");
                    System.err.println( e.getMessage() );
                }
                
            }
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
    
    
    
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPageJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPageJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPageJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPageJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainPageJframe frame = new MainPageJframe();
                frame.setVisible(true);
                ClientThread thread = new ClientThread(frame);
                Thread clientThread = new Thread(thread);
                clientThread.start();
            }
        });
    }
    
    // List mouse listener
    private class ListMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            if (listModel.isEmpty()){
                jButtonFollow.setEnabled(false);
                return;
            }
            if (e.getClickCount() == 1){
                int index = jListFollowing.getSelectedIndex();
                String userToUnfollow = (String)listModel.get(index);
                jButtonUnfollow.setEnabled(true);
            }
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFollow;
    private javax.swing.JButton jButtonLogOff;
    private javax.swing.JButton jButtonSearchHashtag;
    private javax.swing.JButton jButtonSendMessage;
    private javax.swing.JButton jButtonSendPrivateMessage;
    private javax.swing.JButton jButtonUnfollow;
    private javax.swing.JButton jButtonViewSent;
    private javax.swing.JButton jButtonViewUnread;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFollowNotification;
    private javax.swing.JLabel jLabelMessageNotification;
    private javax.swing.JList<String> jListFollower;
    private javax.swing.JList<String> jListFollowing;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextAreaMessages;
    private javax.swing.JTextArea jTextAreaPrivateMessage;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextFieldHashtag;
    private javax.swing.JTextField jTextFieldHashtagSend;
    private javax.swing.JTextField jTextFieldMessageSend;
    private javax.swing.JTextField jTextFieldPrivateMessage;
    private javax.swing.JTextField jTextFieldPrivateUser;
    // End of variables declaration//GEN-END:variables
}
