# PLAN

> Machine-owned execution strategy. The human never reviews this file — only the Definition of Done.
> Evolves through Tier-1 amendments (logged in AMENDMENTS.md) and Tier-2 proposals (human-approved).

## Strategy

The feature is small and the repository already contains the hard half (the `POST_NOTIFICATIONS` permission request flow on Home). What is missing is the posting side:

1. A reusable notification helper (`ui/util/NotificationUtil.kt` — object, mirrors `PermissionUtil`): creates the channel (API 26+ guarded via `NotificationChannelCompat`), posts a simple text notification, silently no-ops when permission is missing. Strings ("hello world", channel name) in `strings.xml`.
2. Trigger from `MainApplication.onCreate()` — the only place guaranteed to run exactly once per cold start (process creation), which satisfies the "cold start only, no duplicates on warm start" criteria for free.
3. README package-tree sync + KDoc per repo rules, folded into the same task.

All of this is one observable behavior ("app shows 'hello world' notification on cold start"), so it is one checkpoint task per the decomposition policy. Verification: `assembleDebug` build (standing capability, pending approval) + fresh-context review; runtime proof via `adb` if that goal-scoped capability is granted, else deferred to human manual check as recorded in DoD.

Execution order: wait for DoD approval + capability grant (current escalation) → T-001 implement + build-verify → fresh invocation runs the DONE-candidate verification.

## Task Graph

- T-001 — App posts a "hello world" notification on every cold start (depends on: DoD approval)

## Known Risks

- Runtime behavior (criteria 1–3) cannot be machine-verified without `adb` + a device/emulator; requested as an optional goal-scoped capability. If declined, those criteria close with code-review evidence + human manual check.
- `MainApplication.onCreate` also runs when the process is recreated for background work (rare for this app: no services/workers registered). Accepted as matching "cold start" semantics; recorded as an assumption in STATE.md.
- Gradle build on this machine is unverified by the engine (no capability yet); `app/build/` artifacts show it has worked for the human.
