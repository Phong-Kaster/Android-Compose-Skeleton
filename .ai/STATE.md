# STATE

> Machine-owned execution memory. Updated every iteration; committed atomically with the code it describes.
> A fresh engine invocation must be able to resume from this file plus the repository alone.

## Current

- **Phase:** escalated (T-001 implemented; verification blocked until capability ledgers are materialized by the human)
- **Loop Branch:** loop/hello-world-cold-start-notification
- **Next task:** T-001 verification (build via `gradlew.bat assembleDebug`, then runtime check via `adb`)
- **DONE-candidate:** no

## Progress

| Task | Status | Evidence |
|---|---|---|
| T-001 | in-progress — code complete, verification pending | Implementation committed this iteration; build + adb evidence pending capability activation (see `.ai/ESCALATION.md`) |

## Assumptions

- "notification" in the PRD means a **system status-bar notification** (not an in-app toast/snackbar). Confirmed by the human at the DoD gate (Decision 2026-07-17).
- "each time open app from cold start" is implemented as "once per process creation" (`Application.onCreate`). Warm starts and configuration changes do not re-post. Confirmed at the DoD gate.
- "hello world" is the notification content text, verbatim lowercase; title is "Skeleton". Confirmed at the DoD gate.

## Iteration History

### Iteration 2 — 2026-07-17 (Implement T-001 + consume escalation)

- Attempted: Consumed the approved bootstrap escalation (Option 2: DoD approved as written; Gradle + adb capabilities granted). Implemented T-001: `ui/util/NotificationUtil.kt` (channel + permission-guarded posting, fail-soft), `showColdStartNotification()` in `MainApplication.onCreate()`, three strings appended to `strings.xml` (`hello_world`, `skeleton`, `important_updates_and_alerts`), README `ui/util` line synced.
- Learned: the granted capabilities are **not yet mechanically active**. The Runtime compiles permissions only from the ledger files (`knowledge/capabilities.json`, `.ai/capabilities.json`), which do not exist; the engine's attempts to create them were denied by the Runtime's immutable deny rules — by design, ledger entries are written by the HUMAN (see `.loop/templates/capabilities.template.json`). Gradle invocation was denied this iteration; no build or adb evidence could be produced. The engine did not attempt to bypass the denial.
- Reconciled: escalation archived below; new Escalation Request written asking the human to paste the already-approved entries into the two ledger files (exact contents provided). T-001 marked in-progress with verification steps recorded in the task file. Tier-1 amendment logged (verification split into the next iteration).

### Iteration 1 — 2026-07-17 (Bootstrap)

- Attempted: Bootstrap per ENGINE §5. Read PRD; inspected repo (Gradle/Kotlin/Compose skeleton, Koin, minSdk 24 / target 36); created Loop Branch `loop/hello-world-cold-start-notification`; generated `knowledge/PROJECT.md`, `.ai/` (DoD, PLAN, TASKS/T-001, STATE, AMENDMENTS); wrote Escalation Request.
- Learned: permission request flow for notifications already exists (`HomeRequestPermission.kt` + bottom sheet + `isNotificationGranted`); no posting code exists anywhere. `POST_NOTIFICATIONS` already in manifest. Build commands unverified (no capability yet) but `app/build/` shows prior successful human builds. Windows host → `gradlew.bat`.
- Reconciled: discoveries recorded in `knowledge/PROJECT.md` and T-001 notes; PRD ambiguities recorded as assumptions above (minor, reversible at DoD gate); DoD approval + toolchain capability + optional `adb` capability escalated.

## Archived Escalations

### E-001 — Bootstrap: DoD approval + capability grants (requested Iteration 1, decided 2026-07-17, consumed Iteration 2)

- **Request:** (1) approve `.ai/DoD.md`; (2) grant standing Gradle toolchain capability (`Bash(./gradlew*)`, `Bash(gradlew.bat*)`, `PowerShell(.\gradlew.bat*)` → `knowledge/capabilities.json`); (3) optionally grant goal-scoped `adb` capability (`Bash(adb *)`, scoped to the app package on the connected device/emulator → `.ai/capabilities.json`).
- **Decision (human):** Option 2 — approved in full. DoD approved as written (system status-bar notification, once per cold start via `Application.onCreate`, text exactly "hello world"). Both capabilities granted with the exact rule strings above.
- **Rationale (human):** DoD matches the codebase's existing direction; machine verification end-to-end preferred; a device/emulator is available on this machine.
- **Consumption note:** the engine cannot write the ledger files itself (Runtime deny rules, by design). Follow-up escalation E-002 asks the human to materialize the approved entries verbatim.
