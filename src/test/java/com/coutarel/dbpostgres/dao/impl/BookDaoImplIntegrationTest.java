package com.coutarel.dbpostgres.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.coutarel.dbpostgres.TestDataUtil;
import com.coutarel.dbpostgres.dao.AuthorDao;
import com.coutarel.dbpostgres.dao.Impl.BookDaoImpl;
import com.coutarel.dbpostgres.domain.Author;
import com.coutarel.dbpostgres.domain.Book;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTest {

  private BookDaoImpl underTest;
  private AuthorDao authorDao;


  @Autowired
  public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDao authorDao){
    this.underTest = underTest;
    this.authorDao = authorDao;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);
    Book book = TestDataUtil.createTestBookA();
    underTest.create(book);
    Optional<Book> result = underTest.find(book.getIsbn());
    assertNotNull(result);
    assertEquals(result.get(), book);
  }

  @Test
  public void testThatMultipleBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);

    Book bookA = TestDataUtil.createTestBookA();
    underTest.create(bookA);
    Book bookB = TestDataUtil.createTestBookB();
    underTest.create(bookB);
    Book bookC = TestDataUtil.createTestBookC();
    underTest.create(bookC);

    List<Book> expected = new ArrayList<Book>(Arrays.asList(bookA,bookB,bookC));

    List<Book> result = underTest.findMany();

    assertEquals(expected,result);

  }

  @Test
  public void testThatUpdateWorks(){
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);
    Book book = TestDataUtil.createTestBookA();
    underTest.create(book);
    book.setTitle("UPDATED");
    underTest.update(book.getIsbn(),book);

    Optional<Book> result = underTest.find(book.getIsbn());
    assertEquals(book, result.get());
  }

  @Test
  public void testThatABookCanBeDeleted(){
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);
    Book book = TestDataUtil.createTestBookA();
    underTest.create(book);
    underTest.delete(book.getIsbn());

    Optional<Book> result = underTest.find(book.getIsbn());
    assertFalse(result.isPresent());
  }

}
