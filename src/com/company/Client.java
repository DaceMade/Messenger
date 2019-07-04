package com.company;

import java.io.*;
import java.net.Socket;

public class Client {

    public static Socket clientSocket;
    public static BufferedReader reader;
    public static BufferedReader in;
    public static BufferedWriter out;

    public static void main(String[] args) {
        try {
            clientSocket = new Socket("localhost",255);

            reader = new BufferedReader(new InputStreamReader(System.in));

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            System.out.println("Enter your message");

            String word = reader.readLine();

            out.write(word + "\n");
            out.flush();
            String serverWord = in.readLine();
            System.out.println(serverWord);

            System.out.println("Client closed");
            clientSocket.close();
            in.close();
            out.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
