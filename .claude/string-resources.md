# String Resources
> Always-on. Portable across Android projects. Applies to every `<string>` in `res/values*/strings.xml`
> and every `stringResource(R.string.…)` / `getString(R.string.…)` in code.

---

## The one rule: the name IS the text

The key is the words of the English text, lowercase, joined by `_`. No more, no less.

```xml
<string name="easy">Easy</string>
<string name="check">Check</string>
<string name="download">Download</string>
<string name="leave_lesson">Leave lesson?</string>
<string name="image_saved_to_gallery">Image saved to your gallery</string>
```

Never invent a different name for words that already mean something:

| ❌ Don't | ✅ Do |
|---|---|
| `listening_tier_easy` → Easy | `easy` |
| `tier_easy` → Easy | `easy` |
| `listening_check` → Check | `check` |
| `my_creations_download` → Download | `download` |
| `my_creations_download_success` → Image saved to your gallery | `image_saved_to_gallery` |
| `leave_lesson_title` → Leave lesson? | `leave_lesson` |
| `praise_great` → Great! | `great` |

- No screen / feature prefix (`listening_`, `home_`, `settings_`, `my_creations_`).
- No role suffix (`_title`, `_label`, `_button`, `_message`, `_text`, `_desc`).
- Drop punctuation: `Great!` → `great`, `Leave lesson?` → `leave_lesson`.
- Format strings are named by their words too: `%d sets` → `number_sets`, `Back to %1$s` → `back_to`.
- A long sentence keeps its words: `Great job! You finished this set` → `great_job_you_finished_this_set`.

---

## One text, one key

Before adding a string, search for its English text in the default `values/strings.xml`:

```bash
grep -n '>Easy<' app/src/main/res/values/strings.xml
```

- **Found** → use that key. Never add a second key with the same text, even for a new screen or feature.
- **Found under a wrong name** → still reuse it; do not copy it. Renaming it is a separate change.
- **Not found** → add it (below).

Duplicates drift: the same English text ends up translated two different ways in the same language,
and a fix to one copy never reaches the other.

---

## Adding a new string

1. Append it to the **end** of `values/strings.xml`.
2. Add the same key to **every** `values-*/strings.xml` the project ships, appended at the end. A key
   missing from a locale silently falls back to English.
3. Translate UI text only (buttons, tags, labels, messages). Learning or user content (words, questions,
   names that come from content files) is not a string resource.
4. `translatable="false"` only for brand names, proper nouns and symbols — never for buttons, tags or
   labels.
5. Escape `'` as `\'`, `&` as `&amp;`, `<` as `&lt;`.

---

## Never rename

- `testTag("…")` values — UI tests find views by them.
- DataStore / SharedPreferences / database keys — they are saved on users' phones.
- A string key in the same change that adds a feature. Renaming existing keys is its own change:
  update the key in **every** `values*/strings.xml` and every `R.string.` reference together.

---

## Check before done

```bash
# no two keys with the same English text (prints the duplicated texts; [] means clean)
python -c "import re,collections;s=open('app/src/main/res/values/strings.xml',encoding='utf-8').read();c=collections.Counter(re.findall(r'<string name=\"[^\"]+\"[^>]*>(.*?)</string>',s));print([t for t,n in c.items() if n>1])"
```

- [ ] Every new key is its own English text in snake_case.
- [ ] No new key repeats the text of an existing one.
- [ ] Every new key exists in every `values-*/strings.xml`.

---

## @author Phong-Kaster
