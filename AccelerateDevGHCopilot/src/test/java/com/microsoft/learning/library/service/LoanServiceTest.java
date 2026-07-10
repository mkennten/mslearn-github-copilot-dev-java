package com.microsoft.learning.library.service;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;
import com.microsoft.learning.library.enums.LoanExtensionStatus;
import com.microsoft.learning.library.enums.LoanReturnStatus;
import com.microsoft.learning.library.factory.LoanFactory;
import com.microsoft.learning.library.factory.PatronFactory;
import com.microsoft.learning.library.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanServiceTest {
    private final LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
    private final LoanService loanService = new LoanService(loanRepository);

    @Test
    void returnLoan_returnsLoanNotFoundWhenLoanMissing() {
        Mockito.when(loanRepository.getLoan(1)).thenReturn(null);

        LoanReturnStatus status = loanService.returnLoan(1);

        assertEquals(LoanReturnStatus.LOAN_NOT_FOUND, status);
    }

    @Test
    void returnLoan_returnsAlreadyReturnedWhenLoanAlreadyReturned() {
        Patron patron = PatronFactory.createCurrentPatron();
        Loan loan = LoanFactory.createReturnedLoanForPatron(patron);
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanReturnStatus status = loanService.returnLoan(loan.getId());

        assertEquals(LoanReturnStatus.ALREADY_RETURNED, status);
    }

    @Test
    void returnLoan_returnsSuccessAndSetsReturnDate() {
        Patron patron = PatronFactory.createCurrentPatron();
        Loan loan = LoanFactory.createCurrentLoanForPatron(patron);
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanReturnStatus status = loanService.returnLoan(loan.getId());

        assertEquals(LoanReturnStatus.SUCCESS, status);
        assertNotNull(loan.getReturnDate());
    }

    @Test
    void extendLoan_extendsLoanSuccessfully() {
        Patron patron = PatronFactory.createCurrentPatron();
        Loan loan = LoanFactory.createCurrentLoanForPatron(patron);
        var previousDueDate = loan.getDueDate();
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanExtensionStatus status = loanService.extendLoan(loan.getId());

        assertEquals(LoanExtensionStatus.SUCCESS, status);
        assertEquals(previousDueDate.plusDays(LoanService.EXTEND_BY_DAYS), loan.getDueDate());
    }

    @Test
    void extendLoan_returnsLoanNotFoundWhenLoanMissing() {
        Mockito.when(loanRepository.getLoan(1)).thenReturn(null);

        LoanExtensionStatus status = loanService.extendLoan(1);

        assertEquals(LoanExtensionStatus.LOAN_NOT_FOUND, status);
    }

    @Test
    void extendLoan_returnsMembershipExpiredWhenPatronMembershipExpired() {
        Patron patron = PatronFactory.createExpiredPatron();
        Loan loan = LoanFactory.createCurrentLoanForPatron(patron);
        var previousDueDate = loan.getDueDate();
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanExtensionStatus status = loanService.extendLoan(loan.getId());

        assertEquals(LoanExtensionStatus.MEMBERSHIP_EXPIRED, status);
        assertEquals(previousDueDate, loan.getDueDate());
    }

    @Test
    void extendLoan_returnsLoanReturnedWhenLoanAlreadyReturned() {
        Patron patron = PatronFactory.createCurrentPatron();
        Loan loan = LoanFactory.createReturnedLoanForPatron(patron);
        var previousDueDate = loan.getDueDate();
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanExtensionStatus status = loanService.extendLoan(loan.getId());

        assertEquals(LoanExtensionStatus.LOAN_RETURNED, status);
        assertEquals(previousDueDate, loan.getDueDate());
    }

    @Test
    void extendLoan_returnsLoanExpiredWhenLoanExpired() {
        Patron patron = PatronFactory.createCurrentPatron();
        Loan loan = LoanFactory.createExpiredLoanForPatron(patron);
        var previousDueDate = loan.getDueDate();
        Mockito.when(loanRepository.getLoan(loan.getId())).thenReturn(loan);

        LoanExtensionStatus status = loanService.extendLoan(loan.getId());

        assertEquals(LoanExtensionStatus.LOAN_EXPIRED, status);
        assertEquals(previousDueDate, loan.getDueDate());
    }
}
