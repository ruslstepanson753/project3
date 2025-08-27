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
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public PersonController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String peopleShow(Model model) {
        List<Person> people = peopleService.findAll();
        model.addAttribute("people", people);
        return "library/people";
    }

    @GetMapping("/new")
    public String goToNewPerson(@ModelAttribute("person") Person person) {
        return "library/newPerson";
    }

    @PostMapping()
    public String newPerson(@ModelAttribute("person") Person person,Model model) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String goToEditPerson(Model model, @PathVariable("id") int id) {
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        return "library/editPerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person,Model model,@PathVariable("id") int id) {
        peopleService.update(id,person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String goToPerson(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findOneWithBooksEager(id);
        List<Book> books = person.getBooks();
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "library/person";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
