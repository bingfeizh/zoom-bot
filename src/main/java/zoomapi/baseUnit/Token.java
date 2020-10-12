package zoomapi.baseUnit;

public class Token {

    String uid;
    String token;
    String timestamp;

    public Token(){}

    public Token(String uid, String token, String timestamp) {
        this.uid = uid;
        this.token = token;
        this.timestamp = timestamp;
    }

    public Token(Token token){
        this.uid = token.getUid();
        this.token = token.getToken();
        this.timestamp = token.getTimestamp();
    }

    public void setUid(String id) {
        this.uid = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "{ Id: " + uid +
                ", token: " + token +
                ", timestamp: " + timestamp +
                " }";
    }
}

