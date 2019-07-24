package com.company;


import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {

    private final int port;
    private String address;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private BlockingQueue<ServerMessage> msgQueue = new ArrayBlockingQueue<>(50);
    private Set<Callback> callbackList = new HashSet<>();


    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void registerCallback(Callback callback) {
        callbackList.add(callback);
    }

    public void unregisterCallback(Callback callback) {
        callbackList.remove(callback);
    }

    public void connect() {
        try {
            while (true) {
                try {
                    socket = new Socket(address, port);
                    break;
                } catch (IOException ignored) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored2) {
                    }
                }
            }


            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            new Thread(sending).start();
            new Thread(receiving).start();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    //from client to server
    private Runnable sending = () -> {
        while (true) {
            try {
                ServerMessage msg = msgQueue.take();
                out.writeObject(msg);
                out.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                disconnect();
                break;
            }
        }
    };

    //from server to client
    private Runnable receiving = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ServerMessage word = (ServerMessage) in.readObject();
                for (Callback callback : callbackList) {
                    callback.onReceive(word);
                }
            } catch (IOException e) {
                disconnect();
                break;
            } catch (ClassNotFoundException ignored) {
            }
        }
    };

    private void disconnect() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
        try {
            in.close();
        } catch (IOException ignored) {
        }
        try {
            out.close();
        } catch (IOException ignored) {
        }
    }

    public void sendMessage(ServerMessage msg) {
        try {
            msgQueue.put(msg);
        } catch (InterruptedException ignored) {
        }
    }

    interface Callback {
        void onReceive(ServerMessage message);
    }
}
