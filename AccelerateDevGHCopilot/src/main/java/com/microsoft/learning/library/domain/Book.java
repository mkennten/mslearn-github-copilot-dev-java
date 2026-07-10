package com.microsoft.learning.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("AuthorId")
    private int authorId;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("ImageName")
    private String imageName;
    @JsonProperty("ISBN")
    private String isbn;
    @JsonIgnore
    private Author author;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
