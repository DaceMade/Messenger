package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class Render extends JFrame {

    public Render() {
        createLoginWindow();
    }

    public void createLoginWindow() {

        JTextField login = new JTextField(15);
        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registrationButton = new JButton("Registration");

        Frame loginFrame = new Frame("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


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
                    createRegFrame();
                }
        );

        setVisible(true);
        pack();
    }

    public void createRegFrame() {
        JFrame registrationFrame = new JFrame("Reg");
        registrationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        registrationFrame.add(regPanel, BorderLayout.NORTH);

        registrationButton.addActionListener(e -> {
                if (!Arrays.equals(passwordField.getPassword(), passwordAgainField.getPassword())) {
                    JOptionPane.showMessageDialog(Render.this,"Passwords do not match");
                }
        });

        registrationFrame.setVisible(true);
        registrationFrame.pack();
    }
}