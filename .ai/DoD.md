# Definition of Done

> Derived from `PRD.md` at bootstrap. Human-owned after approval: the engine may propose changes (Tier 3) but never apply them.
> Every criterion must be verifiable by evidence — a command that can be run, an observable behavior, a measurable property.

## Status

- [ ] APPROVED — approve via the pending `.ai/ESCALATION.md`; edit criteria freely before approving.

## Acceptance Criteria

1. On every cold start (process is created and the app is opened from the launcher), the app posts a system status-bar notification whose message text is exactly "hello world".
2. The notification is posted at most once per cold start: returning from background (warm start) or rotating the screen does not post another one.
3. On Android 13+ (API 33+), when `POST_NOTIFICATIONS` is not granted, the app does not crash; the existing Home permission bottom-sheet flow remains the way the user grants it, and the next cold start after granting posts the notification.
4. On Android 8.0–12 (API 26–32) the notification is posted through a dedicated notification channel; on API 24–25 it posts without a channel. No API-level crashes across minSdk 24 → targetSdk 36.
5. The project compiles: `.\gradlew.bat assembleDebug` finishes with BUILD SUCCESSFUL.
6. Code follows the repository rules in `CLAUDE.md` / `.claude/*.md` (layering, KDoc with `@author Phong-Kaster`, strings appended to `strings.xml`, README package tree synced if files/packages are added).

## Constraints

- Do not remove or bypass the existing `HomeRequestPermission` / `HomePermissionBottomSheet` flow; the cold-start notification must coexist with it.
- No new third-party dependencies; `androidx.core` `NotificationCompat` is sufficient.
- User-visible text ("hello world", channel name) lives in `strings.xml`, not hardcoded in Kotlin.
- Skeleton spirit: the notification helper must be reusable in other projects (clear, documented, no one-off hacks).

## Verification Evidence Required

| Criterion | Evidence |
|---|---|
| 1 | Code path: notification posted from app cold-start entry point; if `adb` capability is granted: `adb shell am force-stop` + relaunch + `adb shell dumpsys notification` showing the record. Otherwise: human manual check on device/emulator. |
| 2 | Code review evidence that the trigger lives in `Application.onCreate` (runs exactly once per process) or equivalent once-per-process guard; manual/adb check that backgrounding + reopening posts nothing new. |
| 3 | Code review of the permission guard (`isNotificationGranted` check before posting); manual/adb check on an API 33+ target with permission denied → no crash, no notification. |
| 4 | Code review of channel creation (`NotificationChannelCompat` / API-26 guard); build passes with `minSdk 24`. |
| 5 | Command output: `.\gradlew.bat assembleDebug` → `BUILD SUCCESSFUL`. |
| 6 | Fresh-context review checklist against `.claude/*.md` rules recorded in the task file. |
