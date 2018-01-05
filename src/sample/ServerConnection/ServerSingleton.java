package sample.ServerConnection;

import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.Conversation.Message;
import sample.MessageWindow.MessageWindowController;
import sample.MessageWindow.MessageWindowSingleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
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

    private ServerSingleton() { }

    public void createConnection(String ipAddr, int port, String login, String psswd) throws Exception{
            socket = new Socket(ipAddr, port);
            socketOpen = true;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Socket created, ip: "+ ipAddr + " port: " +port);
        try {
            String message = "00"+login+";"+psswd+";";
            os.write(message.getBytes());
            System.out.println("message is send");
        }catch(IOException ex){
            socketOpen = false;
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
            socket = new Socket(ipAddr, port);
            socketOpen = true;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Socket created, ip: "+ ipAddr + " port: " +port);
            String message = "03"+login+";"+psswd+";";
            os.write(message.getBytes());
            System.out.println("message is send");
            socket.close();
        }catch(IOException ex){
            socketOpen = false;
            ex.printStackTrace();
        }
    }

    public void closeSocket(){
        if(socketOpen) {
            System.out.println("Closing socket...");
            socketOpen = false;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getSocketOpen(){
        return socketOpen;
    }

    public static ServerSingleton getServerSingleton(){
        return serverSingleton;
    }

    public Socket getSocket(){
        return socket;
    }

    public void sendMessage(String message, String login){
        try {
            message = "01"+login+";"+message+"\n";
            os.write(message.getBytes());
            System.out.println("message is send");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public int getMessage(byte[] buffer){
        if(!socketOpen){
            return -1;
        }
        int bytes = -1;

        try{
            //System.out.println("kurwa");
            //if(is.available() > 0)
            bytes = is.read(buffer);
            //System.out.println("pizda");
            String inputMsg = new String(buffer);

            if(inputMsg.substring(0,2).equals("04")){    //information if login is successful
                System.out.println("Logging successful");
            }
            if(inputMsg.substring(0,2).equals("05")){    //information if login is successful
                System.out.println("Logging failed");
            }
            if(inputMsg.substring(0,2).equals("02")){    //incoming message from someone
                String incomingLogin = inputMsg.substring(2,inputMsg.indexOf(";"));
                System.out.println("msg from: " + incomingLogin);
                inputMsg = inputMsg.substring(inputMsg.indexOf(";")+1, inputMsg.length());
                inputMsg = inputMsg.replace("\n","");
                System.out.println(inputMsg);
                try{
                    Message actualMessage = new Message(incomingLogin, inputMsg);
                    Conversation conversation = ConversationSingleton.getConversationSingleton().getConversationList().stream()
                            .filter(con -> con.getFriendLogin().equals(incomingLogin))
                            .findFirst().get();
                    conversation.getHistory().add(actualMessage);
                    boolean ifMessageWindowControllerFound = false;
                    for(MessageWindowController messageWindowController : MessageWindowSingleton.getMessageWindowSingleton().getMessageWindowControllers()){
                        if(messageWindowController.getFriendLogin().equals(incomingLogin)){
                            messageWindowController.addMsg(actualMessage);
                            ifMessageWindowControllerFound = true;
                        }
                    }
                    System.out.println("IF MESSAGEWINDOWCONTROLLER FOUND: " + ifMessageWindowControllerFound);
                    if(!ifMessageWindowControllerFound){
                        MessageWindowSingleton.getMessageWindowSingleton().createMessageWindow(incomingLogin);
                    }
                }catch(NoSuchElementException ex){
                    ex.printStackTrace();
                }
            }


        }catch(SocketTimeoutException ex){
            //ex.printStackTrace();
        }catch(Exception ex){
            if(socketOpen) {
                ex.printStackTrace();
            }

        }
        return bytes;
    }

}
