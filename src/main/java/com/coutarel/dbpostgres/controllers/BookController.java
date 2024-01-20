package com.coutarel.dbpostgres.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.coutarel.dbpostgres.domain.dto.BookDto;
import com.coutarel.dbpostgres.domain.entities.BookEntity;
import com.coutarel.dbpostgres.mappers.Mapper;
import com.coutarel.dbpostgres.services.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BookController {

  private Mapper<BookEntity, BookDto> bookMapper;

  private BookService bookService;

  public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService){
    this.bookMapper = bookMapper;
    this.bookService = bookService;
  }

  @PutMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> createBook(
    @PathVariable("isbn") String isbn,
    @RequestBody BookDto bookDto) {
      BookEntity bookEntity = bookMapper.mapFrom(bookDto);
      BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
      BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
      return new ResponseEntity<>(savedBookDto,HttpStatus.CREATED);
  }
  

}
