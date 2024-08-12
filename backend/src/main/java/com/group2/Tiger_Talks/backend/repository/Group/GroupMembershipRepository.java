package com.group2.Tiger_Talks.backend.repository.Group;

import com.group2.Tiger_Talks.backend.model.Group.Group;
import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {
    List<GroupMembership> findByGroup_GroupId(int groupId);

    List<GroupMembership> findByUserProfile_Email(String email);

    Optional<GroupMembership> findByGroupAndUserProfileEmail(Group group, String email);

    @Query("SELECT gm FROM GroupMembership gm WHERE gm.group.groupId = :groupId AND gm.isCreator = true")
    Optional<GroupMembership> findGroupCreatorByGroupId(@Param("groupId") int groupId);
}
