package com.coutarel.dbpostgres.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public BookEntity createUpdatedBook(String isbn, BookEntity book){
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

  @Override
  public boolean isExists(String isbn) {
    return bookRepo.existsById(isbn);
  }

  @Override
  public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
    bookEntity.setIsbn(isbn);
    return bookRepo.save(bookEntity);
  }

  @Override
  public void deleteByIsbn(String isbn) {
   bookRepo.deleteById(isbn);
  }

  @Override
  public Page<BookEntity> findAll(Pageable pageable) {
    return bookRepo.findAll(pageable);
  }
  
}
