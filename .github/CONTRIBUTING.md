# Contributing Guidelines

Thank you for your interest in contributing to this project! 🎉
Please take a moment to review these guidelines.

---

## How to contribute
- Report bugs by opening [issues](../../issues)
- Suggest improvements and features
- Submit pull requests

## Branching & Workflow
- `main` contains stable code only
- Create feature branches from `main`:
  `feature/<description>` or `fix/<description>`

## Commit Messages
We follow [Conventional Commits](https://www.conventionalcommits.org/):
- `feat: <new feature>`
- `fix: <bug fix>`
- `docs: <documentation change>`
- `chore: <cleanup, tooling>`
- `test: <tests>`

Example:
```
feat: Add support for record withers
```

## Pull Requests
- Ensure `mvn verify` passes before submitting
- Add or update tests where applicable
- Keep PRs focused and small – prefer multiple smaller PRs over one huge PR
- Describe clearly what the PR does

## Code Style
- Java 17+ required
- Code must follow [Palantir Java Format](https://github.com/palantir/palantir-java-format) (a strict wrapper around Google Java Format)
- Formatting is enforced via [Spotless Maven Plugin](https://github.com/diffplug/spotless)
- Run `mvn spotless:apply` before committing to automatically format your code

⚠️ Do not manually format code — always rely on `mvn spotless:apply`.

## Questions?
If you’re unsure about something, open an issue or start a discussion.
We appreciate every contribution 🚀
