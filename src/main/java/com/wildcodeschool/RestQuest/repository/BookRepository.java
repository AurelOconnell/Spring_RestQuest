package com.wildcodeschool.RestQuest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.RestQuest.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findByTitleContainingOrDescriptionContaining(String title, String description);

}
