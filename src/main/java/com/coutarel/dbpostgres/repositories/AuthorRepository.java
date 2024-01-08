package com.coutarel.dbpostgres.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coutarel.dbpostgres.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
