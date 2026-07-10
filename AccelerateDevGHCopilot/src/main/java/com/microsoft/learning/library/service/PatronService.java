package com.microsoft.learning.library.service;

import com.microsoft.learning.library.domain.Patron;
import com.microsoft.learning.library.enums.MembershipRenewalStatus;
import com.microsoft.learning.library.repository.PatronRepository;

import java.time.LocalDateTime;

public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public MembershipRenewalStatus renewMembership(int patronId) {
        Patron patron = patronRepository.getPatron(patronId);
        if (patron == null) {
            return MembershipRenewalStatus.PATRON_NOT_FOUND;
        }

        if (!patron.getMembershipEnd().isBefore(LocalDateTime.now().plusMonths(1))) {
            return MembershipRenewalStatus.TOO_EARLY_TO_RENEW;
        }

        boolean hasOverdueUnreturnedLoan = patron.getLoans().stream()
                .anyMatch(loan -> loan.getReturnDate() == null && loan.getDueDate().isBefore(LocalDateTime.now()));
        if (hasOverdueUnreturnedLoan) {
            return MembershipRenewalStatus.LOAN_NOT_RETURNED;
        }

        patron.setMembershipEnd(patron.getMembershipEnd().plusYears(1));
        try {
            patronRepository.updatePatron(patron);
            return MembershipRenewalStatus.SUCCESS;
        } catch (RuntimeException ex) {
            return MembershipRenewalStatus.ERROR;
        }
    }
}
