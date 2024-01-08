package com.coutarel.dbpostgres.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coutarel.dbpostgres.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

}
