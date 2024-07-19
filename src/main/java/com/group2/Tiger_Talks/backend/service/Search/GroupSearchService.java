package com.group2.Tiger_Talks.backend.service.Search;

import com.group2.Tiger_Talks.backend.model.Group.GroupDTO;

import java.util.List;

/**
 * Service interface for searching groups.
 * <p>
 * This interface provides methods to search and retrieve groups based on specific criteria.
 * </p>
 */
public interface GroupSearchService {

    /**
     * Finds public groups that match the given group name and where the user is not yet a member.
     * <p>
     * This method searches for groups whose names start with the specified {@code groupName} (case-insensitive)
     * and filters out private groups. Additionally, it ensures that the user identified by {@code userEmail}
     * is not already a member of the found groups.
     * </p>
     *
     * @param groupName the name of the group to search for (case-insensitive)
     * @param userEmail the email of the user to check for membership
     * @return a list of {@link GroupDTO} representing the groups that match the search criteria
     * @throws IllegalArgumentException if {@code groupName} or {@code userEmail} is null or empty
     */
    List<GroupDTO> findPublicGroupMatch(String groupName, String userEmail);
}
