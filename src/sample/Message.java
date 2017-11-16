package sample;

/**
 * Created by Master Faster on 15.11.2017.
 */
public class Message {

    private String author;
    private String message;

    public Message(String author, String message){
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
