package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatWindow extends JFrame {

    private Server server;
    private String userName;

    public ChatWindow(Server server) {
        this.server = server;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);

        JTextArea chatArea = new JTextArea(10,5);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JTextField userNameField = new JTextField();
        JTextField messageField = new JTextField();
        JScrollPane scrollChat = new JScrollPane(chatArea);


        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        chatPanel.add(userNameField, BorderLayout.PAGE_START);
        chatPanel.add(scrollChat, BorderLayout.CENTER);
        chatPanel.add(messageField, BorderLayout.PAGE_END);

        server.registerCallback(message -> {
            int command = message.getCommand();
            switch (command) {
                case ServerCommands.SEND_MESSAGE:
                    String data = (String) message.getData();
                    data = userName + ": " + data + "\n";
                    System.out.println(data);
                    chatArea.setText(chatArea.getText() + data);
                    break;
            }

        });


        messageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    userName = userNameField.getText();
                    userNameField.setEditable(false);
                    String word = messageField.getText();
                    server.sendMessage(new ServerMessage(ServerCommands.SEND_MESSAGE, word));
                    messageField.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        add(chatPanel);
    }
}
