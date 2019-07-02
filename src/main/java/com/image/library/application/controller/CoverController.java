package com.image.library.application.controller;


import com.image.library.application.entity.Cover;
import com.image.library.application.service.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covers")
public class CoverController {

    @Autowired
    private CoverService coverService;

    @GetMapping("/all")
    public List<Cover> getAllCovers(){
        return coverService.getAll();
    }

    @PutMapping
    public void insert(@RequestBody Cover cover){
        coverService.insert(cover);
    }

    @PostMapping
    public void update (@RequestBody Cover cover){
        coverService.update(cover);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        coverService.delete(id);
    }

    @GetMapping("/cover/{bookTitle}")
    public Cover getCoverByBookTitle(@PathVariable("bookTitle") String title){
        return coverService.findByBookTitle(title);
    }
}
