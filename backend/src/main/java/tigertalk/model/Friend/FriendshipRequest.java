package tigertalk.model.Friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest {

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    @JsonBackReference("sender-friendship-request")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    @JsonBackReference("receiver-friendship-request")
    private UserProfile receiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipRequestId;

    private String senderEmailTemp;
    private String receiverEmailTemp;

    private String senderProfilePictureUrlTemp;
    private String receiverProfilePictureUrlTemp;

    private String senderUserNameTemp;
    private String receiverUserNameTemp;

    private LocalDateTime createTime = LocalDateTime.now();

    public FriendshipRequest() {
    }

    public FriendshipRequest(UserProfile sender,
                             UserProfile receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderEmailTemp = sender.getEmail();
        this.receiverEmailTemp = receiver.getEmail();
        this.senderProfilePictureUrlTemp = sender.getProfilePictureUrl();
        this.receiverProfilePictureUrlTemp = receiver.getProfilePictureUrl();
        this.senderUserNameTemp = sender.getUserName();
        this.receiverUserNameTemp = receiver.getUserName();
    }

    public FriendshipRequestDTO toDto() {
        return new FriendshipRequestDTO(
                this.friendshipRequestId,
                this.sender.getEmail(),
                this.sender.getUserName(),
                this.receiver.getEmail(),
                this.receiver.getUserName(),
                this.sender.getProfilePictureUrl(),
                this.receiver.getProfilePictureUrl(),
                this.createTime,
                this.sender.getOnlineStatus(),
                this.receiver.getOnlineStatus()
        );
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

    public Integer getFriendshipRequestId() {
        return friendshipRequestId;
    }

    public void setFriendshipRequestId(Integer friendshipRequestId) {
        this.friendshipRequestId = friendshipRequestId;
    }

    public String getSenderEmailTemp() {
        return senderEmailTemp;
    }

    public void setSenderEmailTemp(String senderEmailTemp) {
        this.senderEmailTemp = senderEmailTemp;
    }

    public String getReceiverEmailTemp() {
        return receiverEmailTemp;
    }

    public void setReceiverEmailTemp(String receiverEmailTemp) {
        this.receiverEmailTemp = receiverEmailTemp;
    }

    public String getSenderProfilePictureUrlTemp() {
        return senderProfilePictureUrlTemp;
    }

    public void setSenderProfilePictureUrlTemp(String senderProfilePictureUrlTemp) {
        this.senderProfilePictureUrlTemp = senderProfilePictureUrlTemp;
    }

    public String getReceiverProfilePictureUrlTemp() {
        return receiverProfilePictureUrlTemp;
    }

    public void setReceiverProfilePictureUrlTemp(String receiverProfilePictureUrlTemp) {
        this.receiverProfilePictureUrlTemp = receiverProfilePictureUrlTemp;
    }

    public String getSenderUserNameTemp() {
        return senderUserNameTemp;
    }

    public void setSenderUserNameTemp(String senderUserNameTemp) {
        this.senderUserNameTemp = senderUserNameTemp;
    }

    public String getReceiverUserNameTemp() {
        return receiverUserNameTemp;
    }

    public void setReceiverUserNameTemp(String receiverUserNameTemp) {
        this.receiverUserNameTemp = receiverUserNameTemp;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


}
