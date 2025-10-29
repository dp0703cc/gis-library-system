package com.example.gislib.dto;

public record BookSearchResultDto(
        Long book_id,
        String isbn,
        String title,
        String author,
        String publisher,
        Long inventory_id,
        String status,
        String call_number,
        Long lib_id,
        String lib_name,
        Double lon,
        Double lat
) {}
