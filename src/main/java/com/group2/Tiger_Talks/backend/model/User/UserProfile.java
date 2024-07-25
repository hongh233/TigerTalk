package com.group2.Tiger_Talks.backend.model.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.group2.Tiger_Talks.backend.model.Authentication.UserValidation;
import com.group2.Tiger_Talks.backend.model.Authentication.UserValidator;
import com.group2.Tiger_Talks.backend.model.Friend.Friendship;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import com.group2.Tiger_Talks.backend.model.Group.GroupPostLike;
import com.group2.Tiger_Talks.backend.model.Notification.Notification;
import com.group2.Tiger_Talks.backend.model.Post.Post;
import com.group2.Tiger_Talks.backend.model.Post.PostComment;
import com.group2.Tiger_Talks.backend.model.Post.PostLike;
import com.group2.Tiger_Talks.backend.repository.User.UserProfileRepository;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.group2.Tiger_Talks.backend.model.Utils.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfile implements UserValidation, FullyDTOConvertible<UserProfileDTO> {
    @Id
    private String email;

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
    private List<GroupMembership> groupMembershipList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostLike> groupPostLikeList = new LinkedList<>();
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikeList = new LinkedList<>();
    @OneToMany(mappedBy = "commentSenderUserProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> postCommentList = new LinkedList<>();
    private String password;
    private String userLevel = UserLevel.USER;   // admin / user
    private String status = UserStatus.PENDING;      // blocked / pending / active
    private boolean isValidated = false;
    private String[] securityQuestions;
    private String[] securityQuestionsAnswer;
    private String role = Role.DEFAULT;        // default / student / instructor / employee
    private String onlineStatus = OnlineStatus.OFFLINE;
    @Column(unique = true)
    private String userName;
    private String personalInterest;
    private String location;
    private String postalCode;
    private String biography;
    private String profileAccessLevel = ProfileAccessLevel.PRIVATE;  // public / protected / private
    private String phoneNumber;
    private int age;
    private String gender;
    private String firstName;
    private String lastName;
    private String profilePictureUrl = DEFAULT_PROFILE_PICTURE;

    public UserProfile(String firstName,
                       String lastName,
                       int age,
                       String gender,
                       String userName,
                       String email,
                       String password,
                       String[] securityQuestionsAnswer,
                       String[] securityQuestions) {
        this.email = email;
        this.password = password;
        this.securityQuestionsAnswer = securityQuestionsAnswer;
        this.securityQuestions = securityQuestions;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserProfile() {
    }


    public static Optional<String> verifyBasics(UserProfile userProfile, UserProfileRepository userProfileRepository, boolean isNewUser) {
        Optional<String> intrinsicsResult = UserValidator.verifyUserIntrinsics(userProfile, userProfileRepository, isNewUser);

        if (intrinsicsResult.isPresent()) {
            return intrinsicsResult;
        }

        return getExtraValidationOptions(userProfileRepository, isNewUser)
                .stream()
                .map(validation -> validation.apply(userProfile))
                .filter(Optional::isPresent)
                .findFirst()
                .orElseGet(Optional::empty);
    }

    private static List<Function<UserProfile, Optional<String>>> getExtraValidationOptions(UserProfileRepository userProfileRepository, boolean isNewUser) {
        return List.of(
                UserValidator::validatePasswordLength,
                UserValidator::validatePasswordUppercase,
                UserValidator::validatePasswordLowercase,
                UserValidator::validatePasswordNumber,
                UserValidator::validatePasswordSpecialCharacter
        );
    }

    public List<PostLike> getPostLikeList() {
        return postLikeList;
    }

    // Getters and setters

    public List<GroupPostLike> getGroupPostLikeList() {
        return groupPostLikeList;
    }

    public String email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String[] getSecurityQuestionsAnswer() {
        return securityQuestionsAnswer;
    }

    public void setSecurityQuestionsAnswer(String[] securityQuestionsAnswer) {
        this.securityQuestionsAnswer = securityQuestionsAnswer;
    }

    public String[] getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(String[] securityQuestions) {
        this.securityQuestions = securityQuestions;
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

    public String userName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonalInterest() {
        return personalInterest;
    }

    public void setPersonalInterest(String personalInterest) {
        this.personalInterest = personalInterest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfileAccessLevel() {
        return profileAccessLevel;
    }

    public void setProfileAccessLevel(String profileAccessLevel) {
        this.profileAccessLevel = profileAccessLevel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int age() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String firstName() {
        return firstName;
    }

    public void firstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


    public List<GroupMembership> getGroupMembershipList() {
        return groupMembershipList;
    }

    public void setGroupMembershipList(List<GroupMembership> groupMembershipList) {
        this.groupMembershipList = groupMembershipList;
    }

    public List<PostComment> getPostCommentList() {
        return postCommentList;
    }

    public void setPostCommentList(List<PostComment> postCommentList) {
        this.postCommentList = postCommentList;
    }


    public Optional<String> findAnswerForSecurityQuestion(String securityQuestion) {
        for (int i = 0; i < securityQuestions.length; i++) {
            if (securityQuestions[i].equals(securityQuestion)) {
                return Optional.of(securityQuestionsAnswer[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile other = (UserProfile) o;
        return this.email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public UserProfileDTO toDto() {
        return new UserProfileDTO(
                age,
                email,
                status,
                isValidated,
                role,
                onlineStatus,
                userName,
                biography,
                profileAccessLevel,
                gender,
                firstName,
                lastName,
                profilePictureUrl,
                userLevel
        );
    }

    @Override
    public void updateFromDto(UserProfileDTO dto) {
        this.age = dto.age();
        this.email = dto.email();
        this.status = dto.status();
        this.isValidated = dto.validated();
        this.role = dto.role();
        this.onlineStatus = dto.onlineStatus();
        this.userName = dto.userName();
        this.biography = dto.biography();
        this.profileAccessLevel = dto.profileAccessLevel();
        this.gender = dto.gender();
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.userLevel = dto.userLevel();
        this.profilePictureUrl = dto.profilePictureUrl();
    }
}
