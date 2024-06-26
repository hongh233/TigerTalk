package com.group2.Tiger_Talks.backend.repository.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    /*  When the sendersEmail and receiversEmail are the same,
     *  we are finding all the friendship of this person
     */
    List<Friendship> findBySenderOrReceiver(
            @Param("sender") UserProfile sender,
            @Param("receiver") UserProfile receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship between two person
     *  unidirectional: (sender ---> receiver)
     */
    Optional<Friendship> findBySenderAndReceiver(
            @Param("sender") UserProfile sender,
            @Param("receiver") UserProfile receiver
    );


    /*  When the sendersEmail and receiversEmail are different,
     *  we are finding a specific friendship between two person
     *  bidirectional: (sender <--> receiver)
     */
    Optional<Friendship> findBySenderAndReceiverOrReceiverAndSender(
            @Param("sender1") UserProfile sender1,
            @Param("receiver1") UserProfile receiver1,
            @Param("sender2") UserProfile sender2,
            @Param("receiver2") UserProfile receiver2
    );

}