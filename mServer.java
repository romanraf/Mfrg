package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

class SocketIO{
    Socket _socket;
    DataOutputStream out;
    DataInputStream inp;
    public void trySetIOSocket(Socket socket) throws IOException {
        _socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        inp = new DataInputStream(socket.getInputStream());
    }
    public void tryClose() throws IOException {

        if(inp != null){
            try {
                inp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(_socket != null){
            try {
                _socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void tryWriteOutUTF(String str) throws IOException {
        if(out == null)
            return;
        if(_socket.isConnected())
            out.writeUTF(str);
    }
    public String tryReadInUTF() throws IOException {
        if(inp == null)
            return "";
        if(_socket.isConnected())
            return inp.readUTF();
        return "";
    }
}

class mSocketContainer{
    private SocketIO lastRegister;
    private HashSet<SocketIO> SocketList;
    mSocketContainer(){
        SocketList = new HashSet<>();
    }
    void waitRegister(ServerSocket ServSocket) throws IOException {
        SocketIO socketIO = new SocketIO();
        socketIO.trySetIOSocket(ServSocket.accept());
        SocketList.add(socketIO);
        if(socketIO != null)
            lastRegister = socketIO;
    }
    SocketIO getLastRegister(){
        return lastRegister;
    }
    public void forEach(Consumer<SocketIO> action){
        SocketList.forEach(action);
    }
    public void clear() {
        SocketList.clear();
    }
}

class mSocketManager{
    mSocketContainer socketContainer;
    HashMap<SocketIO,Thread> mSocketThread;
    Thread currThread;
    public mSocketManager(){
        mSocketThread = new HashMap<>();
        socketContainer = new mSocketContainer();
    }
    public void listenAdd(int port, Consumer<SocketIO> OnAdd){
        if(currThread != null)
            currThread.interrupt();
        currThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(port)){
                    while(true) {
                        socketContainer.waitRegister(serverSocket);
                        SocketIO added = socketContainer.getLastRegister();
                        if(added != null) {
                            OnAdd.accept(added);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        currThread.start();
    }
    public void clearAll(){
        mSocketThread.forEach((S,_Thread) -> {
                if(_Thread.isAlive()) {
                    try {
                        _Thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });
        mSocketThread.clear();
        socketContainer.clear();
    }
    public void forEach(Consumer<SocketIO> action){
        socketContainer.forEach(action);
    }
}



public class mServer extends JFrame {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private JTextField msgInputField;
    private JTextArea chatArea;
    private JButton btnSendMsg;

    private mSocketManager socketManager;
    private SocketIO socketIO;
    public mServer(){
        socketIO = new SocketIO();
        socketManager = new mSocketManager();
        createGui();
        try {
            startServer();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public void startServer() throws IOException {
        socketManager.listenAdd(SERVER_PORT,(_socketIO)->{
            if(_socketIO != null){
                chatArea.append(_socketIO._socket.getInetAddress().getHostAddress() + " joined" +"\n");
                startReading(_socketIO);
            }
        });
    }
    public void createGui(){
        // Параметры окна
        setBounds(600, 300, 500, 500);
        setTitle("Сервер");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand();
            }
        });
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    socketIO.tryWriteOutUTF("/end");
                    closeAllConnections();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public void closeAllConnections(){
        socketManager.forEach((_socketIO)->{
            try {
                _socketIO.tryClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void startReading(SocketIO added){
        if(added != null) {
            Thread _readingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = added.tryReadInUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            chatArea.append(strFromServer + "\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chatArea.append(added._socket.getInetAddress().getHostAddress() + " closed " + "\n");

                }
            });
            socketManager.mSocketThread.put(added,_readingThread);
            _readingThread.start();
        }
    }

    private void sendMessage(){
        try {
            socketIO.tryWriteOutUTF(msgInputField.getText());
            msgInputField.setText("");
            msgInputField.grabFocus();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
        }
    }
    private void processCommand(){
        if (!msgInputField.getText().trim().isEmpty()) {
            String str = msgInputField.getText();
            if (msgInputField.getText().equals("/list")) {
                try {
                    chatArea.append("connection list:");
                    chatArea.append("\n");
                    socketManager.forEach((_socketIO) -> {
                        chatArea.append(_socketIO._socket.getInetAddress().getHostAddress());
                        chatArea.append("\n");
                    });
                    msgInputField.setText("");
                    msgInputField.grabFocus();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            } else if (msgInputField.getText().split(" ")[0].equals("/connect_to")) {
                String address = msgInputField.getText().split(" ")[1];
                chatArea.append("connecting with "+address + "...");
                chatArea.append("\n");
                socketManager.forEach((_socketIO) -> {
                    if (_socketIO._socket.getInetAddress().getHostAddress().equals(address)) {
                        setSocketIO(_socketIO);
                    }
                });
                msgInputField.setText("");
                msgInputField.grabFocus();
            } else if (msgInputField.getText().equals("/close_all")){
                closeAllConnections();
                msgInputField.setText("");
                msgInputField.grabFocus();
            }else if (msgInputField.getText().equals("/clear_all")) {
                closeAllConnections();
                socketManager.clearAll();
                msgInputField.setText("");
                msgInputField.grabFocus();
            }else
            sendMessage();
        }
    }

    private void setSocketIO(SocketIO SocketIO){
        socketIO = SocketIO;
        if(SocketIO != null) {
            chatArea.append("connected with " + SocketIO._socket.getInetAddress().getHostAddress());
            chatArea.append("\n");
        }
    }


}
