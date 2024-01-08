package com.coutarel.dbpostgres.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.coutarel.dbpostgres.dao.BookDao;
import com.coutarel.dbpostgres.domain.Book;

@Component
public class BookDaoImpl implements BookDao {

  private final JdbcTemplate jdbcTemplate;

  public BookDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void create(Book book){
    jdbcTemplate.update("INSERT INTO books (isbn,title,author_id) VALUES (?,?,?)",
        book.getIsbn(),book.getTitle(),book.getAuthorId()
    );
  }

  public Optional<Book> find(String isbn) {
     List<Book> results = jdbcTemplate.query(
        "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1", 
        new BookRowMapper(),
        isbn);

      return results.stream().findFirst();
  }

  public static class BookRowMapper implements org.springframework.jdbc.core.RowMapper<Book> {
    
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException{
      return Book.builder()
      .isbn(rs.getString("isbn"))
      .title(rs.getString("title"))
      .authorId(rs.getLong("author_id"))
      .build();
    }
  }

  public List<Book> findMany() {
    return jdbcTemplate.query("SELECT isbn,title,author_id FROM books",
    new BookRowMapper());
  }

  @Override
  public void update(String isbn, Book book) {
    jdbcTemplate.update(
      "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
      book.getIsbn(),book.getTitle(),book.getAuthorId(),isbn);
  }

  @Override
  public void delete(String isbn){
    jdbcTemplate.update("DELETE FROM books WHERE isbn = ?",isbn);
  }

}
