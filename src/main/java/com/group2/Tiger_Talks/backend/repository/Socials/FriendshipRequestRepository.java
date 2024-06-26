package com.group2.Tiger_Talks.backend.repository.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Integer> {

    List<FriendshipRequest> findByReceiver(@Param("receiver") UserProfile receiver);


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  unidirectional: (sender ---> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiver(UserProfile sender, UserProfile receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  bidirectional: (sender <--> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiverOrReceiverAndSender(UserProfile sender1, UserProfile receiver1, UserProfile sender2, UserProfile receiver2);
}