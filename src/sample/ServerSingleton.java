package sample;

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

    public void closeSocket(){
        System.out.println("Closing socket...");
        socketOpen = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            message = "01"+login+";"+message;
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
        String incomingLogin = "";
        try{
            bytes = is.read(buffer);
            String inputMsg = new String(buffer);
            if(inputMsg.substring(0,2).equals("04")){    //information if login is successful
                System.out.println("Logging successful");
            }
            if(inputMsg.substring(0,2).equals("05")){    //information if login is successful
                System.out.println("Logging failed");
            }
            if(inputMsg.substring(0,2).equals("02")){    //incoming message from someone
                incomingLogin = inputMsg.substring(2,inputMsg.indexOf(";"));
                System.out.println("msg from: " + incomingLogin);
                inputMsg = inputMsg.substring(inputMsg.indexOf(";")+1, inputMsg.length());
                System.out.println(inputMsg);
            }
            String finalIncomingLogin = incomingLogin;
            try{
                Conversation conversation = ConversationSingleton.getConversationSingleton().getConversationList().stream()
                        .filter(con -> con.getFriendLogin().equals(finalIncomingLogin))
                        .findFirst().get();
                conversation.getHistory().add(new Message(finalIncomingLogin,inputMsg));
            }catch(NoSuchElementException ex){
                ex.printStackTrace();
            }
//            for(Conversation con : ConversationSingleton.getConversationSingleton().getConversationList()){
//                if(con.getFriendLogin().equals("Serwer")){
//                    con.getHistory().add(new Message("Serwer",new String(buffer)));
//                    break;
//                }
//            }
        }catch(SocketTimeoutException ex){
            //ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return bytes;
    }

}
