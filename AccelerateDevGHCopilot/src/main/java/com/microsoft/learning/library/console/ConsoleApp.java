package com.microsoft.learning.library.console;

import com.microsoft.learning.library.domain.Loan;
import com.microsoft.learning.library.domain.Patron;
import com.microsoft.learning.library.enums.LoanExtensionStatus;
import com.microsoft.learning.library.enums.LoanReturnStatus;
import com.microsoft.learning.library.enums.MembershipRenewalStatus;
import com.microsoft.learning.library.repository.LoanRepository;
import com.microsoft.learning.library.repository.PatronRepository;
import com.microsoft.learning.library.service.LoanService;
import com.microsoft.learning.library.service.PatronService;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private ConsoleState currentState = ConsoleState.PATRON_SEARCH;
    private List<Patron> matchingPatrons = List.of();
    private Patron selectedPatronDetails;
    private Loan selectedLoanDetails;

    private final Scanner scanner;
    private final LoanService loanService;
    private final PatronService patronService;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;

    public ConsoleApp(Scanner scanner, LoanService loanService, PatronService patronService,
                      PatronRepository patronRepository, LoanRepository loanRepository) {
        this.scanner = scanner;
        this.loanService = loanService;
        this.patronService = patronService;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
    }

    public void run() {
        while (currentState != ConsoleState.QUIT) {
            switch (currentState) {
                case PATRON_SEARCH -> currentState = patronSearch();
                case PATRON_SEARCH_RESULTS -> currentState = patronSearchResults();
                case PATRON_DETAILS -> currentState = patronDetails();
                case LOAN_DETAILS -> currentState = loanDetails();
                default -> currentState = ConsoleState.QUIT;
            }
        }
    }

    private ConsoleState patronSearch() {
        String searchInput = readPatronName();
        matchingPatrons = patronRepository.searchPatrons(searchInput);

        if (matchingPatrons.size() > 20) {
            System.out.println("More than 20 patrons satisfy the search, please provide more specific input...");
            return ConsoleState.PATRON_SEARCH;
        }

        if (matchingPatrons.isEmpty()) {
            System.out.println("No matching patrons found.");
            return ConsoleState.PATRON_SEARCH;
        }

        System.out.println("Matching Patrons:");
        for (int i = 0; i < matchingPatrons.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, matchingPatrons.get(i).getName());
        }

        return ConsoleState.PATRON_SEARCH_RESULTS;
    }

    private String readPatronName() {
        String searchInput = "";
        while (searchInput.isBlank()) {
            System.out.print("Enter a string to search for patrons by name: ");
            searchInput = scanner.nextLine();
        }
        return searchInput;
    }

    private ConsoleState patronSearchResults() {
        InputSelection selection = readInputOptions(EnumSet.of(CommonAction.SELECT, CommonAction.SEARCH_PATRONS, CommonAction.QUIT));
        if (selection.action() == CommonAction.SELECT) {
            int index = selection.optionNumber();
            if (index >= 1 && index <= matchingPatrons.size()) {
                selectedPatronDetails = patronRepository.getPatron(matchingPatrons.get(index - 1).getId());
                return ConsoleState.PATRON_DETAILS;
            }
            System.out.println("Invalid patron number. Please try again.");
            return ConsoleState.PATRON_SEARCH_RESULTS;
        }
        if (selection.action() == CommonAction.SEARCH_PATRONS) {
            return ConsoleState.PATRON_SEARCH;
        }
        if (selection.action() == CommonAction.QUIT) {
            return ConsoleState.QUIT;
        }
        throw new IllegalStateException("An input option is not handled.");
    }

    private ConsoleState patronDetails() {
        System.out.printf("Name: %s%n", selectedPatronDetails.getName());
        System.out.printf("Membership Expiration: %s%n%n", selectedPatronDetails.getMembershipEnd());
        System.out.println("Book Loans:");

        for (int i = 0; i < selectedPatronDetails.getLoans().size(); i++) {
            Loan loan = selectedPatronDetails.getLoans().get(i);
            System.out.printf("%d) %s - Due: %s - Returned: %s%n",
                    i + 1,
                    loan.getBookItem().getBook().getTitle(),
                    loan.getDueDate(),
                    loan.getReturnDate() != null);
        }

        InputSelection selection = readInputOptions(EnumSet.of(
                CommonAction.SEARCH_PATRONS,
                CommonAction.QUIT,
                CommonAction.SELECT,
                CommonAction.RENEW_PATRON_MEMBERSHIP));

        if (selection.action() == CommonAction.SELECT) {
            int index = selection.optionNumber();
            if (index >= 1 && index <= selectedPatronDetails.getLoans().size()) {
                selectedLoanDetails = selectedPatronDetails.getLoans().get(index - 1);
                return ConsoleState.LOAN_DETAILS;
            }
            System.out.println("Invalid book loan number. Please try again.");
            return ConsoleState.PATRON_DETAILS;
        }
        if (selection.action() == CommonAction.RENEW_PATRON_MEMBERSHIP) {
            MembershipRenewalStatus status = patronService.renewMembership(selectedPatronDetails.getId());
            System.out.println(status.getMessage());
            selectedPatronDetails = patronRepository.getPatron(selectedPatronDetails.getId());
            return ConsoleState.PATRON_DETAILS;
        }
        if (selection.action() == CommonAction.SEARCH_PATRONS) {
            return ConsoleState.PATRON_SEARCH;
        }
        if (selection.action() == CommonAction.QUIT) {
            return ConsoleState.QUIT;
        }

        throw new IllegalStateException("An input option is not handled.");
    }

    private ConsoleState loanDetails() {
        System.out.printf("Book title: %s%n", selectedLoanDetails.getBookItem().getBook().getTitle());
        System.out.printf("Book Author: %s%n", selectedLoanDetails.getBookItem().getBook().getAuthor().getName());
        System.out.printf("Due date: %s%n", selectedLoanDetails.getDueDate());
        System.out.printf("Returned: %s%n%n", selectedLoanDetails.getReturnDate() != null);

        InputSelection selection = readInputOptions(EnumSet.of(
                CommonAction.SEARCH_PATRONS,
                CommonAction.QUIT,
                CommonAction.RETURN_LOANED_BOOK,
                CommonAction.EXTEND_LOANED_BOOK));

        if (selection.action() == CommonAction.EXTEND_LOANED_BOOK) {
            LoanExtensionStatus status = loanService.extendLoan(selectedLoanDetails.getId());
            System.out.println(status.getMessage());
            selectedPatronDetails = patronRepository.getPatron(selectedPatronDetails.getId());
            selectedLoanDetails = loanRepository.getLoan(selectedLoanDetails.getId());
            return ConsoleState.LOAN_DETAILS;
        }
        if (selection.action() == CommonAction.RETURN_LOANED_BOOK) {
            LoanReturnStatus status = loanService.returnLoan(selectedLoanDetails.getId());
            System.out.println(status.getMessage());
            selectedLoanDetails = loanRepository.getLoan(selectedLoanDetails.getId());
            return ConsoleState.LOAN_DETAILS;
        }
        if (selection.action() == CommonAction.SEARCH_PATRONS) {
            return ConsoleState.PATRON_SEARCH;
        }
        if (selection.action() == CommonAction.QUIT) {
            return ConsoleState.QUIT;
        }

        throw new IllegalStateException("An input option is not handled.");
    }

    private InputSelection readInputOptions(EnumSet<CommonAction> options) {
        CommonAction action;
        int optionNumber = 0;

        do {
            System.out.println();
            writeInputOptions(options);
            String userInput = scanner.nextLine();

            if ("q".equals(userInput) && options.contains(CommonAction.QUIT)) {
                action = CommonAction.QUIT;
            } else if ("s".equals(userInput) && options.contains(CommonAction.SEARCH_PATRONS)) {
                action = CommonAction.SEARCH_PATRONS;
            } else if ("m".equals(userInput) && options.contains(CommonAction.RENEW_PATRON_MEMBERSHIP)) {
                action = CommonAction.RENEW_PATRON_MEMBERSHIP;
            } else if ("e".equals(userInput) && options.contains(CommonAction.EXTEND_LOANED_BOOK)) {
                action = CommonAction.EXTEND_LOANED_BOOK;
            } else if ("r".equals(userInput) && options.contains(CommonAction.RETURN_LOANED_BOOK)) {
                action = CommonAction.RETURN_LOANED_BOOK;
            } else {
                try {
                    optionNumber = Integer.parseInt(userInput);
                    action = CommonAction.SELECT;
                } catch (NumberFormatException ex) {
                    action = CommonAction.REPEAT;
                }
            }

            if (action == CommonAction.REPEAT) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (action == CommonAction.REPEAT);

        return new InputSelection(action, optionNumber);
    }

    private static void writeInputOptions(EnumSet<CommonAction> options) {
        System.out.println("Input Options:");
        if (options.contains(CommonAction.RETURN_LOANED_BOOK)) {
            System.out.println(" - \"r\" to mark as returned");
        }
        if (options.contains(CommonAction.EXTEND_LOANED_BOOK)) {
            System.out.println(" - \"e\" to extend the book loan");
        }
        if (options.contains(CommonAction.RENEW_PATRON_MEMBERSHIP)) {
            System.out.println(" - \"m\" to extend patron's membership");
        }
        if (options.contains(CommonAction.SEARCH_PATRONS)) {
            System.out.println(" - \"s\" for new search");
        }
        if (options.contains(CommonAction.QUIT)) {
            System.out.println(" - \"q\" to quit");
        }
        if (options.contains(CommonAction.SELECT)) {
            System.out.println("Or type a number to select a list item.");
        }
    }

    private record InputSelection(CommonAction action, int optionNumber) {
    }
}
