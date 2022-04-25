package com.shorted.url.v1.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shorted.url.v1.dto.LinkResponse;
import com.shorted.url.v1.dto.UrlDto;
import com.shorted.url.v1.services.ShortedService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShortedController {
    
    private final ShortedService service;

    @PostMapping
    public ResponseEntity<String> shortedUrl(@RequestBody UrlDto url, HttpServletRequest request) {
        return new ResponseEntity<>(request.getRequestURL() + service.shortedUrl(url), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{code}")
    public void findUrl(@PathVariable String code, HttpServletResponse response) throws IOException {
        this.sendRedirectUrl(response, service.findUrl(code));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<LinkResponse>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    private void sendRedirectUrl(HttpServletResponse response, String newUrl) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Location", newUrl);
        response.setHeader("Connection", "close");
    }

}
