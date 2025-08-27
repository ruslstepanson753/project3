package com.stepanov.project03.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "year_born")
    private int yearBorn;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Person() {
    }

    public Person(String fullName, int yearBorn) {
        this.fullName = fullName;
        this.yearBorn = yearBorn;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getPersonId() {
        return personId;
    }


    public @NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String fullName) {
        this.fullName = fullName;
    }

    @Min(value = 0, message = "Age should be greater than 0")
    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(@Min(value = 0, message = "Age should be greater than 0") int yearBorn) {
        this.yearBorn = yearBorn;
    }


    public void addBook(Book book) {
        if(books == null){
            books = new ArrayList<>();
        }
        books.add(book);
        book.setPerson(this);
    }

    public void removeBook(Book book) {
        book.setPerson(null);
        books.remove(book);
    }
}
