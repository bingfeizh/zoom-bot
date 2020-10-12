package zoomapi.baseUnit;

public class Channel {

    String uid;
    String id;
    String name;
    String type;
    String timestamp;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Channel(){}

    public Channel(String uid, String id, String name, String type, String timestamp){
        this.uid = uid;
        this.id = id;
        this.name = name;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Channel(Channel channel){
        this.uid = channel.getUid();
        this.id = channel.getId();
        this.name = channel.getName();
        this.type = channel.getType();
        this.timestamp = channel.getTimestamp();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) { this.name = name; }

    public void setType(String type) { this.type = type; }

    public String getId() {
        return id;
    }

    public String getName() { return name;}

    public String getType() {return type;}

    public String getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "{ Uid: " + uid +
                ", Id: " + id +
                ", Name: " + name +
                ", Type: " + type +
                " }";
    }

    public boolean equals(Channel c) {

        return this.getId().equals(c.getId()) && this.getName().equals(c.getName());
    }
}
