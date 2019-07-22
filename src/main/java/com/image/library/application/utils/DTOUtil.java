package com.image.library.application.utils;

import com.image.library.application.dto.CoverDTO;
import com.image.library.application.entity.Cover;

public class DTOUtil {

    public Cover dtoToCover(CoverDTO coverDTO){
        if(coverDTO != null) {
            Cover cover = new Cover();
            cover.setId(coverDTO.getId());
            cover.setBookTitle(coverDTO.getBookTitle());
            cover.setLink(coverDTO.getLink());

            return cover;
        }
        return null;
    }

    public CoverDTO coverToDTO(Cover cover){
        if(cover != null) {
            CoverDTO coverDTO = new CoverDTO.Builder(cover.getId())
                    .withBookTitle(cover.getBookTitle())
                    .withLink(cover.getLink())
                    .build();
            return coverDTO;
        }
        return null;
    }
}
