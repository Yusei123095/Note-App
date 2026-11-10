# My Note App

A simple Android app for writing notes and setting alarm reminders, built with Kotlin and Jetpack Compose.

<!-- Screenshots: add 2–3 images here (home list, note editor, alarm editor) -->
<!-- <img src="docs/home.png" width="250"> <img src="docs/editor-note.png" width="250"> <img src="docs/editor-alarm.png" width="250"> -->

## Features

- **Notes** — write a note with a title and body text
- **Alarms** — create a reminder with a memo and a date/time (calendar + clock picker)
- **One editor for both** — switch between *Note* and *Alarm* while creating; the type is locked when editing
- **Validation** — errors appear after the first save attempt, then update live as you type
  - Notes need body text
  - Alarms need a selected date and a time in the future
- **Edit existing items** — open any note or alarm from the home list and update it
- **Home list** — notes and alarms shown together, newest first, updated automatically when data changes

## Tech stack

| Area | Library / tool |
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Navigation | Navigation Compose (type-safe routes) |
| State | ViewModel, `StateFlow`, Kotlin Coroutines |
| Local database | Room (with KSP) |
| Dates & times | `java.time` |

## Architecture

The app follows **unidirectional data flow**: the UI sends events to a ViewModel, the ViewModel updates a single UI state, and the UI renders that state.

```
UI (Compose screens)
  │  events ↓            ↑ state (StateFlow)
ViewModel
  │  calls ↓             ↑ results / Flow
Repository
  │
Room (DAO + database)
```

- **Editor screen** — `EditorViewModel` holds an `EditorUiState` (`Loading` / `Ready`). The `Ready` state contains the mode (create / edit), the selected type, and separate `NoteForm` and `AlarmForm` drafts. Validation errors are calculated from the forms rather than stored.
- **Home screen** — `HomeViewModel` combines the note and alarm `Flow`s from Room with `combine` + `stateIn`, so the list stays in sync with the database.
- **Repositories** catch database exceptions and return a `SaveResult` (`Success` / `NotFound` / `Failure`) so the UI layer never deals with exceptions directly.

### Project structure

```
com.yu.my_note_app
├── data/
│   ├── note/        Note entity, NoteDao, NoteRepository
│   ├── alarm/       Alarm entity, AlarmDao, AlarmRepository
│   ├── SaveResult.kt
│   └── AppDatabase.kt
├── ui/
│   ├── components/  Shared composables (text field, pager, header)
│   ├── home/        Home screen and HomeViewModel
│   ├── editor/      Editor screen, EditorViewModel, forms, timer input
│   └── theme/       Colors, typography, theme
├── util/            Date/time helpers
└── MainActivity.kt
```

## Design notes

- **Times are stored as epoch milliseconds (`Long`).** Each timestamp is a single moment in time, so it shows correctly in whatever time zone the device is in, and it can be passed straight to `AlarmManager`.
- **IDs are UUID strings.** IDs can be created on the device before saving, and they won't collide if the app later syncs with a remote database.
- **The Material 3 `DatePicker` works in UTC.** Its selected date is UTC midnight, so the app converts with `ZoneOffset.UTC` when talking to the picker, and with the device time zone everywhere else.

## Getting started

### Requirements

- Android Studio (latest stable)
- Android device or emulator running Android <!-- min version, e.g. 8.0 (API 26) -->

### Run

1. Clone the repository
   ```bash
   git clone <!-- repository URL -->
   ```
2. Open the project in Android Studio and let Gradle sync
3. Select a device and press **Run**

## Roadmap

- [ ] Schedule alarms with `AlarmManager` and show notifications
- [ ] Handle exact-alarm and notification permissions
- [ ] Re-schedule alarms after device reboot
- [ ] Swipe to delete from the home list
- [ ] Markdown formatting (headings, bold) in notes
- [ ] Multi-language support with string resources
- [ ] Cloud sync with a remote database

## License

<!-- Choose a license, e.g. MIT -->
