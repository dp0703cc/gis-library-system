package com.example.gislib.dto;

public record BorrowRecordDto(
        Long record_id,
        Long inventory_id,
        Long user_id,
        String borrow_date,
        String due_date,
        String return_date,
        Long borrow_lib_id,
        Long return_lib_id,
        String status
) {}
