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
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.domain.entities.BookEntity;

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
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    BookEntity book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    assertNotNull(result);
    assertEquals(result.get(), book);
  }

  // @Test
  public void testThatMultipleBookCanBeCreatedAndRecalled() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();

    BookEntity bookA = TestDataUtil.createTestBookA(author);
    underTest.save(bookA);
    BookEntity bookB = TestDataUtil.createTestBookB(author);
    underTest.save(bookB);
    BookEntity bookC = TestDataUtil.createTestBookC(author);
    underTest.save(bookC);

    Iterable<BookEntity> expected = Arrays.asList(bookA,bookB,bookC);

    Iterable<BookEntity> result = underTest.findAll();

    assertEquals(expected,result);

  }

  @Test
  public void testThatUpdateWorks(){
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    
    BookEntity book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    book.setTitle("UPDATED");
    underTest.save(book);

    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    assertEquals(book, result.get());
  }

  @Test
  public void testThatABookCanBeDeleted(){
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    
    BookEntity book = TestDataUtil.createTestBookA(author);
    underTest.save(book);
    underTest.deleteById(book.getIsbn());

    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    assertFalse(result.isPresent());
  }

}
