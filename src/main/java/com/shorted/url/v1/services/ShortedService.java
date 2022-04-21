package com.shorted.url.v1.services;

import java.util.List;

import com.shorted.url.v1.dto.LinkResponse;
import com.shorted.url.v1.dto.UrlDto;

public interface ShortedService {

    String shortedUrl(UrlDto url);

    String findUrl(String code);

    List<LinkResponse> findAll();
    
}
