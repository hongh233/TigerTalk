package com.group2.Tiger_Talks.backend.repsitory.Socials;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    /* 
        When we call this method, when the sendersEmail and receiversEmail are the same,
        we are finding all the friendship of this person
     */
    List<Friendship> findBySendersEmailOrReceiversEmail(
            String sendersEmail,
            String receiversEmail
    );


    // find exact friendship: sendersEmail ---> receiversEmail (it is not complete search)
    Optional<Friendship> findBySendersEmailAndReceiversEmail(
            String sendersEmail,
            String receiversEmail);


    // find exact friendship: sendersEmail <---> receiversEmail (it is a complete search)
    Optional<Friendship> findBySendersEmailAndReceiversEmailOrReceiversEmailAndSendersEmail(
            String sendersEmail1,
            String receiversEmail1,
            String sendersEmail2,
            String receiversEmail2
    );


}