package tigertalk.model.Friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Friendship {

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    @JsonBackReference(value = "sender-friendship")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    @JsonBackReference(value = "receiver-friendship")
    private UserProfile receiver;

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipMessage> messages = new LinkedList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    private LocalDateTime createTime = LocalDateTime.now();

    public Friendship() {

    }

    public Friendship(UserProfile sender, UserProfile receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public UserProfile getSender() {
        return sender;
    }

    public void setSender(UserProfile sender) {
        this.sender = sender;
    }

    public UserProfile getReceiver() {
        return receiver;
    }

    public void setReceiver(UserProfile receiver) {
        this.receiver = receiver;
    }

    public List<FriendshipMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FriendshipMessage> messages) {
        this.messages = messages;
    }

    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


}

