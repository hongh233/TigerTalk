package com.group2.Tiger_Talks.backend.repository.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {
    List<GroupMembership> findByGroup_GroupId(int groupId);
    List<GroupMembership> findByUserProfile_Email(String email);
}
