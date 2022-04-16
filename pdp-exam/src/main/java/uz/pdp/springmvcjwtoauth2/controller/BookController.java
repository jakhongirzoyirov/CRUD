package uz.pdp.springmvcjwtoauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springmvcjwtoauth2.model.dto.BookDto;
import uz.pdp.springmvcjwtoauth2.model.entity.book.BookEntity;
import uz.pdp.springmvcjwtoauth2.service.BookService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("books/add")
    public HttpEntity<?> addBook(BookDto bookDto){
        BookEntity book = bookService.addBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/books")
    public HttpEntity<?> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/book/{id}")
    public HttpEntity<?> getBook(@PathVariable Long id){
        BookEntity book = bookService.getBook(id);
        return ResponseEntity.status(book != null? HttpStatus.OK : HttpStatus.CONFLICT).body(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/book/edit/{id}")
    public HttpEntity<?> editBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        BookEntity book = bookService.editBook(id, bookDto);
        return ResponseEntity.status(book != null ? 202 : 409).body(book);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/book/delete/{id}")
    public HttpEntity<?> deleteBook(@PathVariable Long id){
        boolean deleted = bookService.deleteBook(id);
        if(deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.noContent().build();
    }

}
