package data;

import java.sql.Timestamp;

public class Comment extends Entity {

    private String description;
    private Timestamp creationTime;
    private User owner;

    public Comment(String description){
        super();
        this.description = description;
        this.creationTime = new Timestamp(System.currentTimeMillis());
        this.owner = null;
    }

    public Comment(User owner, String description){
        super();
        this.description = description;
        this.creationTime = new Timestamp(System.currentTimeMillis());
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
