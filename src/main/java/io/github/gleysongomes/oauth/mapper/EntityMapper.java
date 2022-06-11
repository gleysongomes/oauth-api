package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.MappingTarget;

public interface EntityMapper<E, D, I> {

	D toDto(E entity);

	List<D> toDtos(List<E> entities);

	E toDomainObject(I input);

	void copyToDomainObject(I input, @MappingTarget E entity);
}
