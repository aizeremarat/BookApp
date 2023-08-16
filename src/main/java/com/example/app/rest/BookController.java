package com.example.app.rest;

import com.example.app.entity.Book;
import com.example.app.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


        @GetMapping(value = "/books")
        public List<Book> getAllBooks () {
            return bookRepository.findAll();
        }


        @GetMapping(value = "/books/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable Long id) {
            Optional<Book> optionalBook = bookRepository.findById(id);
            return optionalBook.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @PostMapping(value = "/books")
        public ResponseEntity<Book> createBook (@RequestBody Book book){
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.ok(savedBook);
        }
        @PutMapping("/books/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
            Optional<Book> optionalBook = bookRepository.findById(id);

            if (optionalBook.isPresent()) {
                Book existingBook = optionalBook.get();
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setRelease(updatedBook.getRelease());

                Book savedBook = bookRepository.save(existingBook);
                return ResponseEntity.ok(savedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
