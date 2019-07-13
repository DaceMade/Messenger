package com.company;



public class Client {

    private static final int PORT = 255;
    private static final String IP = "localhost";


    public static void main(String[] args) {
        Render render = new Render();
        new ClientConnect(IP, PORT).msgThreadStart();
    }


}
