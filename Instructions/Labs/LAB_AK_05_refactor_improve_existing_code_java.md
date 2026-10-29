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

GitHub Copilot can help you evaluate your existing codebase and suggest updates that refactor and improve code quality, reliability, readability, and performance. In this exercise, you use GitHub Copilot to refactor specified sections of a Java application while making improvements to code quality and maintainability.

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

If you're using a local machine, confirm your Java toolchain:

```bash
git --version
java -version
mvn -version
```

## Exercise scenario

You're a developer working in the IT department of your local community. The backend systems that support the public library were lost in a fire. Your team needs to develop a temporary solution to help the library staff manage their operations until the system can be replaced. Your team chose GitHub Copilot to accelerate the development process.

You handed off an initial version of the library application for review. The review team identified opportunities to improve code quality, performance, readability, and maintainability:

1. **Keep status messages with their enum values**: Unlike the original C# project, the Java port already stores each status message in its enum value and exposes it through `getMessage()`. Analyze how this design avoids a separate description helper.

1. **Replace a collection loop with a Java Stream**: Refactor the remaining list-building loop in `JsonDataStore.getPopulatedPatrons` while preserving its ordering and mutable-list behavior.

This exercise includes the following tasks:

1. Set up the library application in Visual Studio Code.
1. Analyze the Java status-message design using Ask mode.
1. Refactor `JsonDataStore.getPopulatedPatrons` to use a Stream.

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

1. Verify the build and all tests pass before making any changes:

   ```bash
   mvn clean test
   ```

   All tests should pass. These tests serve as your safety net during refactoring.

   > **IMPORTANT**: Never refactor code that has failing tests. If any tests fail, fix them before proceeding.

## Analyze the Java status-message design

Unlike the original C# project, this Java port does not have an `EnumHelper`. Each status enum stores a message for each enum value and exposes it through `getMessage()`. `ConsoleApp` calls that method directly on the typed status returned by each service.

Use the following steps to analyze the existing design:

1. Open `LoanReturnStatus.java`, `LoanExtensionStatus.java`, and `MembershipRenewalStatus.java` in `src/main/java/com/microsoft/learning/library/enums/`.

1. Review how each enum constructor stores a message and how `getMessage()` returns it.

1. Open `ConsoleApp.java` and find where it prints the status returned by the loan and patron services.

1. Open the Chat view in **Ask** mode and add the enum files and `ConsoleApp.java` to the Chat context.

1. Enter the following prompt:

   ```plaintext
   /explain how these Java status enums provide user-facing messages. Compare this design with a centralized helper that dispatches on enum types. Why does this Java port not need an EnumHelper, and what would need to change if a new status value were added?
   ```

   > **Expected response characteristics**: Copilot should explain that each enum value is constructed with its own message, callers already know the concrete enum type and can invoke `getMessage()` directly, and adding a status means defining its message alongside the new enum constant. No separate map, `instanceof` chain, or helper is required.

1. Enter this follow-up prompt:

   ```plaintext
   Would introducing a shared DescribedEnum interface improve this code as it stands? Consider how the status values are returned and consumed, and explain when a shared interface would be useful.
   ```

   > **Prompt goal**: Evaluate abstractions against actual usage. A shared interface can be useful when different enum types must be consumed polymorphically, but these callers use typed statuses directly, so adding an interface solely to replace a nonexistent helper would add no value.

## Refactor JsonDataStore.getPopulatedPatrons with Java Streams

### Analyze `getPopulatedPatrons` using the Chat view

The persistence methods `saveLoans` and `savePatrons` already use Java Streams in this port. Instead, refactor the remaining list-building loop in `getPopulatedPatrons`. It creates a new populated patron for each input patron and returns the results in encounter order.

Use the following steps to analyze the method before refactoring:

1. Open `JsonDataStore.java` and review `getPopulatedPatrons` and `getPopulatedPatron`.

1. Open the Chat view in **Ask** mode and add `JsonDataStore.java` to the Chat context.

1. Enter the following prompt:

   ```plaintext
   /explain the getPopulatedPatrons method. How could its list-building loop be expressed with stream().map() and a method reference to getPopulatedPatron? What behavior should the refactor preserve?
   ```

   > **Expected response characteristics**: Copilot should identify the one-to-one mapping from each source patron to a populated patron, preserve encounter order, and avoid changing the behavior of `getPopulatedPatron`.

