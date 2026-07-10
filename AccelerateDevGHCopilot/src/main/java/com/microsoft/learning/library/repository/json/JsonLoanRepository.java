package com.microsoft.learning.library.repository.json;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.repository.LoanRepository;

public class JsonLoanRepository implements LoanRepository {
    private final JsonDataStore jsonDataStore;

    public JsonLoanRepository(JsonDataStore jsonDataStore) {
        this.jsonDataStore = jsonDataStore;
    }

    @Override
    public Loan getLoan(int id) {
        jsonDataStore.ensureDataLoaded();

        for (Loan loan : jsonDataStore.loans()) {
            if (loan.getId() == id) {
                return jsonDataStore.getPopulatedLoan(loan);
            }
        }

        return null;
    }

    @Override
    public void updateLoan(Loan loan) {
        Loan existing = null;
        for (Loan current : jsonDataStore.loans()) {
            if (current.getId() == loan.getId()) {
                existing = current;
                break;
            }
        }

        if (existing != null) {
            existing.setBookItemId(loan.getBookItemId());
            existing.setPatronId(loan.getPatronId());
            existing.setLoanDate(loan.getLoanDate());
            existing.setDueDate(loan.getDueDate());
            existing.setReturnDate(loan.getReturnDate());
            jsonDataStore.saveLoans(jsonDataStore.loans());
            jsonDataStore.loadData();
        }
    }
}
