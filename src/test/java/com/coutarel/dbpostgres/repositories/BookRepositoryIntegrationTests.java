package com.coutarel.dbpostgres.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.coutarel.dbpostgres.TestDataUtil;
import com.coutarel.dbpostgres.domain.Author;
import com.coutarel.dbpostgres.domain.Book;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

  private BookRepository underTest;

  @Autowired
  public BookRepositoryIntegrationTests(BookRepository underTest){
    this.underTest = underTest;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();
    Book book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    Optional<Book> result = underTest.findById(book.getIsbn());
    assertNotNull(result);
    assertEquals(result.get(), book);
  }

  // @Test
  public void testThatMultipleBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();

    Book bookA = TestDataUtil.createTestBookA(author);
    underTest.save(bookA);
    Book bookB = TestDataUtil.createTestBookB(author);
    underTest.save(bookB);
    Book bookC = TestDataUtil.createTestBookC(author);
    underTest.save(bookC);

    Iterable<Book> expected = Arrays.asList(bookA,bookB,bookC);

    Iterable<Book> result = underTest.findAll();

    assertEquals(expected,result);

  }

  @Test
  public void testThatUpdateWorks(){
    Author author = TestDataUtil.createTestAuthorA();
    
    Book book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    book.setTitle("UPDATED");
    underTest.save(book);

    Optional<Book> result = underTest.findById(book.getIsbn());
    assertEquals(book, result.get());
  }

  @Test
  public void testThatABookCanBeDeleted(){
    Author author = TestDataUtil.createTestAuthorA();
    
    Book book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    underTest.deleteById(book.getIsbn());

    Optional<Book> result = underTest.findById(book.getIsbn());
    assertFalse(result.isPresent());
  }

}
