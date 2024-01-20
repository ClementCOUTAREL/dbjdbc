package com.coutarel.dbpostgres.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.coutarel.dbpostgres.domain.dto.AuthorDto;
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.mappers.Mapper;
import com.coutarel.dbpostgres.services.AuthorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthorController {

  private AuthorService authorService;

  private Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper ){
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }

  @PostMapping(path = "/authors")
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
    AuthorEntity authorEntity = authorMapper.mapFrom(author);
    AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
    return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
  }
  

}