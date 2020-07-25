package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User extends Entity {
    private Map<UUID,Issue> assignedIssued;
    private Map<UUID, Comment> comments;

    public User(String name){
       super(name);
        this.assignedIssued = new HashMap<>();
        this.comments = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addComment(Comment comment) {
        this.comments.put(comment.getId(), comment);
    }

    public List<Comment> getComments() {
        if(comments.isEmpty()) return null;
        return new ArrayList<>(this.comments.values());
    }

    public List<Issue> getAssignedIssued() {
        return new ArrayList<>(this.assignedIssued.values());
    }

    public void assignIssue(Issue issue){
        this.assignedIssued.put(issue.getId(), issue);
    }

    public void removeAssignedIssue(UUID id) {
        this.assignedIssued.remove(id);
    }

    public void removeComment(UUID id) {
        this.comments.remove(id);
    }
}
