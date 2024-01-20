package com.coutarel.dbpostgres.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coutarel.dbpostgres.domain.entities.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {

}
