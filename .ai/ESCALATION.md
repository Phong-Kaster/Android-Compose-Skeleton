# ESCALATION REQUEST

> The engine persists this before stopping whenever a decision exceeds its authority.
> Fill the **Decision** section (decision + rationale), then re-run the Runtime. At most one pending request at a time.

- **Type:** DoD evidence decision — criterion 3 denial-path runtime check (E-003)
- **Iteration:** 3
- **Timestamp:** 2026-07-17

## Question

Everything else in the DoD is now machine-verified (details in `.ai/TASKS/T-001.md`):

- **c5** build: `./gradlew.bat assembleDebug` → BUILD SUCCESSFUL.
- **c1** cold start posts a notification with text exactly "hello world" (verified via `dumpsys notification` on your connected API 35 phone).
- **c2** warm start posts nothing new (same process PID, no notification timestamps changed device-wide).
- **c3, granted half** — cold start after granting posts the notification (verified live).
- **c4** channel `skeleton_general` used on API 35; compat no-op below API 26; builds at minSdk 24.
- **c6** repo-rule checklist recorded in the task file.

One check remains that this engine **cannot** perform: *"API 33+ with permission denied → cold start does not crash and posts nothing"* (c3, denied half). Your phone (`b56e2819`, physical Android 15) blocks every shell route to a denied state: `pm grant`/`pm revoke` throw SecurityException and `cmd appops set POST_NOTIFICATION deny` is silently reverted — an OEM restriction, not a Runtime one. How do you want to close this last criterion?

## Context

The code has two independent defenses, both code-review-verified: `postSimpleMessage` returns early when `isNotificationGranted(context)` is false (`checkSelfPermission(POST_NOTIFICATIONS)` on API 33+), and `notify()` is additionally wrapped in `try/catch (SecurityException)`. A crash in the denied state would require both to fail simultaneously.

## Options Considered

1. **You run a 10-second manual check** on the connected phone: Settings → Apps → the app → Notifications → turn off · force-close the app · reopen from the launcher → it must open normally with no notification · turn notifications back on · force-close and reopen → the "hello world" notification appears. Write pass/fail in the Decision. Recommended — closes c3 with real runtime evidence.
2. **Enable your OEM's "USB debugging (Security settings)" toggle** (allows shell `pm revoke`); the engine machine-verifies the denied path next iteration.
3. **Accept the code-review evidence alone** as sufficient for the denied half of c3 (the DoD's evidence spec for c3 lists code review first; the guard + try/catch make a crash implausible).

## Engine Recommendation

Option 1 — fastest and gives genuine runtime evidence on real hardware. Option 3 is defensible if you trust the double guard.

## Proposed Capabilities (if any)

None. (Option 2 is a device setting, not an engine capability.)

## Decision

<!-- Human writes here: chosen option + result/rationale. For Option 1, please record pass/fail. -->
