package com.shorted.url.v1.services.impl;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.shorted.url.exceptions.BadRequestException;
import com.shorted.url.exceptions.NotFoundException;
import com.shorted.url.v1.dto.LinkResponse;
import com.shorted.url.v1.dto.UrlDto;
import com.shorted.url.v1.model.Link;
import com.shorted.url.v1.repository.LinkRepository;
import com.shorted.url.v1.services.ShortedService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShortedServiceImpl implements ShortedService{
    
    private final LinkRepository repository;
    
    @Override
    public String shortedUrl(UrlDto url) {
        try {
            Link save = repository.save(this.toLink(url.getUrl()));
            return save.getCode();
        } catch (Exception e) {
            throw new BadRequestException("Canot generate shorted url");
        }
    }

    @Override
    public List<LinkResponse> findAll() {
        return repository.findAll().stream().map(LinkResponse::toLinkResponse).collect(Collectors.toList());
    }

    @Override
    public String findUrl(String code) {
        return repository.findByCode(code).orElseThrow(() -> new NotFoundException("Not Found Link")).getUrl();
    }

    private String generateCode() {
        Long count = repository.count();
        String code = Base64.getEncoder().encodeToString(count.toString().getBytes());
        if (code.length() <= 8) {
            return code;
        } else {
            return code.substring(0, 7);
        } 
    }


    private Link toLink(String url) {
        return Link.builder()
            .code(this.generateCode())
            .url(url)
            .createdAt(LocalDateTime.now())
            .build();
    }


}
