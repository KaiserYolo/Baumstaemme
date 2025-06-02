package com.baumstaemme.backend.map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface MapRepo extends JpaRepository<Map,Long> {
    @Query("SELECT m.id FROM Map m")
    List<Long> findAllMapIds();
}
