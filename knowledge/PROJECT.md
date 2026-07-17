# PROJECT KNOWLEDGE

> Engine-maintained cache of **verified** operational truth about this repository. Survives every feature run.
> Human-editable without approval. It is a cache, never the source of truth: on conflict, the codebase wins and the engine corrects this file.

## Toolchain (verified commands)

<!-- Only commands that have actually been run successfully. Record the command and what "success" looks like. -->
| Purpose | Command | Verified |
|---|---|---|
| Build (debug) | `.\gradlew.bat assembleDebug` | no — capability granted by the human (2026-07-17) but not yet active: the ledger file `knowledge/capabilities.json` must be created by the human (engine writes are denied by design) |
| Unit tests | `.\gradlew.bat testDebugUnitTest` | no — same |
| Lint | `.\gradlew.bat lintDebug` | no — same |

Environment note: Windows 11 host; use `gradlew.bat` (not `./gradlew`). A previous successful human build exists (`app/build/` contains debug/release intermediates), so the toolchain is known to work on this machine.

## Architecture Conventions

Distilled from `CLAUDE.md` + `.claude/*.md` rule files (authoritative, read them before writing code) and the codebase:

- Clean architecture: `domain/` (models + repository interfaces, Android-free) · `data/` (remote, database, datastore, mapper, repository/impl) · `core/` (CoreActivity/CoreFragment/CoreLayout, config, extensions) · `common/` (Outcome, Constant, Language) · `ui/` (fragments, viewmodels, components, theme, util) · `injection/` (Koin modules).
- Source `namespace`: `com.example.skeleton` (Kotlin packages, `R` class). Installable `applicationId`: `com.example.myapplication` (use this for all `adb` device commands). Single-activity (`MainActivity : CoreActivity`) + fragment navigation; Compose rendered inside fragments via `CoreFragment.ComposeView()`.
- DI: Koin. `MainApplication.onCreate()` starts Koin with `appModule` (which includes database, datastore, repository, viewModel, network, locale modules).
- ViewModel state: `_uiState.value = _uiState.value.copy(...)` only — never `.update { }`.
- Every class/function gets KDoc ending with `@author Phong-Kaster`.
- New strings appended to end of `strings.xml`; string names describe the content, not the feature (`hello_world`, not `notification_hello_world`).
- README.md must be kept in sync with the package tree when packages/files change.

## Environmental Facts

- `compileSdk`/`targetSdk` = 36, `minSdk` = 24 — notification channels (API 26+) need `NotificationChannelCompat`/guarded creation; `POST_NOTIFICATIONS` runtime permission only exists on API 33+.
- `AndroidManifest.xml` already declares `android.permission.POST_NOTIFICATIONS`.
- A notification **permission request flow already exists**: `ui/fragment/home/component/HomeRequestPermission.kt` + `HomePermissionBottomSheet` (Accompanist Permissions), with helper `isNotificationGranted(context)`. There is **no notification posting code** (no channel, no `NotificationCompat`) anywhere in the app yet.
- Dependencies available: Compose BOM, Koin, Room+KSP, Ktor, DataStore, Accompanist Permissions, core-ktx (includes `NotificationCompat`).
- Working tree carries an unrelated human modification (`skills-lock.json`) — never stage it.

## Sources Consulted

- `CLAUDE.md`, `.claude/android-skeleton-project.md`, `.claude/repository-layer.md`, `.claude/viewmodel-layer.md`, `.claude/figma-design-system.md`, `.claude/jetpack-compose-ui.md`
- `app/build.gradle.kts`, `settings.gradle.kts`, `app/src/main/AndroidManifest.xml`
- `MainApplication.kt`, `MainActivity.kt`, `ui/util/PermissionUtil.kt`, `ui/fragment/home/component/HomeRequestPermission.kt`
