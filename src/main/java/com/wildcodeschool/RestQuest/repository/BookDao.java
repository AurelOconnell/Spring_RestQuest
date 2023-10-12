package com.wildcodeschool.RestQuest.repository;

import com.wildcodeschool.RestQuest.entity.Book;

import java.util.List;

public interface BookDao {
    Book save(Book entity);

    Book findById(int id);

    List<Book> findAll();

    Book update(Book entity);

    void deleteById(int id);
}