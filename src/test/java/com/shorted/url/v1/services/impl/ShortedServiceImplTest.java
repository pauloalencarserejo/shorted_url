package com.shorted.url.v1.services.impl;

import com.shorted.url.exceptions.BadRequestException;
import com.shorted.url.exceptions.NotFoundException;
import com.shorted.url.v1.creators.LinkCreator;
import com.shorted.url.v1.creators.UrlCreator;
import com.shorted.url.v1.dto.LinkResponse;
import com.shorted.url.v1.model.Link;
import com.shorted.url.v1.repository.LinkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ShortedServiceImplTest {

    @InjectMocks
    private ShortedServiceImpl service;

    @Mock
    private LinkRepository repository;

    @Test
    void shortedUrl_generateCodeReturnCode_sucess() {
        BDDMockito.when(repository.save(ArgumentMatchers.any(Link.class)))
                .thenReturn(LinkCreator.linkWithId());

        String code = service.shortedUrl(UrlCreator.urlDto());

        Assertions.assertThat(code).isNotEmpty();
    }

    @Test
    void shortedUrl_generateCodeReturnBadRequestException_fail() {
        BDDMockito.when(repository.save(ArgumentMatchers.any(Link.class)))
                .thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThatThrownBy(() -> service.shortedUrl(UrlCreator.urlDto()))
                .isInstanceOf(BadRequestException.class).isNotNull();
    }

    @Test
    void findAll_returnListLinkResponse_sucesso() {
        BDDMockito.when(repository.findAll())
                .thenReturn(Collections.singletonList(LinkCreator.linkWithId()));

        List<LinkResponse> links = service.findAll();

        Assertions.assertThat(links).isNotNull().isNotEmpty().hasSize(1);
    }

    @Test
    void findAll_returnEmptyList_notFound() {
        BDDMockito.when(repository.findAll())
                .thenReturn(Collections.emptyList());
        List<LinkResponse> links = service.findAll();

        Assertions.assertThat(links).isNotNull().isEmpty();
    }

    @Test
    void findUrl_returnUrl_sucesso() {
        BDDMockito.when(repository.findByCode(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(LinkCreator.linkWithId()));
        String code = LinkCreator.linkWithId().getCode();
        String url = service.findUrl(code);

        Assertions.assertThat(url).isNotNull().isNotEmpty();
    }

    @Test
    void findUrl_returnNotFoundException_notFound() {
        BDDMockito.when(repository.findByCode(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        String code = LinkCreator.linkWithId().getCode();

        Assertions.assertThatThrownBy(() -> service.findUrl(code))
                .isInstanceOf(NotFoundException.class).isNotNull();
    }


}