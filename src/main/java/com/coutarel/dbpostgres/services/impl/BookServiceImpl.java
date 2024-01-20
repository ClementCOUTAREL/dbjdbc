package com.coutarel.dbpostgres.services.impl;

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

}
