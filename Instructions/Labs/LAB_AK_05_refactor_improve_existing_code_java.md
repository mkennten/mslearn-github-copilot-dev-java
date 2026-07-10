---
lab:
  title: Exercise - Refactor existing code using GitHub Copilot (Java)
  description: Java variant of the Module 5 exercise using Maven, Java 21 LTS, and JUnit 5.
  duration: 30 minutes
  level: 200
  islab: true
  primarytopics:
    - GitHub
    - Visual Studio Code
    - Java
---

# Refactor existing code using GitHub Copilot (Java)

This is the Java variant of the Module 5 exercise. The original C# instructions are in `LAB_AK_05_refactor_improve_existing_code.md`.

GitHub Copilot can help you evaluate your existing codebase and suggest updates that refactor and improve code quality, reliability, readability, and performance. In this exercise, you use GitHub Copilot to refactor specified sections of a Java application while making improvements to code quality and maintainability.

This exercise should take approximately **30** minutes to complete.

> **IMPORTANT**: To complete this exercise, you must provide your own GitHub account and GitHub Copilot subscription. If you don't have a GitHub account, you can <a href="https://github.com/" target="_blank">sign up</a> for a free individual account and use a GitHub Copilot Free plan to complete the exercise.

## Before you start

Your lab environment must include the following:

- Git 2.48 or later
- **Java 21 LTS** (verify with `java -version`)
- Maven 3.9 or later (verify with `mvn -version`)
- Visual Studio Code with the **Extension Pack for Java** and **Maven for Java** extensions installed
- A GitHub account with GitHub Copilot enabled

Confirm your Java toolchain:

```bash
java -version
mvn -version
```

## Exercise scenario

You're a developer working in the IT department of your local community. The backend systems that support the public library were lost in a fire. Your team needs to develop a temporary solution to help the library staff manage their operations until the system can be replaced. Your team chose GitHub Copilot to accelerate the development process.

You handed off an initial version of the library application for review. The review team identified opportunities to improve code quality, performance, readability, and maintainability:

1. **Replace verbose if/else chains with a type-safe dispatch**: The `EnumHelper` class uses a chain of `if/instanceof` statements to look up enum descriptions. Replacing these with a static `Map<Enum, String>` for each enum type eliminates branching and makes it easier to add new statuses.

1. **Replace for-loops with Java Streams**: The `JsonDataStore` class uses traditional for-loops in `saveLoans` and `savePatrons`. Replacing these with Streams and method references produces more concise, readable, and idiomatic Java.

This exercise includes the following tasks:

1. Set up the library application in Visual Studio Code.
1. Analyze and refactor the `EnumHelper` class using Ask and Agent modes.
1. Refactor `JsonDataStore` to use Streams instead of for-loops.

## Set up the library application in Visual Studio Code

Use the following steps to set up the library application:

1. Open a browser window in your lab environment.

1. To download a zip file containing the library application, paste the following URL into your browser's address bar: [GitHub Copilot lab - refactor existing code (Java)](https://github.com/MicrosoftLearning/mslearn-github-copilot-dev/raw/refs/heads/main/DownloadableCodeProjects/Downloads/AZ2007LabAppM5Java.zip)

    The zip file is named **AZ2007LabAppM5Java.zip**.

1. Extract the files from the **AZ2007LabAppM5Java.zip** file.

    For example:

    1. Navigate to the downloads folder in your lab environment.
    1. Right-click **AZ2007LabAppM5Java.zip**, and then select **Extract all**.
    1. Select **Show extracted files when complete**, and then select **Extract**.

1. Copy the **AccelerateDevGHCopilot** folder to a location that's easy to access.

1. Open the **AccelerateDevGHCopilot** folder in Visual Studio Code.

1. Verify the build and all tests pass before making any changes:

    ```bash
    mvn clean test
    ```

    All tests should pass. These tests serve as your safety net during refactoring.

    > **IMPORTANT**: Never refactor code that has failing tests. If any tests fail, fix them before proceeding.

## Analyze and refactor the `EnumHelper` class

### Analyze the EnumHelper class using Ask mode

The `EnumHelper` class is responsible for converting enum values to human-readable status messages. In the starter project, it uses a chain of `if/instanceof` pattern-matching statements to dispatch to the correct message.

Use the following steps to analyze `EnumHelper` before refactoring it:

