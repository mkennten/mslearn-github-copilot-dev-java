package com.microsoft.learning.library;

import com.microsoft.learning.library.console.ConsoleApp;
import com.microsoft.learning.library.repository.LoanRepository;
import com.microsoft.learning.library.repository.PatronRepository;
import com.microsoft.learning.library.repository.json.JsonDataStore;
import com.microsoft.learning.library.repository.json.JsonLoanRepository;
import com.microsoft.learning.library.repository.json.JsonPatronRepository;
import com.microsoft.learning.library.service.LoanService;
import com.microsoft.learning.library.service.PatronService;

import java.nio.file.Path;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        JsonDataStore dataStore = new JsonDataStore(Path.of("data", "Json"));
        PatronRepository patronRepository = new JsonPatronRepository(dataStore);
        LoanRepository loanRepository = new JsonLoanRepository(dataStore);
        LoanService loanService = new LoanService(loanRepository);
        PatronService patronService = new PatronService(patronRepository);

        ConsoleApp consoleApp = new ConsoleApp(new Scanner(System.in), loanService, patronService, patronRepository, loanRepository);
        consoleApp.run();
    }
}
