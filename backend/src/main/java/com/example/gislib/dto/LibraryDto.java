package com.example.gislib.dto;

import com.example.gislib.entity.Library;

public record LibraryDto(
        Long lib_id,
        String lib_name,
        String address,
        Double lon,
        Double lat
) {
    public static LibraryDto fromEntity(Library l) {
        return new LibraryDto(l.getLibId(), l.getLibName(), l.getAddress(), l.getLon(), l.getLat());
    }
}
