# Compose Fragment Preview
> Loaded when working in any file under `ui/fragment/`. Use together with `android-skeleton-project.md` (highest priority) and `figma-design-system.md`.

---

## Always add @Preview for layout composables

When creating or modifying a fragment that uses Jetpack Compose, always add a `@Preview` composable for the layout composable so it can be previewed in Android Studio.

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun SettingPrayerLayoutPreview() {
    SettingPrayerLayout(
        uiState = SettingPrayerUiState(
            madhhabIndex = 0,
            selectedMethodName = "University of Islamic Sciences, Karachi",
            methodItems = listOf(
                PrayerMethodInfo("Method name", "Fajar Angle: 18°, Isha Angle: 18°"),
            ),
        ),
    )
}
```

- Preview the layout composable (e.g. `SettingPrayerLayout`), not the fragment itself.
- Use sample / default UI state with realistic data.
- Place the preview at the end of the file, after the layout composable.
- Wrap with `MaterialTheme { ... }` if your composable relies on Material primitives like `HorizontalDivider`.
- Write multiple `@Preview(name = "...")` when there are meaningful UI states to cover (loaded, empty, loading). See `HomeFragment` (2 previews), `QuranFragment` (5 previews), `CompassFragment` (3 previews). Match that energy.

---

## val/var placement in composables

Do not declare `val`/`var` in the middle of composable content. Declare all of them at the top.

**Do not:**
```kotlin
HorizontalDivider(
    modifier = Modifier.fillMaxWidth(),
    thickness = 1.dp,
    color = ColorBorderSubtle,
)

val shareLabel = stringResource(R.string.share)
val shareEnabled = latestDua != null
val shareColor = if (shareEnabled) {
    ColorTextPrimary
} else {
    ColorTextPrimary.copy(alpha = 0.38f)
}
Row(
    modifier = Modifier.fillMaxWidth()
)
```

**Do this:**
```kotlin
val shareLabel = stringResource(R.string.share)
val shareEnabled = latestDua != null
val shareColor = if (shareEnabled) {
    ColorTextPrimary
} else {
    ColorTextPrimary.copy(alpha = 0.38f)
}

HorizontalDivider(
    modifier = Modifier.fillMaxWidth(),
    thickness = 1.dp,
    color = ColorBorderSubtle,
)
Row(
    modifier = Modifier.fillMaxWidth()
)
```

---

## When to extract a val vs. write inline

Only extract a `val`/`var` when computation or if-else is needed to decide the value. Otherwise write the value straight at its position. Applies to all composable attributes: `stringResource`, `Color`, `RoundedCornerShape`, `TextStyle`, `Boolean`, and similar.

**Do not:**
```kotlin
val emptyLabel = stringResource(R.string.not_empty)
val color = if (isEmpty) Color.Red else Color.Blue

Text(
    modifier = modifier,
    color = color,
    text = emptyLabel,
    style = customizedTextStyle(
        fontSize = 14,
        fontWeight = 400,
        lineHeight = 20,
        color = ColorTextPrimary,
    ),
)
```

**Do this:**
```kotlin
val color = if (isEmpty) Color.Red else Color.Blue  // needs if-else, so val is justified

Text(
    modifier = modifier,
    color = color,
    text = stringResource(R.string.empty),
    style = customizedTextStyle(
        fontSize = 14,
        fontWeight = 400,
        lineHeight = 20,
        color = ColorTextPrimary,
    ),
)
```

---

## Do not pre-declare colors at the top of a composable

If a color value does not need computation, write it inline at its position.

**Do not:**
```kotlin
@Composable
fun DuasOfTheDayCard(
    dua: Duas?,
    modifier: Modifier = Modifier,
    onHeaderClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val cardShape = RoundedCornerShape(24.dp)
    val primaryBlue = Color(0xFF2F70BC)
    val textPrimary = Color(0xFF091222)
    val borderGray = Color(0xFFE6E7E9)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = cardShape,
        border = BorderStroke(width = 1.dp, color = borderGray),
    )
}
```

**Do this:**
```kotlin
@Composable
fun DuasOfTheDayCard(
    dua: Duas?,
    modifier: Modifier = Modifier,
    onHeaderClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val primaryBlue = Color(0xFF2F70BC)  // only kept because it is used in multiple places inside this composable

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE6E7E9)),
    )
}
```

---

## Text

By default, always add `maxLines = 1` and `modifier = Modifier.basicMarquee(Int.MAX_VALUE)` for single-line `Text`:

```kotlin
Text(
    text = ...,
    style = ...,
    maxLines = 1,
    modifier = Modifier.basicMarquee(Int.MAX_VALUE),
)
```

---

## Documentation in composables

Write document short enough that a person can understand in 2 seconds after reading.

**Good but not recommended (too long):**
```kotlin
/**
 * When this is **true** (row 0, 2, 4…), the **left** tile is the narrow "square" (it grows with [Modifier.weight])
 * and the **right** tile is the wide "rectangle" (fixed [200.dp]). When **false** (row 1, 3, 5…), we **swap**:
 * left becomes the wide rectangle and right becomes the square. So the grid zig-zags...
 */
val squareFirst = rowIndex % 2 == 0
```

**Do this:**
```kotlin
/**
 * squareFirst answers: is the left position a square shape or a rectangle shape?
 */
val squareFirst = rowIndex % 2 == 0
```

---

## @author Phong-Kaster
