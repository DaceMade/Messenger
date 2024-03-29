package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginDialog extends JFrame {

    private Server server;

    public LoginDialog(Server server) {
        this.server = server;
        setTitle("Login");
        JTextField login = new JTextField(15);
        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registrationButton = new JButton("Registration");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        panel.add(loginLabel);
        panel.add(login);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registrationButton);

        add(panel, BorderLayout.NORTH);

        registrationButton.addActionListener(e -> {
            new RegistrationDialog(server).setVisible(true);
        });

        server.registerCallback(message -> {
            int command = message.getCommand();
            switch (command) {
                case ServerCommands.LOGIN_SUCCESS:
                    dispose();
                    new ChatWindow(server, (LoginSet) message.getData()).setVisible(true);
                    break;
                case ServerCommands.INCORRECT_PASSWORD:
                    JOptionPane.showMessageDialog(this, "Incorrect password");
                    break;
                case ServerCommands.INCORRECT_LOGIN:
                    JOptionPane.showMessageDialog(this, "Incorrect login");
                    break;
            }
        });

        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            LoginSet loginSet = new LoginSet(login.getText(), password);
            server.sendMessage(new ServerMessage(ServerCommands.LOGIN, loginSet));
        });

        pack();
    }
}
