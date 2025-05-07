package br.com.cp2_java.service;

import br.com.cp2_java.domainmodel.Book;
import br.com.cp2_java.exceptions.BookNotFound;
import br.com.cp2_java.exceptions.InsufficientStock;
import br.com.cp2_java.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(UUID id) throws BookNotFound {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found"));
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }

    public Book addBook(UUID id, int quantity) throws BookNotFound {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found"));
        book.addBook(quantity);
        return this.bookRepository.save(book);
    }

    public void removeBook(UUID id, int quantity) throws InsufficientStock, BookNotFound {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found"));
        if(book.getQuantity() < quantity){
            throw new InsufficientStock("There is no such amount in stock");
        }
        book.removeBook(quantity);
        this.bookRepository.save(book);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book updateBook(UUID id, Book update) throws BookNotFound {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found"));
        book.setAuthor(update.getAuthor());
        book.setTitle(update.getTitle());
        book.setQuantity(update.getQuantity());
        book.setPublisher(update.getPublisher());
        book.setPublicationDate(update.getPublicationDate());
        book.setPrice(update.getPrice());
        return this.bookRepository.save(book);

    }
}
