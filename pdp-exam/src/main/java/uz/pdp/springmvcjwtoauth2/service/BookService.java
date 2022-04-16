package uz.pdp.springmvcjwtoauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.springmvcjwtoauth2.model.dto.BookDto;
import uz.pdp.springmvcjwtoauth2.model.entity.book.BookEntity;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.repository.BookRepository;
import uz.pdp.springmvcjwtoauth2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookEntity addBook(BookDto bookDto) {
        BookEntity book = new BookEntity();
        UserEntity user = new UserEntity();

        boolean details = bookDetails(book, bookDto, user);
        if (details)
            return bookRepository.save(book);
        return null;
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBook(Long id) {
        Optional<BookEntity> byId = bookRepository.findById(id);

        return byId.orElse(null);
    }

    public BookEntity editBook(Long id, BookDto bookDto) {
        Optional<BookEntity> byId = bookRepository.findById(id);

        if (byId.isPresent()) {
            UserEntity user = new UserEntity();
            BookEntity book = byId.get();
            boolean details = bookDetails(book, bookDto, user);
            if (details)
                return bookRepository.save(book);
        }
        return null;
    }

    public boolean deleteBook(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (exists) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean bookDetails(BookEntity bookEntity, BookDto bookDto, UserEntity user) {
        boolean exists = userRepository.existsById(user.getId());
        if (exists) {
            bookEntity.setName(bookDto.getName());
            bookEntity.setCreatedBy(userRepository.getById(user.getId()));
            bookEntity.setUpdatedBy(userRepository.getById(user.getId()));
            return true;
        }
        return false;
    }

}

