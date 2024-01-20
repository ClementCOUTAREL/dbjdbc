package com.coutarel.dbpostgres.services;

import java.util.List;

import com.coutarel.dbpostgres.domain.entities.AuthorEntity;

public interface AuthorService {

  AuthorEntity createAuthor(AuthorEntity author);

  List<AuthorEntity> findAll();

}
