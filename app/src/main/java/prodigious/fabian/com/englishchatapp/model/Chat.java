package prodigious.fabian.com.englishchatapp.model;

public class Chat {
    String author;
    String message;

    public Chat(String author, String message){
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{author:\"%s\",message:\"%s\"}", author, message);
    }
}