1. Open the `EnumHelper.java` file in `src/main/java/com/microsoft/learning/library/enums/`.

1. Review the current implementation. It uses `if (value instanceof LoanReturnStatus status)` style pattern matching with nested `if` statements.

1. Open the Chat view in **Ask** mode.

1. Drag the following files to the Chat context:
    - `EnumHelper.java`
    - `LoanExtensionStatus.java`
    - `LoanReturnStatus.java`
    - `MembershipRenewalStatus.java`

1. Enter the following prompt:

    ```plaintext
    /explain Explain how the current EnumHelper.getDescription method works. What is the problem with using a chain of if/instanceof checks for dispatching enum descriptions? What are the alternatives in Java?
    ```

    > **Prompt goal**: Use the **/explain** slash command with the enum files in context. You want Copilot to identify the maintainability problem (adding a new enum value requires finding and editing the if-chain) and suggest the Map-based alternative.

    > **Expected response characteristics**: Copilot should identify:
    > - The pattern-matching if-chain is brittle and grows linearly as new enum values are added
    > - A `Map<LoanReturnStatus, String>` approach is O(1) lookup and consolidates all descriptions near their enum definitions
    > - The alternative is to put the description string directly in the enum constructor (like the existing enums already do via `getMessage()`)

1. Look at `LoanReturnStatus.java` and `LoanExtensionStatus.java`. Notice these enums already have a `getMessage()` method that returns the description string.

1. Enter the following follow-up prompt:

    ```plaintext
    #codebase I notice that LoanReturnStatus, LoanExtensionStatus, and MembershipRenewalStatus already have a getMessage() method on each enum value. Given this, what is the simplest refactoring of EnumHelper.getDescription to eliminate the if/instanceof chains while keeping full compatibility with existing callers?
    ```

    > **Prompt goal**: Guide Copilot toward a key insight: if the enums already carry their own description, `EnumHelper.getDescription` can simply delegate to a common interface or cast. This leads to a much simpler refactoring than adding Maps.

    > **Expected response characteristics**: Copilot should suggest that `getDescription` can call `getMessage()` directly if all three enum types implement a common interface (e.g., `DescribedEnum`), or it can use a series of `instanceof` checks to call the correct `getMessage()`. The simplest version just calls `.getMessage()` on each branch.

### Refactor the EnumHelper class using Agent mode

Use the following steps to refactor `EnumHelper` using Agent mode:

1. Open the Chat view and switch to **Agent** mode.

    > **NOTE**: Agent mode is preferred here because the refactoring touches `EnumHelper.java` and may involve adding a shared interface to the enum classes. Agent mode can autonomously determine which files to edit and apply consistent changes across them.

1. Enter the following prompt:

    ```plaintext
    #codebase Refactor EnumHelper.getDescription to eliminate the if/instanceof chain. Since LoanReturnStatus, LoanExtensionStatus, and MembershipRenewalStatus each already have a getMessage() method, introduce a shared interface named DescribedEnum with a single method String getMessage(). Make all three enums implement DescribedEnum. Then simplify getDescription to: if the value implements DescribedEnum, return ((DescribedEnum) value).getMessage(); otherwise return value.toString(). Update all affected files.
    ```

    > **Prompt goal**: Provide the full refactoring intent in one prompt. Specify the new interface name, which classes need to implement it, and the simplified method body. Agent mode will handle finding and editing all affected files.

    > **Expected response characteristics**: The agent should:
    > - Create or update `DescribedEnum.java` interface with `String getMessage()`
    > - Add `implements DescribedEnum` to all three enum classes
    > - Replace the `getDescription` body with a simple two-branch implementation
    > - Verify no compilation errors

1. Review the agent's proposed changes carefully before accepting.

    Pay attention to whether the three enum files need modification. Since they already have `getMessage()`, the changes may only require adding `implements DescribedEnum` to each.

1. Accept the changes.

1. Run the tests to verify the refactoring didn't break anything:

    ```bash
    mvn test
    ```

    > **Checkpoint**: All tests should still pass. The refactoring changes the implementation of `EnumHelper` but not its public API, so callers should be unaffected.

1. If tests fail, use GitHub Copilot to diagnose:

    ```plaintext
    #codebase The test <test-name> is failing after refactoring EnumHelper. The error is: <paste error>. What went wrong with the DescribedEnum refactoring?
    ```

### Verify the EnumHelper refactoring

Use the following steps to confirm the refactoring is complete and correct:

