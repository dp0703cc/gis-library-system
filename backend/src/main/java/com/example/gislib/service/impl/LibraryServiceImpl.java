package com.example.gislib.service.impl;

import com.example.gislib.dao.LibrarySpatialDao;
import com.example.gislib.dto.LibraryDto;
import com.example.gislib.repository.LibraryRepository;
import com.example.gislib.service.LibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LibraryServiceImpl implements LibraryService {

    private final com.example.gislib.repository.LibraryRepository libraryRepository;
    private final LibrarySpatialDao spatialDao;

    public LibraryServiceImpl(LibraryRepository libraryRepository, LibrarySpatialDao spatialDao) {
        this.libraryRepository = libraryRepository;
        this.spatialDao = spatialDao;
    }

    @Override
    public List<LibraryDto> listLibraries() {
        return libraryRepository.findAll().stream()
                .map(com.example.gislib.dto.LibraryDto::fromEntity)
                .toList();
    }

    @Override
    public List<LibraryDto> findLibrariesWithinKm(double lat, double lon, double km) {
        double safeKm = Math.max(0.1, Math.min(km, 20.0));
        return spatialDao.findWithinKm(lon, lat, safeKm);
    }

    @Override
    public List<LibraryDto> findNearestLibraries(double lat, double lon, int k) {
        int safeK = Math.max(1, Math.min(k, 10));
        return spatialDao.findNearest(lon, lat, safeK);
    }
}
