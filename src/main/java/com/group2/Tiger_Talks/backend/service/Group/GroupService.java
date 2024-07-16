package com.group2.Tiger_Talks.backend.service.Group;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;
import com.group2.Tiger_Talks.backend.model.Group.GroupMembershipDTO;
import com.group2.Tiger_Talks.backend.model.Group.GroupUpdate;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    /**
     * Creates a new group with the specified name, creator, and privacy setting.
     *
     * @param groupName   the name of the group to be created.
     * @param creatorEmail the email of the user creating the group.
     * @param isPrivate   whether the group is private or public.
     * @return an Optional containing a success message if the group is created,
     * or an error message if the creator email is not found.
     */
    Optional<String> createGroup(String groupName, String creatorEmail, boolean isPrivate);

    /**
     * Adds a user to a group by their email and group ID.
     *
     * @param email   the email of the user joining the group.
     * @param groupId the ID of the group to join.
     * @return an Optional containing a success message if the user is added,
     * or an error message if the user or group is not found or the user is already a member.
     */
    Optional<String> joinGroup(String email, int groupId);

    /**
     * Retrieves a list of all groups.
     *
     * @return a list of GroupDTO objects representing all groups.
     */
    List<GroupDTO> getAllGroups();

    /**
     * Retrieves details of a specific group by its ID.
     *
     * @param groupId the ID of the group to retrieve.
     * @return An optional of a GroupDTO object representing the group, or empty if the group is not found.
     */
    Optional<GroupDTO> getGroup(int groupId);

    /**
     * Retrieves a list of all groups that a user is a member of or is the creator of.
     *
     * @param userEmail the email of the user.
     * @return a list of GroupDTO objects representing the groups the user is associated with.
     */
    List<GroupDTO> getAllGroupsByUser(String userEmail);

    /**
     * Updates the information of an existing group.
     *
     * @param groupUpdate the updated group information.
     * @return an Optional containing a success message if the group is updated,
     * or an error message if the group ID is not found.
     */
    Optional<String> updateGroupInfo(GroupUpdate groupUpdate);

    /**
     * Deletes a group by its ID.
     *
     * @param groupId the ID of the group to delete.
     * @return an Optional containing a success message if the group is deleted,
     * or an error message if the group ID is not found.
     */
    Optional<String> deleteGroup(int groupId);

    /**
     * Deletes a group membership by its ID.
     *
     * @param groupMembershipId the ID of the group membership to delete.
     * @return an Optional containing a success message if the group membership is deleted,
     * or an error message if the group membership ID is not found.
     */
    Optional<String> deleteGroupMembership(int groupMembershipId);


    /**
     * Retrieves a list of group members by the group ID.
     *
     * @param groupId the ID of the group.
     * @return a list of GroupMembershipDTO objects representing the members of the group.
     */
    List<GroupMembershipDTO> getGroupMembersByGroupId(int groupId);


    /**
     * Get the membership id of a specific group by their email and group ID.
     *
     * @param userEmail the email of the user.
     * @param groupId the ID of the group.
     * @return The membership id if the user is a member of the group, Empty otherwise.
     */
    Optional<Integer> getMemberShipId(String userEmail, int groupId);
}
