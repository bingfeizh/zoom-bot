package zoomapi.baseUnit;

/**
 * POJO Member
 */

public class Member {

    String uid;
    String id;
    String email;
    String firstName;
    String lastName;
    String role;
    String timestamp;

    public Member(){}

    public Member(String uid, String id, String email, String firstName, String lastName, String role, String timestamp) {
        this.uid = uid;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.timestamp = timestamp;
    }

    public Member(Member member){
        this.uid = member.getUid();
        this.id = member.getId();
        this.email = member.getEmail();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.role = member.getRole();
        this.timestamp = member.getTimestamp();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return "{ Uid:" + uid +
                " Id: " + id +
                ", Email: " + email +
                ", First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Role: " + role +
                " }";
    }

    public boolean equals(Message c) {

        return this.getId().equals(c.getId());
    }
}

