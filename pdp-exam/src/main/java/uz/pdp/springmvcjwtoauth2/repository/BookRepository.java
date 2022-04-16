package uz.pdp.springmvcjwtoauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springmvcjwtoauth2.model.entity.book.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
