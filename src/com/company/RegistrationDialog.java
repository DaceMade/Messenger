package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class RegistrationDialog extends JFrame {

    public RegistrationDialog(Server server) {
        setTitle("Registration");
        setLocationRelativeTo(null);
        JLabel loginLabel = new JLabel("Login:");
        JLabel nullLabel = new JLabel("");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel passwordAgainLabel = new JLabel("Password again:");
        JTextField login = new JTextField(15);
        JPasswordField passwordField = new JPasswordField();
        JPasswordField passwordAgainField = new JPasswordField();
        JButton registrationButton = new JButton("Registration");


        JPanel regPanel = new JPanel();
        regPanel.setLayout(new GridLayout(4, 2));
        regPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        regPanel.add(loginLabel);
        regPanel.add(login);
        regPanel.add(passwordLabel);
        regPanel.add(passwordField);
        regPanel.add(passwordAgainLabel);
        regPanel.add(passwordAgainField);
        regPanel.add(nullLabel);
        regPanel.add(registrationButton);

        add(regPanel, BorderLayout.NORTH);

        server.registerCallback(message -> {
            int command = message.getCommand();
            switch (command) {
                case ServerCommands.REGISTER_FAILED:
                    JOptionPane.showMessageDialog(this, "This login is already taken");
                    break;
                case ServerCommands.REGISTER_SUCCESS:
                    JOptionPane.showMessageDialog(this, "Successful registration");
                    dispose();
                    break;
            }
        });

        registrationButton.addActionListener(e -> {
            if (login.getText().length() == 0 || passwordAgainField.getPassword().length == 0 || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Fill in all the fields");
            } else if (!Arrays.equals(passwordField.getPassword(), passwordAgainField.getPassword())) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            }else {
                //Data for registration
                String password = new String(passwordField.getPassword());
                LoginSet loginSet = new LoginSet(login.getText(), password);
                server.sendMessage(new ServerMessage(ServerCommands.REGISTER,loginSet));
            }
        });

        pack();
    }

}
