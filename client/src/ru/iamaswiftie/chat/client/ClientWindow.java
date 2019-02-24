package ru.iamaswiftie.chat.client;

import ru.iamaswiftie.network.TCPConnection;
import ru.iamaswiftie.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP_ADDR = "localhost";
    private static final int PORT = 8189;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


    private final JTextArea log = new JTextArea();
    private final JTextArea nowOnline = new JTextArea();
    private final JTextField fieldNickname = new JTextField("user");
    private final JTextField fieldInput = new JTextField();
    private final JPanel littlePanel = new JPanel();
    private final JPanel middlePanel = new JPanel();

    private TCPConnection connection;

    protected ClientWindow(String username) {
        super("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);


        log.setEditable(false);
        log.setLineWrap(true);

        nowOnline.setEditable(false);
        nowOnline.setLineWrap(true);

        fieldNickname.setText(username);

        littlePanel.setLayout(new BorderLayout());
        littlePanel.add(nowOnline, BorderLayout.CENTER);
        littlePanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);

        middlePanel.setLayout(new BorderLayout());
        middlePanel.add(littlePanel, BorderLayout.WEST);
        middlePanel.add(log, BorderLayout.CENTER);

        add(middlePanel, BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);

        fieldInput.addActionListener(this);

        setVisible(true);

        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
            connection.sendString(username + " connected");
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        Date date = new Date();

        fieldInput.setText(null);
        connection.sendString("(" + simpleDateFormat.format(date) + ") " + fieldNickname.getText() + ": " + msg);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) { printMsg("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
