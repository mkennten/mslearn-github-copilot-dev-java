package com.microsoft.learning.library.repository.json;

import com.microsoft.learning.library.domain.Book;
import com.microsoft.learning.library.repository.BookRepository;

import java.util.List;

public class JsonBookRepository implements BookRepository {
    private final JsonDataStore jsonDataStore;

    public JsonBookRepository(JsonDataStore jsonDataStore) {
        this.jsonDataStore = jsonDataStore;
    }

    @Override
    public List<Book> searchBooks(String searchInput) {
        // TODO (Student exercise): Implement book search by title or author using GitHub Copilot.
        // See LAB_AK_03_develop_code_features_java.md for step-by-step instructions.
        throw new UnsupportedOperationException("searchBooks is not yet implemented.");
    }
}
