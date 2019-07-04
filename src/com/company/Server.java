package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static ServerSocket server;
    public static Socket clientSocket;
    public static BufferedReader in;
    public static BufferedWriter out;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(255);
            System.out.println("Server started");
            clientSocket = server.accept();

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String word = in.readLine();
            System.out.println("Server get message:" + word);
            out.write("Your message:" + word +"\n");
            out.flush();
            System.out.println("Server send the message");

            clientSocket.close();
            in.close();
            out.close();
            System.out.println("Server stopped");
            server.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
