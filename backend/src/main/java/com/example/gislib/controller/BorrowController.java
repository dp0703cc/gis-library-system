package com.example.gislib.controller;

import com.example.gislib.dto.BorrowRecordDto;
import com.example.gislib.service.BorrowService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/borrow")
    public BorrowRecordDto borrow(@RequestBody Map<String, Object> payload) {
        long userId = ((Number)payload.get("userId")).longValue();
        long inventoryId = ((Number)payload.get("inventoryId")).longValue();
        long borrowLibId = ((Number)payload.get("borrowLibId")).longValue();
        return borrowService.borrow(userId, inventoryId, borrowLibId);
    }

    @PostMapping("/return")
    public BorrowRecordDto returnBook(@RequestBody Map<String, Object> payload) {
        long recordId = ((Number)payload.get("recordId")).longValue();
        long returnLibId = ((Number)payload.get("returnLibId")).longValue();
        return borrowService.returnBook(recordId, returnLibId);
    }

    @GetMapping("/borrows/user/{userId}")
    public List<BorrowRecordDto> listByUser(@PathVariable long userId) {
        return borrowService.listByUser(userId);
    }
}
