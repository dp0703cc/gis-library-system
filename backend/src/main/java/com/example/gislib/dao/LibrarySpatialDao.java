package com.example.gislib.dao;

import com.example.gislib.dto.LibraryDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LibrarySpatialDao {

    private final NamedParameterJdbcTemplate jdbc;

    public LibrarySpatialDao(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<LibraryDto> DTO_ROW_MAPPER = (rs, rowNum) -> new LibraryDto(
        rs.getLong("lib_id"),
        rs.getString("lib_name"),
        rs.getString("address"),
        rs.getDouble("lon"),
        rs.getDouble("lat")
    );

    public List<LibraryDto> findWithinKm(double lon, double lat, double km) {
        String sql = "SELECT l.lib_id, l.lib_name, l.address, l.lon, l.lat " +
                     "FROM LIBRARIES l " +
                     "WHERE SDO_WITHIN_DISTANCE(" +
                     "  l.location, " +
                     "  MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(:lon, :lat, NULL), NULL, NULL), " +
                     "  'unit=KM distance=' || :km" +
                     ") = 'TRUE'";
        Map<String, Object> p = Map.of("lon", lon, "lat", lat, "km", km);
        return jdbc.query(sql, p, DTO_ROW_MAPPER);
    }

    public List<LibraryDto> findNearest(double lon, double lat, int k) {
        String sql = "SELECT l.lib_id, l.lib_name, l.address, l.lon, l.lat, SDO_NN_DISTANCE(1) AS dist " +
                     "FROM LIBRARIES l " +
                     "WHERE SDO_NN(" +
                     "  l.location, " +
                     "  MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(:lon, :lat, NULL), NULL, NULL), " +
                     "  'sdo_num_res=' || :k || ' unit=KM', " +
                     "  1" +
                     ") = 'TRUE' " +
                     "ORDER BY dist";
        Map<String, Object> p = Map.of("lon", lon, "lat", lat, "k", k);
        return jdbc.query(sql, p, DTO_ROW_MAPPER);
    }
}
