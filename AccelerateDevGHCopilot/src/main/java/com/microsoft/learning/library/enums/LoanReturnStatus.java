package com.microsoft.learning.library.enums;

public enum LoanReturnStatus {
    SUCCESS("Book was successfully returned."),
    LOAN_NOT_FOUND("Loan not found."),
    ALREADY_RETURNED("Cannot return book as the book is already returned."),
    ERROR("Cannot return book due to an error.");

    private final String message;

    LoanReturnStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
