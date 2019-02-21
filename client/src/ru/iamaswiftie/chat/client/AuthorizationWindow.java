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

    private final JTextArea user_input = new JTextArea();
    private final JPasswordField password_input = new JPasswordField();
    private JButton reg_button = new JButton("Registration");
    private JButton login_button = new JButton("Login");

    private AuthorizationWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        user_input.setToolTipText("username");
        password_input.setToolTipText("password");

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 1, 20, 20));
        container.add(user_input);
        container.add(password_input);


        container.add(reg_button);
        container.add(login_button);

        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(user_input.getText().equals("") || new String(password_input.getPassword()).equals("")){
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Fill username and password!");
                    } else if(ManagementSystem.getInstance().authorization(user_input.getText(), new String(password_input.getPassword()))) {
                        dispose();  //add this to close authorisation window but not to set it invisible
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new ClientWindow(user_input.getText());
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

        reg_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(user_input.getText().equals("") || new String(password_input.getPassword()).equals("")){
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "Fill username and password!");
                    } else if(ManagementSystem.getInstance().doesThisUserExist(user_input.getText(), new String(password_input.getPassword()))) {
                        JOptionPane.showMessageDialog(AuthorizationWindow.this, "This user already exists!");
                    } else  {
                        ManagementSystem.getInstance().registration(user_input.getText(), new String(password_input.getPassword()));
                        user_input.setText("");
                        password_input.setText("");
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