1. Open the Chat view in **Ask** mode.

1. Drag `EnumHelper.java` to the Chat context.

1. Enter the following prompt:

    ```plaintext
    Review the refactored EnumHelper.getDescription method. Does it: (1) correctly return descriptions for all three enum types, (2) handle enum values that don't implement DescribedEnum, (3) require no changes when a new value is added to an existing enum? Identify any remaining quality issues.
    ```

    > **Prompt goal**: Use Copilot as a code reviewer for the refactored class. This prompt checks three quality dimensions: correctness, robustness, and maintainability. Good code should pass all three.

1. Apply any additional improvements suggested by Copilot.

## Refactor JsonDataStore to use Java Streams

### Analyze JsonDataStore using the Chat view

The `JsonDataStore` class currently uses for-loops in `saveLoans` and `savePatrons` to build a list of simplified (non-populated) objects before saving. You'll refactor these to use Java Streams, which is more concise and idiomatic.

Use the following steps to analyze `JsonDataStore` before refactoring:

1. Open the `JsonDataStore.java` file.

1. Review the `saveLoans` and `savePatrons` methods. Notice that both:
    - Declare an empty list
    - Iterate with a for-each loop, creating a copy of each item with only the fields that should be persisted
    - Add each copy to the list
    - Call `saveJson` with the result

1. Open the Chat view in **Ask** mode and drag `JsonDataStore.java` to the Chat context.

1. Enter the following prompt:

    ```plaintext
    /explain Explain the saveLoans and savePatrons methods in JsonDataStore.java. What pattern do they follow, and how would Java Streams (stream().map().toList()) improve readability and conciseness?
    ```

    > **Prompt goal**: Use the **/explain** command to walk through the current code before generating a refactored version. Understanding the existing logic helps you verify that the refactored version preserves the same behavior.

    > **Expected response characteristics**: Copilot should explain that:
    > - Both methods create a lightweight "persistence-only" copy of each entity
    > - The for-loop pattern can be replaced with `stream().map(l -> new Loan(...)).toList()`
    > - The Stream version eliminates the intermediate list variable and is more readable

1. Enter the following follow-up prompt:

    ```plaintext
    What potential issues should I watch for when refactoring a for-loop to a Stream in saveLoans? Are there any side effects or mutation concerns with the current code?
    ```

    > **Prompt goal**: Before refactoring, surface edge cases. The current for-loop creates new objects (good — immutable operation), so streaming it is safe. Copilot should confirm this is a safe, semantics-preserving refactoring.

### Refactor the saveLoans method using Agent mode

Use the following steps to refactor `saveLoans`:

1. Open the `JsonDataStore.java` file.

1. Select the body of the `saveLoans` method.

1. Open the inline chat using **Ctrl+I**.

1. Enter the following prompt:

    ```plaintext
    Refactor the saveLoans method to use Java Streams. Replace the for-each loop and mutable list with stream().map().toList(). The mapped Loan should set only: id, bookItemId, patronId, loanDate, dueDate, and returnDate — same fields as the current loop.
    ```

    > **Prompt goal**: Use inline chat for targeted, single-method refactoring. Specifying which fields to copy prevents Copilot from accidentally including populated navigation properties like `bookItem` or `patron`.

    > **Expected response characteristics**: The refactored method should be 3-5 lines instead of 12+, using:
    > ```java
    > List<Loan> toSave = sourceLoans.stream().map(l -> {
    >     Loan loan = new Loan();
    >     loan.setId(l.getId());
    >     // ... set remaining fields
    >     return loan;
    > }).toList();
    > ```
    > Or using a constructor if one is available.

1. Accept the suggestion and save the file.

### Refactor the savePatrons method using Agent mode

Use the following steps to refactor `savePatrons`:

1. Open the Chat view in **Agent** mode.

1. Enter the following prompt:

    ```plaintext
    #codebase Refactor the savePatrons method in JsonDataStore.java to use Java Streams in the same way as saveLoans. The mapped Patron should set only: id, name, membershipStart, membershipEnd, and imageName. Apply the refactoring directly to the file.
    ```

    > **Prompt goal**: Agent mode is well-suited here because you want the change applied directly to the file. You reference the `saveLoans` refactoring as a pattern, which gives Copilot a concrete example to follow for consistency.

    > **Expected response characteristics**: The refactored method should mirror the `saveLoans` Stream pattern, copying only the five specified fields. The result should be shorter and more readable than the original.

