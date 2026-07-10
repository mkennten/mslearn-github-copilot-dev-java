package com.microsoft.learning.library.repository.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.learning.library.domain.Author;
import com.microsoft.learning.library.domain.Book;
import com.microsoft.learning.library.domain.BookItem;
import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonDataStore {
    private static final String AUTHORS_FILE = "Authors.json";
    private static final String BOOKS_FILE = "Books.json";
    private static final String BOOK_ITEMS_FILE = "BookItems.json";
    private static final String PATRONS_FILE = "Patrons.json";
    private static final String LOANS_FILE = "Loans.json";

    private final Path dataDirectory;
    private final ObjectMapper objectMapper;

    private List<Author> authors;
    private List<Book> books;
    private List<BookItem> bookItems;
    private List<Patron> patrons;
    private List<Loan> loans;

    public JsonDataStore(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public synchronized void ensureDataLoaded() {
        if (patrons == null) {
            loadData();
        }
    }

    public synchronized void loadData() {
        try {
            authors = loadJson(AUTHORS_FILE, new TypeReference<>() {});
            books = loadJson(BOOKS_FILE, new TypeReference<>() {});
            bookItems = loadJson(BOOK_ITEMS_FILE, new TypeReference<>() {});
            patrons = loadJson(PATRONS_FILE, new TypeReference<>() {});
            loans = loadJson(LOANS_FILE, new TypeReference<>() {});
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load JSON data.", ex);
        }
    }

    public synchronized void saveLoans(List<Loan> sourceLoans) {
        List<Loan> toSave = sourceLoans.stream().map(l -> {
            Loan loan = new Loan();
            loan.setId(l.getId());
            loan.setBookItemId(l.getBookItemId());
            loan.setPatronId(l.getPatronId());
            loan.setLoanDate(l.getLoanDate());
            loan.setDueDate(l.getDueDate());
            loan.setReturnDate(l.getReturnDate());
            return loan;
        }).toList();

        saveJson(LOANS_FILE, toSave);
    }

    public synchronized void savePatrons(List<Patron> sourcePatrons) {
        List<Patron> toSave = sourcePatrons.stream().map(p -> {
            Patron patron = new Patron();
            patron.setId(p.getId());
            patron.setName(p.getName());
            patron.setMembershipStart(p.getMembershipStart());
            patron.setMembershipEnd(p.getMembershipEnd());
            patron.setImageName(p.getImageName());
            return patron;
        }).toList();

        saveJson(PATRONS_FILE, toSave);
    }

    public synchronized List<Patron> getPopulatedPatrons(List<Patron> sourcePatrons) {
        List<Patron> populated = new ArrayList<>();
        for (Patron patron : sourcePatrons) {
            populated.add(getPopulatedPatron(patron));
        }
        return populated;
    }

    public synchronized Patron getPopulatedPatron(Patron source) {
        Patron patron = new Patron();
        patron.setId(source.getId());
        patron.setName(source.getName());
        patron.setImageName(source.getImageName());
        patron.setMembershipStart(source.getMembershipStart());
        patron.setMembershipEnd(source.getMembershipEnd());

        List<Loan> patronLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getPatronId() == source.getId()) {
                patronLoans.add(getPopulatedLoan(loan));
            }
        }
        patron.setLoans(patronLoans);
        return patron;
    }

    public synchronized Loan getPopulatedLoan(Loan source) {
        Loan loan = new Loan();
        loan.setId(source.getId());
        loan.setBookItemId(source.getBookItemId());
        loan.setPatronId(source.getPatronId());
        loan.setLoanDate(source.getLoanDate());
        loan.setDueDate(source.getDueDate());
        loan.setReturnDate(source.getReturnDate());

        for (BookItem item : bookItems) {
            if (item.getId() == source.getBookItemId()) {
                loan.setBookItem(getPopulatedBookItem(item));
                break;
            }
        }

        for (Patron patron : patrons) {
            if (patron.getId() == source.getPatronId()) {
                loan.setPatron(patron);
                break;
            }
        }

        return loan;
    }

    public synchronized List<Patron> patrons() {
        return patrons;
    }

    public synchronized List<Loan> loans() {
        return loans;
    }

    private BookItem getPopulatedBookItem(BookItem source) {
        BookItem item = new BookItem();
        item.setId(source.getId());
        item.setBookId(source.getBookId());
        item.setAcquisitionDate(source.getAcquisitionDate());
        item.setCondition(source.getCondition());

        for (Book book : books) {
            if (book.getId() == source.getBookId()) {
                item.setBook(getPopulatedBook(book));
                break;
            }
        }

        return item;
    }

    private Book getPopulatedBook(Book source) {
        Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setAuthorId(source.getAuthorId());
        book.setGenre(source.getGenre());
        book.setIsbn(source.getIsbn());
        book.setImageName(source.getImageName());

        for (Author author : authors) {
            if (author.getId() == source.getAuthorId()) {
                Author copy = new Author();
                copy.setId(author.getId());
                copy.setName(author.getName());
                book.setAuthor(copy);
                break;
            }
        }

        return book;
    }

    private <T> T loadJson(String fileName, TypeReference<T> type) throws IOException {
        return objectMapper.readValue(dataDirectory.resolve(fileName).toFile(), type);
    }

    private void saveJson(String fileName, Object value) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(dataDirectory.resolve(fileName).toFile(), value);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to save JSON data.", ex);
        }
    }
}
