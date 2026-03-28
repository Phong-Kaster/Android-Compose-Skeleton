# Android Compose Skeleton

A **reusable Android app skeleton** you can copy into new projects. It favors clear layers, Koin wiring, and conventions that are easy to extend rather than one-off shortcuts. Project-specific AI guidance lives in [`.cursor/rules/android-skeleton-project.mdc`](.cursor/rules/android-skeleton-project.mdc); keep this README updated when you add notable packages or modules.

## Tech stack

| Area | Choice |
|------|--------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| DI | Koin |
| Navigation | Navigation Component with fragments; Compose screens inside fragments |
| Network | Ktor client |
| Local | Room, DataStore |
| Async / API shape | Flows wrapped with `safeApiCallFlow`; UI uses `Result<T>` (`Loading` / `Success` / `Error`) in [`common`](app/src/main/java/com/example/skeleton/common) |

Configuration: API base URL and timeouts are driven from [`core.config.AppConfig`](app/src/main/java/com/example/skeleton/core/config/AppConfig.kt) and `BuildConfig.API_BASE_URL` (see `app/build.gradle.kts`). Shared app constants (e.g. datastore name) live in [`common.Constant`](app/src/main/java/com/example/skeleton/common/Constant.kt).

## Requirements

- **JDK 11** (matches `compileOptions` / `jvmTarget` in the app module)
- **Android Studio** with a recent AGP (see `gradle/libs.versions.toml`)
- **minSdk 24**, **targetSdk / compileSdk 36** (see `app/build.gradle.kts`)

## Getting started

1. Clone the repository and open the root folder in Android Studio.
2. Ensure `local.properties` points to your Android SDK (Studio usually creates this for you).
3. Build a debug APK:

   ```bash
   ./gradlew :app:assembleDebug
   ```

   On Windows:

   ```bat
   gradlew.bat :app:assembleDebug
   ```

4. Run the `app` configuration on an emulator or device.

