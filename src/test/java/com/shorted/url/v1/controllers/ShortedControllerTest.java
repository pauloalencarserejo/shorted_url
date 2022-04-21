package com.shorted.url.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shorted.url.exceptions.BadRequestException;
import com.shorted.url.exceptions.NotFoundException;
import com.shorted.url.v1.creators.LinkCreator;
import com.shorted.url.v1.creators.UrlCreator;
import com.shorted.url.v1.dto.UrlDto;
import com.shorted.url.v1.services.ShortedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShortedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShortedService service;

    @InjectMocks
    private ShortedController controller;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        BDDMockito.when(service.shortedUrl(ArgumentMatchers.any(UrlDto.class)))
                .thenReturn(LinkCreator.linkWithId().getCode());

        BDDMockito.when(service.findAll())
                .thenReturn(Collections.singletonList(LinkCreator.linkResponse()));

        BDDMockito.when(service.findUrl(ArgumentMatchers.anyString()))
                .thenReturn(LinkCreator.linkWithId().getUrl());
    }

    @Test
    void shortedUrl() throws Exception {
        String request = mapper.writeValueAsString(UrlCreator.urlDto());
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated());
    }

    @Test
    void shortedUrl_BadRequest() throws Exception {
        BDDMockito.when(service.shortedUrl(ArgumentMatchers.any(UrlDto.class)))
            .thenThrow(BadRequestException.class);
        String request = mapper.writeValueAsString(UrlCreator.urlDto());
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findUrl() throws Exception {
        String code = LinkCreator.linkWithId().getCode();
        mockMvc.perform(get("/" + code)).andExpect(status().isMovedPermanently());
    }

    @Test
    void findUrl_NotFound() throws Exception {
        BDDMockito.when(service.findUrl(ArgumentMatchers.anyString()))
                .thenThrow(NotFoundException.class);
        String code = LinkCreator.linkWithId().getCode();
        mockMvc.perform(get("/" + code)).andExpect(status().isNotFound());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/all")).andExpect(status().isOk());
    }

}