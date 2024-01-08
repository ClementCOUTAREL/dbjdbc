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
import com.coutarel.dbpostgres.dao.Impl.AuthorDaoImpl;
import com.coutarel.dbpostgres.domain.Author;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTest {

  private AuthorDaoImpl underTest;

  @Autowired
  public AuthorDaoImplIntegrationTest(AuthorDaoImpl underTest){
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertNotNull(result);
    assertEquals(result.get(), author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndCalled(){
    Author authorA = TestDataUtil.createTestAuthorA();
    underTest.create(authorA);
    Author authorB = TestDataUtil.createTestAuthorB();
    underTest.create(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    underTest.create(authorC);

    List<Author> expected = new ArrayList<Author>(Arrays.asList(authorA,authorB,authorC));

    List<Author> result = underTest.findMany();

    assertEquals(expected, result);;
  }

  @Test
  public void testThatAuthorCanBeUpdated(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);
    author.setName("UPDATED");
    underTest.update(author.getId(), author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertEquals(author, result.get());
  }

  @Test
  public void testThatDeleteActuallyDeletesTheAuthor(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);
    underTest.delete(1L);

    Optional<Author> result = underTest.findOne(1L);
    assertFalse(result.isPresent());
  }

}
