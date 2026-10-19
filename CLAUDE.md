# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

The README is the main documentation. It describes the integration, the architecture, the development commands, the workflows and the release:

@README.adoc

## Rules for Changes

- Keep the README current. When you change the public API, a command or a workflow, update the README in the same commit.
- The other Cyface Android libraries and the app use this library. `appcompat` and `datastore` are `api` dependencies: a change of their version changes the public API.
- Follow the rules of the workflows in the README section "GitHub Workflows" for each change of `.github/workflows/`.

## Commit Style

`[TICKET-ID] Short imperative summary` (for example `[LEIP-409] Add StorageHelper with fallback`).
