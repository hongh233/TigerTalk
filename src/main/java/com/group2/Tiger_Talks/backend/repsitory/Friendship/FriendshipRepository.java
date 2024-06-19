package com.group2.Tiger_Talks.backend.repsitory.Friendship;

import com.group2.Tiger_Talks.backend.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    /* When we call this method, when the userFriendshipSender and userFriendshipReceiver are the same,
       we are find all the friendship of this person
     */
    List<Friendship> findByUserFriendshipSenderOrUserFriendshipReceiver(
            String userFriendshipSender,
            String userFriendshipReceiver);


    // find exact friendship: userFriendshipSender ---> userFriendshipReceiver (it is not complete search)
    Optional<Friendship> findByUserFriendshipSenderAndUserFriendshipReceiver(
            String userFriendshipSender,
            String userFriendshipReceiver);


    // find exact friendship: userFriendshipSender <---> userFriendshipReceiver (it is a complete search)
    Optional<Friendship> findByUserFriendshipSenderAndUserFriendshipReceiverOrUserFriendshipReceiverAndUserFriendshipSender(
            String userFriendshipSender1,
            String userFriendshipReceiver1,
            String userFriendshipSender2,
            String userFriendshipReceiver2
    );


}