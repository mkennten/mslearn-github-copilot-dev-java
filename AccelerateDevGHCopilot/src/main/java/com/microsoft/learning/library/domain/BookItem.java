package com.microsoft.learning.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BookItem {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("BookId")
    private int bookId;
    @JsonProperty("AcquisitionDate")
    private LocalDateTime acquisitionDate;
    @JsonProperty("Condition")
    private String condition;
    @JsonIgnore
    private Book book;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public LocalDateTime getAcquisitionDate() { return acquisitionDate; }
    public void setAcquisitionDate(LocalDateTime acquisitionDate) { this.acquisitionDate = acquisitionDate; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
