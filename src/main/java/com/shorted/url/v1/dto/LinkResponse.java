package com.shorted.url.v1.dto;

import java.time.LocalDateTime;

import com.shorted.url.v1.model.Link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkResponse {

    private Long id;

    private String url;

    private String code;

    private LocalDateTime createdAt;

    public static LinkResponse toLinkResponse(Link link) {
        return LinkResponse.builder()
            .id(link.getId())
            .url(link.getUrl())
            .code(link.getCode())
            .createdAt(link.getCreatedAt())
            .build();
    }

    
}
