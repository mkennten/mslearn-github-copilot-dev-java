---
lab:
  title: Exercise - Examine GitHub Copilot settings and user interface features
  description: Learn how to review GitHub Copilot settings and use GitHub Copilot features in Visual Studio Code running in GitHub Codespaces or on a local machine.
  duration: 25 minutes
  level: 200
  islab: true
  primarytopics:
    - GitHub
    - GitHub Codespaces
    - Visual Studio Code
---

# Examine GitHub Copilot settings and user interface features

Visual Studio Code provides a built-in GitHub Copilot experience that includes chat, agent workflows, inline suggestions, and editor actions. In this exercise, you review the most useful settings and explore the current GitHub Copilot user interface in a GitHub Codespaces workspace or a local Visual Studio Code workspace.

This exercise should take approximately **25** minutes to complete.

> **IMPORTANT**: To complete this exercise, you must use a GitHub account that has access to GitHub Copilot through an eligible individual, organization, or enterprise plan. Some Copilot features can also be limited by organization policies.

## Before you start

Your lab environment must include the following:

- Either a GitHub Codespace created from this repository, or a local clone of this repository.
- Access to a GitHub account with GitHub Copilot enabled.
- GitHub Copilot available in Visual Studio Code.
- If you're using a local machine: Git 2.48 or later, Visual Studio Code, Java 21 LTS, and Maven 3.9 or later.

If you aren't using Codespaces, install the required tools and then clone this repository locally:

```bash
git clone https://github.com/mkennten/mslearn-github-copilot-dev-java.git
cd mslearn-github-copilot-dev-java
code AccelerateDevGHCopilot
```

Before you continue:

1. Open this repository in GitHub Codespaces, or open your local clone in Visual Studio Code.

1. Wait for the workspace to finish loading.

1. If you opened the repository root, in the Explorer view open the `AccelerateDevGHCopilot/` folder.

1. Select the GitHub Copilot icon in the status bar and confirm that Visual Studio Code shows the Copilot plan and remaining AI credits for the signed-in user.

## Exercise scenario

You're a developer working in the IT department of your local community. The backend systems that support the public library were lost in a fire. Your team needs to develop a temporary solution to help the library staff manage their operations until the system can be replaced. Your team chose GitHub Copilot to accelerate the development process.

This exercise includes the following tasks:

1. Examine GitHub Copilot settings in Visual Studio Code.
1. Explore the GitHub Copilot user interface in Visual Studio Code.

## Examine GitHub Copilot settings in Visual Studio Code

GitHub Copilot settings are split between your GitHub account and the Visual Studio Code environment. In Visual Studio Code, the most useful settings are grouped around code editing, chat, agents, and inline chat.

Starting with recent versions of Visual Studio Code, GitHub Copilot and chat are built in, so you don't need to install a separate GitHub Copilot Chat extension for this exercise.

> **NOTE**: The exact layout of menus and settings can change over time. If you don't see a menu item exactly as described, use Visual Studio Code's Settings search or Command Palette and search for `Copilot` or `Chat`.

### Review status bar controls and core settings

Use the following steps to complete this section of the exercise:

1. Open the `App.java` file in the editor.

1. On the status bar, select the GitHub Copilot icon.

   The Copilot status menu gives you quick access to Copilot status information and common feature controls.

1. Review the options that are available from the Copilot status menu.

   Depending on your version of Visual Studio Code and your organization settings, the menu can include controls for inline suggestions, next edit suggestions, temporary snooze options, or shortcuts to Copilot settings.

1. Open the Visual Studio Code settings editor.

1. In the Settings search box, enter `copilot`.

1. Review the settings categories that are available.

   Current Copilot-related settings are commonly grouped across areas such as code editing, chat, agents, and inline chat.

1. In the Settings search box, enter `github.copilot.enable`.

1. Review the language-specific inline suggestion settings.

   By default, Markdown is often disabled for inline completions. This helps reduce noisy suggestions when you're writing documentation.

1. If you want to experiment, temporarily enable Copilot for Markdown files, and then restore the original value before you continue.

1. In the Settings search box, enter `next edit suggestions`.

1. Review the settings related to Next Edit Suggestions.

   Depending on your account and policy configuration, some of these settings may be read-only or centrally managed.

1. In the Settings search box, enter `inlineChat.askInChat`.

1. Review the description for this setting.

   This setting controls whether **Ctrl+I** opens inline chat directly in the editor, or routes the request into the Chat view when the current file is already part of an active chat editing session.

1. Close the settings editor.

### Review chat and agent settings

Use the following steps to continue exploring the Settings editor:

1. Open the Settings editor again.

1. Search for `chat.requestQueuing.defaultAction`.

1. Review how Visual Studio Code handles a new message while a previous chat request is still running.

1. Search for `chat.permissions.default`.

1. Review the default approval model for agent sessions.

   In many environments, agent tools require approval before they can run commands or make edits.

1. Search for `chat.useAgentsMdFile`.

1. Review how Visual Studio Code can use repository guidance files as additional chat context.

1. Close the settings editor.

## Explore the GitHub Copilot user interface in Visual Studio Code

Visual Studio Code integrates GitHub Copilot across several interfaces that support different ways of working.

GitHub Copilot features are organized into the following categories:

- Agents: GitHub Copilot can work as an agent that searches the workspace, edits files, runs terminal commands, and requests approval when needed.
- Natural language chat: The main chat surfaces are the Chat view, Quick Chat, and Inline Chat.
- Inline suggestions: Copilot can suggest code completions and next edit suggestions directly in the editor.
- Smart Actions: Context menu actions such as Explain and Review let you trigger common AI workflows without writing a prompt from scratch.

