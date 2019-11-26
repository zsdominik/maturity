package com.zsirosd.maturity.repository;

import com.zsirosd.maturity.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
