package com.microsoft.learning.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Loan {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("BookItemId")
    private int bookItemId;
    @JsonProperty("PatronId")
    private int patronId;
    @JsonIgnore
    private Patron patron;
    @JsonProperty("LoanDate")
    private LocalDateTime loanDate;
    @JsonProperty("DueDate")
    private LocalDateTime dueDate;
    @JsonProperty("ReturnDate")
    private LocalDateTime returnDate;
    @JsonIgnore
    private BookItem bookItem;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookItemId() { return bookItemId; }
    public void setBookItemId(int bookItemId) { this.bookItemId = bookItemId; }

    public int getPatronId() { return patronId; }
    public void setPatronId(int patronId) { this.patronId = patronId; }

    public Patron getPatron() { return patron; }
    public void setPatron(Patron patron) { this.patron = patron; }

    public LocalDateTime getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDateTime loanDate) { this.loanDate = loanDate; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }

    public BookItem getBookItem() { return bookItem; }
    public void setBookItem(BookItem bookItem) { this.bookItem = bookItem; }
}
