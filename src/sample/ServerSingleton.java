package sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * Created by Master Faster on 09.11.2017.
 */
public class ServerSingleton {
    private final static ServerSingleton serverSingleton = new ServerSingleton();

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private ServerSingleton() { }

    public void createConnection(String ipAddr, int port, String login, String psswd) throws Exception{
            socket = new Socket(ipAddr, port);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            System.out.println("Socket created, ip: "+ ipAddr + " port: " +port);
    }

    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerSingleton getServerSingleton(){
        return serverSingleton;
    }

    public Socket getSocket(){
        return socket;
    }

    public void sendMessage(String message, String login){
        try {
            os.write(message.getBytes());
            System.out.println("message is send");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public int getMessage(byte[] buffer){
        int bytes = -1;
        try{
            bytes = is.read(buffer);
            for(Conversation con : ConversationSingleton.getConversationSingleton().getConversationList()){
                if(con.getFriendLogin().equals("Serwer")){
                    con.getHistory().add(new Message("Serwer",new String(buffer)));
                    break;
                }
            }
        }catch(SocketTimeoutException ex){
            //ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return bytes;
    }

}
