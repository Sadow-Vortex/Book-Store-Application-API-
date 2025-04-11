package com.begin.bookstore_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        book.setPublishDate(LocalDate.now());
        return bookRepository.save(book);
    }

public List<Book> getAllBooks() {
        return bookRepository.findAll();
}
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> updateBook(Long id,Book updatebook) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthor(updatebook.getAuthor());
            book.setTitle(updatebook.getTitle());
            book.setPrice(updatebook.getPrice());
            book.setCategory(updatebook.getCategory());
            book.setRating(updatebook.getRating());
            book.setPublishDate(updatebook.getPublishDate());
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(Long id) {
        return bookRepository.findById(id).map(book -> {
            bookRepository.delete(book);
            return true;
        }).orElse(false);
    }
}
