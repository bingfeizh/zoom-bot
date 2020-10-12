package zoomapi.baseUnit;

/**
 * POJO message
 */

public class Message {

    String uid;
    String id;
    String message;
    String sender;
    String date_time;
    String timestamp;

    public Message(){}

    public Message(Message message){
        this.uid = message.getUid();
        this.id = message.getId();
        this.message = message.getMessage();
        this.sender = message.getSender();
        this.date_time = message.getDate_time();
        this.timestamp = message.getTimestamp();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Message(String uid, String id, String message, String sender, String date_time, String timestamp) {
        this.uid = uid;
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.date_time = date_time;
        this.timestamp = timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "{ Uid: " + uid +
                " Id: " + id +
                ", Message: " + message +
//                ", Sender: " + sender +
                ", Date_time: " + date_time +
                ", Timestamp: " + timestamp +
                " }";
    }

    public boolean equals(Message c) {

        return this.getId().equals(c.getId()) && this.getMessage().equals(c.getMessage());
    }

}
