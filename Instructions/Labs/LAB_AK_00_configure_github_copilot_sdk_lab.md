---
lab:
   title: Prepare - Configure your GitHub Copilot SDK lab environment
   description: Open the repository in GitHub Codespaces or on a local machine and complete the minimum setup required for the GitHub Copilot SDK exercises.
  duration: 15 minutes
  level: 200
  primarytopics:
    - GitHub
    - GitHub Codespaces
    - Visual Studio Code
---

# Configure your GitHub Copilot SDK lab environment

Before you begin a GitHub Copilot SDK lab exercise, make sure that your lab environment is ready to use GitHub Copilot features and GitHub Copilot CLI.

GitHub Codespaces is the preferred environment for this repository. If you can't use Codespaces, you can also clone the repository to a local machine and complete the lab there.

Your lab environment must include the following resources:

- Access to a GitHub account.
- Access to GitHub Copilot through an eligible individual, organization, or enterprise plan.
- Either a GitHub Codespace created from this repository, or a local clone of this repository.
- Git 2.48 or later.
- Visual Studio Code.
- Java 21 LTS.
- Maven 3.9 or later.
- GitHub Copilot CLI installed and authenticated in the environment that you'll use for the lab.

## Local machine requirements

If you aren't using Codespaces, install and verify the following tools on your local machine before you continue:

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

If you can use Codespaces, follow these steps to create and open your Codespace:

1. Open this repository on GitHub.

1. Create a new Codespace for the repository.

1. Wait for the dev container to finish building and for the workspace to load.

1. In the Explorer view, open the `AccelerateDevGHCopilot/` folder.

   This folder contains the Maven project used in the Java lab exercises.

## Verify GitHub Copilot access

GitHub Copilot and chat features are built into current versions of Visual Studio Code, so you don't need to install a separate GitHub Copilot Chat extension for this lab.

Complete the following steps to confirm that GitHub Copilot is available:

1. Make sure that you're signed in to GitHub in Visual Studio Code.

   In Codespaces, the workspace usually uses your GitHub identity automatically. On a local machine, you may be asked to sign in and authorize GitHub Copilot features.

1. Open the Chat view in Visual Studio Code.

1. If prompted, enable or activate GitHub Copilot features for your account.

1. Select the GitHub Copilot icon in the status bar and confirm that Visual Studio Code shows the Copilot plan and remaining AI credits for the signed-in user.

If GitHub Copilot isn't available, verify that your account has access to GitHub Copilot through your assigned plan or organization settings.

## Install GitHub Copilot CLI

The GitHub Copilot SDK exercises use GitHub Copilot CLI for command-line chat and code generation scenarios. Install the CLI in the environment that you'll use for the lab.

In this repository's Codespaces environment, `npm` isn't available by default because the dev container image installs Java and Maven tooling, but it doesn't install Node.js. Because of that, `npm install -g @github/copilot` doesn't work unless you install Node.js separately.

The simplest approach in Visual Studio Code is to start `copilot` in the integrated terminal and let it bootstrap the CLI for you. In Codespaces, the first run can display a message such as `Cannot find GitHub Copilot CLI` followed by an install prompt. That is the expected bootstrap flow. If you need the latest installation guidance, see the official documentation: <a href="https://docs.github.com/en/copilot/how-tos/copilot-cli/install-copilot-cli" target="_blank">Install GitHub Copilot CLI</a>.

1. Open a terminal in your Codespace or local Visual Studio Code window.

1. Start GitHub Copilot CLI:

   ```bash
   copilot
   ```

1. If the terminal reports that the CLI isn't installed and asks `Install GitHub Copilot CLI? ['y/N']`, enter `y` and wait for the installation to finish.

1. Verify that the CLI is available:

   ```bash
   copilot --help
   ```

If the bootstrap prompt doesn't appear, use a platform-specific fallback:

- Codespaces, Linux, or macOS:

  ```bash
  curl -fsSL https://gh.io/copilot-install | bash
  ```

- Windows:

  ```powershell
  winget install GitHub.Copilot
  ```

- Any platform with Node.js 22 or later already installed:

  ```bash
  npm install -g @github/copilot
  ```

## Authenticate GitHub Copilot CLI

After the CLI is installed, authenticate it in your lab environment.

1. In the terminal, start GitHub Copilot CLI.

   ```bash
   copilot
   ```

1. If the CLI prompts you to authenticate, run the `/login` slash command and follow the on-screen instructions.

1. Complete the browser-based authorization flow using your GitHub account.

If your organization manages GitHub Copilot access, organization or enterprise policies can prevent GitHub Copilot CLI from being used. If authentication fails, verify that GitHub Copilot CLI is allowed for your account.

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

1. Use a Copilot feature in Visual Studio Code, such as chat, inline suggestions, or an edit action, to confirm that AI features are available in your environment.

After these checks pass, your lab environment is ready for the GitHub Copilot SDK lab exercises.
