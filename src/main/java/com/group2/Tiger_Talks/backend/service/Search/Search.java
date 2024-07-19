package com.group2.Tiger_Talks.backend.service.Search;

import java.util.List;

/**
 * Functional interface for performing a search operation.
 *
 * @param <DTO> the type of DTO (Data Transfer Object) returned by the search operation.
 */
@FunctionalInterface
public interface Search<DTO> {
    /**
     * Performs a search operation based on the given parameters.
     *
     * @param searchQuery the search query string.
     * @param userEmail   the email of the user performing the search.
     * @return a list of DTO representing the result of the search operation.
     */
    List<DTO> search(String searchQuery, String userEmail);
}
