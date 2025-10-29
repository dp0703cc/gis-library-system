package com.example.gislib.controller;

import com.example.gislib.dto.BookSearchResultDto;
import com.example.gislib.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public List<BookSearchResultDto> search(@RequestParam(name = "q", required = false) String q) {
        return bookService.search(q);
    }
}
