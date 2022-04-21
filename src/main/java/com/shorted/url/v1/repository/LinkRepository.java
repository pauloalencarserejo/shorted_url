package com.shorted.url.v1.repository;

import java.util.Optional;

import com.shorted.url.v1.model.Link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    
    Optional<Link> findByCode(String code);

}
