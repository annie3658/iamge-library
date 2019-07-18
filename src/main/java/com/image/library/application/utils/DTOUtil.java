package com.image.library.application.utils;

import com.image.library.application.dto.CoverDTO;
import com.image.library.application.entity.Cover;

public class DTOUtil {

    public Cover dtoToCover(CoverDTO coverDTO){
        Cover cover = new Cover();
        cover.setId(coverDTO.getId());
        cover.setBookTitle(coverDTO.getBookTitle());
        cover.setLink(coverDTO.getLink());

        return cover;
    }

    public CoverDTO coverToDTO(Cover cover){
        CoverDTO coverDTO = new CoverDTO.Builder(cover.getId())
                .withBookTitle(cover.getBookTitle())
                .withLink(cover.getLink())
                .build();
        return coverDTO;
    }
}