1. Review the agent's proposed changes.

1. Accept the changes.

### Build and run the application

Use the following steps to verify the refactoring:

1. Run the full test suite:

    ```bash
    mvn clean test
    ```

    All tests should still pass. The refactoring changes implementation details, not behavior.

    > **Checkpoint**: If tests fail, use:
    > ```plaintext
    > #codebase Tests are failing after refactoring JsonDataStore. Error: <paste error>. What went wrong in saveLoans or savePatrons?
    > ```

1. Run the application to confirm end-to-end behavior is unchanged:

    ```bash
    mvn exec:java
    ```

1. When prompted for a patron name, type **One** and then press Enter.

1. At the "Input Options" prompt, type **2** and then press Enter.

1. At the "Input Options" prompt, type **1** and then press Enter.

1. At the "Input Options" prompt, type **r** and then press Enter to return the book.

1. Verify the message **Book was successfully returned.** is displayed.

1. At the "Input Options" prompt, type **q** to quit.

## Final code review

Use GitHub Copilot to do a final review of your refactored code:

1. Open the Chat view in **Ask** mode.

1. Drag `EnumHelper.java` and `JsonDataStore.java` to the Chat context.

1. Enter the following prompt:

    ```plaintext
    Review the refactored EnumHelper.java and JsonDataStore.java files. For each file, evaluate: (1) code readability — is it easier to understand than before? (2) maintainability — is it easier to add new enum values or new entity fields? (3) correctness — are there any edge cases or null-safety issues? Provide a brief quality assessment for each file.
    ```

    > **Prompt goal**: A final review prompt ensures you haven't missed any issues and gives you a structured quality assessment. This demonstrates how GitHub Copilot can be used as part of a code review workflow, not just for code generation.

1. Apply any final improvements suggested by Copilot.

1. Run the tests one final time to confirm everything is clean:

    ```bash
    mvn test
    ```

## Summary

In this exercise, you used GitHub Copilot to refactor code in a Java library application. You used **Ask mode** to analyze the `EnumHelper` class and explore a cleaner dispatch strategy using a shared `DescribedEnum` interface. You used **Agent mode** to apply the refactoring across multiple files. You used **inline chat** to refactor `saveLoans` in `JsonDataStore` to use Java Streams. Finally, you used **Agent mode** again to apply the same Stream pattern to `savePatrons`, and verified the full test suite still passed.

Key GitHub Copilot capabilities practiced:
- **/explain** command for walking through existing code before refactoring
- **#codebase** variable for full-project context during analysis
- **Ask mode** for understanding code and planning safe refactoring approaches
- **Agent mode** for autonomously refactoring across multiple files
- **Inline chat** for targeted single-method refactoring
- Iterative prompting to verify refactoring quality

## Clean up

Now that you've finished the exercise, take a minute to ensure that you haven't made any changes to your GitHub account or GitHub Copilot subscription that you don't want to keep.

## Troubleshooting

- **Tests fail after EnumHelper refactoring**: Ensure all three enum classes (`LoanReturnStatus`, `LoanExtensionStatus`, `MembershipRenewalStatus`) implement the `DescribedEnum` interface. Also verify the interface is in the correct package.
- **`toList()` not recognized**: Java's `Stream.toList()` requires Java 16+. If you're on an older JDK, use `.collect(Collectors.toList())` instead. Verify `java -version` reports 21.
- **Application behavior changes after saveLoans refactoring**: Ensure the Stream map only sets the six loan persistence fields (id, bookItemId, patronId, loanDate, dueDate, returnDate) and does not include populated navigation properties.
- **No tests discovered**: Ensure `mvn test` is run from the directory containing `pom.xml`.

## Trainer notes

- The C# source for this module is in `az-2007-m5-refactor-improve-code/AccelerateDevGHCopilot`.
- The Java starter for this module is in `az-2007-m5-refactor-improve-code-java/AccelerateDevGHCopilot`.
- The C# `EnumHelper` uses reflection; the Java `EnumHelper` uses if/instanceof chains — both are the "before" state for the refactoring exercise.
- The Java equivalent of C# LINQ is the Java Streams API (`stream().filter().map().toList()`).
- The `saveLoans` and `savePatrons` methods start with for-loops in the M5 starter but already use Streams in the M2 starter — this is intentional to create the refactoring target.
