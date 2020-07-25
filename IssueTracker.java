package data;

import java.sql.Timestamp;
import java.util.*;

public class IssueTracker {
    private Map<UUID, Issue> allIssues = new HashMap<>();
    private Map<UUID, User> allUsers = new HashMap<>();
    Scanner in;
    User user = null;
    Issue issue = null;
    UUID userId = null;
    UUID issueId = null;
    Comment comment = null;

    public static void main(String[] args) {
        int option = 0;
        do {
            IssueTracker tracker = new IssueTracker();
            System.out.println("Select following scenarios for testing:");
            System.out.println("1. Custom Scenario");
            System.out.println("2. Create issue, Create user, Assign user to issue, " +
                    "Change status of issue with a  comment, Change Status of issue without a comment," +
                    "Record start time, Record end time, Record duration and Record change history of issue");
            System.out.println("3. Create an issue, Create a user, Add a comment to the issue & display all comments on issue");
            System.out.println("4. Create an issue, Create a user, Display all issues and users in the system");
            System.out.println("5. Create a user, Display name of the user, Assign existing issue to user, Display issues assigned to user, Remove the assigned issue");
            System.out.println("6. Create a user, Display name of the user, Add comment by user to an existing issue, Display all comments by the user & Remove a particular comment");
            System.out.println("7. Create a new comment, Display the comment, Display comment creation time & Display owner of comment");
            System.out.println("8. Create an issue & issue's creation time");
            System.out.println("9. Exit");

            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    tracker.scenario1();
                    break;
                case 2:
                    tracker.scenario2();
                    break;
                case 3:
                    tracker.scenario3();
                    break;
                case 4:
                    tracker.scenario4();
                    break;
                case 5:
                    tracker.scenario5();
                    break;
                case 6:
                    tracker.scenario6();
                    break;
                case 7:
                    tracker.scenario7();
                    break;
                case 8:
                    tracker.scenario8();
                    break;
                case 9:
                    scanner.close();
                    System.exit(1);
                default:
                    System.out.println("Invalid option");
            }
        }while (true);
    }
    /**
     * 1. Custom Scenario
     */
    private void scenario1(){
        int option = 0;
        do{
            in = new Scanner(System.in);
            System.out.println("Select an action: ");
            System.out.println("1. Create an user");
            System.out.println("2. Create an Issue");
            System.out.println("3. Assign issue to user");
            System.out.println("4. Change state of Issue");
            System.out.println("5. List all Users");
            System.out.println("6. List all Issues");
            System.out.println("7. List all Issues for an User");
            System.out.println("8. Add Comment");
            System.out.println("9. List all comments for an Issue");
            System.out.println("10. List change history for an Issue");
            System.out.println("11. Exit");

            option = in.nextInt();
            switch (option){
                case 1:
                    System.out.println("Enter name of User");
                    in = new Scanner(System.in);
                    String name = in.nextLine();
                    userId = this.createUser(name);
                    System.out.println("User created with ID: "+userId+" and name: "+name);
                    break;
                case 2:
                    System.out.println("Enter title of Issue");
                    in = new Scanner(System.in);
                    String title = in.nextLine();
                    issueId = this.addIssue(title);
                    System.out.println("Issue created with ID: "+issueId+" and title: "+title);
                    break;
                case 3:
                    System.out.println("Enter ID of User");
                    in = new Scanner(System.in);
                    userId = UUID.fromString(in.nextLine());
                    user = this.allUsers.getOrDefault(userId, null);
                    if(user == null){
                        System.out.println("Cannot find user by ID: "+ userId);
                        break;
                    }
                    System.out.println("Enter ID of Issue");
                    in = new Scanner(System.in);
                    issueId = UUID.fromString(in.nextLine());
                    issue = this.allIssues.getOrDefault(issueId, null);
                    if(issue == null){
                        System.out.println("Cannot find issue by ID: "+ issueId);
                        break;
                    }
                    this.assignIssuetoUser(user, issue);
                    System.out.println("Assigned issue with ID: "+issueId+" to user with ID: "+userId);
                    break;
                case 4:
                    System.out.println("Enter ID of issue");
                    in = new Scanner(System.in);
                    issueId = UUID.fromString(in.nextLine());
                    issue = this.allIssues.getOrDefault(issueId, null);
                    if(issue == null){
                        System.out.println("Cannot find issue by ID: "+ issueId);
                        break;
                    }
                    System.out.println("Current state of issue is: "+issue.getState()+". Enter new state from the following:");
                    System.out.println("1. TODO");
                    System.out.println("2. INPROGRESS");
                    System.out.println("3. DONE");
                    in = new Scanner(System.in);
                    int state = in.nextInt();
                    if(state==1){
                        issue.setState(Status.TODO);
                        System.out.println("Status of issue: "+issue.getId()+" changed to "+issue.getState());
                        break;
                    }else if(state==2){
                        issue.setState(Status.INPROGRESS);
                        System.out.println("Status of issue: "+issue.getId()+" changed to "+issue.getState());
                        break;
                    }else if(state==3){
                        issue.setState(Status.DONE);
                        System.out.println("Status of issue: "+issue.getId()+" changed to "+issue.getState());
                        break;
                    }else {
                        System.out.println("Invalid input");
                        break;
                    }
                case 5:
                    System.out.println("All users: ");
                    System.out.println("UserID\tName");
                    for(User user1: this.allUsers.values()){
                        System.out.println(user1.getId()+"\t"+user1.getName());
                    }
                    break;
                case 6:
                    System.out.println("All issues: ");
                    System.out.println("IssueID\tTitle");
                    for(Issue issue1: this.allIssues.values()){
                        System.out.println(issue1.getId()+"\t"+issue1.getName());
                    }
                    break;
                case 7:
                    System.out.println("Enter ID of User");
                    in = new Scanner(System.in);
                    userId = UUID.fromString(in.nextLine());
                    user = this.allUsers.getOrDefault(userId, null);
                    if(user == null){
                        System.out.println("Cannot find user by ID: "+ userId);
                        break;
                    }
                    List<Issue> issues = user.getAssignedIssued();
                    if(issues.isEmpty()){
                        System.out.println("No issues currently assigned");
                        break;
                    }
                    System.out.println("IssueID\tTitle");
                    for(Issue issue1: issues){
                        System.out.println(issue1.getId()+"\t"+issue1.getName());
                    }
                    break;
                case 8:
                    System.out.println("Enter description for comment");
                    in = new Scanner(System.in);
                    String description = in.nextLine();
                    comment = new Comment(description);
                    System.out.println("Enter ID of issues");
                    issueId = UUID.fromString(in.nextLine());
                    issue = this.allIssues.getOrDefault(issueId, null);
                    if(issue == null){
                        System.out.println("Cannot find issue by ID: "+ issueId);
                        break;
                    }
                    System.out.println("Enter ID of user");
                    userId = UUID.fromString(in.nextLine());
                    user = this.allUsers.getOrDefault(userId, null);
                    if(user == null){
                        System.out.println("Cannot find user by ID: "+ userId);
                        break;
                    }
                    comment.setOwner(user);
                    issue.addComment(comment);
                    break;

                case 9:
                    System.out.println("Enter ID of issue");
                    in = new Scanner(System.in);
                    issueId = UUID.fromString(in.nextLine());
                    issue = this.allIssues.getOrDefault(issueId, null);
                    if(issue == null){
                        System.out.println("Cannot find issue by ID: "+ issueId);
                        break;
                    }
                    List<Comment> comments = issue.getCommentsList();
                    if(comments.isEmpty()){
                        System.out.println("No comments on this issue");
                        break;
                    }
                    System.out.println("CommentID\tDescription");
                    for(Comment c: comments){
                        System.out.println(c.getId()+"\t"+c.getDescription());
                    }
                    break;
                case 10:
                    System.out.println("Enter ID of issue");
                    in = new Scanner(System.in);
                    issueId = UUID.fromString(in.nextLine());
                    issue = this.allIssues.getOrDefault(issueId, null);
                    if(issue == null){
                        System.out.println("Cannot find issue by ID: "+ issueId);
                        break;
                    }
                    this.printChangeHistory(issue.getChangeHistory());
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }while (option!=11);
    }
    
    /**
     * 2. Create issue, Create user, Assign user to issue, Change status of issue with a comment, 
     * Change Status of issue without a comment, Record start time, Record end time, 
     * Record duration and Record change history of issue
     */
    private void scenario2() {
        issueId = addIssue("Issue2");
        print("Created Issue "+issueId.toString());

        userId = createUser("User2");
        print("Created User "+userId);

        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);

        Issue issue = allIssues.get(issueId);
        print("Current State "+ issue.getState().name());
        print("Start Time "+issue.getStartTime());
        issue.changeStatus(Status.INPROGRESS, new Comment("Sample Comment From Scenario2"));
        print("New State with comment "+issue.getState().name());
        print("Start Time "+issue.getStartTime());
        print("End Time "+issue.getEndTime());

        issue.changeStatus(Status.DONE);
        print("New State without comment "+issue.getState().name());
        print("Start Time "+issue.getStartTime());
        print("End Time "+issue.getEndTime());
        print("Duration to resolve Issue "+issue.durationTask());
        printChangeHistory(issue.getChangeHistory());
    }
    
    /**
     * 3. Create an issue, Create a user, Add a comment to the issue & display all comments on issue
     */
    private void scenario3() {
        issueId = addIssue("Issue3");
       issue = allIssues.get(issueId);
        print("Created Issue "+issueId.toString());

        userId = createUser("User3");
        print("Created User "+userId.toString());

        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);

        print("comments on issue3 size()"+issue.getCommentsList());
        comment = new Comment("Comment3 on issue3 by user3");
        issue.addComment(comment);
        print("comments on issue3 size"+issue.getCommentsList().size());
    }
    
    /**
     * 4. Create an issue, Create a user, Display all issues and users in the system
     */
    private void scenario4() {

        issueId = addIssue("Issue4");
        print("Created Issue "+issueId.toString());

        userId = createUser("User4");
        print("Created User "+userId.toString());
        print("All Issues");
        printAllIssues(allIssues);
        print("All Users");
        printAllUsers(allUsers);
    }

    /**
     * 5. Create a user, Display name of the user, Assign existing issue to user, 
     * Display issues assigned to user, Remove the assigned issue
     */
    private void scenario5() {

        userId = createUser("User5");
        print("Created User "+userId.toString());
        user = allUsers.get(userId);
        print("Created user name "+user.getName());

        issueId = addIssue("Issue5");
        issue = allIssues.get(issueId);
        print("Created Issue "+issueId.toString());

        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);

        print("All issues assigned ");
        print(user.getAssignedIssued());

        print("Issue Owner "+issue.getAssignedUser().getName());
        issue.removeAssignedUser(user);
        print("Removed assigned user "+issue.getAssignedUser());
    }
    
    /**
     * 6. Create a user, Display name of the user,  
     * Add comment by user to an existing issue, Display all comments by the user & Remove a particular comment
     */
    private void scenario6() {
        issueId = addIssue("Issue6");
        issue = allIssues.get(issueId);
        print("Created Issue "+issueId.toString());

        userId = createUser("User6");
        user = allUsers.get(userId);
        print("Created User "+userId.toString());
        print("User Name "+user.getName());

        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);

        print("comments on issue6 size"+issue.getCommentsList());
        print("comments by user6"+user.getComments());
        print("Enter Comment");
        Comment comment6 = new Comment(new Scanner(System.in).nextLine());
        issue.addComment(comment6);
        print("comments on issue6 size"+issue.getCommentsList().size());

        if(!issue.getComments().isEmpty() && issue.getComments().containsKey(comment6.getId())){
            issue.getComments().remove(comment6);
            print("Removed Comment");
        }
    }
    
    /**
     * 7. Create a new comment, Display the comment, Display comment creation time & Display owner of comment
     */
    private void scenario7() {
        issueId = addIssue("Issue8");
        print("Created Issue "+issueId.toString());

        userId = createUser("User8");
        print("Created User "+userId.toString());
        User user = allUsers.get(userId);
        
        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);

        Comment comment = new Comment("Comment Scenario 8");
        
        comment.setOwner(user);
        print("Comment Description "+comment.getDescription());
        print("Comment Owner "+comment.getOwner());
        print("Comment creation time "+comment.getCreationTime());
    }
    
    /**
     * Scenario 8. Create an issue & issue's creation time
     */
    private void scenario8() {
        issueId = addIssue("Issue8");
        print("Created Issue "+issueId.toString());

        userId = createUser("User8");
        print("Created User "+userId.toString());

        assignIssuetoUser(userId, issueId);
        print("Assigned User "+userId+" to issue "+issueId);
        Issue issue = allIssues.get(issueId);
        
        print("Create time of issue "+issue.getCreateTime().toString());
    }

    public UUID addIssue(String newTitle){
        Issue issue = new Issue(newTitle);
        UUID id = issue.getId();
        allIssues.put(id, issue);
        return id;
    }

    public UUID createUser(String name){
        User user = new User(name);
        UUID id = user.getId();
        allUsers.put(id, user);
        return id;
    }

    public void removeIssue(UUID issueID){
        if(allIssues.containsKey(issueID)){
            Issue issue = allIssues.get(issueID);
            User user = issue.getAssignedUser();
            user.getAssignedIssued().remove(issueID);
            List<Comment> comments = issue.getCommentsList();
            for(Comment c: comments){
                user.removeComment(c.getId());
            }
            allIssues.remove(issueID);
        }else {
            throw new IllegalArgumentException("Issue with ID: " + issueID + " not found");
        }
    }
    public void setIssueState(UUID issueID, Status state, Comment comment){
        Issue issue = allIssues.get(issueID);
        if (issue != null){
            issue.changeStatus(state, comment);
        }
    }
    public void setIssueState(UUID issueID, Status state){
        Issue issue = allIssues.get(issueID);
        if (issue != null){
            issue.changeStatus(state);
        }
    }

    public void assignIssuetoUser(UUID userID, UUID issueID){
        Issue issue = allIssues.get(issueID);
        if (issue != null){
            issue.setAssignedUser(allUsers.get(userID));
        }
    }

    public void assignIssuetoUser(User user, Issue issue){
       issue.setAssignedUser(user);
    }

    public void addComment(UUID issueID, String comment){
        Issue issue = allIssues.get(issueID);
        if (issue != null){
            issue.addComment(new Comment(issue.getAssignedUser(),comment));
        }
    }

    public List<Issue> getIssues(Status state, UUID userID){
        List<Issue> result = new ArrayList<>();

        for(Map.Entry<UUID, Issue> entry : allIssues.entrySet()){
            if(entry.getValue().getState() == state && entry.getValue().getAssignedUser().getId() == userID){
                result.add(entry.getValue());
            }
        }
        return result;
    }

    public List<Issue> getIssues() {
        return new ArrayList<>(this.allIssues.values());
    }

    public List<Issue> getIssues(User user) {
        return user.getAssignedIssued();
    }

    public List<Issue> getIssues(Status status) {
        List<Issue> issues = this.getIssues();
        List<Issue> result = new ArrayList<>();
        for(Issue issue: issues){
            if(issue.getState() == status){
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> getIssues(Timestamp startDate, Timestamp endDate) {
        List<Issue> issues = this.getIssues();
        List<Issue> result = new ArrayList<>();
        if (startDate != null && endDate != null) {
            for (Issue issue : issues) {
                if ((issue.getStartTime().equals(startDate) || issue.getStartTime().after(startDate)) && issue.getStartTime().before(endDate)) {
                    result.add(issue);
                }
            }
            return result;
        } else if (startDate != null) {
            for (Issue issue : issues) {
                if (issue.getStartTime().equals(startDate) || issue.getStartTime().after(startDate)) {
                    result.add(issue);
                }
            }
            return result;
        } else if (endDate != null) {
            for (Issue issue : issues) {
                if (issue.getStartTime().before(endDate)) {
                    result.add(issue);
                }
            }
            return result;
        }
        return issues;
    }

    public Issue getIssue(int issueID){
        return  allIssues.get(issueID);
    }

    public UUID addUser(String name) {
        User user = new User(name);
        allUsers.put(user.getId(), user);
        return user.getId();
    }

    public List<User> getUsers(){
        List<User> result = new ArrayList<>();
        for(Map.Entry<UUID, User> e : allUsers.entrySet()){
            result.add(e.getValue());
        }
        return result;
    }

    public User getUser(UUID userID){
        return allUsers.get(userID);
    }

    public void printIssueHistory(UUID issueID){
        if(allIssues.containsKey(issueID)){
            Issue issue = allIssues.get(issueID);
            for(Map.Entry<Timestamp, String> e : issue.getChangeHistory().entrySet()){
                System.out.println(e.getKey()+" : "+e.getValue());
            }
        }
    }

    private void print(String str){
        System.out.println(str);
    }

    private void print(List<Issue> list){
        System.out.println("IssueID\t\t\tIssueName");
        for (Issue issue : list){
            System.out.println(issue.getId()+" "+issue.getName());
        }
    }

    private void printAllIssues(Map<UUID, Issue> mapUser){
        for(Map.Entry<UUID, Issue> e : mapUser.entrySet()){
            System.out.println(e.getKey()+","+e.getValue().getName());
        }
    }
    private void printAllUsers(Map<UUID, User> mapUser){
        for(Map.Entry<UUID, User> e : mapUser.entrySet()){
            System.out.println(e.getKey()+","+e.getValue().getName());
        }
    }
    
    private void printChangeHistory(TreeMap<Timestamp, String> history){
        System.out.println("TimeStamp\t\t\tAction");
        for(Map.Entry<Timestamp, String> e : history.entrySet()){
            System.out.println(e.getKey()+"\t"+e.getValue());
        }
    }
}
