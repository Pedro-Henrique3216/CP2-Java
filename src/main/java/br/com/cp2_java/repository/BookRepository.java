package br.com.cp2_java.repository;

import br.com.cp2_java.domainmodel.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
