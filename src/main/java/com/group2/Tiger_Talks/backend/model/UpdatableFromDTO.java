package com.group2.Tiger_Talks.backend.model;

@FunctionalInterface
public interface UpdatableFromDTO<DTO> {
    /**
     * Updates the implementing object using the provided DTO.
     *
     * @param dto the DTO used to update the implementing object.
     */
    void updateFromDto(DTO dto);
}
