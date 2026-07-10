package com.microsoft.learning.library.service;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.enums.LoanExtensionStatus;
import com.microsoft.learning.library.enums.LoanReturnStatus;
import com.microsoft.learning.library.repository.LoanRepository;

import java.time.LocalDateTime;

public class LoanService {
    public static final int EXTEND_BY_DAYS = 14;

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public LoanReturnStatus returnLoan(int loanId) {
        Loan loan = loanRepository.getLoan(loanId);
        if (loan == null) {
            return LoanReturnStatus.LOAN_NOT_FOUND;
        }

        if (loan.getReturnDate() != null) {
            return LoanReturnStatus.ALREADY_RETURNED;
        }

        loan.setReturnDate(LocalDateTime.now());
        try {
            loanRepository.updateLoan(loan);
            return LoanReturnStatus.SUCCESS;
        } catch (RuntimeException ex) {
            return LoanReturnStatus.ERROR;
        }
    }

    public LoanExtensionStatus extendLoan(int loanId) {
        Loan loan = loanRepository.getLoan(loanId);
        if (loan == null) {
            return LoanExtensionStatus.LOAN_NOT_FOUND;
        }

        if (loan.getPatron() != null && loan.getPatron().getMembershipEnd().isBefore(LocalDateTime.now())) {
            return LoanExtensionStatus.MEMBERSHIP_EXPIRED;
        }

        if (loan.getReturnDate() != null) {
            return LoanExtensionStatus.LOAN_RETURNED;
        }

        if (loan.getDueDate().isBefore(LocalDateTime.now())) {
            return LoanExtensionStatus.LOAN_EXPIRED;
        }

        loan.setDueDate(loan.getDueDate().plusDays(EXTEND_BY_DAYS));
        try {
            loanRepository.updateLoan(loan);
            return LoanExtensionStatus.SUCCESS;
        } catch (RuntimeException ex) {
            return LoanExtensionStatus.ERROR;
        }
    }
}
