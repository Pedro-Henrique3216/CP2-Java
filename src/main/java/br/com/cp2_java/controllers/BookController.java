package br.com.cp2_java.controllers;

import br.com.cp2_java.domainmodel.Book;
import br.com.cp2_java.dtos.books.BookRequest;
import br.com.cp2_java.dtos.books.BookResponse;
import br.com.cp2_java.dtos.books.UpdateBookQuantityRequest;
import br.com.cp2_java.dtos.books.UpdateBookRequest;
import br.com.cp2_java.exceptions.BookNotFound;
import br.com.cp2_java.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Add a new book", description = "Creates a new book and returns the created book with its URI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest request, UriComponentsBuilder uriBuilder) {
        Book book = request.toBook();
        URI uri = uriBuilder.path("/{id}").buildAndExpand(book.getId()).toUri();
        Book savedBook = this.bookService.save(book);
        return ResponseEntity.created(uri).body(BookResponse.toResponse(savedBook));
    }

    @Operation(summary = "Get all books", description = "Returns a list of all books")
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> books = this.bookService.findAll();
        return ResponseEntity.ok(books.stream().map(BookResponse::toResponse).toList());
    }

    @Operation(summary = "Get all books in pages", description = "Returns a paginated list of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
    })
    @GetMapping("/page")
    public ResponseEntity<Page<BookResponse>> getAllBooksInPages(@PageableDefault Pageable pageable) {
        Page<BookResponse> bookResponses = this.bookService
                .findAll(pageable)
                .map(BookResponse::toResponse);
        return ResponseEntity.ok(bookResponses);
    }

    @Operation(summary = "Get a book by ID", description = "Returns a book based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") UUID id) throws BookNotFound {
        Book book = this.bookService.findById(id);
        return ResponseEntity.ok(BookResponse.toResponse(book));
    }

    @Operation(summary = "add the quantity of a book", description = "Updates the quantity of the specified book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book quantity updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookQuantity(@PathVariable("id") UUID id, @Valid @RequestBody UpdateBookQuantityRequest request) throws BookNotFound {
        Book book = this.bookService.addBook(id, request.quantity());
        return ResponseEntity.ok(BookResponse.toResponse(book));
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes a book from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id) {
        this.bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update book details", description = "Updates the details of an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") UUID id, @Valid @RequestBody UpdateBookRequest request) throws BookNotFound {
        Book book = this.bookService.updateBook(id, request.toBook());
        return ResponseEntity.ok(BookResponse.toResponse(book));
    }
}
