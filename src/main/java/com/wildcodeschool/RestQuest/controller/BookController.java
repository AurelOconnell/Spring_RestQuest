package com.wildcodeschool.RestQuest.controller;

import com.wildcodeschool.RestQuest.entity.Book;
import com.wildcodeschool.RestQuest.repository.BookRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepositoryInjected){
        this.bookRepository = bookRepositoryInjected;
    }

    @GetMapping()
    public List<Book> index(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        // les méthodes du repo (pas ttes) renvoient des optionals = wrapper ajouter une vérif supplémentaire sur l'existence de la valeur d'1 variable
        Optional<Book> optionalBook = bookRepository.findById(id);
        // la méthode isPresent permet de vérifier l'existence de la valeur (d'où la condition if) 
        if (optionalBook.isPresent()) {
            // si valeur présente => récupéré grace à la méthode .get()
            Book book = optionalBook.get();
            return ResponseEntity.ok(book);
        } else {
            return new ResponseEntity<>("Nope", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public List<Book> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return bookRepository.findByTitleContainingOrDescriptionContaining(searchTerm, searchTerm);
    }

    @PostMapping()
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> optionalBookToUpdate = bookRepository.findById(id);
        if (optionalBookToUpdate.isPresent()) {
            Book bookToUpdate = optionalBookToUpdate.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setDescription(book.getDescription());
            return ResponseEntity.ok(bookRepository.save(bookToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return true;
    }
}