package com.microsoft.learning.library.enums;

public enum MembershipRenewalStatus {
    SUCCESS("Membership renewal was successful."),
    PATRON_NOT_FOUND("Patron not found."),
    TOO_EARLY_TO_RENEW("It is too early to renew the membership."),
    LOAN_NOT_RETURNED("Cannot renew membership due to an outstanding loan."),
    ERROR("Cannot renew membership due to an error.");

    private final String message;

    MembershipRenewalStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
