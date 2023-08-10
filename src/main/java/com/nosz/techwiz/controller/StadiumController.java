package com.nosz.techwiz.controller;

import com.nosz.techwiz.dto.StadiumDto;
import com.nosz.techwiz.dto.filter.StadiumFilter;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.service.StadiumService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/stadium")
public class StadiumController {
    private final StadiumService stadiumService;
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(stadiumService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getPage(@RequestParam StadiumFilter filter) {
        if (filter.getPageIndex() > 0 && filter.getPageSize() > 0) {
            return ResponseEntity.ok(stadiumService.getPageStadium(filter, PageRequest.of(filter.getPageIndex() -1, filter.getPageSize())));
        }
        throw new CustomException("Bad Request paging");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (id > 0) {
            return ResponseEntity.ok(stadiumService.getById(id));
        }
        throw new CustomException("Bad Request stadium id");
    }

    @PostMapping()
    public ResponseEntity<?> createStadium(@RequestBody StadiumDto stadiumDto) {
        return ResponseEntity.ok(stadiumService.addStadium(stadiumDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStadium(@PathVariable Long id, @RequestBody StadiumDto stadiumDto) {
        if (id > 0) {
            return ResponseEntity.ok(stadiumService.updateStadium(id, stadiumDto));
        }
        throw new CustomException("Bad Request stadium id");
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> deleteStadium(@PathVariable Long id) {
        if (id > 0) {
            stadiumService.deleteStadium(id);
            return ResponseEntity.ok(new HashMap<>().put("message", String.format("Delete stadium id %d successfully", id)));
        }
        throw new CustomException("Bad Request stadium id");
    }
}
