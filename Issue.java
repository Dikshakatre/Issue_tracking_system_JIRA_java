package data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Issue extends Entity {

    private Status state;
    private Map<UUID, Comment> comments;
    private User assignedUser;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private TreeMap<Timestamp, String> changeHistory;

    public Issue(String name){
        super(name);
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.comments= new HashMap<>();
        this.state = Status.TODO;
        this.changeHistory = new TreeMap<>();
        this.changeHistory.put(this.createTime, "Issue created with ID: "+this.getId()+" and status: TODO");
    }

    public void changeStatus(Status newStatus, Comment comment){
        this.changeHistory.put(new Timestamp(System.currentTimeMillis()), "Issue status changed from "
                +this.state.toString()+" to "+newStatus.toString()+" with comment: "+comment.getDescription());
        setState(newStatus);
    }

    public void changeStatus(Status newStatus){
        this.changeHistory.put(new Timestamp(System.currentTimeMillis()), "Issue status changed from "
                +this.state.toString()+" to "+newStatus.toString());
        setState(newStatus);
    }

    public void setAssignedUser(User user){
        if(this.assignedUser!=null){
            this.assignedUser.removeAssignedIssue(this.getId());
        }
        this.assignedUser = user;
        user.assignIssue(this);
    }

    public void removeAssignedUser(User user){
        this.assignedUser = null;
        user.removeAssignedIssue(user.getId());
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void addComment(Comment comment){

        if(assignedUser != null){
            assignedUser.addComment(comment);
        }
        this.comments.put(comment.getId(), comment);
    }

    public Map<UUID, Comment> getComments() {
        return comments;
    }

    public List<Comment> getCommentsList() {
        if(comments.isEmpty()){
            return new ArrayList<>();
        }
        return new ArrayList<>(this.comments.values());
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
        if(state == Status.DONE){
            setEndTime(new Timestamp(System.currentTimeMillis()));
        }
        if(state == Status.INPROGRESS){
            setStartTime(new Timestamp(System.currentTimeMillis()));
        }
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp durationTask(){
        return new Timestamp(endTime.getTime() - startTime.getTime());
    }

    public TreeMap<Timestamp, String> getChangeHistory() {
        return changeHistory;
    }

    public void setChangeHistory(TreeMap<Timestamp, String> changeHistory) {
        this.changeHistory = changeHistory;
    }
}
