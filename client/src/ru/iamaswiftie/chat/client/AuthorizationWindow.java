package ru.iamaswiftie.chat.client;


import ru.iamaswiftie.network.ManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationWindow extends JFrame {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthorizationWindow();
            }
        });
    }

    private final JTextField userInput = new JTextField();    //We need change it to JTextField
    private final JPasswordField passwordInput = new JPasswordField();
    private final JLabel labelForInformation = new JLabel(" ", SwingConstants.CENTER);
    private JButton regButton = new JButton("Registration");
    private JButton loginButton = new JButton("Sign in");

    private AuthorizationWindow() {
        super("Authorization");


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        labelForInformation.setForeground(Color.RED);

        Box loginBox = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Login:");
        loginBox.add(loginLabel);
        loginBox.add(Box.createHorizontalStrut(6));
        loginBox.add(userInput);

        Box passwordBox = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password:");
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(6));
        passwordBox.add(passwordInput);

        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(loginButton);
        buttonsBox.add(Box.createHorizontalStrut(12));
        buttonsBox.add(regButton);

        Box logsBox = Box.createHorizontalBox();
        logsBox.add(labelForInformation);

        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));

        mainBox.add(loginBox);
        mainBox.add(Box.createVerticalStrut(12));

        mainBox.add(passwordBox);
        mainBox.add(Box.createVerticalStrut(20));

        mainBox.add(buttonsBox);
        mainBox.add(Box.createVerticalStrut(12));

        mainBox.add(logsBox);
        mainBox.add(Box.createVerticalStrut(12));

        setContentPane(mainBox);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelForInformation.setText("");
                try {
                    if(userInput.getText().equals("") || new String(passwordInput.getPassword()).equals("")){
                        labelForInformation.setText("Fill username and password!");
                    } else if(ManagementSystem.getInstance().authorization(userInput.getText(), new String(passwordInput.getPassword()))) {
                        dispose();  //add this to close authorisation window but not to set it invisible
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new ClientWindow(userInput.getText());
                            }
                        });
                    } else {
                        labelForInformation.setText("Wrong username/password!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(userInput.getText().equals("") || new String(passwordInput.getPassword()).equals("")){
                        labelForInformation.setText("Fill username and password!");
                    } else if(ManagementSystem.getInstance().doesThisUserExist(userInput.getText())) {
                        labelForInformation.setText("This user already exists!");
                    } else  {
                        ManagementSystem.getInstance().registration(userInput.getText(), new String(passwordInput.getPassword()));
                        userInput.setText("");
                        passwordInput.setText("");
                        labelForInformation.setText("Registration is successfull!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

}
