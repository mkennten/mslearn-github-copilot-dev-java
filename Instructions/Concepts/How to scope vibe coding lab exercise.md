---
lab:
  title: How to scope vibe coding lab exercise
  description: Vibe coding lab exercises should be scoped to focus on specific tasks and outcomes, ensuring that participants can effectively learn and apply the concepts within a limited timeframe.
  duration: 5 minutes
  level: 200
---

# How to scope vibe coding lab exercise

Vibe coding lab exercises should be scoped to focus on specific tasks and outcomes, ensuring that participants can effectively learn and apply the concepts within a limited timeframe.

For this repo, the extra vibe coding exercise should be a separate Java web project that learners create in GitHub Codespaces or on a local machine using Visual Studio Code. It should not depend on the existing library console app used in the other exercises.

## Before you start

Use the same setup expectations across the lab content:

- Access to this repository in GitHub Codespaces, or a local clone of this repository.
- Git 2.48 or later.
- Visual Studio Code.
- Java 21 LTS.
- Maven 3.9 or later.
- A GitHub account with GitHub Copilot enabled.

If you aren't using Codespaces, verify the local toolchain:

```bash
git --version
java -version
mvn -version
---
lab:
   title: How to scope vibe coding lab exercise
   description: Scope Java-based vibe coding lab exercises around small, concrete outcomes that can be completed in GitHub Codespaces or a local Visual Studio Code environment.
   duration: 5 minutes
   level: 200
---
code .
```

For a start-from-scratch vibe coding exercise, create a new project folder in the workspace and use GitHub Copilot to scaffold the app.

For example:

```bash
mkdir vibe-coding-ecommerce
cd vibe-coding-ecommerce
```

If learners create a Spring Boot project, they can verify the environment by running Maven commands such as `mvn -version` and later using `mvn spring-boot:run` after the project is scaffolded.

## Vibe coding project types

The coding projects for a lab exercise need to be carefully scoped, either narrowly or specifically, to accommodate 20-30 minute exercises.

1. Start from scratch projects
   1. Create a prototype e-commerce website

      Example prompt:
      - I need to create a prototype e-commerce website using Java 21, Maven, Spring Boot, HTML, and CSS.
      - The prototype e-commerce app must run in GitHub Codespaces and in Visual Studio Code on a local machine.
      - The app should include the following pages: products list, product details, shopping cart, and checkout. Each page should provide basic functionality and forward or back navigation between pages.
      - Use a simple dataset of 10 fruit products. Include product name, description, price per unit, and a simple image URL or placeholder image.
      - The products list page should display a list of products with basic information such as product name, price per unit, and image. The page should also provide a way to select a quantity and add selected items to the shopping cart.
      - The product details page should display detailed information about a selected product, including product name, description, price per unit, and image.
      - The shopping cart page should display products added to the cart, including product name, quantity, and total price. The page should also provide a way to update quantities and remove products.
      - The checkout page should display an order summary and a way to confirm the order.
      - The website should provide basic navigation between pages and simple responsive styling for desktop and phone layouts.
      - The prototype should not include backend services such as authentication, payment processing, or database integration. It can use in-memory data and simple controllers.

   1. Create a prototype for an AI-enhanced shopping experience
      - For example, add a product recommendation or gift suggestion feature to the e-commerce site.
      - Scope the task to one integration point and one user flow.
      - Avoid adding full authentication, deployment, or multi-service orchestration to a short lab.

1. Add a new feature to a project
   1. Add a new feature to an existing app
      - For example, add a search feature to the e-commerce site.
      - The search feature should allow users to search for products by name or description.
      - The results should display a list of matching products with name, price, and image.
      - The feature should fit in one controller or service slice plus a small UI update.

   1. Add a new page to an existing app
      - For example, add a contact us page to the e-commerce app.
      - The page should include a form that allows users to submit their name, email address, and message.
      - The page should also provide a way to navigate back to the products list page.

1. Change a tool, framework, or technology projects
   1. Change the front-end approach of an existing app
      - For example, change the UI implementation from server-rendered templates to a lightweight JavaScript-enhanced front end while keeping the Java backend.
      - The new approach should preserve the same functionality as the original app, including product listing, product details, cart, and checkout.
      - Keep the exercise focused on one clear technology change.

   1. Change the back-end approach of an existing app
      - For example, change the back end from simple in-memory collections to a JSON-backed repository.
      - The new implementation should preserve the same user-facing behavior without expanding the scope into full production infrastructure.

1. Code review and improvement projects
   1. Add logging to a Java web project
   1. Add JUnit tests to a Java web project
   1. Improve layout, prompt quality, or controller structure in one focused area

1. Coding language conversion projects

   Convert an existing app to a different language or framework.

   For example, convert a small Python or JavaScript e-commerce prototype into a Java Spring Boot app.

   Keep the conversion target narrow. A single page flow, service layer, or data model is more appropriate than migrating an entire application in one lab.

1. Redesign the architecture projects (too big)

## Scoping guidance for this repo

When you create lab prompts for this repository, prefer exercises that:

- Can be completed in GitHub Codespaces without extra infrastructure.
- Use Java 21 and Maven so the setup stays consistent with the rest of the repo.
- Create a small standalone web project rather than modifying the existing console app.
- Focus on one end-to-end feature or a small set of pages that can be completed in 20 to 30 minutes.
- End with a simple verification step such as `mvn test` or `mvn spring-boot:run`.

Avoid prompts that require cloud deployment, production authentication, payment gateways, database provisioning, or large-scale architecture changes unless the lab is explicitly designed for that scope.