The sample **Posts** flow uses [JSONPlaceholder](https://jsonplaceholder.typicode.com/); debug builds use HTTP for that host (see `network_security_config` and debug `buildConfigField`).

## Project structure

```
Android-Compose-Skeleton/
├── .cursor/
│   └── rules/
│       └── android-skeleton-project.mdc   # Conventions & stack (highest-priority project rules)
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── java/com/example/skeleton/     # Package root: com.example.skeleton
│       │   ├── MainActivity.kt            # Hosts NavHostFragment via CoreActivity
│       │   ├── MainApplication.kt         # Application + Koin start
│       │   ├── common/                    # Constant, Language, Result, type aliases
│       │   │   ├── Constant.kt
│       │   │   ├── Language.kt
│       │   │   ├── Result.kt
│       │   │   └── Typealias.kt
│       │   ├── core/                      # CoreActivity / Fragment / Layout, AppConfig, extensions
│       │   │   ├── CoreActivity.kt
│       │   │   ├── CoreFragment.kt
│       │   │   ├── CoreLayout.kt
│       │   │   ├── config/
│       │   │   │   └── AppConfig.kt
│       │   │   └── extension/
│       │   │       ├── collection/
│       │   │       │   ├── AdvancedCollectionExtension.kt
│       │   │       │   └── BasicCollectionExtension.kt
│       │   │       ├── date_and_time/
│       │   │       │   ├── DateExtension.kt
│       │   │       │   └── LocalDateExtension.kt
│       │   │       ├── flow/
│       │   │       │   └── FlowExtension.kt
│       │   │       ├── null_safety/
│       │   │       │   ├── CollectionNullSafetyExtension.kt
│       │   │       │   └── NullSafetyExtension.kt
│       │   │       ├── number/
│       │   │       │   ├── NumberFormattingExtension.kt
│       │   │       │   └── RangeAndBoundaryExtensions.kt
│       │   │       └── string_validation/
│       │   │           ├── AdvancedStringValidationExtension.kt
│       │   │           └── BasicStringValidationExtension.kt
│       │   ├── data/                      # Remote, mappers, repository impls, Room, DataStore
│       │   │   ├── database/local/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── Migration.kt
│       │   │   │   ├── converter/
│       │   │   │   │   └── DateConverter.kt
│       │   │   │   ├── dao/
│       │   │   │   │   ├── PostDao.kt
│       │   │   │   │   └── UserActionDao.kt
│       │   │   │   └── entity/
│       │   │   │       ├── PostEntity.kt
│       │   │   │       └── UserActionEntity.kt
│       │   │   ├── datastore/
│       │   │   │   └── SettingDatastore.kt
│       │   │   ├── mapper/
│       │   │   │   ├── PostMapper.kt
│       │   │   │   └── UserActionMapper.kt
│       │   │   ├── remote/
│       │   │   │   ├── api/
│       │   │   │   │   ├── ApiPath.kt
│       │   │   │   │   ├── PostApi.kt
│       │   │   │   │   └── WeatherApi.kt
│       │   │   │   ├── dto/
│       │   │   │   │   └── PostDto.kt
│       │   │   │   └── util/
│       │   │   │       └── safeApiCallFlow.kt
│       │   │   └── repository/impl/
│       │   │       ├── PostRepositoryImpl.kt
│       │   │       ├── SettingRepositoryImpl.kt
│       │   │       └── UserActionRepositoryImpl.kt
│       │   ├── domain/                    # Models, repository interfaces, enums (no Android/data deps)
│       │   │   ├── enums/
│       │   │   │   └── BottomBarDestination.kt
│       │   │   ├── model/
│       │   │   │   ├── Post.kt
│       │   │   │   └── UserAction.kt
│       │   │   └── repository/
│       │   │       ├── PostRepository.kt
│       │   │       ├── SettingRepository.kt
│       │   │       └── UserActionRepository.kt
│       │   ├── injection/               # Koin modules
│       │   │   ├── AppModule.kt
│       │   │   ├── DatabaseModule.kt
│       │   │   ├── DatastoreModule.kt
│       │   │   ├── LocaleModule.kt
│       │   │   ├── NetworkModule.kt
│       │   │   ├── RepositoryModule.kt
│       │   │   └── ViewModelModule.kt
│       │   └── ui/                      # Fragments, ViewModels, Compose, theme, util
│       │       ├── component/
│       │       │   ├── CoreBottomBar.kt
│       │       │   ├── CoreBottomSheet.kt
│       │       │   ├── CoreTopBar.kt
│       │       │   ├── CoreTopBar4.kt
│       │       │   ├── LifecycleComposable.kt
│       │       │   └── ratebottomsheet/
│       │       │       ├── RateBottomSheet.kt
│       │       │       └── RateOption.kt
│       │       ├── fragment/
│       │       │   ├── home/
│       │       │   │   ├── HomeFragment.kt
│       │       │   │   ├── HomeUiState.kt
│       │       │   │   ├── HomeViewModel.kt
│       │       │   │   └── component/
│       │       │   │       ├── HomePermissionBottomSheet.kt
│       │       │   │       └── HomeRequestPermission.kt
│       │       │   └── setting/
│       │       │       ├── SettingFragment.kt
│       │       │       ├── SettingUiState.kt
│       │       │       ├── SettingViewModel.kt
│       │       │       ├── component/
│       │       │       │   └── SettingItem.kt
│       │       │       └── language/
│       │       │           ├── SettingLanguageFragment.kt
│       │       │           └── component/
│       │       │               └── LanguageItem.kt
│       │       ├── modifier/
│       │       │   └── Shadow.kt
│       │       ├── theme/
│       │       │   ├── Color.kt
│       │       │   ├── Theme.kt
│       │       │   └── Type.kt
│       │       └── util/
│       │           ├── AppUtil.kt
│       │           ├── LocaleManager.kt
│       │           ├── LogUtil.kt
│       │           ├── NavigationUtil.kt
│       │           ├── NetworkUtil.kt
│       │           ├── PermissionUtil.kt
│       │           ├── RateUtil.kt
│       │           ├── SystemBarUtil.kt
│       │           └── error/
│       │               └── UiErrorMapper.kt
│       └── res/                           # Layouts, navigation graph, themes, strings
├── gradle/
│   ├── libs.versions.toml                 # Version catalog
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts                    # rootProject.name = Android-Compose-Skeleton
├── gradle.properties
├── gradlew / gradlew.bat
└── README.md
```

### Naming conventions (summary)

- Repository: `XxxRepository` in `domain/repository`, `XxxRepositoryImpl` in `data/repository/impl`.
- Remote: `XxxApi` under `data/remote/api/`, `XxxDto` under `data/remote/dto/`, paths in `ApiPath.kt`.
- Prefer **full lambda bodies** (no hidden single-expression lambdas where the project rule expects clarity).
- **ViewModel state:** assign with `_uiState.value = _uiState.value.copy(...)` (not `update { copy(...) }` helpers), per project rules.

For heavy domain work, prefer **use cases** with explicit names (e.g. `CreateDailyNotificationUseCase`).

## Contributing / extending

When you add a new API surface: extend `ApiPath`, add `*Api` + DTOs + mappers, repository interface + impl, and register bindings in `NetworkModule` and `RepositoryModule`. Surface errors in the UI with `Throwable.toUiMessage(context)` from the UI error utilities.

After adding packages or modules that others should discover, **update this README** so the structure stays accurate.
