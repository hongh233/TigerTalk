package com.group2.Tiger_Talks.backend.service.Search;

import java.util.List;
import java.util.Optional;

/**
 * Functional interface for performing a search operation.
 *
 * <p>This interface defines methods for executing search operations. Implementations should define
 * how to perform searches based on provided query parameters. The search can include optional constraints
 * to refine the search results.</p>
 *
 * @param <DTO> the type of DTO (Data Transfer Object) returned by the search operation. This type represents
 *              the result of the search operation and will be used to encapsulate the search results.
 */
@FunctionalInterface
public interface Searchable<DTO> {

    /**
     * Performs a search operation based on the given search query and user email.
     * <p>This method provides a default implementation for searches that do not include additional constraints.
     * It delegates the search operation to the more detailed {@link #search(String, String, Optional)} method
     * by passing an empty {@link Optional} for constraints.</p>
     *
     * @param searchQuery the search query string. This string is used to filter the search results based on
     *                    the criteria defined by the implementation.
     * @param userEmail   the email of the user performing the search. This parameter can be used to personalize
     *                    the search results or enforce user-specific access control.
     * @return a list of DTO representing the result of the search operation. The returned list contains instances
     * of the DTO type, which encapsulate the search results and are typically used for transferring data
     * between layers of the application.
     */
    default List<DTO> search(String searchQuery, String userEmail) {
        return search(searchQuery, userEmail, Optional.empty());
    }

    /**
     * Performs a search operation based on the given parameters, including optional constraints.
     * <p>This method should be implemented to define how to perform searches with additional constraints. The
     * constraints parameter allows the caller to provide extra filtering criteria that can influence the search
     * results.</p>
     *
     * @param searchQuery the search query string. This string is used to filter the search results based on
     *                    the criteria defined by the implementation.
     * @param userEmail   the email of the user performing the search. This parameter can be used to personalize
     *                    the search results or enforce user-specific access control.
     * @param constraints an {@link Optional} containing an array of constraints. These constraints are additional
     *                    criteria that can be used to refine the search results. If no constraints are provided,
     *                    the {@link Optional} will be empty.
     * @return a list of DTO representing the result of the search operation. The returned list contains instances
     * of the DTO type, which encapsulate the search results and are typically used for transferring data
     * between layers of the application.
     */
    List<DTO> search(String searchQuery, String userEmail, Optional<String[]> constraints);
}
