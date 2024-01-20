package com.coutarel.dbpostgres.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.coutarel.dbpostgres.domain.entities.BookEntity;
import com.coutarel.dbpostgres.repositories.BookRepository;
import com.coutarel.dbpostgres.services.BookService;

@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepo;

  public BookServiceImpl(BookRepository bookRepo){
    this.bookRepo = bookRepo;
  }

  @Override
  public BookEntity createBook(String isbn, BookEntity book){
    book.setIsbn(isbn);
    return bookRepo.save(book);
  }

  @Override
  public List<BookEntity> findAll(){
    return StreamSupport.stream(bookRepo.findAll().spliterator(), false).collect(Collectors.toList()) ;
  }

  @Override
  public Optional<BookEntity> findByIsbn(String isbn) {
    return bookRepo.findById(isbn);
  }

  
}
