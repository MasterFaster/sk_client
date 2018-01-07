package sample.ServerConnection;

import javafx.scene.control.Alert;
import sample.AlertWindows.AlertWindow;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.Conversation.Message;
import sample.MessageWindow.MessageWindowController;
import sample.MessageWindow.MessageWindowSingleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.NoSuchElementException;


/**
 * Created by Master Faster on 09.11.2017.
 */
public class ServerSingleton {
    private final static ServerSingleton serverSingleton = new ServerSingleton();

    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private boolean socketOpen = false;
    private boolean isLogged = false;
    private ReadMessageThread readMessageThread;
    private static final String CODING = "Windows-1252";  //Windows-1252    ISO-8859-1"

    private ServerSingleton() { }

    public void createConnection(String ipAddr, int port, String login, String psswd) throws Exception{
            socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddr, port), 1000);
            socketOpen = true;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Socket created, ip: "+ ipAddr + " port: " +port);
        try {
            String message = "00"+login+";"+psswd+";";
            os.write(message.getBytes());
            System.out.println("Sending login and password...");
            System.out.println("Waiting for answer if logging is completed");
            byte[] buffer = new byte[100];
            getMessage(buffer);
        }catch(IOException ex){
            socketOpen = false;
            AlertWindow.showWarningWindow("Sending message failed", "Something went wrong while sending message");
            ex.printStackTrace();
        }
    }

    /**
     * function runs when user want to create account
     * @param ipAddr    socket ip address
     * @param port  socket port
     * @param login new account login
     * @param psswd new account password
     */
    public void createAccount(String ipAddr, int port, String login, String psswd){
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddr, port), 1000);
            socketOpen = true;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Socket created, ip: "+ ipAddr + " port: " +port);
            String message = "03"+login+";"+psswd+";";
            os.write(message.getBytes(CODING));
            System.out.println("message is send");
            byte[] buffer = new byte[50];
            getMessage(buffer);
            socket.close();
            socketOpen = false;
        }catch(SocketTimeoutException ex){
            socketOpen = false;
            AlertWindow.showWarningWindow("Wrong port", "There is no host with such port and ip");
        }catch(NumberFormatException ex){
            socketOpen = false;
            AlertWindow.showWarningWindow("Wrong port", "Check if port is an integer");
        }catch(IllegalArgumentException ex){
            socketOpen = false;
            AlertWindow.showWarningWindow("Wrong port", "Port out of range");
        }catch(UnknownHostException ex){
            socketOpen = false;
            AlertWindow.showWarningWindow("Unknown Host", "This host is unreachable");
        }catch(IOException ex){
            AlertWindow.showWarningWindow("WARNING", "Input is incorrect");
            socketOpen = false;
            ex.printStackTrace();
        }catch(Exception ex) {
            socketOpen = false;
            AlertWindow.showWarningWindow("WARNING", "Something went wrong, try again later");
            System.out.println(ex);
        }
    }

    public void closeSocket(){
        isLogged = false;
        if(socketOpen) {
            System.out.println("Closing socket...");
            socketOpen = false;
            try {
                socket.close();
            } catch(Exception ex) {
                socketOpen = false;
                System.out.println(ex);
            }
        }
    }

    public boolean getSocketOpen(){
        return socketOpen;
    }

    public boolean getIsLogged(){
        return isLogged;
    }

    public static ServerSingleton getServerSingleton(){
        return serverSingleton;
    }

    public Socket getSocket(){
        return socket;
    }

    public void startReadMessageThread(){
        readMessageThread = new ReadMessageThread();
        readMessageThread.start();
    }

    public void stopReadMessageThread(){
        readMessageThread.stop();
    }

    public void sendMessage(String message, String login){
        try {
            message = "01"+login+";"+message+"\n";
            os.write(message.getBytes());
            System.out.println("message is send");
        }catch(SocketException ex){
            AlertWindow.showWarningWindow("Message can't be send","Something went wrong when sending message");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public int getMessage(byte[] buffer){
        if(!socketOpen){
            return -1;
        }
        int bytes = -1;

        try{
            //if(is.available() > 0)
            bytes = is.read(buffer);
            String inputMsg = new String(buffer);

            if(inputMsg.substring(0,2).equals("04")){    //information if login is successful
                isLogged = true;
                System.out.println("Logging successful");
            }
            if(inputMsg.substring(0,2).equals("05")){    //information if login is successful
                isLogged = false;
                System.out.println("Logging failed");
                AlertWindow.showWarningWindow("Wrong Password", "Password is incorrect for this user");
            }
            if(inputMsg.substring(0,2).equals("06")){    //information if creating account is successful
                isLogged = false;
                System.out.println("Creating account successful");
            }
            if(inputMsg.substring(0,2).equals("07")){    //information if creating account is successful
                isLogged = false;
                System.out.println("Creating account failed");
                AlertWindow.showWarningWindow("Creating account failed", "Account with such login already exists");
            }
            if(inputMsg.substring(0,2).equals("02")){    //incoming message from someone
                String incomingLogin = inputMsg.substring(2,inputMsg.indexOf(";"));
                System.out.println("msg from: " + incomingLogin);
                inputMsg = inputMsg.substring(inputMsg.indexOf(";")+1, inputMsg.length());
                inputMsg = inputMsg.replace("\n","");
                System.out.println(inputMsg);
                Conversation conversation = new Conversation();
                Message actualMessage = new Message(incomingLogin, inputMsg);
                try{
                    conversation = ConversationSingleton.getConversationSingleton().getConversationList().stream()
                            .filter(con -> con.getFriendLogin().equals(incomingLogin))
                            .findFirst().get();
                }catch(NoSuchElementException ex){
                    conversation.setFriendLogin(incomingLogin);
                    ConversationSingleton.getConversationSingleton().addConversation(conversation);
                    //ex.printStackTrace();
                }
                conversation.getHistory().add(actualMessage);
                boolean ifMessageWindowControllerFound = false;
                for(MessageWindowController messageWindowController : MessageWindowSingleton.getMessageWindowSingleton().getMessageWindowControllers()){
                    if(messageWindowController.getFriendLogin().equals(incomingLogin)){
                        messageWindowController.addMsg(actualMessage);
                        ifMessageWindowControllerFound = true;
                    }
                }
                if(!ifMessageWindowControllerFound){
                    MessageWindowSingleton.getMessageWindowSingleton().createMessageWindow(incomingLogin);
                }

            }


        }catch(SocketTimeoutException ex){
            ex.printStackTrace();
            AlertWindow.showWarningWindow("Something went wrong", "Connection can't be established");
        }catch(SocketException ex){
            AlertWindow.showWarningWindow("Something went wrong", "Try one more time, previous user is still not logged out");
        }catch(Exception ex){
            if(socketOpen) {
                ex.printStackTrace();
            }
        }
        return bytes;
    }

}
