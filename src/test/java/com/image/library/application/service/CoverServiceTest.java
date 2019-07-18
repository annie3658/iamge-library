package com.image.library.application.service;

import com.image.library.application.dto.CoverDTO;
import com.image.library.application.entity.Cover;
import com.image.library.application.exception.CoverNotFoundException;
import com.image.library.application.repository.CoverRepository;
import com.image.library.application.utils.DTOUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoverServiceTest {

    private CoverService service;
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public void setService(CoverService service) {
        this.service = service;
    }
    @Autowired
    public void setRepository(CoverRepository repository) {
        this.repository = repository;
    }

    @MockBean
    private CoverRepository repository;

    @Test(expected = CoverNotFoundException.class)
    public void findCoverByIdThrowsExceptionTest(){
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();
        Cover cover = new Cover("1","link","test");
        doReturn(Optional.of(cover)).when(repository).findById("1");
        assertEquals(coverDTO, service.findCoverById("444"));
    }

    @Test
    public void findCoverByIdTest(){
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();
        Cover cover = new Cover("1","link","test");
        doReturn(Optional.of(cover)).when(repository).findById("1");
        assertEquals(coverDTO, service.findCoverById("1"));
    }

    @Test(expected = CoverNotFoundException.class)
    public void findByBookTitleThrowsExceptionTest(){
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();
        Cover cover = new Cover("1","link","test");
        doReturn(cover).when(repository).findByBookTitle("test");
        assertEquals(coverDTO, service.findCoverByBookTitle("444"));
    }

    @Test
    public void findCoverByBookTitleTest(){
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();
        Cover cover = new Cover("1","link","test");
        doReturn(cover).when(repository).findByBookTitle("test");
        assertEquals(coverDTO, service.findCoverByBookTitle("test"));
    }

    @Test
    public void getAllTest(){
        List<Cover> covers = new ArrayList<>(
                Arrays.asList(
                        new Cover("1","link1","test1"),
                        new Cover("2","link2","test2"),
                        new Cover("3","link3","test3")));
        when(repository.findAll()).thenReturn(covers);
        List<CoverDTO> authorDTOS = covers.stream()
                .map(cover -> dtoUtil.coverToDTO(cover))
                .collect(Collectors.toList());
        assertEquals(authorDTOS , service.getAll());
    }

    @Test
    public void insertTest(){
        Cover cover = new Cover("1","link","test");
        when(repository.insert(cover)).thenReturn(cover);
        assertEquals(dtoUtil.coverToDTO(cover), service.insert(dtoUtil.coverToDTO(cover)));
    }

    @Test
    public void updateTest(){
        Cover cover = new Cover("1","link","test");
        when(repository.save(cover)).thenReturn(cover);
        assertEquals(dtoUtil.coverToDTO(cover), service.update(dtoUtil.coverToDTO(cover)));
    }

    @Test
    public void deleteTest(){
        service.delete("1");
        verify(repository, times(1)).deleteById("1");
    }

}
