package sample.Conversation;

import java.util.ArrayList;

/**
 * Created by Master Faster on 15.11.2017.
 */
public class ConversationSingleton {
    private final static ConversationSingleton conversationSingleton = new ConversationSingleton();

    private ArrayList<Conversation> conversationList = new ArrayList<>();

    public static ConversationSingleton getConversationSingleton(){
        return conversationSingleton;
    }

    public void addConversation(Conversation conversation){
        conversationList.add(conversation);
    }

    public ArrayList<Conversation> getConversationList(){
        return conversationList;
    }

    public void setConversationList(ArrayList<Conversation> conversationList){
        this.conversationList = conversationList;
    }
}
