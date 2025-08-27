package com.stepanov.project03.controllers;

import com.stepanov.project03.models.Book;
import com.stepanov.project03.models.Person;
import com.stepanov.project03.services.BookService;
import com.stepanov.project03.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(
            Model model,
            @RequestParam(value = "page" ,required = false) Integer page,
            @RequestParam(value = "booksPerPage",required = false) Integer booksPerPage,
            @RequestParam(value = "sortByYear",defaultValue = "false") boolean sortByYear) {
        List<Book> books ;
        books = bookService.getBooks(page, booksPerPage, sortByYear);
        model.addAttribute("books", books);
        return "library/books";
    }

    @GetMapping("/new")
    public String goToNewBook(@ModelAttribute("book") Book book) {
        return "library/newBook";
    }

    @PostMapping()
    public String newBook(@ModelAttribute("book") Book book,Model model) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String goToEditBook(Model model, @PathVariable("id") int id) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "library/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book,Model model,@PathVariable("id") int id) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String goToBook(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBook(id);
        Person person = book.getPerson();

        if (person != null) {
            model.addAttribute("person", peopleService.findOne(person.getPersonId()));
        } else {
            model.addAttribute("person", null);
        }
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("book", book);
        return "library/book";
    }

    @PatchMapping("/addperson")
    public String addPerson(@ModelAttribute("person") Person person,
                            Model model,
                            @ModelAttribute("bookId") Integer bookId)
    {
        Book book = bookService.getBook(bookId);
        // Устанавливаем связь
        person.addBook(book);
        bookService.save(book);
        return "redirect:/books" ;
    }

    @PatchMapping("/freebook/{id}")
    public String makeFreeBook(Model model,@PathVariable("id") int id) {
        Book book = bookService.getBookWithPersonEager(id);
        Person person = book.getPerson();
        person.removeBook(book);
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(Model model,@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "query",required = false) String query, Model model) {
       if(query != null) {
           Book book = bookService.searchBooks(query);
           model.addAttribute("book", book);
       }
        return "library/bookSearch";
    }

}
