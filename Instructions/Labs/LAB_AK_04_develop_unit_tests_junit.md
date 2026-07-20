---
lab:
  title: Exercise - Develop unit tests using GitHub Copilot (Java)
  description: Java variant of the Module 4 exercise using Maven, Java 21 LTS, JUnit 5, and Mockito.
  duration: 25 minutes
  level: 200
  islab: true
  primarytopics:
    - GitHub
    - Visual Studio Code
    - Java
---

# Develop unit tests using GitHub Copilot (Java)

This is the Java variant of the Module 4 exercise. The original C# (xUnit) instructions are in `LAB_AK_04_develop_unit_tests_xunit.md`.

The large language models behind GitHub Copilot are trained on a wide range of code testing frameworks and scenarios. GitHub Copilot is a great tool for generating test cases, test methods, assertions, mocks, and test data. In this exercise, you use GitHub Copilot to accelerate the development of unit tests for a Java application using JUnit 5 and Mockito.

This exercise should take approximately **25** minutes to complete.

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

If you're using a local machine, confirm your Java toolchain:

```bash
git --version
java -version
mvn -version
```

## Exercise scenario

You're a developer working in the IT department of your local community. The backend systems that support the public library were lost in a fire. Your team needs to develop a temporary solution to help the library staff manage their operations until the system can be replaced. Your team chose GitHub Copilot to accelerate the development process.

You have an initial version of the library application that includes unit tests for the service layer. You need to accelerate the development of additional unit tests for the data access (repository) layer using GitHub Copilot.

This exercise includes the following tasks:

1. Set up the library application in Visual Studio Code.
1. Examine the existing unit testing approach in this project.
1. Extend the test suite to test the `JsonLoanRepository` data access class.

## Set up the library application in Visual Studio Code

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

1. In the EXPLORER view, verify the following project structure:

   ```
   AccelerateDevGHCopilot/
   ├── pom.xml
   └── src/
       ├── main/java/com/microsoft/learning/library/
       │   ├── console/
       │   ├── domain/
       │   ├── enums/
       │   ├── repository/
       │   └── service/
       └── test/java/com/microsoft/learning/library/
           ├── factory/
           │   ├── LoanFactory.java
           │   └── PatronFactory.java
           ├── repository/json/
           │   └── GetLoanTest.java  ← empty stub (you'll fill this in)
           └── service/
               ├── LoanServiceTest.java
               └── PatronServiceTest.java
   ```

1. Verify that the existing tests pass:

   ```bash
   mvn clean test
   ```

   You should see the existing service tests pass. The `GetLoanTest` class has no test methods yet — that's what you'll add in this exercise.

## Examine the approach to unit testing implemented by the test directory

In this section, you use GitHub Copilot to understand the existing testing approach in the project.

Use the following steps to complete this section:

1. Expand the `src/test/java/.../service/` folder in the EXPLORER view.

   Your existing test suite mirrors the service layer of the application:

   ```
   src/test/
   └── java/com/microsoft/learning/library/
       ├── factory/
       │   ├── LoanFactory.java          ← creates test Loan objects
       │   └── PatronFactory.java        ← creates test Patron objects
       └── service/
           ├── LoanServiceTest.java      ← tests LoanService.returnLoan and extendLoan
           └── PatronServiceTest.java    ← tests PatronService.renewMembership
   ```

1. Open the Chat view and set the mode to **Ask**.

1. Enter the following prompt to understand the existing testing approach:

   ```plaintext
   Explain the approach to unit testing that's been implemented in this Java workspace. How are test factories used? What mocking framework is used and why?
   ```

   > **Prompt goal**: Use the **#codebase** chat variable to get Copilot to analyze the full test structure. You want to understand the Mockito mocking pattern and the factory pattern before extending the suite.

   > **Expected response characteristics**: Copilot should explain:
   >
   > - `LoanFactory` and `PatronFactory` create pre-configured domain objects for testing
   > - Mockito is used to create `LoanRepository` mocks via `Mockito.mock()`
   > - Tests use the AAA pattern: Arrange (set up mocks), Act (call service method), Assert (verify result)
   > - Test class and method naming follows a verb-based convention

1. Enter the following follow-up prompt:

   ```plaintext
   What are the benefits of using factory classes like LoanFactory and PatronFactory in unit tests instead of creating objects inline in each test method?
   ```

   > **Prompt goal**: Deepen your understanding of the factory pattern in testing. Copilot's response highlights DRY principles and test readability — both are important when GitHub Copilot generates test code for you.

