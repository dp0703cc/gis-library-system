package com.example.gislib.service;

import com.example.gislib.dto.BookSearchResultDto;
import java.util.List;

public interface BookService {
    List<BookSearchResultDto> search(String keyword);
}
