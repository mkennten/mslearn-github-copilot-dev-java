# mslearn-github-copilot-dev-java

Java-only training repository for GitHub Copilot development labs.

This repository contains:

- a Java Maven sample application in `AccelerateDevGHCopilot/`
- lab instructions in `Instructions/`
- supporting documentation in `docs/`

## Purpose

This repository is a Java-focused split of the Microsoft Learn GitHub Copilot development content. It is intended for learning, exercises, and experimentation with GitHub Copilot in a Java codebase.

## Important notice

This repository is provided for educational purposes only.

- It is **not intended for production use**
- It may contain intentionally incomplete starter code for lab exercises
- Security hardening, operational controls, and production readiness are out of scope for the sample application

For example, the Java starter includes exercise code that students are expected to complete during the labs.

## Repository structure

- `.devcontainer/` — Java development container configuration
- `AccelerateDevGHCopilot/` — Java sample console application
- `Instructions/` — lab guides and concept materials
- `docs/` — supporting documentation

## Codespaces

This repository is designed to work in GitHub Codespaces for easy access to the lab environment.

Codespaces provides a ready-to-use setup with the Java 21 and Maven toolchain already configured through the included dev container. This lets you open the repository and start working without installing local dependencies first.

To get started in Codespaces:

1. Open the repository in GitHub Codespaces.
2. Wait for the container to finish building and the workspace to load.
3. Open the `AccelerateDevGHCopilot/` folder, which contains the `pom.xml` file.
4. Run Maven commands from that folder, such as `mvn clean test` or `mvn exec:java`.

## Java sample app

The sample app is located in `AccelerateDevGHCopilot/`.

### Prerequisites

- Java 21
- Maven 3.9+

### Build and test

```bash
cd AccelerateDevGHCopilot
mvn clean test
```

### Run

```bash
cd AccelerateDevGHCopilot
mvn exec:java
```

### Data files

The console application reads and writes JSON data stored in:

- `AccelerateDevGHCopilot/data/Json`

## Included lab content

This repo includes Java-related lab materials and selected setup/configuration instructions from the broader learning content.

## Exclusions

This repository excludes:

- non-Java lab variants
- C# and Python project variants
- media assets not needed for this Java-focused split

## Security

If you discover a security issue, please do not open a public issue. See `SECURITY.md` for reporting guidance.

## License

- MIT