1. Open `LoanServiceTest.java` and review the existing tests.

1. Drag `LoanServiceTest.java`, `LoanFactory.java`, and `PatronFactory.java` from the EXPLORER to the Chat context.

1. Enter the following prompt to understand how to extend testing to the repository layer:

   ```plaintext
   How can I extend the test suite to begin testing methods in the JsonLoanRepository class? What's the main difference between testing a service class (like LoanService) and testing a repository class (like JsonLoanRepository)?
   ```

   > **Prompt goal**: Use the context you've added to guide Copilot toward a concrete plan. The answer should highlight that repository tests need a real (or test) `JsonDataStore`, not just mocked repositories.

   You should see a response similar to the following:
   - Service tests mock the repository layer completely.
   - Repository tests need a `JsonDataStore` configured with a real data directory so `getLoan` can load and search the JSON files.
   - For `JsonLoanRepository.getLoan`, you should set up a `JsonDataStore` pointing to the `data/Json` directory, create a `JsonLoanRepository` with that store, and assert that retrieving a known loan ID returns the correct loan.

   > **TIP**: Use GitHub Copilot's **Ask** mode to investigate your approach to testing before switching to **Agent** mode to generate code. Understanding first leads to better prompts.

## Extend the test suite to test the JsonLoanRepository class

### Create a test class for JsonLoanRepository using Agent mode

In this section, you use GitHub Copilot's Agent mode to create a test class for `JsonLoanRepository.getLoan`.

Agent mode is optimized for complex tasks that may involve editing multiple files, creating new files, or running commands. The AI operates autonomously, determines the relevant context, and iterates to resolve issues.

Use the following steps to complete this section:

1. Open the Chat view and switch to **Agent** mode.

1. Enter the following prompt to create the test class:

   ```plaintext
   Create fields and a class constructor (or setup method) for the GetLoanTest.java file. The class will be used to create unit tests for the getLoan method in JsonLoanRepository.java. Create the following private fields: mockLoanRepository (a Mockito mock of LoanRepository), jsonLoanRepository (a real JsonLoanRepository using a real JsonDataStore), and jsonDataStore (a JsonDataStore pointing to the data/Json directory). Initialize them in a @BeforeEach setup method. The data directory path should be resolved relative to the project root using Paths.get("data", "Json").
   ```

   > **Prompt goal**: Use Agent mode because this task modifies an existing file (`GetLoanTest.java`) and needs to resolve the correct data path from the project structure. Providing the exact field names and initialization requirements keeps the generated code consistent with the existing test style.

   > **Expected response characteristics**: The agent should:
   >
   > - Add `@BeforeEach` setup method to `GetLoanTest.java`
   > - Use `Mockito.mock(LoanRepository.class)` for the mock field
   > - Use `new JsonDataStore(Paths.get("data", "Json"))` for the data store
   > - Initialize `jsonLoanRepository = new JsonLoanRepository(jsonDataStore)`
   > - Ensure correct imports are added

1. Review the generated code to ensure it follows the same pattern as `LoanServiceTest.java`.

1. Review the generated code and confirm the `GetLoanTest.java` now has a class body with fields and a setup method.

### Create unit tests for the getLoan method

In this section, you use GitHub Copilot to create unit tests for `JsonLoanRepository.getLoan`.

The `getLoan` method takes a loan ID and returns the matching `Loan` object from the JSON data store, or `null` if not found. You'll write tests to verify both scenarios.

Use the following steps to complete this section:

1. Open the `Loans.json` file in the `data/Json/` directory to find a valid loan ID to use in your test.

   > **TIP**: You can enter `#codebase What loan IDs are in the Loans.json file?` in the Chat view to get a quick answer.

1. Open `JsonLoanRepository.java` and `GetLoanTest.java` side by side.

1. In the Chat view (Agent mode), enter the following prompt:

   ```plaintext
   Add a unit test to GetLoanTest.java for JsonLoanRepository.getLoan. The test should verify the case where a loan ID that exists in Loans.json is provided. Use jsonLoanRepository to call getLoan and assert that the returned loan is not null and that the loan ID matches the requested ID. Name the test getLoan_returnsLoanWhenIdFound and use a loan ID that exists in the data/Json/Loans.json file.
   ```

   > **Prompt goal**: Specify the method under test, the scenario (happy path — ID found), the data source constraint (use a real ID from Loans.json), the assertion strategy, and the test name convention — all in one prompt. The more specific the constraints, the less manual editing you'll need after accepting the suggestion.

   > **Expected response characteristics**: The test should:
   >
   > - Use `jsonLoanRepository.getLoan(knownLoanId)` to retrieve an actual loan
   > - Assert that the result is not null with `assertNotNull(result)`
   > - Assert that the ID matches with `assertEquals(knownLoanId, result.getId())`
   > - Use a loan ID that actually exists in `Loans.json`

