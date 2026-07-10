package com.microsoft.learning.library.service;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;
import com.microsoft.learning.library.enums.MembershipRenewalStatus;
import com.microsoft.learning.library.factory.LoanFactory;
import com.microsoft.learning.library.factory.PatronFactory;
import com.microsoft.learning.library.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatronServiceTest {
    private final PatronRepository patronRepository = Mockito.mock(PatronRepository.class);
    private final PatronService patronService = new PatronService(patronRepository);

    @Test
    void renewMembership_renewsMembershipSuccessfullyWithoutLoans() {
        Patron patron = PatronFactory.createCurrentPatron();
        var membershipEnd = patron.getMembershipEnd();
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.SUCCESS, status);
        assertEquals(membershipEnd.plusYears(1), patron.getMembershipEnd());
    }

    @Test
    void renewMembership_renewsMembershipSuccessfullyWithExpiredMembership() {
        Patron patron = PatronFactory.createExpiredPatron();
        var membershipEnd = patron.getMembershipEnd();
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.SUCCESS, status);
        assertEquals(membershipEnd.plusYears(1), patron.getMembershipEnd());
    }

    @Test
    void renewMembership_renewsMembershipSuccessfullyWithReturnedLoans() {
        Patron patron = PatronFactory.createCurrentPatron();
        patron.setMembershipEnd(LocalDateTime.now().plusDays(1));
        var membershipEnd = patron.getMembershipEnd();
        Loan loan = LoanFactory.createReturnedLoanForPatron(patron);
        patron.setLoans(List.of(loan));
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.SUCCESS, status);
        assertEquals(membershipEnd.plusYears(1), patron.getMembershipEnd());
    }

    @Test
    void renewMembership_renewsMembershipSuccessfullyWithCurrentLoans() {
        Patron patron = PatronFactory.createCurrentPatron();
        patron.setMembershipEnd(LocalDateTime.now().plusDays(1));
        var membershipEnd = patron.getMembershipEnd();
        Loan loan = LoanFactory.createCurrentLoanForPatron(patron);
        patron.setLoans(List.of(loan));
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.SUCCESS, status);
        assertEquals(membershipEnd.plusYears(1), patron.getMembershipEnd());
    }

    @Test
    void renewMembership_returnsPatronNotFoundWhenPatronMissing() {
        Mockito.when(patronRepository.getPatron(42)).thenReturn(null);

        MembershipRenewalStatus status = patronService.renewMembership(42);

        assertEquals(MembershipRenewalStatus.PATRON_NOT_FOUND, status);
    }

    @Test
    void renewMembership_returnsTooEarlyToRenewWhenRenewalNotAllowedYet() {
        Patron patron = PatronFactory.createTooEarlyToRenewPatron();
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.TOO_EARLY_TO_RENEW, status);
    }

    @Test
    void renewMembership_returnsLoanNotReturnedWhenPatronHasOverdueLoans() {
        Patron patron = PatronFactory.createCurrentPatron();
        patron.setLoans(List.of(LoanFactory.createExpiredLoanForPatron(patron)));
        Mockito.when(patronRepository.getPatron(patron.getId())).thenReturn(patron);

        MembershipRenewalStatus status = patronService.renewMembership(patron.getId());

        assertEquals(MembershipRenewalStatus.LOAN_NOT_RETURNED, status);
    }
}
