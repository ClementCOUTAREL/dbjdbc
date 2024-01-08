package com.coutarel.dbpostgres.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coutarel.dbpostgres.TestDataUtil;
import com.coutarel.dbpostgres.dao.Impl.BookDaoImpl;
import com.coutarel.dbpostgres.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private BookDaoImpl underTest;

  @Test
  public void testThatCreatesBookGeneratesCorrectSql(){
    Book book = TestDataUtil.createTestBookA();

    underTest.create(book);

    verify(jdbcTemplate).update(eq("INSERT INTO books (isbn,title,author_id) VALUES (?,?,?)"),
             eq("sqfsdfc"),eq("Au nom de la Rose"), eq(1L)
    );

  }

  @Test
  public void testThatFindOneBookGeneratesCorrectSql(){
    underTest.find("sqfsdfc");

    verify(jdbcTemplate).query(
      eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
      ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
      eq("sqfsdfc")
      );
  }

  @Test
  public void testThatFindManyGenerateCorrectSql() {
    underTest.findMany();

    verify(jdbcTemplate).query(
      eq("SELECT isbn,title,author_id FROM books"),
      ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
  }

  @Test
  public void testThatUpdateGeneratesCorrectSql(){
    Book book = TestDataUtil.createTestBookA();
    underTest.create(book);
    book.setTitle("UPDATED");
    underTest.update(book.getIsbn(), book);

    verify(jdbcTemplate).update(
      "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
      "sqfsdfc", "UPDATED", 1L,"sqfsdfc");
  }

  @Test
  public void testThatDeleteGeneratesCorrectSql(){
    
    Book book = TestDataUtil.createTestBookA();
    underTest.delete(book.getIsbn());

    verify(jdbcTemplate).update(
      "DELETE FROM books WHERE isbn = ?",
      book.getIsbn()
      );
  }
}
