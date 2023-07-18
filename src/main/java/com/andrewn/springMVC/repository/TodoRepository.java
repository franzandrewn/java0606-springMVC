package com.andrewn.springMVC.repository;

import com.andrewn.springMVC.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.desc LIKE %?1%")
    List<Todo> findByDesc(String desc);

    @Query(nativeQuery = true, value = "SELECT * FROM todo WHERE complete=?1")
    List<Todo> findByComplete(boolean complete);
}
