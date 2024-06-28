package com.group2.Tiger_Talks.backend.model.Socials;

public class FriendshipDTO {

    private Integer id;
    private String senderEmail;
    private String senderName;
    private String receiverEmail;
    private String receiverName;

    private String senderProfilePictureUrl;
    private String receiverProfilePictureUrl;

    public FriendshipDTO(Friendship friendship) {
        this.id = friendship.getFriendshipId();
        this.senderEmail = friendship.getSender().getEmail();
        this.senderName = friendship.getSender().getUserName();
        this.receiverEmail = friendship.getReceiver().getEmail();
        this.receiverName = friendship.getReceiver().getUserName();
        this.senderProfilePictureUrl = friendship.getSender().getProfilePictureUrl();
        this.receiverProfilePictureUrl= friendship.getReceiver().getProfilePictureUrl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderProfilePictureUrl() {
        return senderProfilePictureUrl;
    }

    public void setSenderProfilePictureUrl(String senderProfilePictureUrl) {
        this.senderProfilePictureUrl = senderProfilePictureUrl;
    }

    public String getReceiverProfilePictureUrl() {
        return receiverProfilePictureUrl;
    }

    public void setReceiverProfilePictureUrl(String receiverProfilePictureUrl) {
        this.receiverProfilePictureUrl = receiverProfilePictureUrl;
    }
}