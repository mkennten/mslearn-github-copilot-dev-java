---
lab:
   title: Prepare - Configure your lab environment for GitHub Copilot exercises
   description: Open the repository in GitHub Codespaces or on a local machine and complete the minimum setup required before starting the lab exercises.
  duration: 15 minutes
  level: 200
  primarytopics:
    - GitHub
    - GitHub Codespaces
    - Visual Studio Code
---

# Configure your lab environment for GitHub Copilot exercises

This repository is designed to run in GitHub Codespaces. If you can't use Codespaces, you can also clone the repository to a local machine and complete the lab there.

Before you begin the lab exercises, make sure that your environment is ready to use GitHub Copilot.

Your lab environment must include the following resources:

- Access to a GitHub account.
- Access to GitHub Copilot through an eligible individual, organization, or enterprise plan.
- Either a GitHub Codespace created from this repository, or a local clone of this repository.
- Git 2.48 or later.
- Visual Studio Code.
- Java 21 LTS.
- Maven 3.9 or later.

## Local machine requirements

If you aren't using Codespaces, install and verify the following tools on your local machine:

- Git 2.48 or later.
- Visual Studio Code.
- Java 21 LTS.
- Maven 3.9 or later.

You can verify your local toolchain by running the following commands in a terminal:

```bash
git --version
java -version
mvn -version
```

If you're using a local machine, clone this repository and open the `AccelerateDevGHCopilot/` folder in Visual Studio Code:

```bash
git clone https://github.com/mkennten/mslearn-github-copilot-dev-java.git
cd mslearn-github-copilot-dev-java
code AccelerateDevGHCopilot
```

## Open the repository in GitHub Codespaces

Complete the following steps to create and open your Codespace:

1. Open this repository on GitHub.

1. Create a new Codespace for the repository.

1. Wait for the dev container to finish building and for the workspace to load.

1. In the Explorer view, open the `AccelerateDevGHCopilot/` folder.

   This folder contains the Maven project used in the lab exercises.

## Verify GitHub Copilot access

GitHub Copilot and chat features are built into current versions of Visual Studio Code, so you don't need to install a separate GitHub Copilot Chat extension for this lab.

Complete the following steps to confirm that GitHub Copilot is available:

1. Make sure that you're signed in to GitHub in Visual Studio Code.

   In Codespaces, the workspace usually uses your GitHub identity automatically. On a local machine, you may be asked to sign in and authorize GitHub Copilot features.

1. Open the Chat view in Visual Studio Code.

1. If prompted, enable or activate GitHub Copilot features for your account.

1. Select the GitHub Copilot icon in the status bar and confirm that Visual Studio Code shows the Copilot plan and remaining AI credits for the signed-in user.

If GitHub Copilot isn't available, verify that your account has access to GitHub Copilot through your assigned plan or organization settings.

## Verify the lab workspace

Use the following steps to confirm that your environment is ready for the exercises:

1. In the terminal, change to the Maven project folder:

   ```bash
   cd AccelerateDevGHCopilot
   ```

1. Run the unit tests:

   ```bash
   mvn clean test
   ```

After these checks pass, your lab environment is ready for the GitHub Copilot lab exercises.
