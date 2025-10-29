package com.example.gislib.service;

import com.example.gislib.dto.LibraryDto;
import java.util.List;

public interface LibraryService {
    List<LibraryDto> listLibraries();
    List<LibraryDto> findLibrariesWithinKm(double lat, double lon, double km);
    List<LibraryDto> findNearestLibraries(double lat, double lon, int k);
}
