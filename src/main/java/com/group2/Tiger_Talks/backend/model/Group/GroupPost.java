package com.group2.Tiger_Talks.backend.model.Group;

import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import com.group2.Tiger_Talks.backend.service._implementation.Group.GroupPostServiceImpl;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GroupPost implements FullyDTOConvertible<GroupPostDTO> {
    // TODO: Add number of likes to a group post and number of comments

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPostId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> groupPostCommentList = new LinkedList<>();

    private LocalDateTime postCreateTime = LocalDateTime.now();

    private String groupPostContent;

    private String groupPostSenderEmail;

    private String groupPostPictureURL;

    public GroupPost(Group group,
                     String groupPostContent,
                     String groupPostSenderEmail,
                     String groupPostPictureURL) {
        this.group = group;
        this.groupPostContent = groupPostContent;
        this.groupPostSenderEmail = groupPostSenderEmail;
        this.groupPostPictureURL = groupPostPictureURL;
    }

    public GroupPost() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LocalDateTime getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(LocalDateTime messageCreateTime) {
        this.postCreateTime = messageCreateTime;
    }

    public String getGroupPostContent() {
        return groupPostContent;
    }

    public void setGroupPostContent(String groupPostContent) {
        this.groupPostContent = groupPostContent;
    }

    public int getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(int groupPostId) {
        this.groupPostId = groupPostId;
    }

    public String getGroupPostSenderEmail() {
        return groupPostSenderEmail;
    }

    public void setGroupPostSenderEmail(String groupPostSenderEmail) {
        this.groupPostSenderEmail = groupPostSenderEmail;
    }

    public List<GroupPostComment> getGroupPostCommentList() {
        return groupPostCommentList;
    }

    public void setGroupPostCommentList(List<GroupPostComment> groupPostCommentList) {
        this.groupPostCommentList = groupPostCommentList;
    }

    public String getGroupPostPictureURL() {
        return groupPostPictureURL;
    }

    public void setGroupPostPictureURL(String groupPostPictureURL) {
        this.groupPostPictureURL = groupPostPictureURL;
    }

    @Override
    public GroupPostDTO toDto() {
        UserProfile userProfile = GroupPostServiceImpl.findUserProfileByEmail(this).orElseThrow();
        return new GroupPostDTO(
                this.getGroupPostId(),
                this.getGroupPostSenderEmail(),
                this.getGroupPostContent(),
                this.getPostCreateTime(),
                userProfile.getUserName(),
                userProfile.getProfilePictureUrl(),
                this.getGroupPostPictureURL()
        );
    }

    @Override
    public void updateFromDto(GroupPostDTO groupPostDTO) {
        this.groupPostContent = groupPostDTO.groupPostContent();
        this.postCreateTime = groupPostDTO.groupPostCreateTime();
        this.groupPostPictureURL = groupPostDTO.postPictureURL();
    }
}