1. Enter this follow-up prompt:

   ```plaintext
   The current method returns a mutable ArrayList. Stream.toList() returns an unmodifiable list. How can I use a Stream here while preserving the current collection mutability and encounter order?
   ```

   > **Prompt goal**: Notice the collection contract, not just the loop syntax. To preserve the current mutable result, collect with `Collectors.toCollection(ArrayList::new)` rather than `Stream.toList()`.

### Refactor `getPopulatedPatrons` using Agent mode

1. Open the Chat view in **Agent** mode.

1. Enter the following prompt:

   ```plaintext
   Refactor only JsonDataStore.getPopulatedPatrons to use a Stream and the method reference this::getPopulatedPatron. Preserve encounter order and the current mutable ArrayList return behavior by collecting with Collectors.toCollection(ArrayList::new). Do not change other methods.
   ```

   > **Expected response characteristics**: The method should return the mapped results using `sourcePatrons.stream().map(this::getPopulatedPatron).collect(Collectors.toCollection(ArrayList::new))`. Add the `Collectors` import if needed, and leave the population logic unchanged.

1. Review and accept the proposed change.

1. Review the refactored method in **Ask** mode with this prompt:

   ```plaintext
   Review getPopulatedPatrons. Does it preserve encounter order, return a mutable list like the original implementation, and call getPopulatedPatron once per input? Identify any behavior changes or unnecessary edits.
   ```

1. Apply only improvements that preserve the existing behavior.

### Build and run the application

Use the following steps to verify the refactoring:

1. Run the full test suite:

   ```bash
   mvn clean test
   ```

   All tests should still pass. The refactoring changes implementation details, not behavior.

   > **Checkpoint**: If tests fail, use:
   >
   > ```plaintext
   > #codebase Tests are failing after refactoring JsonDataStore.getPopulatedPatrons. Error: <paste error>. What behavior may have changed?
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

1. Add the three status enum files, `ConsoleApp.java`, and `JsonDataStore.java` to the Chat context.

1. Enter the following prompt:

   ```plaintext
   Review the Java status-message design in the three enum files and ConsoleApp.java, and the refactored JsonDataStore.getPopulatedPatrons method. Is the enum design appropriately simple for its typed callers? Does the Stream refactor preserve encounter order, list mutability, and the existing population behavior? Identify any remaining quality issues.
   ```

   > **Prompt goal**: A final review prompt ensures you haven't missed any issues and gives you a structured quality assessment. This demonstrates how GitHub Copilot can be used as part of a code review workflow, not just for code generation.

1. Apply any final improvements suggested by Copilot.

1. Run the tests one final time to confirm everything is clean:

   ```bash
   mvn test
   ```

## Summary

In this exercise, you used GitHub Copilot to analyze the Java port's enum-owned status messages and refactor `JsonDataStore.getPopulatedPatrons` to use a Stream. You verified that the refactor preserves encounter order and the method's mutable-list behavior, then ran the test suite and application.

Key GitHub Copilot capabilities practiced:

- **/explain** command for walking through existing code before refactoring
- **#codebase** variable for full-project context during analysis
- **Ask mode** for understanding code and planning safe refactoring approaches
- **Agent mode** for applying a targeted refactoring
- Iterative prompting to verify refactoring quality

## Clean up

Now that you've finished the exercise, take a minute to ensure that you haven't made any changes to your GitHub account or GitHub Copilot subscription that you don't want to keep.

## Troubleshooting
- **`toList()` not recognized**: Java's `Stream.toList()` requires Java 16+. If you're on an older JDK, use `.collect(Collectors.toList())` instead. Verify `java -version` reports 21.
- **The refactored method returns an unmodifiable list**: `Stream.toList()` returns an unmodifiable list. To preserve the current mutable-list behavior of `getPopulatedPatrons`, use `.collect(Collectors.toCollection(ArrayList::new))`.
- **Application behavior changes after the Stream refactor**: Ensure the method still maps each source patron through `getPopulatedPatron` and does not modify the source patrons or their population logic.
- **No tests discovered**: Ensure `mvn test` is run from the directory containing `pom.xml`.
