---
lab:
  title: Exercise - Develop new code features using GitHub Copilot (Java)
  description: Java variant of the Module 3 exercise using Maven, Java 21 LTS, and JUnit 5.
  duration: 30 minutes
  level: 200
  islab: true
  primarytopics:
    - GitHub
    - Visual Studio Code
    - Java
---

# Develop new code features using GitHub Copilot (Java)

GitHub Copilot's code completion and interactive chat features help developers write code faster and with fewer errors. It provides context-aware suggestions for code snippets, methods, and even entire classes based on the surrounding code. In this exercise, you use GitHub Copilot to accelerate the development of new code features in a Java application using Visual Studio Code.

This exercise should take approximately **30** minutes to complete.

> **IMPORTANT**: To complete this exercise, you must provide your own GitHub account and GitHub Copilot subscription. If you don't have a GitHub account, you can <a href="https://github.com/" target="_blank">sign up</a> for a free individual account and use a GitHub Copilot Free plan to complete the exercise.

## Before you start

Your lab environment must include the following:

- Access to this repository in GitHub Codespaces, or a local clone of this repository.
- Git 2.48 or later.
- **Java 21 LTS** (verify with `java -version`).
- Maven 3.9 or later (verify with `mvn -version`).
- Visual Studio Code.
- If you're using a local machine, Java support for Visual Studio Code such as the Extension Pack for Java and Maven for Java.
- A GitHub account with GitHub Copilot enabled.

If you're using a local machine, verify your toolchain:

```bash
git --version
java -version
mvn -version
```

## Exercise scenario

You're a developer working in the IT department of your local community. The backend systems that support the public library were lost in a fire. Your team needs to develop a temporary solution to help the library staff manage their operations until the system can be replaced. Your team chose GitHub Copilot to accelerate the development process.

An initial version of the library application was tested by end users and several additional features are requested. Your team agreed to work on the following features:

- **Book availability**: Enable a librarian to determine the availability status of a book. This feature should display a message indicating that a book is available for loan, or show the return due date if the book is currently on loan to another patron.

- **Book loans**: Enable a librarian to loan a book to a patron if the book is available. This feature should display the option to check out a book, create a new Loan record in Loans.json, and display updated loan details.

- **Book search**: Enable a librarian to search for books by title or author name. This feature needs a `searchBooks` method in the `JsonBookRepository` class.

Each team member will work on one of the new features. You'll work on implementing the **book search** feature using GitHub Copilot. Your coworker will work on the **book availability** feature.

This exercise includes the following tasks:

1. Set up the library application in Visual Studio Code.
1. Create a "book-search" branch in the code repository.
1. Implement the `searchBooks` method using GitHub Copilot.
1. Merge your "book-search" updates into the main branch of the repository.

## Set up the library application in Visual Studio Code

Use this repository as the lab project. If you aren't using Codespaces, clone the repository locally and open it in Visual Studio Code.

Use the following steps to set up the library application:

1. If you're using Codespaces, open this repository in GitHub Codespaces and wait for the workspace to load.

1. If you're using a local machine, clone this repository:

   ```bash
   git clone https://github.com/mkennten/mslearn-github-copilot-dev-java.git
   cd mslearn-github-copilot-dev-java
   ```

1. Open the `AccelerateDevGHCopilot` folder in Visual Studio Code.

   For example, on a local machine:

   ```bash
   code AccelerateDevGHCopilot
   ```

1. In the Visual Studio Code EXPLORER view, verify the following project structure:

   ```
   AccelerateDevGHCopilot/
   ├── data/Json/
   │   ├── Authors.json
   │   ├── Books.json
   │   ├── BookItems.json
   │   ├── Loans.json
   │   └── Patrons.json
   ├── pom.xml
   └── src/
       ├── main/java/com/microsoft/learning/library/
       │   ├── App.java
       │   ├── console/
       │   ├── domain/
       │   ├── enums/
       │   ├── repository/
       │   └── service/
       └── test/java/com/microsoft/learning/library/
           ├── factory/
           └── service/
   ```

1. Verify that the build succeeds and the tests pass:

   ```bash
   mvn clean test
   ```

   You may see some Warnings, but there should be no Errors. The existing service tests should pass.

1. Run the console application to confirm it starts correctly:

   ```bash
   mvn exec:java
   ```

   When prompted, enter **One** to search for a patron by name.

## Create a new branch in the repository

Use the following steps to create a feature branch for the book search implementation:

1. In Visual Studio Code, open the Source Control view (Ctrl+Shift+G).

1. Select the branch name in the status bar (it should say **main**), then select **+ Create new branch**.