### Explore the Chat view

The Chat view is the main code-focused chat surface in Visual Studio Code. It runs in the sidebar and supports Ask, Plan, and Agent workflows in a single conversation.

Use the following steps to complete this section of the exercise:

1. Open the Chat view.

   You can use the Chat button in the title bar, the Chat icon, or the Command Palette and search for `Chat: Open Chat`.

1. Take a moment to review the Chat view interface.

   Notice the prompt box, the response area, the option to add context, the session controls, the mode selector, and the model selector.

1. Confirm that the chat mode is set to **Ask**.

1. If a model picker is available, keep the model selection set to **Auto**.

1. Enter the following prompt and submit it:

   ```text
   Explain how App.java initializes the library application and which services and repositories it wires together.
   ```

1. Review the response.

   Notice that Ask mode is useful for explanations, summaries, and guidance without changing files.

1. In the same chat session, enter the `#` character in the prompt box.

1. Review the context options that are offered.

   Visual Studio Code can add context such as files, folders, symbols, your codebase, and terminal output.

### Explore Quick Chat

Quick Chat is a lightweight chat experience for short questions.

Use the following steps to complete this section of the exercise:

1. Open Quick Chat.

   If you don't see it directly in the UI, use the Command Palette and search for `Chat: Open Quick Chat`.

1. Enter the following prompt:

   ```text
   What does App.java do at startup?
   ```

1. Review the response.

   If the response is too general, add `App.java` as context and ask the same question again.

1. If the Quick Chat surface offers an option to open the conversation in the Chat view, select it.

   This is useful when a quick interaction turns into a longer coding conversation.

### Review Inline Chat behavior

Inline Chat lets you work directly in the editor instead of switching to the sidebar.

Use the following steps to review Inline Chat:

1. Open `App.java` in the editor.

1. Select the line that creates the `JsonDataStore` instance.

1. Press **Ctrl+I**.

1. Notice what happens.

   Depending on whether the file is already part of an active chat editing session, Visual Studio Code might open inline chat directly in the editor or route the request to the Chat view as **Ask in Chat**.

1. If inline chat opens in the editor, enter the following prompt:

   ```text
   Add a brief comment that explains why the application reads JSON files from the data/Json folder.
   ```

1. Review the suggested edit, and then use **Undo** or cancel the change.

   This exercise focuses on the user interface, so you don't need to keep the edit.

### Compare Ask and Agent modes

Ask mode is best for explanations and suggestions. Agent mode is best when you want Copilot to make or propose actual workspace changes.

Use the following steps to compare the two modes:

1. Open `LoanService.java` in the editor.

1. Select the `extendLoan` method.

1. In the Chat view, make sure the mode is set to **Ask**.

1. Enter the following prompt:

   ```text
   Explain the selected method and identify one small refactoring that could improve readability.
   ```

1. Review the response.

   Ask mode should explain the logic and suggest a possible improvement without modifying the file.

1. Switch the same chat session to **Agent** mode.

1. Enter the following prompt:

   ```text
   Add a brief comment above the overdue-date check that explains why an expired loan cannot be extended. Do not make any other changes.
   ```

1. Review the proposed edit.

   In Agent mode, Visual Studio Code can apply an edit and show the change inline with **Keep** and **Undo** controls.

1. After reviewing the result, use **Undo** if you want to leave the repository unchanged.

> **NOTE**: Depending on your account, plan, and policy configuration, Agent mode can also request permission to use tools such as workspace search, file edits, terminal commands, or network access.

### Explore inline suggestions and next edit suggestions

GitHub Copilot provides two closely related code editing experiences:

- Inline suggestions: ghost text completions that appear at the cursor.
- Next edit suggestions: follow-up edit predictions that appear after you begin making a related change.

Use the following steps to complete this section of the exercise:

1. Open `PatronService.java` in the editor.

1. Locate the `hasOverdueUnreturnedLoan` variable.

1. Select the variable name and rename it to `hasOverdueLoan`.

1. Watch for Next Edit Suggestions on related occurrences.

   Visual Studio Code may suggest the same rename on the remaining references in the method.

1. If a suggestion appears, accept it with **Tab** or reject it with **Esc**.

1. Restore the original variable name when you're done.

1. To explore inline code completions, place the cursor on a new blank line inside a method and start typing a clear Java statement.

1. If Copilot shows a ghost text completion, accept it with **Tab** or dismiss it with **Esc**.

### Access Smart Actions

Smart Actions provide predefined AI actions from the editor context menu.

Use the following steps to complete this section of the exercise:

1. In `LoanService.java`, select the `returnLoan` method.

1. Right-click the selected code and look for AI actions such as **Explain** or **Review**.

1. Select **Explain**.

1. Review the explanation that appears in chat.

1. Right-click the same selection again and select **Review**.

1. Review the feedback.

   Review is useful when you want Copilot to focus on code quality, clarity, safety, or maintainability rather than only describing the code.

## Summary

In this exercise, you reviewed GitHub Copilot settings and explored the current GitHub Copilot user interface in Visual Studio Code. You used the status bar and Settings editor to review core Copilot controls, explored the Chat view and Quick Chat, compared Ask and Agent modes, reviewed Inline Chat behavior, observed inline suggestions and next edit suggestions, and used Smart Actions such as Explain and Review.

## Clean up

If you changed any settings or accepted any code edits during this exercise, restore the original values and undo any changes that you don't want to keep.
