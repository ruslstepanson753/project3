package com.stepanov.project03.repositories;

import com.stepanov.project03.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findFirstByBookNameStartingWith(String prefix);
}
