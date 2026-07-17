# ESCALATION REQUEST

> The engine persists this before stopping whenever a decision exceeds its authority.
> Fill the **Decision** section (decision + rationale), then re-run the Runtime. At most one pending request at a time.

- **Type:** DoD approval + Capability grant
- **Iteration:** 1 (Bootstrap)
- **Timestamp:** 2026-07-17

## Question

1. Approve the Definition of Done in `.ai/DoD.md` (edit criteria freely before approving — especially the three assumptions listed below).
2. Grant the **standing** toolchain capability (Gradle build/test/lint) so the engine can verify its own work.
3. Optionally grant the **goal-scoped** `adb` capability so the engine can machine-verify the runtime behavior (notification actually appears on cold start) on a connected device/emulator. If declined, runtime criteria are closed by your manual check.

## Context

Bootstrap complete on Loop Branch `loop/hello-world-cold-start-notification`. PRD: pop up a "hello world" notification on every cold start. The repo already has the notification **permission** flow; only the **posting** side is missing (helper + channel + trigger in `MainApplication.onCreate`). Interpretation choices you may want to override (full list in `.ai/STATE.md` → Assumptions):

- "notification" = system status-bar notification (not an in-app toast).
- "cold start" = once per process creation via `Application.onCreate`.
- Text is verbatim "hello world" as content, with a short app title.

## Options Considered

1. Approve DoD as written + grant Gradle capability, skip `adb` — engine implements and build-verifies; you manually confirm the notification on a device. Consequence: DONE will cite your manual check for runtime criteria.
2. Approve DoD + grant Gradle + `adb` — fully machine-verified end-to-end. Consequence: engine runs install/force-stop/launch/dumpsys against a connected device or emulator.
3. Edit DoD first (e.g. you meant an in-app toast/snackbar instead of a system notification) — then approve. Consequence: engine re-plans under the corrected DoD.

## Engine Recommendation

Option 2 if a device/emulator is normally connected on this machine; otherwise Option 1. The DoD as written matches the codebase's existing direction (manifest permission + permission bottom sheet strongly imply a system notification was intended).

## Proposed Capabilities (if any)

```json
{
  "intent": "Verify engine work with the repository's Gradle toolchain (build, unit tests, lint)",
  "command": ".\\gradlew.bat assembleDebug | testDebugUnitTest | lintDebug (and their clean/help variants)",
  "scope": "consumer repository, Windows shell",
  "lifetime": "standing",
  "allow": ["Bash(./gradlew*)", "Bash(gradlew.bat*)", "PowerShell(.\\gradlew.bat*)"],
  "target_ledger": "knowledge/capabilities.json"
}
```

```json
{
  "intent": "Machine-verify runtime behavior of the cold-start notification on a connected device/emulator (install, force-stop, launch, inspect notifications)",
  "command": "adb install / adb shell am force-stop|start / adb shell dumpsys notification / adb shell pm grant|revoke com.example.myapplication android.permission.POST_NOTIFICATIONS",
  "scope": "the app package com.example.myapplication on the connected device/emulator only",
  "lifetime": "goal",
  "allow": ["Bash(adb *)"],
  "target_ledger": ".ai/capabilities.json"
}
```

## Decision

<!-- HUMAN WRITES HERE: the decision AND its rationale. The rationale becomes part of the audit trail. -->
