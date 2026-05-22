# Compose Fragment Preview
> Loaded when working in any file under `ui/fragment/`. Use together with `android-skeleton-project.md` (highest priority) and `figma-design-system.md`.

---

## Always add @Preview for layout composables

When creating or modifying a fragment that uses Jetpack Compose, always add a `@Preview` composable for the layout composable so it can be previewed in Android Studio.

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun XxxLayoutPreview() {
    XxxLayout(
        uiState = XxxUiState(
            isLoading = false,
            listOfItems = listOf(
                XxxModel(id = 1, name = "Sample item"),
            ),
        ),
    )
}
```

- Preview the layout composable (e.g. `XxxLayout`), not the fragment itself.
- Use sample / default UI state with realistic data.
- Place the preview at the end of the file, after the layout composable.
- Wrap with `MaterialTheme { ... }` if your composable relies on Material primitives like `HorizontalDivider`.
- Write multiple `@Preview(name = "...")` when there are meaningful UI states to cover — at minimum: loaded, empty/loading, and any prominent error or edge-case state.

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
val shareEnabled = latestItem != null
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
val shareEnabled = latestItem != null
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

Pre-declare a color `val` **only** when the same value appears in 3+ places within the same composable — otherwise write it inline.

**Do not:**
```kotlin
@Composable
fun XxxCard(
    item: XxxModel?,
    modifier: Modifier = Modifier,
    onHeaderClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val cardShape = RoundedCornerShape(24.dp)
    val primaryBlue = Color(0xFF2F70BC)
    val textPrimary = Color(0xFF091222)
    val borderGray = Color(0xFFE6E7E9)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, shape = cardShape, color = borderGray)
            .background(color = Color.White, shape = cardShape),
    ) {
        // content
    }
}
```

**Do this:**
```kotlin
@Composable
fun XxxCard(
    item: XxxModel?,
    modifier: Modifier = Modifier,
    onHeaderClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val primaryBlue = Color(0xFF2F70BC)  // justified: used in 3+ places inside this composable

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, shape = RoundedCornerShape(24.dp), color = Color(0xFFE6E7E9))
            .background(color = Color.White, shape = RoundedCornerShape(24.dp)),
    ) {
        // content
    }
}
```

---

## Text

For single-line labels in constrained-width containers (navigation bars, list item titles, card headers) — add `maxLines = 1` and `Modifier.basicMarquee(Int.MAX_VALUE)` so long text scrolls instead of truncating:

```kotlin
Text(
    text = ...,
    style = ...,
    maxLines = 1,
    modifier = Modifier.basicMarquee(Int.MAX_VALUE),
)
```

Do not apply to paragraph text, error messages, button labels, or any `Text` where wrapping is the correct behavior.

---

## Documentation in composables

Write document short enough that a person can understand in 2 seconds after reading.

**Good but not recommended (too long):**
```kotlin
/**
 * When this is **true** (even rows), the **left** tile is the narrow shape and the **right** is the wide shape.
 * When **false** (odd rows), the layout swaps: left becomes wide, right becomes narrow. This creates a zig-zag
 * grid pattern across rows...
 */
val isEvenRow = rowIndex % 2 == 0
```

**Do this:**
```kotlin
/**
 * isEvenRow answers: should the left tile be the narrow shape this row?
 */
val isEvenRow = rowIndex % 2 == 0
```

---

## @author Phong-Kaster
