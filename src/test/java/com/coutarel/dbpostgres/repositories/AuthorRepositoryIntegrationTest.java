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
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertNotNull(result);
    assertEquals(result.get(), author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndCalled(){
    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<AuthorEntity> expected = Arrays.asList(authorA,authorB, authorC);

    Iterable<AuthorEntity> result = underTest.findAll();

    assertEquals(expected, result);;
  }

  @Test
  public void testThatAuthorCanBeUpdated(){
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    author.setName("UPDATED");
    underTest.save(author);
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertEquals(author, result.get());
  }

  @Test
  public void testThatDeleteActuallyDeletesTheAuthor(){
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    underTest.deleteById(1L);

    Optional<AuthorEntity> result = underTest.findById(1L);
    assertFalse(result.isPresent());
  }

  @Test
  public void testThatGetAuthorsWithAgeLessThan(){
    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<AuthorEntity> expected = Arrays.asList(authorB, authorC);
    Iterable<AuthorEntity> result = underTest.findByAgeLessThan(61);
    assertEquals(expected, result);

  }

  @Test
  public void testThatGetAuthorsWithAgeGreaterThan(){

    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<AuthorEntity> expected = Arrays.asList(authorA, authorC);
    Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
    assertEquals(expected, result);

  }

}
