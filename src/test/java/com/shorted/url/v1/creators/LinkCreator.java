package com.shorted.url.v1.creators;

import com.shorted.url.v1.dto.LinkResponse;
import com.shorted.url.v1.model.Link;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class LinkCreator {

    private static Long id = 1L;

    private static String url = "https://www.google.com/search?q=spring+boot&oq=spring+boot+&aqs=edge..69i57j0i131i433i512j0i512l5j69i61l2.9190j0j1&sourceid=chrome&ie=UTF-8";

    private static String code = "M1==";

    private static LocalDateTime createdAt = LocalDateTime.of(2022,04,15,10,30);

    public static Link linkWithId() {
        return Link.builder()
                .id(id)
                .code(code)
                .url(url)
                .createdAt(createdAt)
                .build();
    }

    public static Link linkWithoutId() {
        return Link.builder()
                .code(code)
                .url(url)
                .createdAt(createdAt)
                .build();
    }

    public static LinkResponse linkResponse() {
        return LinkResponse.builder()
                .code(code)
                .url(url)
                .createdAt(createdAt)
                .build();
    }


}
