package com.coutarel.dbpostgres.dao;

import java.util.List;
import java.util.Optional;

import com.coutarel.dbpostgres.domain.Author;

public interface AuthorDao {

    void create(Author author);

    Optional<Author> findOne(Long l);

    List<Author> findMany();

    void update(long id,Author author);

    void delete(long id);
}
