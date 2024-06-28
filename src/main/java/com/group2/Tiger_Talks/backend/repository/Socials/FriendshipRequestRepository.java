package com.group2.Tiger_Talks.backend.repository.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Integer> {

    List<FriendshipRequest> findByReceiver(
            @Param("receiver") UserProfile receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  unidirectional: (sender ---> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiver(
           @Param("sender")  UserProfile sender,
           @Param("receiver")  UserProfile receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  bidirectional: (sender <--> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiverOrReceiverAndSender(
            @Param("sender1") UserProfile sender1,
            @Param("receiver1") UserProfile receiver1,
            @Param("sender2") UserProfile sender2,
            @Param("receiver2") UserProfile receiver2);


    Optional<FriendshipRequest> findBySender(
            @Param("sender") UserProfile sender
    );


}