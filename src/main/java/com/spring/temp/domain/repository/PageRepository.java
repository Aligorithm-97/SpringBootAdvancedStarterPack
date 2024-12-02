package com.spring.temp.domain.repository;

import com.spring.temp.domain.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page,Long> {

    Optional<Page> findByPagePath(String path);
}
