package com.coutarel.dbpostgres.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.coutarel.dbpostgres.dao.AuthorDao;
import com.coutarel.dbpostgres.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

  private final JdbcTemplate jdbcTemplate;

  public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void create(Author author) {
    jdbcTemplate.update("INSERT INTO authors (id,name,age) VALUES (?,?,?)",
      author.getId(),author.getName(),author.getAge());
  }

  public Optional<Author> findOne(Long authorId) {
    List<Author> resultsList = jdbcTemplate.query(
      "SELECT id,name,age FROM authors WHERE id = ? LIMIT 1",
       new AuthorRowMapper(),
        authorId);
    return resultsList.stream().findFirst();
  }

  public static class AuthorRowMapper implements org.springframework.jdbc.core.RowMapper<Author>{

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Author.builder()
      .id(rs.getLong("id"))
      .name(rs.getString("name"))
      .age(rs.getInt("age"))
      .build();
    }
  }

  @Override
  public List<Author> findMany() {
    return jdbcTemplate.query(
      "SELECT id,name,age FROM authors",
      new AuthorRowMapper()
    );

  }

  @Override
  public void update(long id,Author author) {
    jdbcTemplate.update(
      "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
      author.getId(),author.getName(),author.getAge(),id
      );
  }

  @Override
  public void delete(long id) {
    jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
  }

}
