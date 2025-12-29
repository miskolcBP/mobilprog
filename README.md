# Bucket Buddy

A simple Android CRUD application for managing a personal bucket list.  
Users can add goals, mark them as completed, edit details, and delete entries.  
The app is built using **Kotlin**, **Jetpack Compose**, and **Room (SQLite)**, following modern Android development practices.

---

## Features

### Core Functionality
- Create bucket list items
- Edit existing items
- Delete items
- Mark items as completed
- View active and completed items separately

### Data Rules & Validation
- All items start as **uncompleted**
- `targetDate` is **mandatory** and validated (`yyyy-MM-dd`)
- `priority` is restricted to values **1–5**
- Completion date is stored automatically when an item is marked completed

### UI / UX
- Jetpack Compose UI (Material 3)
- Dark color scheme
- Card-based list layout
- Segmented toggle for switching between Active / Completed items
- Empty-state UI when no items exist
- Modal dialog for adding and editing items

---

## Tech Stack

| Layer | Technology |
|------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Architecture | MVVM |
| Persistence | Room ORM (SQLite) |
| Async | Coroutines |
| Build | Gradle (KTS) |

---

## Architecture Overview

The app follows **MVVM (Model–View–ViewModel)**:

UI (Compose)
↓
ViewModel
↓
Repository
↓
Room DAO
↓
SQLite

yaml
Copy code

### Key Components

#### Entity
`BucketItem` represents a single bucket list entry.

Fields:
- `id` (Primary Key)
- `title`
- `description`
- `category`
- `targetDate`
- `priority`
- `status` (completed / not completed)
- `completedAt` (nullable)

---

#### DAO (Data Access Object)
Defines all database operations:
- Insert
- Update
- Delete
- Query active items
- Query completed items

---

#### Database
Room database with versioning and migration support.

Migration added:
- **v1 → v2**: adds `completedAt` column

---

#### ViewModel
- Holds UI state
- Launches coroutines via `viewModelScope`
- Separates business logic from UI
- Handles add / edit / delete / complete actions

---

## UI Structure

### Main Screen
- Top app bar with app title
- Segmented toggle:
  - **Active** items
  - **Completed** items
- LazyColumn displaying cards
- Floating Action Button (`+`) for adding items

### Cards
Each bucket item is displayed as a card containing:
- Title
- Description
- Metadata (category, priority, target date)
- Action icons:
  - Edit
  - Delete
  - Complete (active view only)

### Add / Edit Dialog
- Shared dialog for adding and editing items
- Pre-filled fields in edit mode
- Validation before saving

---

## Validation Logic

### Target Date
- Required
- Must match `yyyy-MM-dd`
- Parsed using `LocalDate.parse()` to ensure validity

### Priority
- Numeric
- Range enforced between 1 and 5

Validation is handled **at the UI layer**, not in the database.

---

## Design Decisions

- **Jetpack Compose** chosen for modern declarative UI
- **Room ORM** used for safe SQLite access
- Dates stored as `String` for simplicity (ISO-8601 format)
- Completion date stored separately to preserve item history
- Material 3 components used for modern look & accessibility
- Dark theme enforced for consistent visual identity

---

## Known Limitations / Future Improvements

- Replace text-based date input with a date picker
- Convert database queries to `Flow` for reactive updates
- Add sorting (by priority or date)
- Add search/filter functionality
- Optional cloud sync or backup

---

## Setup Instructions

1. Clone the repository
2. Open in Android Studio (Hedgehog or newer recommended)
3. Ensure Gradle sync completes
4. Run on emulator or physical device (API 26+)

---

## Requirements

- Android Studio with Kotlin 1.9+
- Android SDK 26+
- Gradle (KTS)
- No external services required

---

## License

This project is for educational purposes.  
No license restrictions.