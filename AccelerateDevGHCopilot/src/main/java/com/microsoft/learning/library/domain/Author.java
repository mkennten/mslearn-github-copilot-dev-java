package com.microsoft.learning.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
