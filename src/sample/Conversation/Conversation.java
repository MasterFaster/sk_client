package sample.Conversation;


import java.util.*;

/**
 * Created by Master Faster on 15.11.2017.
 */
public class Conversation {

    //conversation with $friendLogin
    private String friendLogin;
    private ArrayList<Message> history = new ArrayList<>();

    public Conversation(){}

    public Conversation(String friendLogin){
        this.friendLogin = friendLogin;
    }

    public String getFriendLogin() {
        return friendLogin;
    }

    public void setFriendLogin(String friendLogin) {
        this.friendLogin = friendLogin;
    }

    public ArrayList<Message> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Message> history) {
        this.history = history;
    }
}
