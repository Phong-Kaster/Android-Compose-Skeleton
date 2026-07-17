# Android Compose Skeleton

A **reusable Android skeleton** you can copy into new apps. It follows **Clean Architecture**, **Koin** DI, and team rules in **`.cursor/rules/`**—with **`android-skeleton-project.mdc` taking highest priority** when several rule files apply.

## Purpose

- Ship a **starter app** with navigation, Compose inside fragments, local + remote data, and settings—not a one-off demo.
- Prefer **clear layers and config** over shortcuts so the codebase stays **easy to expand** and **easy to recycle** into other projects.
- Prefer code that is **easy for humans to read** over micro-optimizations unless you have a measured need.

## What this app does (features)

| Area | What you get |
|------|----------------|
| **Home** | **Posts** from [JSONPlaceholder](https://jsonplaceholder.typicode.com/) via **Ktor**, cached in **Room**, offline-first list UI with refresh and error display. |
| **Settings** | **Dark mode** (DataStore), **language** selection, privacy/terms links, share app, **in-app review** (rate bottom sheet). |
| **Shell** | Bottom navigation (Home / Settings), **CoreLayout** + top bar, **Material 3**. |
| **Permissions** | Compose flows for **notification**, **location**, and **exact alarm** on the home screen. |

## Documentation expectations (for contributors)

Match **`.cursor/rules/android-skeleton-project.mdc`**:

- Add **KDoc** (or a short paragraph) on classes and functions—especially complex logic—and include **`@param`** where it helps.
- End class-level docs with **`@author Phong-Kaster`**.
- Write explanations so **another human can follow** (“explain like I’m new to this file”).

## Tech stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose + Material 3 (Compose inside **Navigation** fragments)  
- **DI:** Koin (Hilt is an allowed alternative per rules; this repo uses Koin)  
- **Navigation:** Navigation Component + `NavHostFragment`  
- **Network:** Ktor client (OkHttp engine); Retrofit is an allowed alternative per rules  
- **Local:** Room, DataStore Preferences  
- **Config:** `core.config.AppConfig` (API base URL, timeouts); **`common.Constant`** (app URLs, DataStore names, shared constants)  
- **Use cases:** For **non-trivial work** that takes time and drives a decision, add a use case in **`domain/usecase/`** with a **purpose-driven name** (e.g. `CreateOngoingUseCase`, `CreateDailyNotificationUseCase`).

## Clean Architecture — package layout (layers)

| Layer | Packages | Role |
|-------|-----------|------|
| **domain** | `model/`, `repository/` (interfaces only), `enums/`, `usecase/` | No Android or data-layer dependencies. |
| **data** | `remote/api/`, `remote/dto/`, `remote/util/` (e.g. `safeApiCallFlow`), `mapper/`, `repository/impl/`, `database/`, `datastore/` | APIs, DTOs, mappers, Room, repository implementations. |
| **core** | `config/` (`AppConfig`), `CoreActivity`, `CoreFragment`, `CoreLayout`, `extension/` | App shell and shared non-UI building blocks. |
| **common** | `Resource` / result types, `Constant`, `Language`, shared types, extensions | Shared Kotlin-only helpers (prefer **`common`** for new shared utilities). |
| **ui** | `fragment/`, `viewmodel` (by convention next to fragments), `component/`, `theme/`, `util/` (e.g. `UiErrorMapper`, `LocaleManager`) | Compose UI, navigation from fragments, UI-only helpers. |
| **injection** | Koin modules | **`appModule`** includes database, datastore, repository, viewModel, **network**, **locale** (see `injection/AppModule.kt`). |

## Project package tree (`com.example.skeleton`)

Source root: `app/src/main/java/com/example/skeleton/`.

```
com.example.skeleton
├── MainActivity.kt
├── MainApplication.kt
├── common/                    # Shared types, Constant, Language, Result, …
├── core/
│   ├── config/                # AppConfig
│   ├── CoreActivity, CoreFragment, CoreLayout
│   └── extension/             # collection, date_and_time, flow, null_safety, number, string_validation
├── data/
│   ├── database/              # cache, local (AppDatabase, dao, entity, converter)
│   ├── datastore/
│   ├── mapper/
│   ├── remote/
│   │   ├── api/               # ApiPath, PostApi, WeatherApi, …
│   │   ├── dto/
│   │   └── util/              # safeApiCallFlow, …
│   └── repository/
│       └── impl/              # XxxRepositoryImpl
├── domain/
│   ├── enums/
│   ├── model/
│   ├── repository/          # XxxRepository interfaces
│   └── usecase/
├── injection/                 # appModule, DatabaseModule, NetworkModule, …
├── ui/
│   ├── component/             # CoreTopBar, CoreBottomBar, rate bottom sheet, …
│   ├── fragment/
│   │   ├── home/              # HomeFragment, HomeViewModel, HomeUiState, components
│   │   └── setting/           # Setting + language screens and components
│   ├── modifier/
│   ├── theme/
│   └── util/                  # NavigationUtil, PermissionUtil, NotificationUtil, LocaleManager, error/, …
└── util/                      # Small app-level helpers (if any)
```

> When you add **new shared** extensions, prefer **`common`** (e.g. `common.extensions`); keep **UI-only** helpers under **`ui/util`**, per project rules.

## Naming and style (short checklist)

- **Repositories:** `XxxRepository` in `domain/repository/` → `XxxRepositoryImpl` in `data/repository/impl/`.
- **Remote:** `XxxApi` in `data/remote/api/`, `XxxDto` in `data/remote/dto/` (e.g. post-related DTOs under `dto/post/…`), paths in **`ApiPath.kt`** using **`AppConfig`** base URLs.
- **New API flow:** *Api + DTO + mapper + domain repository + impl +* register in **`NetworkModule`** and **`RepositoryModule`** (and **`ViewModelModule`** for new ViewModels).
- **Network:** wrap calls in **`safeApiCallFlow`**; expose **`Resource<T>`**; in UI use **`Throwable.toUiMessage(context)`** when you have `Context`.
- **ViewModels:** inject repositories via Koin **`by viewModel()`**; keep screen state in **`XxxUiState`**; expose flows/`StateFlow`; in Composables use **`collectAsState()`**; update with **`_uiState.value = _uiState.value.copy(...)`** (do **not** use `MutableStateFlow.update { copy(...) }` for this project).
- **Lambdas:** use **explicit** lambdas (e.g. full `defaultValue = { … }` bodies)—avoid “hidden” one-liner lambdas where the rule calls for clarity.
- **Context:** only in **UI** or **`ui/util`**—not in domain/data layers.

## Requirements

- **Android Studio** with a current AGP (Kotlin 2.x, Compose BOM, `compileSdk` as in `app/build.gradle.kts`).
- **minSdk** 24 (see `app/build.gradle.kts`).

## Getting started

1. Clone the repository.  
2. Open in Android Studio and sync Gradle.  
3. Run the **app** configuration on an emulator or device.

## API base URL (JSONPlaceholder)

`API_BASE_URL` comes from **`BuildConfig`**:

- **Debug:** `http://jsonplaceholder.typicode.com/` — reduces TLS trust issues on some emulators or inspected networks; cleartext is scoped to that host in `res/xml/network_security_config.xml`.  
- **Release:** `https://jsonplaceholder.typicode.com/`.

Update the `buildConfigField` entries in **`app/build.gradle.kts`** when you switch to your own backend.

## Cursor / AI rules (`.cursor/rules/`)

| File | Focus |
|------|--------|
| **`android-skeleton-project.mdc`** | Purpose, documentation, README + **package tree**, naming, tech stack, Clean Architecture, API conventions. **Highest priority.** |
| **`kotlin-android-patterns.mdc`** | CoreFragment / CoreLayout, `collectAsState`, `Resource`, no Context in domain/data, Koin module registration. |

---

*Conventions and documentation style: **Phong-Kaster**.*
