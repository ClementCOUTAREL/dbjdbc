package com.coutarel.dbpostgres.services.impl;

import java.util.List;
import java.util.Optional;
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
  public AuthorEntity save(AuthorEntity authorEntity){
    return authorRepo.save(authorEntity);
  }

  @Override
  public List<AuthorEntity> findAll(){
    return StreamSupport.stream(authorRepo.findAll().spliterator(),false).collect(Collectors.toList());
  }

  @Override
  public Optional<AuthorEntity> findById(Long id){
    return authorRepo.findById(id);
  }

  @Override
  public boolean isExists(Long id) {
    return authorRepo.existsById(id);
  }

  @Override
  public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
    authorEntity.setId(id);
    
    return authorRepo.findById(id).map(existingAuthor -> {
      Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
      Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
      return authorRepo.save(existingAuthor);
    }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    
  }

  @Override
  public void delete(Long id) {
    authorRepo.deleteById(id);
  }

}
