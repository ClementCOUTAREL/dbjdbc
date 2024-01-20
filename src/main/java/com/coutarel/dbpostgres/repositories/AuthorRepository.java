package com.coutarel.dbpostgres.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coutarel.dbpostgres.domain.entities.AuthorEntity;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

  Iterable<AuthorEntity> findByAgeLessThan(int age);

  @Query("SELECT a from AuthorEntity a where a.age > ?1")
  Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);

}
