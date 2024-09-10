package tigertalk.service._implementation.Friend;

import tigertalk.model.Friend.Friendship;
import tigertalk.model.Friend.FriendshipRequest;
import tigertalk.model.Friend.FriendshipRequestDTO;
import tigertalk.model.Friend.UserProfileDTOFriendship;
import tigertalk.model.Notification.Notification;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.Friend.FriendshipRepository;
import tigertalk.repository.Friend.FriendshipRequestRepository;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Friend.FriendshipRequestService;
import tigertalk.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;


    // We have E->A, D->A, get(A) will get E, D
    @Override
    public List<FriendshipRequestDTO> getAllFriendRequests(String email) {
        UserProfile user = userProfileRepository.findById(email).get();
        List<FriendshipRequestDTO> list = new ArrayList<>();

        for (FriendshipRequest friendshipRequest : friendshipRequestRepository.findByReceiver(user)) {
            FriendshipRequestDTO dto = friendshipRequest.toDto();
            list.add(dto);
        }
        return list;
    }

    /* We have A -> E, E -> J, get all these 6 request relations
     *         B -> E, E -> K
     *         C -> E, E -> L
     */
    @Override
    public List<FriendshipRequestDTO> getAllFriendRequests_doubleSided(String email) {
        UserProfile user = userProfileRepository.findById(email).get();
        List<FriendshipRequestDTO> list = new ArrayList<>();

        for (FriendshipRequest friendshipRequest : friendshipRequestRepository.findByReceiver(user)) {
            FriendshipRequestDTO dto = friendshipRequest.toDto();
            list.add(dto);
        }

        for (FriendshipRequest friendshipRequest : friendshipRequestRepository.findBySender(user)) {
            FriendshipRequestDTO dto = friendshipRequest.toDto();
            list.add(dto);
        }
        return list;
    }

    @Override
    public FriendshipRequestDTO sendFriendshipRequest(String senderEmail, String receiverEmail) {
        UserProfile sender = userProfileRepository.findById(senderEmail).get();
        UserProfile receiver = userProfileRepository.findById(receiverEmail).get();

        FriendshipRequest friendshipRequest = new FriendshipRequest(sender, receiver);
        friendshipRequest = friendshipRequestRepository.save(friendshipRequest);

        notificationService.createNotification(new Notification(
                receiver,
                "You have a new friend request from " + senderEmail,
                "FriendshipRequestSend"
        ));

        return friendshipRequest.toDto();
    }

    @Override
    public UserProfileDTOFriendship acceptFriendshipRequest(Integer friendshipRequestId) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId).get();
        Friendship friendship = new Friendship(friendshipRequest.getSender(), friendshipRequest.getReceiver());
        friendship = friendshipRepository.save(friendship);
        friendshipRequestRepository.delete(friendshipRequest);
        notificationService.createNotification(new Notification(
                friendshipRequest.getSender(),
                "Your friend request to " + friendshipRequest.getReceiver().getEmail() + " has been accepted.",
                "FriendshipRequestAccept"
        ));
        return new UserProfileDTOFriendship(friendshipRequest.getSender(), friendship);
    }

    @Override
    public Optional<String> rejectFriendshipRequest(Integer friendshipRequestId) {
        Optional<FriendshipRequest> friendshipRequestOptional = friendshipRequestRepository.findById(friendshipRequestId);
        if (friendshipRequestOptional.isPresent()) {
            FriendshipRequest friendshipRequest = friendshipRequestOptional.get();
            friendshipRequestRepository.delete(friendshipRequest);
            Notification notification = new Notification(
                    friendshipRequest.getSender(),
                    "Your friend request to " + friendshipRequest.getReceiver().getEmail() + " has been rejected.",
                    "FriendshipRequestReject"
            );
            return notificationService.createNotification(notification);
        } else {
            throw new IllegalStateException("Friendship request ID does not exist!");
        }
    }

    @Override
    public boolean areFriendshipRequestExist(String email1, String email2) {
        Optional<UserProfile> senderOptional = userProfileRepository.findById(email1);
        if (senderOptional.isEmpty()) {
            throw new IllegalStateException("Sender not found");
        }
        UserProfile sender = senderOptional.get();

        Optional<UserProfile> receiverOptional = userProfileRepository.findById(email2);
        if (receiverOptional.isEmpty()) {
            throw new IllegalStateException("Receiver not found");
        }
        UserProfile receiver = receiverOptional.get();

        return friendshipRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent() ||
                friendshipRequestRepository.findBySenderAndReceiver(receiver, sender).isPresent();
    }


}
