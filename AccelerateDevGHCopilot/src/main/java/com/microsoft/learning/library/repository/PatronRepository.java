package com.microsoft.learning.library.repository;

import com.microsoft.learning.library.domain.Patron;

import java.util.List;

public interface PatronRepository {
    List<Patron> searchPatrons(String searchInput);

    Patron getPatron(int id);

    void updatePatron(Patron patron);
}
