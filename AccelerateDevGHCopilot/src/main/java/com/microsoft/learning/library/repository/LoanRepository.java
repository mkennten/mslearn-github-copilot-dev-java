package com.microsoft.learning.library.repository;

import com.microsoft.learning.library.domain.Loan;

public interface LoanRepository {
    Loan getLoan(int id);

    void updateLoan(Loan loan);
}
