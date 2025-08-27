package com.stepanov.project03.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "book_name")
    private String bookName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "year_book")
    private int yearBook;

    @ManyToOne()
    @JoinColumn(name = "person_id",referencedColumnName = "person_id")
    private Person person;

    @Column(name = "taken_at") // Убрали @Temporal для LocalDateTime
    private LocalDateTime takenAt;

    @Transient
    public boolean isExpired() {
        if (takenAt == null) {
            return false; // книга не взята, значит не просрочена
        }

        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        return takenAt.isBefore(tenDaysAgo);
    }

    public Book() {
    }

    public Book(String bookName, String author, int yearBook, Integer personId) {
        this.bookName = bookName;
        this.author = author;
        this.yearBook = yearBook;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int yearBook) {
        this.yearBook = yearBook;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person == null) {
            this.person = null;
            setTakenAt(null);
        } else {
            this.person = person;
            setTakenAt(LocalDateTime.now());
        }
    }
}
