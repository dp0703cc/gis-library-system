package com.example.gislib.service.impl;

import com.example.gislib.dto.BorrowRecordDto;
import com.example.gislib.service.BorrowService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final NamedParameterJdbcTemplate jdbc;

    public BorrowServiceImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<BorrowRecordDto> ROW_MAPPER = (rs, i) -> new BorrowRecordDto(
        rs.getLong("record_id"),
        rs.getLong("inventory_id"),
        rs.getLong("user_id"),
        rs.getString("borrow_date"),
        rs.getString("due_date"),
        rs.getString("return_date"),
        rs.getLong("borrow_lib_id"),
        rs.getLong("return_lib_id"),
        rs.getString("status")
    );

    @Override
    @Transactional
    public BorrowRecordDto borrow(long userId, long inventoryId, long borrowLibId) {
        String upd = "UPDATE BOOK_INVENTORY SET status='BORROWED' WHERE inventory_id=:iid AND status='AVAILABLE'";
        int n = jdbc.update(upd, Map.of("iid", inventoryId));
        if (n == 0) {
            throw new IllegalStateException("该馆藏不可借（可能已借出/预约/仅阅览）");
        }

        String ins = "INSERT INTO BORROW_RECORDS " +
                     "  (record_id, inventory_id, user_id, borrow_date, due_date, return_date, borrow_lib_id, return_lib_id, status) " +
                     "VALUES (SEQ_BORROW.NEXTVAL, :iid, :uid, SYSDATE, SYSDATE + 30, NULL, :blib, NULL, 'ACTIVE')";
        MapSqlParameterSource p = new MapSqlParameterSource()
                .addValue("iid", inventoryId)
                .addValue("uid", userId)
                .addValue("blib", borrowLibId);
        jdbc.update(ins, p);

        String sel = "SELECT * FROM (" +
                     "  SELECT record_id, inventory_id, user_id, TO_CHAR(borrow_date,'YYYY-MM-DD') AS borrow_date, " +
                     "         TO_CHAR(due_date,'YYYY-MM-DD') AS due_date, " +
                     "         NULL AS return_date, borrow_lib_id, return_lib_id, status " +
                     "  FROM BORROW_RECORDS WHERE inventory_id=:iid AND user_id=:uid AND status='ACTIVE' " +
                     "  ORDER BY record_id DESC" +
                     ") WHERE ROWNUM = 1";
        return jdbc.queryForObject(sel, Map.of("iid", inventoryId, "uid", userId), ROW_MAPPER);
    }

    @Override
    @Transactional
    public BorrowRecordDto returnBook(long recordId, long returnLibId) {
        Long inventoryId = jdbc.queryForObject(
                "SELECT inventory_id FROM BORROW_RECORDS WHERE record_id=:rid",
                Map.of("rid", recordId),
                Long.class);
        if (inventoryId == null) {
            throw new IllegalArgumentException("借阅记录不存在");
        }

        String updRec = "UPDATE BORROW_RECORDS " +
                        "SET status='RETURNED', return_date=SYSDATE, return_lib_id=:rlib " +
                        "WHERE record_id=:rid AND status='ACTIVE'";
        int n1 = jdbc.update(updRec, Map.of("rid", recordId, "rlib", returnLibId));
        if (n1 == 0) {
            throw new IllegalStateException("借阅记录不处于可归还状态");
        }

        String updInv = "UPDATE BOOK_INVENTORY SET status='AVAILABLE', lib_id=:rlib WHERE inventory_id=:iid";
        jdbc.update(updInv, Map.of("rlib", returnLibId, "iid", inventoryId));

        String sel = "SELECT record_id, inventory_id, user_id, " +
                     "       TO_CHAR(borrow_date,'YYYY-MM-DD') AS borrow_date, " +
                     "       TO_CHAR(due_date,'YYYY-MM-DD') AS due_date, " +
                     "       TO_CHAR(return_date,'YYYY-MM-DD') AS return_date, " +
                     "       borrow_lib_id, return_lib_id, status " +
                     "FROM BORROW_RECORDS WHERE record_id=:rid";
        return jdbc.queryForObject(sel, Map.of("rid", recordId), ROW_MAPPER);
    }

    @Override
    public List<BorrowRecordDto> listByUser(long userId) {
        String sql = "SELECT record_id, inventory_id, user_id, " +
                     "       TO_CHAR(borrow_date,'YYYY-MM-DD') AS borrow_date, " +
                     "       TO_CHAR(due_date,'YYYY-MM-DD') AS due_date, " +
                     "       TO_CHAR(return_date,'YYYY-MM-DD') AS return_date, " +
                     "       borrow_lib_id, return_lib_id, status " +
                     "FROM BORROW_RECORDS WHERE user_id=:uid ORDER BY record_id DESC";
        return jdbc.query(sql, Map.of("uid", userId), ROW_MAPPER);
    }
}
