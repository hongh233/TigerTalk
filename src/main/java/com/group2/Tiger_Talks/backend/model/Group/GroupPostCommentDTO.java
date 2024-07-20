package com.group2.Tiger_Talks.backend.model.Group;

import java.time.LocalDateTime;

public record GroupPostCommentDTO(
        int groupPostCommentId,
        String groupPostCommentContent,
        LocalDateTime groupPostCommentCreateTime,
        String groupPostCommentSenderUserName,
        String groupPostCommentSenderProfilePictureURL,
        String senderEmail
) {
}
