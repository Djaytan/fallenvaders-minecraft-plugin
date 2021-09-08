# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Create the CHANGELOG based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
- Add an MkDocs website for the project Wiki.
- Setup SonarQube for code quality analyzes.

## [0.1.0] - 2021-08-28
### Added
- Update and import Dornacraft's plugins sources code:
  - JusticeHands: manages sanctions over the server.
  - MailBox: send and receive mails and maybe items to players even if there
      are disconnected.
  - MiniEvents: easy launch of events in the server.
  - FightSession: prevents disconnections during fights and limits some usage
      in this context (e.g. deactive enderpearl usage).
- Setup a Minecraft test server with debug mode option.
- Use [google-java-format](https://google.github.io/styleguide/javaguide.html) for code style guideline.
- Setup CircleCI CI/CD platform.
- Setup project with Maven as dependencies manager, builder with multi-modules.
- Start following [SemVer](https://semver.org) properly.
