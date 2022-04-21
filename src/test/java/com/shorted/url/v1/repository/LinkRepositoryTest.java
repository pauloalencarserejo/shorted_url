package com.shorted.url.v1.repository;


import com.shorted.url.v1.creators.LinkCreator;
import com.shorted.url.v1.model.Link;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration
public class LinkRepositoryTest {

    @Autowired
    private LinkRepository repository;

    @Test
    void save_persistLink() {
        Link link = LinkCreator.linkWithoutId();
        Link save = repository.save(link);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isNotNull();
    }

    @Test
    void findCode_returnLink() {
        Link save = repository.save(LinkCreator.linkWithoutId());
        String code = save.getCode();

        Optional<Link> link = repository.findByCode(code);

        Assertions.assertThat(link).isNotNull().isNotEmpty();
        Assertions.assertThat(link.get().getCode()).isNotEmpty().isNotEmpty().isEqualTo(code);
    }

    @Test
    void findAll_returnListLink() {
        repository.save(LinkCreator.linkWithoutId());

        List<Link> all = repository.findAll();

        Assertions.assertThat(all).isNotNull().isNotEmpty().hasSize(1);
    }


}