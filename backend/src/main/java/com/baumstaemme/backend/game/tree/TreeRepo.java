package com.baumstaemme.backend.game.tree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreeRepo extends JpaRepository<Tree, Long> {

    @Query("SELECT tree.id FROM Tree tree")
    List<Long> findAllIds();

    List<Object> findByOwner(Object o);
}
