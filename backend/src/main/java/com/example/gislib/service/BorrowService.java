package com.example.gislib.service;

import com.example.gislib.dto.BorrowRecordDto;
import java.util.List;

public interface BorrowService {
    BorrowRecordDto borrow(long userId, long inventoryId, long borrowLibId);
    BorrowRecordDto returnBook(long recordId, long returnLibId);
    List<BorrowRecordDto> listByUser(long userId);
}
