package com.microsoft.learning.library.repository;

import com.microsoft.learning.library.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> searchBooks(String searchInput);
}
