---
lab:
  title: Prepare - Enable GitHub Copilot in Visual Studio Code
  description: Complete the steps required to verify GitHub Copilot access in GitHub Codespaces or on a local machine.
  duration: 10 minutes
  level: 200
  primarytopics:
    - GitHub
    - GitHub Codespaces
    - Visual Studio Code
---

# Enable GitHub Copilot in Visual Studio Code

GitHub Copilot and chat features are built into current versions of Visual Studio Code, so you don't need to install a separate GitHub Copilot Chat extension for this lab.

If you aren't using Codespaces, install the following tools on your local machine before you continue:

- Git 2.48 or later.
- Visual Studio Code.
- Java 21 LTS.
- Maven 3.9 or later.

If you're using a local machine, clone this repository and open the `AccelerateDevGHCopilot/` folder in Visual Studio Code:

```bash
git clone https://github.com/mkennten/mslearn-github-copilot-dev-java.git
cd mslearn-github-copilot-dev-java
code AccelerateDevGHCopilot
```

Complete the following steps to verify that GitHub Copilot is enabled in your environment:

1. Open this repository in GitHub Codespaces, or open your local clone in Visual Studio Code.

1. Wait for the workspace to finish loading.

1. In the Visual Studio Code window, locate the GitHub Copilot icon on the status bar.

1. If the status bar shows that GitHub Copilot needs to be set up, select the Copilot icon and complete the sign-in or activation flow.

   The Codespace usually uses your GitHub identity automatically, but you may still be asked to confirm access to GitHub Copilot features.

1. If a browser-based authorization flow opens, sign in with the GitHub account that you'll use for the lab and complete the authorization steps.

1. Select the GitHub Copilot icon in the status bar again.

1. Confirm that Visual Studio Code shows the Copilot plan and remaining AI credits for the signed-in user.

If GitHub Copilot isn't available, verify that your account has access through an eligible individual, organization, or enterprise plan, and verify that your organization hasn't disabled Copilot access for Codespaces or Visual Studio Code.

After these checks pass, GitHub Copilot is ready to use in your lab environment.
