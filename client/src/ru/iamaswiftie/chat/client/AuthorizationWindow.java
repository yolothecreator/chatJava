package ru.iamaswiftie.chat.client;

import ru.iamaswiftie.network.ManagementSystem;

import javax.swing.*;
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

    private final JTextArea userInput = new JTextArea();
    private final JPasswordField passwordInput = new JPasswordField();
    private JButton regButton = new JButton("Registration");
    private JButton loginButton = new JButton("Login");

    private AuthorizationWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        userInput.setToolTipText("username");
        passwordInput.setToolTipText("password");

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 1, 20, 20));
        container.add(userInput);
        container.add(passwordInput);


        container.add(regButton);
        container.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(userInput.getText().equals("") || new String(passwordInput.getPassword()).equals("")){
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Fill username and password!");
                    } else if(ManagementSystem.getInstance().authorization(userInput.getText(), new String(passwordInput.getPassword()))) {
                        dispose();  //add this to close authorisation window but not to set it invisible
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new ClientWindow(userInput.getText());
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Wrong username or password!");
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
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Fill username and password!");
                    } else if(ManagementSystem.getInstance().doesThisUserExist(userInput.getText(), new String(passwordInput.getPassword()))) {
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "This user already exists!");
                    } else  {
                        ManagementSystem.getInstance().registration(userInput.getText(), new String(passwordInput.getPassword()));
                        userInput.setText("");
                        passwordInput.setText("");
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Registration is successfull!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
