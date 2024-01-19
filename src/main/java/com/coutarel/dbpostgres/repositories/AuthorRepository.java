package com.coutarel.dbpostgres.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coutarel.dbpostgres.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

  Iterable<Author> findByAgeLessThan(int age);

  @Query("SELECT a from Author a where a.age > ?1")
  Iterable<Author> findAuthorsWithAgeGreaterThan(int age);

}
