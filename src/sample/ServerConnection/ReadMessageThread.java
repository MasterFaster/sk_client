package sample.ServerConnection;

import sample.MessageWindow.MessageWindowController;

import java.io.InputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by Master Faster on 15.11.2017.
 */
public class ReadMessageThread extends Thread {



    public ReadMessageThread(){
    }

    @Override
    public void run() {
        System.out.println("Reading message thread is running");
        while (true) {
            if(!ServerSingleton.getServerSingleton().getSocketOpen()){
                break;
            }
            byte[] buffer = new byte[100];
            int byteNumber = ServerSingleton.getServerSingleton().getMessage(buffer);
        }
    }
}
