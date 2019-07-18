package com.image.library.application.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.library.application.dto.CoverDTO;
import com.image.library.application.service.CoverService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CoverControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    CoverController controller;

    @Mock
    CoverService service;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllCovers() throws Exception{
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();

        CoverDTO coverDTO1 = new CoverDTO.Builder("2")
                .withLink("link2")
                .withBookTitle("test2")
                .build();

        List<CoverDTO> coverDTOS = new ArrayList<>();
        coverDTOS.add(coverDTO);
        coverDTOS.add(coverDTO1);

        when(service.getAll()).thenReturn(coverDTOS);
        mockMvc.perform(MockMvcRequestBuilders.get("/covers/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCoverByBookTitleTest() throws Exception{
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();

        when(service.findCoverByBookTitle("test")).thenReturn(coverDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/covers/cover/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.is("link")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookTitle", Matchers.is("test")));
        verify(service).findCoverByBookTitle("test");
        verify(service, times(1)).findCoverByBookTitle("test");
    }

    @Test
    public void insertTest() throws Exception{
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();

        when(service.insert(any(CoverDTO.class))).thenReturn(coverDTO);
        String jsonRequest = objectMapper.writeValueAsString(coverDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/covers")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")));

        ArgumentCaptor<CoverDTO> dtoCaptor = ArgumentCaptor.forClass(CoverDTO.class);
        verify(service, times(1)).insert(dtoCaptor.capture());
        verifyNoMoreInteractions(service);

        CoverDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getBookTitle(), is("test"));
    }

    @Test
    public void insertCoverWithoutLinkReturnValidationErrorTest() throws Exception {
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withBookTitle("test")
                .build();
        String jsonRequest = objectMapper.writeValueAsString(coverDTO);
        when(service.insert(any(CoverDTO.class))).thenReturn(coverDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/covers")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").exists())*/
                .andReturn();

        verifyZeroInteractions(service);
    }

    @Test
    public void updateTest() throws Exception {
        CoverDTO coverDTO = new CoverDTO.Builder("1")
                .withLink("link")
                .withBookTitle("test")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(coverDTO);
        when(service.update(any(CoverDTO.class))).thenReturn(coverDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/covers")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));

        ArgumentCaptor<CoverDTO> dtoCaptor = ArgumentCaptor.forClass(CoverDTO.class);
        verify(service, times(1)).update(dtoCaptor.capture());
        verifyNoMoreInteractions(service);

        CoverDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getBookTitle(), is("test"));
    }

    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/covers/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
