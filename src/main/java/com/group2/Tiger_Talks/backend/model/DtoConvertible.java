package com.group2.Tiger_Talks.backend.model;

/**
 * Interface for objects that can be converted to and updated from a Data Transfer Object (DTO).
 *
 * @param <DTO> the type of DTO that the implementing class will use.
 *
 *              <p>This interface provides methods for converting an object to its DTO and updating an object from its DTO.
 *              Implementing this interface allows for a consistent approach to transforming data between domain models and
 *              their DTO representations.</p>
 *
 *              <p>Example usage:</p>
 *              <pre>
 *                           {@code
 *                           // User DTO class
 *                           public class UserDTO {
 *                               private String username;
 *                               private String email;
 *                           }
 *
 *                           // User class implementing DtoConvertible
 *                           public class User implements DtoConvertible<UserDTO> {
 *                               private String username;
 *                               private String email;
 *
 *                               // Constructor
 *                               public User(String username, String email) {...}
 *
 *                               @Override
 *                               public UserDTO toDto() {
 *                                   UserDTO dto = new UserDTO();
 *                                   dto.username = this.username;
 *                                   dto.email = this.email;
 *                                   return dto;
 *                               }
 *
 *                               @Override
 *                               public void updateFromDto(UserDTO dto) {
 *                                   this.username = dto.getUsername();
 *                                   this.email = dto.getEmail();
 *                               }
 *                           }
 *
 *                           // Example usage
 *                           public class ExampleUsage {
 *                               public static void main(String[] args) {
 *                                   // Create a User instance
 *                                   User user = new User("john_doe", "john.doe@example.com");
 *
 *                                   // Convert User to UserDTO
 *                                   UserDTO userDto = user.toDto();
 *                                   System.out.println("UserDTO: " + userDto.getUsername() + ", " + userDto.getEmail());
 *
 *                                   // Create a new UserDTO with updated information
 *                                   UserDTO updatedDto = new UserDTO();
 *                                   updatedDto.setUsername("john_smith");
 *                                   updatedDto.setEmail("john.smith@example.com");
 *
 *                                   // Update the User instance from the UserDTO
 *                                   user.updateFromDto(updatedDto);
 *                                   System.out.println("Updated User: " + user.getUsername() + ", " + user.getEmail());
 *                               }
 *                           }
 *                           }
 *                           </pre>
 */
public interface DtoConvertible<DTO> {

    /**
     * Converts the implementing object to a DTO.
     *
     * @return the DTO representation of the implementing object.
     */
    DTO toDto();

    /**
     * Updates the implementing object using the provided DTO.
     *
     * @param dto the DTO used to update the implementing object.
     */
    void updateFromDto(DTO dto);
}
