package com.group2.Tiger_Talks.backend.model;

@FunctionalInterface
public interface ToDTO<DTO> {
    /**
     * Converts the implementing object to a DTO.
     *
     * @return the DTO representation of the implementing object.
     */
    DTO toDto();
}
