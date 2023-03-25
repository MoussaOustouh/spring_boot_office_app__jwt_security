package com.example.office.dtos.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

/**
 * Contract for a generic Entity and DTO mapper
 *
 * @param  <E> Entity type parameter
 * @param  <D> DTO type parameter
 */

public interface EntityMapper<E, D> {
    E toEntity(D d);
    D toDto(E e);

    List<E> toEntity(List<D> dtosList);
    List<D> toDto(List<E> entitiesList);

    Set<E> toEntity(Set<D> dtosSet);
    Set<D> toDto(Set<E> entitiesSet);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
