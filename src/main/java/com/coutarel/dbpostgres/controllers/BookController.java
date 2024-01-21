package com.coutarel.dbpostgres.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.coutarel.dbpostgres.domain.dto.BookDto;
import com.coutarel.dbpostgres.domain.entities.BookEntity;
import com.coutarel.dbpostgres.mappers.Mapper;
import com.coutarel.dbpostgres.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
public class BookController {

  private Mapper<BookEntity, BookDto> bookMapper;

  private BookService bookService;

  public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService){
    this.bookMapper = bookMapper;
    this.bookService = bookService;
  }

  @PutMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
    BookEntity bookEntity = bookMapper.mapFrom(bookDto);
    Boolean bookExists = bookService.isExists(isbn);
    BookEntity savedBookEntity = bookService.createUpdatedBook(isbn, bookEntity);
    BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

    if(bookExists){
      return new ResponseEntity<>(savedBookDto,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(savedBookDto,HttpStatus.CREATED);
    }
  }

  @PatchMapping(path = "/books/{isbn}")
  public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn,@RequestBody BookDto bookDto){
    if(!bookService.isExists(isbn)){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BookEntity bookEntity = bookMapper.mapFrom(bookDto);
    BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
    return new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity), HttpStatus.OK); 
  }
  
  @GetMapping("/books/{isbn}")
  public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
      Optional<BookEntity> book = bookService.findByIsbn(isbn);
      return book.map(bookEntity -> {
        BookDto bookDto = bookMapper.mapTo(bookEntity);
        return new ResponseEntity<>(bookDto,HttpStatus.OK);
      }
      ).orElse(
        new ResponseEntity<>(HttpStatus.NOT_FOUND)
      );
  }

  @GetMapping(path = "/books")
  public Page<BookDto> listBooks(Pageable pageable) {
      Page<BookEntity> books = bookService.findAll(pageable);
      return books.map(bookMapper::mapTo);
  }

  @DeleteMapping(path = "/books/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable("isbn") String isbn){
    bookService.findByIsbn(isbn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
