# AI-Assisted Honor of Kings Information Management System

## 1. Project Overview

A Java OOP-based console application for managing players, heroes, equipment, teams, and match records for Honor of Kings. This project is submitted as part of the Java Coursework 2026, with a focus on responsible AI use and transparent development process.

## 2. How to Run

```bash
# Compile
javac -d out src/**/*.java src/*.java

# Run
java -cp out Main
```

## 3. Default Login Accounts

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Player (12 accounts) | player1–player15 | pass123 |

All 15 player accounts share the same password.

## 4. Implemented Features

- [x] Player Lookup (by ID or name, shows heroes + equipment)
- [x] Team Overview (members, avg level, win rate, top player)
- [x] Hero Details (type, stats, equipment, owners)
- [x] Equipment Statistics (rank by score or usage count)
- [x] Match History (last N by player or team)
- [x] Leaderboard (win rate, level, matches, custom score)
- [x] Data Management (Admin CRUD for players/heroes/equipment/teams/matches)
- [x] Authentication (Login/Logout with role-based menus)
- [x] File Persistence (auto-save/load with Java serialization)

## 5. Java Concepts Used

| Concept | Where Used |
|---------|------------|
| Inheritance | Person → Player, Admin |
| Interface | Searchable, Authenticatable |
| Collections | ArrayList, HashMap |
| Exception Handling | File I/O, Input Validation |
| File I/O | Data persistence |
| Enums | HeroType, MatchResult, EquipmentType |
| Encapsulation | Private fields with getters/setters |
| Polymorphism | Person references for Player/Admin |

## 6. AI Usage Summary

See [ai/prompts.md](ai/prompts.md), [ai/agent-log.md](ai/agent-log.md), and [ai/reflection.md](ai/reflection.md).

## 7. Testing Summary

See [docs/test-cases.md](docs/test-cases.md).

## 8. Known Limitations

- Console-based interface (GUI is extra credit)
- Data persistence via Java serialization (binary .dat file)
- No database integration
- Player self-edit limited to name, username, and password
