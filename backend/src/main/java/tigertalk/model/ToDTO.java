package tigertalk.model;

/**
 * A functional interface for converting an object to a Data Transfer Object (DTO).
 *
 * @param <DTO> the type of the DTO to which the object will be converted
 */
@FunctionalInterface
public interface ToDTO<DTO> {
    /**
     * Converts the implementing object to its DTO representation.
     *
     * @return the DTO representation of the implementing object
     */
    DTO toDto();
}
