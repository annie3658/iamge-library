package com.image.library.application.service;

import com.image.library.application.entity.Cover;
import com.image.library.application.repository.CoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverService {

    @Autowired
    private CoverRepository coverRepository;

    public Cover findByBookTitle(String title){
        return coverRepository.findByBookTitle(title);
    }

    public List<Cover> getAll(){
        List<Cover> books = coverRepository.findAll();
        return books;
    }

    public Cover insert(Cover cover){

        return coverRepository.insert(cover);
    }

    public Cover update(Cover book){

        return coverRepository.save(book);
    }

    public void delete(String id){
        coverRepository.deleteById(id);
    }
}
