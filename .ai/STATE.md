# STATE

> Machine-owned execution memory. Updated every iteration; committed atomically with the code it describes.
> A fresh engine invocation must be able to resume from this file plus the repository alone.

## Current

- **Phase:** escalated (bootstrap complete; waiting on DoD approval + capability grants)
- **Loop Branch:** loop/hello-world-cold-start-notification
- **Next task:** T-001 (blocked on escalation decision)
- **DONE-candidate:** no

## Progress

| Task | Status | Evidence |
|---|---|---|
| T-001 | pending | — |

## Assumptions

- "notification" in the PRD means a **system status-bar notification** (not an in-app toast/snackbar). Grounds: the manifest already declares `POST_NOTIFICATIONS` and the app already ships a notification-permission request flow. Reversible at the DoD gate — the human can edit `.ai/DoD.md` before approving.
- "each time open app from cold start" is implemented as "once per process creation" (`Application.onCreate`). Warm starts and configuration changes do not re-post. Rare non-launcher process starts (none currently exist in this app — no services/workers) would also post; accepted.
- "hello world" is rendered as the notification content text, verbatim lowercase as written in the PRD; a short title ("Skeleton") accompanies it since Android notifications need a title. Editable in DoD/strings if the human prefers different casing.

## Iteration History

### Iteration 1 — 2026-07-17 (Bootstrap)

- Attempted: Bootstrap per ENGINE §5. Read PRD; inspected repo (Gradle/Kotlin/Compose skeleton, Koin, minSdk 24 / target 36); created Loop Branch `loop/hello-world-cold-start-notification`; generated `knowledge/PROJECT.md`, `.ai/` (DoD, PLAN, TASKS/T-001, STATE, AMENDMENTS); wrote Escalation Request.
- Learned: permission request flow for notifications already exists (`HomeRequestPermission.kt` + bottom sheet + `isNotificationGranted`); no posting code exists anywhere. `POST_NOTIFICATIONS` already in manifest. Build commands unverified (no capability yet) but `app/build/` shows prior successful human builds. Windows host → `gradlew.bat`.
- Reconciled: discoveries recorded in `knowledge/PROJECT.md` and T-001 notes; PRD ambiguities recorded as assumptions above (minor, reversible at DoD gate); DoD approval + toolchain capability + optional `adb` capability escalated.

## Archived Escalations

<!-- Full request + decision + rationale of every consumed Escalation Request. -->