1. Review the generated test and accept the changes.

1. Open the inline chat in `GetLoanTest.java` using **Ctrl+I**.

1. Position the cursor after the first test method.

1. Enter the following prompt to generate the "not found" test:

   ```plaintext
   Add a test for getLoan when the loan ID does not exist in the data. The test should assert that getLoan returns null. Use a loan ID of 99999 which does not exist in the data. Name the test getLoan_returnsNullWhenIdNotFound.
   ```

   > **Prompt goal**: Use inline chat for this second test since you're adding it directly after an existing method. Inline chat preserves code context and places the new test in the right location without needing to specify the file path.

   > **Expected response characteristics**: The test should:
   >
   > - Call `jsonLoanRepository.getLoan(99999)` (or another non-existent ID)
   > - Assert the result is null with `assertNull(result)`
   > - Follow the same naming convention as the first test

1. Review and accept the suggestion.

### Add descriptive test display names

Clear test names help you understand failures at a glance. Use GitHub Copilot to add `@DisplayName` annotations:

1. Select all tests in `GetLoanTest.java`.

1. Open the inline chat (**Ctrl+I**) and enter:

   ```plaintext
   Add @DisplayName annotations to each @Test method in this file. The display names should describe the scenario being tested in plain English, for example: "getLoan returns the correct loan when the ID exists in the data".
   ```

   > **Prompt goal**: `@DisplayName` annotations dramatically improve test report readability. This prompt demonstrates how GitHub Copilot can improve code quality beyond just generating logic.

   > **Expected response characteristics**: Each `@Test` method should have a corresponding `@DisplayName("...")` annotation above it with a human-readable description.

1. Accept the changes.

## Run the unit tests

Use the following steps to run the tests:

1. Open a terminal and run the full test suite:

   ```bash
   mvn test
   ```

1. Review the test output. All tests in `LoanServiceTest`, `PatronServiceTest`, and `GetLoanTest` should pass.

1. You can also run tests from the VS Code Test Explorer. Select the flask icon in the Activity Bar to open the Test Explorer.

   > **Checkpoint**: If `getLoan_returnsLoanWhenIdFound` fails, check that:
   >
   > - The loan ID you're using exists in `data/Json/Loans.json`
   > - `JsonDataStore` is using the correct path (`data/Json`)
   > - You're running `mvn test` from the project root directory

1. Enter the following prompt to investigate any failures:

   ```plaintext
   The test getLoan_returnsLoanWhenIdFound is failing with: <paste your error here>. What might be wrong with the JsonDataStore path or the loan ID used in the test?
   ```

   > **Prompt goal**: Share the error message with Copilot using **#codebase** to get a diagnostic response. Include the full exception message for the best results.

## Summary

In this exercise, you used GitHub Copilot to accelerate the development of unit tests for a Java application. You used **Ask mode** to examine the existing test structure, understand the difference between service and repository testing, and plan your approach. You used **Agent mode** to add a setup method to an existing test class and generate test methods for `JsonLoanRepository.getLoan`. You also used **inline chat** to add a second test and `@DisplayName` annotations.

Key GitHub Copilot capabilities practiced:

- **#codebase** variable for full-project context
- **Ask mode** for understanding and planning
- **Agent mode** for autonomous file modification and test generation
- **Inline chat** for targeted in-place code generation
- Iterative prompting to refine generated tests

## Clean up

Now that you've finished the exercise, take a minute to ensure that you haven't made any changes to your GitHub account or GitHub Copilot subscription that you don't want to keep.

## Troubleshooting

- **Tests fail with `Failed to load JSON data`**: The `JsonDataStore` cannot find the data files. Ensure you're running `mvn test` from the `AccelerateDevGHCopilot/` directory, and that `data/Json/` contains all five JSON files.
- **`NullPointerException` in getLoan test**: The loan ID used in the test may not exist in `Loans.json`. Open the file and verify the ID, or use the prompt `#codebase What are the first 5 loan IDs in Loans.json?` to find a valid ID.
- **`GetLoanTest has no test methods`**: If Maven can't find test methods, verify that the methods are annotated with `@Test` and that the class is in the `src/test/` source tree.
- **No tests discovered at all**: Make sure `mvn test` is run from the directory containing `pom.xml`.
