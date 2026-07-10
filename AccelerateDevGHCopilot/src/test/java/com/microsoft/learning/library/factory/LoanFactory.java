package com.microsoft.learning.library.factory;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;

import java.time.LocalDateTime;

public final class LoanFactory {
    private static int loanId = 777;

    private LoanFactory() {
    }

    public static Loan createReturnedLoanForPatron(Patron patron) {
        Loan loan = new Loan();
        loan.setId(loanId++);
        loan.setDueDate(LocalDateTime.now().plusDays(1));
        loan.setReturnDate(LocalDateTime.now().minusDays(1));
        loan.setPatronId(patron.getId());
        loan.setPatron(patron);
        return loan;
    }

    public static Loan createCurrentLoanForPatron(Patron patron) {
        Loan loan = new Loan();
        loan.setId(loanId++);
        loan.setDueDate(LocalDateTime.now().plusDays(1));
        loan.setReturnDate(null);
        loan.setPatronId(patron.getId());
        loan.setPatron(patron);
        return loan;
    }

    public static Loan createExpiredLoanForPatron(Patron patron) {
        Loan loan = new Loan();
        loan.setId(loanId++);
        loan.setDueDate(LocalDateTime.now().minusDays(1));
        loan.setReturnDate(null);
        loan.setPatronId(patron.getId());
        loan.setPatron(patron);
        return loan;
    }
}
