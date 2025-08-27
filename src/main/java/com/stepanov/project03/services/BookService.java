package com.stepanov.project03.services;

import com.stepanov.project03.models.Book;
import com.stepanov.project03.models.Person;
import com.stepanov.project03.repositories.BookRepository;
import com.stepanov.project03.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public List<Book> getBooksByPerson(Person person) {
        List<Book> books = person.getBooks();
        Hibernate.initialize(books); // Инициализация в той же транзакции
        return books;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).get();
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBookId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooks(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (page == null) page = 0;
        if (booksPerPage == null) booksPerPage = 10;

        PageRequest pageRequest;

        if (sortByYear) {
            pageRequest = PageRequest.of(page, booksPerPage, Sort.by("yearBook"));
        } else {
            pageRequest = PageRequest.of(page, booksPerPage);
        }

        return bookRepository.findAll(pageRequest).getContent();
    }

    public Book searchBooks(String query) {
        return bookRepository.findFirstByBookNameStartingWith(query);
    }

    public Book getBookWithPersonEager(int id) {
        Book book = bookRepository.findById(id).get();
        List<Book> books = book.getPerson().getBooks();
        Hibernate.initialize(books);
        return book;
    }
}
