package com.group2.Tiger_Talks.backend.repository.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Integer> {

    List<FriendshipRequest> findByReceiver(
            @Param("receiver") UserTemplate receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  unidirectional: (sender ---> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiver(
            @Param("sender") UserTemplate sender,
            @Param("receiver") UserTemplate receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship request between two person
     *  bidirectional: (sender <--> receiver)
     */
    Optional<FriendshipRequest> findBySenderAndReceiverOrReceiverAndSender(
            @Param("sender1") UserTemplate sender1,
            @Param("receiver1") UserTemplate receiver1,
            @Param("sender2") UserTemplate sender2,
            @Param("receiver2") UserTemplate receiver2
    );

}