1. Name the new branch **book-search** and press Enter.

   GitHub Copilot can help generate branch names. To get a suggestion, open the Chat view and enter:

   ```plaintext
   #codebase Suggest a branch name for implementing the searchBooks feature in JsonBookRepository
   ```

   > **Prompt goal**: Use the **#codebase** variable to give Copilot full context. A good branch name is short, descriptive, and uses kebab-case.

## Implement the `searchBooks` method using GitHub Copilot

### Understand the existing code structure

Before writing any code, use GitHub Copilot to understand the codebase.

Use the following steps to complete this section:

1. Open the Chat view in **Ask** mode.

1. Enter a prompt to understand how the repository classes are structured:

   ```plaintext
   #codebase Explain how the repository classes in this Java project are organized. What interfaces exist and what do they do?
   ```

   > **Prompt goal**: Use the **#codebase** chat variable to get a comprehensive overview of the repository pattern used in the project. Notice how Copilot identifies `LoanRepository`, `PatronRepository`, `BookRepository`, and their JSON-backed implementations.

1. Examine the `BookRepository.java` interface in `src/main/java/com/microsoft/learning/library/repository/`.

1. Examine the `JsonBookRepository.java` class in the `repository/json/` subfolder.

   Notice that `searchBooks` currently throws an `UnsupportedOperationException`. Your task is to implement this method using GitHub Copilot.

1. Open the `JsonDataStore.java` file and review its structure.

1. Enter the following prompt to understand how the data store works:

   ```plaintext
   /explain How does JsonDataStore load and serve book data? What methods are available for querying books?
   ```

   > **Prompt goal**: Use the **/explain** slash command to generate a detailed walkthrough of the data loading and access pattern. Understanding this is essential before implementing `searchBooks`.

   > **NOTE**: Notice the `ensureDataLoaded()` pattern — always call this before accessing data collections.

### Implement the `searchBooks` method using inline chat

Use the following steps to implement the `searchBooks` method:

1. Open the `JsonBookRepository.java` file in the code editor.

1. Select the body of the `searchBooks` method (the `throw new UnsupportedOperationException(...)` line).

1. Open the inline chat using **Ctrl+I** (or right-click → **Copilot** → **Start Inline Chat**).

1. Enter the following prompt:

   ```plaintext
   Implement searchBooks to filter books from jsonDataStore where the title or author name contains searchInput (case-insensitive). Return a List<Book>. Call ensureDataLoaded() first.
   ```

   > **Prompt goal**: Provide implementation intent, data source, filter criteria, and the method contract in a single precise prompt. The more specific you are, the more accurate the generated code will be.

   > **Expected response characteristics**: The generated code should:
   >
   > - Call `jsonDataStore.ensureDataLoaded()` first
   > - Use Java Streams or a for-loop to filter books
   > - Perform case-insensitive comparison using `.toLowerCase()`
   > - Check both book title and author name
   > - Return a `List<Book>`

1. Review the generated code. If Copilot uses a for-loop, you can ask it to use streams instead:

   ```plaintext
   Refactor this method to use Java Streams with filter() and collect() instead of a for-loop.
   ```

   > **Prompt goal**: Explore refactoring options using GitHub Copilot. Streams are more idiomatic Java and demonstrate Copilot's ability to adapt its suggestions.

1. Accept the suggestion by selecting **Accept** or pressing **Tab**.

### Verify the implementation

Use the following steps to verify the `searchBooks` implementation:

1. Open the Chat view in **Ask** mode.

1. Add the `JsonBookRepository.java` file to the chat context using drag-and-drop from the EXPLORER view.

1. Enter the following prompt:

   ```plaintext
   Review the searchBooks implementation in this file. Does it correctly handle: case-insensitive search, null author names, empty search strings, and results where no books match? Suggest improvements if needed.
   ```

   > **Prompt goal**: Use GitHub Copilot as a code reviewer to check for edge cases before running tests. This prompt targets readability, correctness, and defensive coding — key quality criteria.

   > **Expected response characteristics**: Copilot should identify any potential `NullPointerException` risks on author name access and suggest a null-safe guard.

1. If Copilot suggests improvements, apply them and save the file.

1. Run the tests to confirm the existing tests still pass:

   ```bash
   mvn test
   ```

   > **Checkpoint**: All tests should pass. If any test fails, review the implementation with GitHub Copilot using:
   >
   > ```plaintext
   > #codebase The test <test-name> is failing. What might be wrong with the searchBooks implementation?
   > ```

### Add a unit test for `searchBooks` using Agent mode

Use the following steps to add a test class using GitHub Copilot's Agent mode:

1. Open the Chat view and switch to **Agent** mode.

