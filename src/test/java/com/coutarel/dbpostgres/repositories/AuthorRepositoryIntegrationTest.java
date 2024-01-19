package com.coutarel.dbpostgres.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.coutarel.dbpostgres.TestDataUtil;
import com.coutarel.dbpostgres.domain.Author;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {

  private AuthorRepository underTest;

  @Autowired
  public AuthorRepositoryIntegrationTest(AuthorRepository underTest){
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());
    assertNotNull(result);
    assertEquals(result.get(), author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndCalled(){
    Author authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);
    Author authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<Author> expected = Arrays.asList(authorA,authorB, authorC);

    Iterable<Author> result = underTest.findAll();

    assertEquals(expected, result);;
  }

  @Test
  public void testThatAuthorCanBeUpdated(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    author.setName("UPDATED");
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());
    assertEquals(author, result.get());
  }

  @Test
  public void testThatDeleteActuallyDeletesTheAuthor(){
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    underTest.deleteById(1L);

    Optional<Author> result = underTest.findById(1L);
    assertFalse(result.isPresent());
  }

}
