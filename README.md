# ChatApplication

# Description

Welcome to the Chatroom Project, a real time chat application that enables users to communicate in group chats
designed to bring people together in real-time conversations. This chatroom application is built to provide users
with a seamless and engaging communication experience.

## Features
Server-Client Architecture: Central server manages all client connections  
Multi-user Chat: Multiple clients can connect and chat simultaneously  
User Authentication: Username/password system with database storage  
GUI Interface: Swing-based graphical interface for easy use  
Real-time Messaging: Instant message broadcasting to all connected clients  

## Prerequisites
Java JDK 8 or later  
MySQL database server  
MySQL Connector/J (for database connectivity)  

## Database Setup
1.Create a MySQL database named chat  

2.Create a table with:  

CREATE TABLE chat_history (  
id INT AUTO_INCREMENT PRIMARY KEY,  
username VARCHAR(255) NOT NULL,  
password VARCHAR(255) NOT NULL  
);  
3.Update the database credentials in DatabaseHandler.java  


## How It Works
1.Server starts and listens for client connections  
2.Client connects and provides username/password  
3.Server checks credentials against database  
4.If valid, client joins chat room  
5.Messages are broadcast to all connected clients  
6.Clients can exit by typing "exit"  

## Limitations
No message history persistence (only live chat)  
No encryption for messages or passwords  
Basic error handling  

## Future Enhancements
Add private messaging between users  
Implement message history  
Add user roles and admin features  
Improve security with password hashing  

## Team Members
Contributions:  
  Nishu  
    ChatServer.java  
    ChatClient.java  
    ClientHandler.java  
  Bharti  
    ChatClientGUI.java  
    DatabaseHandler.java  