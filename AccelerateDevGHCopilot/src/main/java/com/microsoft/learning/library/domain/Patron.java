package com.microsoft.learning.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("MembershipEnd")
    private LocalDateTime membershipEnd;
    @JsonProperty("MembershipStart")
    private LocalDateTime membershipStart;
    @JsonProperty("ImageName")
    private String imageName;
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getMembershipEnd() { return membershipEnd; }
    public void setMembershipEnd(LocalDateTime membershipEnd) { this.membershipEnd = membershipEnd; }

    public LocalDateTime getMembershipStart() { return membershipStart; }
    public void setMembershipStart(LocalDateTime membershipStart) { this.membershipStart = membershipStart; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public List<Loan> getLoans() { return loans; }
    public void setLoans(List<Loan> loans) { this.loans = loans; }
}
