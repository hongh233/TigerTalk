package tigertalk.model.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import tigertalk.model.Friend.Friendship;
import tigertalk.model.Friend.FriendshipRequest;
import tigertalk.model.Group.GroupMembership;
import tigertalk.model.Group.GroupPost;
import tigertalk.model.Group.GroupPostComment;
import tigertalk.model.Group.GroupPostLike;
import tigertalk.model.Notification.Notification;
import tigertalk.model.Post.Post;
import tigertalk.model.Post.PostComment;
import tigertalk.model.Post.PostLike;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfile {

    // User Sending request to others, e.g. user is A, and A ---> B,C,D , return B,C,D
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "sender-friendship")
    private List<Friendship> senderFriendshipList = new LinkedList<>();

    // User Receiving request from others, e.g. user is A, and B,C,D ---> A, return B,C,D
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "receiver-friendship")
    private List<Friendship> receiverFriendshipList = new LinkedList<>();
    // User Sending request to others, e.g. user is A, and A ---> B,C,D , return B,C,D
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "sender-friendship-request")
    private List<FriendshipRequest> senderFriendshipRequestList = new LinkedList<>();
    // User Receiving request from others, e.g. user is A, and B,C,D ---> A, return B,C,D
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "receiver-friendship-request")
    private List<FriendshipRequest> receiverFriendshipRequestList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notificationList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> postList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GroupPost> groupPostList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikeList = new LinkedList<>();
    @OneToMany(mappedBy = "commentSenderUserProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postCommentList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMembership> groupMembershipList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostLike> groupPostLikeList = new LinkedList<>();
    @OneToMany(mappedBy = "groupPostCommentCreator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> groupPostCommentList = new LinkedList<>();

    @Id
    private String email;                       // contain @, unique

    // authentication variable:
    private String password;                    // 8 <= length <= 30, must have at least 1 uppercase, 1 lowercase, 1 number, 1 special character !@#$%^&*<>? and only contain those
    private String securityQuestion;            // choose from 15 questions provided from frontend
    private String securityQuestionAnswer;      // 5 <= length <= 50, any characters are available


    // admin variable:
    private boolean isValidated = true;         // everyone is validated by default
    private String userLevel = "user";          // "admin" / "user"
    private String role = "none";               // "none" / "student" / "instructor" / "employee"
    private LocalDateTime userCreateTime = LocalDateTime.now();


    // user variable (common):
    private String userName;                    // 3 <= length <= 20, only contain A-Za-z0-9!@#$%^&*<>?
    private String gender;                      // "Don't specify" / "they/them" / "she/her" / "he/him" / "other"
    private String firstName = "";              // "" means not specify, 0 <= length <= 50, only advocate a-zA-Z
    private String lastName = "";               // "" means not specify, 0 <= length <= 50, only advocate a-zA-Z
    private String biography = "";              // "" means not specify, 0 <= length <= 255, any characters are available
    private String birthday = "0000-00-00";     // "YYYY-MM-DD", "0000-00-00" represent not specified


    // user variable (distributed):
    private String onlineStatus = "offline";    // "available" / "busy" / "away" / "offline"
    private String profilePictureUrl = "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg";


    public UserProfile() {
    }

    public UserProfileDTO toDto() {
        return new UserProfileDTO(
                birthday,
                email,
                isValidated,
                role,
                onlineStatus,
                userName,
                biography,
                gender,
                firstName,
                lastName,
                profilePictureUrl,
                userLevel,
                userCreateTime,
                postList.size(),
                senderFriendshipList.size() + receiverFriendshipList.size(),
                groupMembershipList.size()
        );
    }

    // Getter and Setter:
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Friendship> getSenderFriendshipList() {
        return senderFriendshipList;
    }

    public void setSenderFriendshipList(List<Friendship> senderFriendshipList) {
        this.senderFriendshipList = senderFriendshipList;
    }

    public List<Friendship> getReceiverFriendshipList() {
        return receiverFriendshipList;
    }

    public void setReceiverFriendshipList(List<Friendship> receiverFriendshipList) {
        this.receiverFriendshipList = receiverFriendshipList;
    }

    public List<FriendshipRequest> getSenderFriendshipRequestList() {
        return senderFriendshipRequestList;
    }

    public void setSenderFriendshipRequestList(List<FriendshipRequest> senderFriendshipRequestList) {
        this.senderFriendshipRequestList = senderFriendshipRequestList;
    }

    public List<FriendshipRequest> getReceiverFriendshipRequestList() {
        return receiverFriendshipRequestList;
    }

    public void setReceiverFriendshipRequestList(List<FriendshipRequest> receiverFriendshipRequestList) {
        this.receiverFriendshipRequestList = receiverFriendshipRequestList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<GroupPost> getGroupPostList() {
        return groupPostList;
    }

    public void setGroupPostList(List<GroupPost> groupPostList) {
        this.groupPostList = groupPostList;
    }

    public List<PostLike> getPostLikeList() {
        return postLikeList;
    }

    public void setPostLikeList(List<PostLike> postLikeList) {
        this.postLikeList = postLikeList;
    }

    public List<PostComment> getPostCommentList() {
        return postCommentList;
    }

    public void setPostCommentList(List<PostComment> postCommentList) {
        this.postCommentList = postCommentList;
    }

    public List<GroupMembership> getGroupMembershipList() {
        return groupMembershipList;
    }

    public void setGroupMembershipList(List<GroupMembership> groupMembershipList) {
        this.groupMembershipList = groupMembershipList;
    }

    public List<GroupPostLike> getGroupPostLikeList() {
        return groupPostLikeList;
    }

    public void setGroupPostLikeList(List<GroupPostLike> groupPostLikeList) {
        this.groupPostLikeList = groupPostLikeList;
    }

    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }
    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDateTime getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(LocalDateTime userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

}
