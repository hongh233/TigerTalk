package com.group2.Tiger_Talks.backend.model.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post implements FullyDTOConvertible<PostDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "email")
    @JsonBackReference
    private UserProfile userProfile;

    private String content;
    private int numOfLike;
    private String associatedImageURL;
    private Boolean edited;
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PostComment> postComments = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    public Post() {
    }

    public Post(UserProfile userProfile, String content, String associatedImageURL) {
        this.userProfile = userProfile;
        this.content = content;
        this.associatedImageURL = associatedImageURL;
        this.numOfLike = 0;
        this.edited = false;
    }

    public String getAssociatedImageURL() {
        return associatedImageURL;
    }

    public void setAssociatedImageURL(String associatedImageURL) {
        this.associatedImageURL = associatedImageURL;
    }

    public List<PostLike> getLikes() {
        return postLikes;
    }

    public void setLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public int getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.numOfLike = numOfLike;
    }

    public List<PostComment> getComments() {
        return postComments;
    }

    public void setComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public void addComment(PostComment postComment) {
        postComments.add(postComment);
        postComment.setPost(this);
    }

    public void removeComment(PostComment postComment) {
        postComments.remove(postComment);
        postComment.setPost(null);
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @Override
    public PostDTO toDto() {
        return new PostDTO(
                this.getPostId(),
                this.getUserProfile().getEmail(),
                this.getContent(),
                this.getTimestamp(),
                this.getNumOfLike(),
                this.getUserProfile().getUserName(),
                this.getUserProfile().getProfilePictureUrl(),
                this.getAssociatedImageURL(),
                this.edited
        );
    }


    @Override
    public void updateFromDto(PostDTO postDTO) {
        this.content = postDTO.content();
        this.timestamp = getTimestamp();
        this.associatedImageURL = postDTO.postImageURL();
        this.edited=postDTO.edited();
        /*
            Num of likes should not be updated as this would cause a break of synchronization
            between the post like list and num_of_likes
         */
    }
}