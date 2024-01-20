package com.coutarel.dbpostgres.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.repositories.AuthorRepository;
import com.coutarel.dbpostgres.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

  private AuthorRepository authorRepo;

  public AuthorServiceImpl(AuthorRepository authorRepo){
    this.authorRepo = authorRepo;
  }

  @Override
  public AuthorEntity createAuthor(AuthorEntity authorEntity){
    return authorRepo.save(authorEntity);
  }

  @Override
  public List<AuthorEntity> findAll(){
    return StreamSupport.stream(authorRepo.findAll().spliterator(),false).collect(Collectors.toList());
  }
}
