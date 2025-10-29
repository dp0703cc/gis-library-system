package com.example.gislib.controller;

import com.example.gislib.dto.LibraryDto;
import com.example.gislib.service.LibraryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<LibraryDto> list() {
        return libraryService.listLibraries();
    }

    @GetMapping("/nearby")
    public List<LibraryDto> nearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(name = "radiusKm", defaultValue = "2") double radiusKm
    ) {
        return libraryService.findLibrariesWithinKm(lat, lon, radiusKm);
    }

    @GetMapping("/nearest")
    public List<LibraryDto> nearest(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(name = "k", defaultValue = "1") int k
    ) {
        return libraryService.findNearestLibraries(lat, lon, k);
    }
}
