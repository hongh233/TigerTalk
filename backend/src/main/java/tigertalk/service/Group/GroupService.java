package tigertalk.service.Group;

import tigertalk.model.Group.GroupDTO;
import tigertalk.model.Group.GroupMembershipDTO;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<String> createGroup(String groupName, String creatorEmail, boolean isPrivate, String interest);


    Optional<String> joinGroup(String email, int groupID);


    List<GroupDTO> getAllGroups();


    Optional<GroupDTO> getGroup(int groupId);


    List<GroupDTO> getAllGroupsByUser(String userEmail);


    Optional<String> updateGroupInfo(GroupDTO groupUpdate);


    Optional<String> deleteGroup(int groupId);


    Optional<String> deleteGroupMembership(int groupMembershipId);


    List<GroupMembershipDTO> getGroupMembersByGroupId(int groupId);


    Optional<Integer> getMemberShipId(String userEmail, int groupId);


    Optional<String> transferGroupOwnership(int previousOwnerMembershipId, int newOwnerMembershipId);
}
