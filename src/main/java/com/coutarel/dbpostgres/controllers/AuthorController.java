package com.coutarel.dbpostgres.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.coutarel.dbpostgres.domain.dto.AuthorDto;
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.mappers.Mapper;
import com.coutarel.dbpostgres.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
    AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
    return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
  }

  @GetMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
      Optional<AuthorEntity> author = authorService.findById(id);
      return author.map(authorEntity -> {
        AuthorDto authorDto = authorMapper.mapTo(authorEntity);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
      }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  @GetMapping(path = "/authors")
  public List<AuthorDto> listAuthors() {
     List<AuthorEntity> authors = authorService.findAll();
     return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
  }

  @PutMapping(path = "authors/{id}")
  public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
    if(!authorService.isExists(id)){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }  

    authorDto.setId(id);
    AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
    AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
    return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity),HttpStatus.OK);
  }
  
  @PatchMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
    if(!authorService.isExists(id)){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
    AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);
    authorMapper.mapTo(updatedAuthorEntity);
    return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity),HttpStatus.OK);
  }

  @DeleteMapping("/authors/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id){
    authorService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
