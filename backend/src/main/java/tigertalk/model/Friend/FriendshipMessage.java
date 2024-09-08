package tigertalk.model.Friend;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FriendshipMessage {

    @ManyToOne
    @JoinColumn(name = "friendship_id", nullable = false)
    private Friendship friendship;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserProfile receiver;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    private LocalDateTime createTime = LocalDateTime.now();

    private String messageContent;

    private boolean isRead = false;

    public FriendshipMessage() {
    }

    public FriendshipMessageDTO toDto() {
        return new FriendshipMessageDTO(
                this.messageId,
                this.createTime,
                this.messageContent,
                this.sender.getEmail(),
                this.sender.getUserName(),
                this.sender.getProfilePictureUrl(),
                this.sender.getOnlineStatus(),
                this.receiver.getEmail(),
                this.receiver.getUserName(),
                this.receiver.getProfilePictureUrl(),
                this.receiver.getOnlineStatus(),
                this.isRead,
                this.friendship.getFriendshipId()
        );
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

}
