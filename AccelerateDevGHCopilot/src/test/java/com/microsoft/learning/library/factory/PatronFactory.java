package com.microsoft.learning.library.factory;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PatronFactory {
    private static int patronId = 42;

    private PatronFactory() {
    }

    public static Patron createCurrentPatron() {
        Patron patron = new Patron();
        patron.setId(patronId++);
        patron.setName("John Doe");
        patron.setMembershipEnd(LocalDateTime.now().plusDays(1));
        patron.setLoans(new ArrayList<>());
        return patron;
    }

    public static Patron createTooEarlyToRenewPatron() {
        Patron patron = new Patron();
        patron.setId(patronId++);
        patron.setName("John Doe");
        patron.setMembershipEnd(LocalDateTime.now().plusMonths(2));
        patron.setLoans(new ArrayList<>());
        return patron;
    }

    public static Patron createExpiredPatron() {
        Patron patron = new Patron();
        patron.setId(patronId++);
        patron.setName("John Doe");
        patron.setMembershipEnd(LocalDateTime.now().minusMonths(2));
        patron.setLoans(new ArrayList<Loan>());
        return patron;
    }

    public static Patron withLoans(Patron patron, List<Loan> loans) {
        patron.setLoans(loans);
        return patron;
    }
}
