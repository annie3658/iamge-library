package com.image.library.application.controller;


import com.image.library.application.dto.CoverDTO;
import com.image.library.application.service.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/covers")
public class CoverController {

    private CoverService coverService;

    @Autowired
    public CoverController(CoverService coverService) {
        this.coverService = coverService;
    }

    @GetMapping("/all")
    public List<CoverDTO> getAllCovers(){
        return coverService.getAll();
    }

    @PutMapping
    public CoverDTO insert(@Valid @RequestBody CoverDTO cover){
        return coverService.insert(cover);
    }

    @PostMapping
    public CoverDTO update (@Valid @RequestBody CoverDTO cover){
        return coverService.update(cover);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        coverService.delete(id);
    }

    @GetMapping("/cover/{bookTitle}")
    public CoverDTO getCoverByBookTitle(@PathVariable("bookTitle") String title){
        return coverService.findCoverByBookTitle(title);
    }
}