1. Enter the following prompt to create a test class:

   ```plaintext
   Create a JUnit 5 test class named JsonBookRepositoryTest in the src/test/java/com/microsoft/learning/library/repository/json/ directory. The class should test the searchBooks method of JsonBookRepository. Use Mockito to mock JsonDataStore. Include tests for: finding books by title (case-insensitive), finding books by author name, returning an empty list when no books match, and handling a null or empty search string. Follow the same test naming convention as LoanServiceTest.java.
   ```

   > **Prompt goal**: Use **Agent mode** for this task because it autonomously creates new files, determines the correct directory structure, and wires up the test class with appropriate imports and mock setup. Agent mode is ideal for tasks that span multiple files or require file creation.

   > **Expected response characteristics**: The agent should:
   >
   > - Identify the correct package and directory for the new test file
   > - Use `@ExtendWith(MockitoExtension.class)` and `@Mock` annotations
   > - Write clearly named test methods (e.g., `searchBooks_findsByTitleCaseInsensitive`)
   > - Include assertions using `assertFalse(results.isEmpty())` or `assertEquals`

1. Review the generated test class and accept the changes.

1. Run the tests to verify the new tests pass:

   ```bash
   mvn test
   ```

   > **Checkpoint**: All new and existing tests should pass. If tests fail, use Copilot to diagnose:
   >
   > ```plaintext
   > The test searchBooks_returnsEmptyListWhenNoMatch fails with: <error message>. What is wrong with the test setup?
   > ```

## Merge your "book-search" updates into the main branch

### Test the application

Use the following steps to test the application end-to-end:

1. Open a terminal in Visual Studio Code and run the application:

   ```bash
   mvn exec:java
   ```

1. When prompted for a patron name, type **One** and then press Enter.

1. At the "Input Options" prompt, type **2** and then press Enter to select the second patron.

1. At the "Input Options" prompt, type **1** and then press Enter to select the first loan.

1. At the "Input Options" prompt, type **r** and then press Enter to return the book.

1. Verify that the message **Book was successfully returned.** is displayed.

1. At the "Input Options" prompt, type **q** to quit.

### Sync your changes to the remote repository

Use the following steps to commit and push your changes:

1. Open the Source Control view (**Ctrl+Shift+G**) in Visual Studio Code.

1. Enter a commit message for your changes. To get a suggestion from GitHub Copilot, select the sparkle icon next to the commit message field.

   Alternatively, enter the following prompt in the Chat view:

   ```plaintext
   #codebase Generate a concise git commit message (50 chars or less) summarizing the searchBooks implementation in JsonBookRepository.
   ```

   > **Prompt goal**: Use GitHub Copilot to generate a commit message that follows conventional commit format. Good commit messages describe **what** changed and **why**, not **how**.

1. Stage all changed files and commit.

1. Push the **book-search** branch to your remote repository.

### Create a pull request to merge your changes

Use the following steps to create a pull request:

1. In the Chat view, enter the following prompt to generate a pull request description:

   ```plaintext
   Generate a pull request description for the book-search feature branch. Include: what was implemented, which files were changed, and how to test the changes. Format it as markdown.
   ```

   > **Prompt goal**: GitHub Copilot can read your codebase and generate complete PR descriptions that help reviewers understand your changes. This prompt emphasizes the **who/what/why** structure of a good PR description.

1. Open your GitHub repository in a browser and create a pull request from **book-search** to **main** using the generated description.

1. Merge the pull request into the **main** branch.

## Summary

In this exercise, you used GitHub Copilot to develop a new code feature in a Java library application. You created a feature branch, used inline chat, Ask mode, and Agent mode to implement the `searchBooks` method in `JsonBookRepository`, and evaluated the implementation for edge cases using GitHub Copilot's review capabilities. You also added unit tests with Mockito, and used GitHub Copilot to generate a commit message and pull request description before merging the feature branch into main.

Key GitHub Copilot capabilities practiced:

- **#codebase** variable for full-project context
- **Inline chat** for targeted in-place code generation
- **Ask mode** for investigation and code review
- **Agent mode** for autonomous multi-file tasks (creating test classes)
- **@github** participant for GitHub-specific knowledge

## Clean up

Now that you've finished the exercise, take a minute to ensure that you haven't made any changes to your GitHub account or GitHub Copilot subscription that you don't want to keep. If you made any changes, revert them now.

## Troubleshooting

- **`release version 21 not supported`**: In VS Code, press **Ctrl+Shift+P** → **Java: Configure Java Runtime** and ensure JDK 21 is selected. Also verify `java -version` reports 21.
- **`Failed to load JSON data`**: Run Maven commands from the project root (`AccelerateDevGHCopilot/`) so the `data/Json` path resolves correctly.
- **No tests discovered**: Ensure you run `mvn test` from the directory containing `pom.xml`.
- **`UnsupportedOperationException` on searchBooks**: The `searchBooks` method body is a stub — this is expected before implementing it. Complete the implementation task above.
