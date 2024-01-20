package com.coutarel.dbpostgres.services;

import java.util.List;
import java.util.Optional;

import com.coutarel.dbpostgres.domain.entities.AuthorEntity;

public interface AuthorService {

  AuthorEntity save(AuthorEntity author);

  List<AuthorEntity> findAll();

  Optional<AuthorEntity> findById(Long id);

  boolean isExists(Long id);

  AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

}
