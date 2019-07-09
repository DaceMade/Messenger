package com.company;

import java.io.*;
import java.net.Socket;

class ClientConnect {

    private Socket socket;
    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;
    private String userName;
    private int port;
    private String address;

    ClientConnect(String address, int port) {
        this.address = address;
        this.port = port;

        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Incorrect socket");
        }

        reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.getNickname();
            new ReadMsg().start();
            new WriteMsg().start();

        } catch (IOException e) {
            ClientConnect.this.downService();
            e.printStackTrace();

        }


    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String word;
            while (true) {
                try {
                    word = in.readLine();
                    if (word.equals("!disconnect")) {
                        ClientConnect.this.downService();
                        break;
                    }

                    System.out.println(word);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String word;
                try {
                    word = reader.readLine();
                    if (word.equals("!disconnect")) {
                        out.write("Disconnect" + "\n");
                        ClientConnect.this.downService();
                        break;
                    } else {
                        out.write(userName + ": " + word + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private void getNickname() {
        System.out.print("Enter your nick: ");
        try {
            userName = reader.readLine();
            out.write("Hello " + userName + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}