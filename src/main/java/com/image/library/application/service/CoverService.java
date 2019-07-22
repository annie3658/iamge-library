package com.image.library.application.service;

import ch.qos.logback.classic.Logger;
import com.image.library.application.dto.CoverDTO;
import com.image.library.application.entity.Cover;
import com.image.library.application.repository.CoverRepository;
import com.image.library.application.utils.DTOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoverService {

    private CoverRepository coverRepository;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CoverService.class);
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public CoverService(CoverRepository coverRepository) {
        this.coverRepository = coverRepository;
    }

    private Cover findById(String id){
        Optional<Cover> cover = coverRepository.findById(id);
        if (cover.isPresent()) {
            return cover.get();
        }
        LOGGER.warn("The Cover with id {} was not found", id);
        return null;
    }

    public CoverDTO findCoverById(String id){
        return dtoUtil.coverToDTO(findById(id));
    }

    public CoverDTO findCoverByBookTitle(String bookTitle){
        Cover cover = coverRepository.findByBookTitle(bookTitle);
        if(cover == null ){
            LOGGER.warn("The Cover for the book {} was not found",  bookTitle);
        }
        return dtoUtil.coverToDTO(cover);
    }

    public List<CoverDTO> getAll(){
        List<Cover> covers = coverRepository.findAll();
        if(covers.isEmpty()){
            LOGGER.warn("The cover list is empty");
        }
        return covers.stream()
                .map(cover -> dtoUtil.coverToDTO(cover))
                .collect(Collectors.toList());
    }

    public CoverDTO insert(CoverDTO cover){
            LOGGER.info("Created new Cover: " + cover.toString());
            return dtoUtil.coverToDTO(coverRepository.insert(dtoUtil.dtoToCover(cover)));
    }

    public CoverDTO update(CoverDTO cover){
        LOGGER.info("Updated Cover: " + cover.toString());
        return dtoUtil.coverToDTO(coverRepository.save(dtoUtil.dtoToCover(cover)));
    }

    public void delete(String id){
        LOGGER.info("Deleted Cover with id: " + id);
        coverRepository.deleteById(id);

    }
}
