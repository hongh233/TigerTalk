package com.group2.Tiger_Talks.backend.service._implementation.Friend;

import com.group2.Tiger_Talks.backend.repository.Friend.FriendshipMessageRepository;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipMessageServiceImpl implements FriendshipMessageService {

    @Autowired
    private FriendshipMessageRepository friendshipMessageRepository;


}
