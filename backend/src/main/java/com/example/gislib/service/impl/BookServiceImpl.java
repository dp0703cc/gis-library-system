package com.example.gislib.service.impl;

import com.example.gislib.dto.BookSearchResultDto;
import com.example.gislib.service.BookService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final NamedParameterJdbcTemplate jdbc;

    public BookServiceImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<BookSearchResultDto> ROW_MAPPER = (rs, i) -> new BookSearchResultDto(
        rs.getLong("book_id"),
        rs.getString("isbn"),
        rs.getString("title"),
        rs.getString("author"),
        rs.getString("publisher"),
        rs.getLong("inventory_id"),
        rs.getString("status"),
        rs.getString("call_number"),
        rs.getLong("lib_id"),
        rs.getString("lib_name"),
        rs.getDouble("lon"),
        rs.getDouble("lat")
    );

    @Override
    public List<BookSearchResultDto> search(String keyword) {
        String kw = (keyword == null || keyword.isBlank()) ? "%" : "%" + keyword.toLowerCase() + "%";
        String sql = "SELECT b.book_id, b.isbn, b.title, b.author, b.publisher, " +
                     "       i.inventory_id, i.status, i.call_number, " +
                     "       l.lib_id, l.lib_name, l.lon, l.lat " +
                     "FROM BOOKS b " +
                     "JOIN BOOK_INVENTORY i ON i.book_id = b.book_id " +
                     "JOIN LIBRARIES l ON l.lib_id = i.lib_id " +
                     "WHERE (LOWER(b.title) LIKE :kw OR LOWER(b.author) LIKE :kw OR b.isbn LIKE :kw) " +
                     "ORDER BY b.title, l.lib_name";
        return jdbc.query(sql, Map.of("kw", kw), ROW_MAPPER);
    }
}
