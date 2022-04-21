package com.shorted.url.v1.creators;

import com.shorted.url.v1.dto.UrlDto;

public class UrlCreator {

    private static String url = "https://www.google.com/search?q=spring+boot&oq=spring+boot+&aqs=edge..69i57j0i131i433i512j0i512l5j69i61l2.9190j0j1&sourceid=chrome&ie=UTF-8";

    public static UrlDto urlDto() {
        return new UrlDto(url);
    }

}
