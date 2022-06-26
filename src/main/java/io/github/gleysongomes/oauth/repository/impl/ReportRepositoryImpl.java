package io.github.gleysongomes.oauth.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.repository.ReportRepository;

@Transactional
public abstract class ReportRepositoryImpl<E, F> implements ReportRepository<E, F> {

	@PersistenceContext
	protected EntityManager entityManager;

}
