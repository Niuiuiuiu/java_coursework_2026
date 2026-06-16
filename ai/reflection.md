# Reflection on AI-Assisted Development

Answer the following 10 questions honestly after completing the project.

---

## 1. Which AI tools or models did you use?

I used **Claude Code** (Anthropic Claude) as my primary AI assistant, accessed through the VSCode extension. I used it in three distinct roles:

- **Architect Agent** — for designing class structure, planning interfaces, and reviewing OOP design
- **Implementation Agent** — for writing Java methods, service classes, and the console menu system
- **Testing/Reviewer Agent** — for finding bugs, reviewing data consistency, and suggesting fixes

I also used **GitHub** (remote repository) and **Git** (local version control) as required by the coursework.

---

## 2. Which prompt was the most useful? Why?

The most useful prompt was when I asked the AI to "阅读一下requirement，我这个程序还有什么需要做的" (read the requirements and tell me what I still need to do). The AI produced a detailed gap analysis comparing every section of the requirement PDF against my actual codebase, identifying exactly what was missing: interfaces, File I/O, team size requirements, test cases, reflection, and git evidence.

This was useful because it gave me a clear priority-ordered checklist. Instead of guessing what to work on next, I had a concrete plan with 12 action items. It also caught things I had completely overlooked, like the requirement that each team must have ≥5 players and that git-history.txt must be included.

---

## 3. Which AI-generated suggestion was wrong, incomplete, or misleading?

When the AI first wrote `InputHelper.java`, the `readString()` method rejected all empty input — it looped until the user typed something non-empty. Later, when the AI wrote `readOptionalString()` and `readOptionalInt()` in `Main.java`, it called `input.readString("")` internally, assuming it would allow empty inputs. This was contradictory — the "leave blank to keep current value" hint could never actually work because `readString()` would never return an empty string.

The Testing/Reviewer Agent caught this bug during code review. The fix was adding a dedicated `readOptionalString()` method to `InputHelper` that uses `scanner.nextLine()` directly without the empty-input loop.

---

## 4. How did you check whether AI-generated code was correct?

I used three methods:

1. **Compilation check** — I ran `javac` after every significant change. If the code didn't compile, I knew something was wrong immediately.
2. **Manual testing** — I ran the program as both Admin and Player, tested each menu option, tried edge cases (searching for non-existent players, entering invalid input, deleting connected records).
3. **Code review** — I read through every class and asked the AI to explain each method and keyword I didn't understand (recorded in prompts.md). This forced me to verify that each piece of code made logical sense, not just that it compiled.

I also used the Testing/Reviewer Agent to independently review the code and found 7 issues — confirming that AI-generated code needs human verification.

---

## 5. What bugs did you fix yourself instead of asking AI to fix?

I personally identified and asked the AI to help fix the following:

- The `addMatch()` method stored match records but never updated team `totalMatches`/`totalWins` — team win rates would stay frozen at their initial hardcoded values forever
- The `subList()` call in `getMatchesByTeam()` returned a live view instead of a defensive copy, which could break if someone later modified the result list

Additionally, I noticed during manual testing that the "leave blank" prompts didn't actually work, which the AI confirmed and fixed.

---

## 6. What Java concept did you understand better after using AI?

I understood **interfaces** much better. Before this project, I thought interfaces were just "a list of abstract methods." Through the AI's explanations and seeing `Authenticatable` and `Searchable` integrated into my own code, I understood that:

- An interface is a **contract** — it defines what methods must exist but not how they work
- `implements` means the class promises to fulfill the contract; the compiler enforces this
- `default` methods (like `List.sort()`) let interfaces provide shared implementations so every implementing class doesn't have to rewrite the same code
- Functional interfaces (one abstract method only) enable lambda expressions like `(a, b) -> ...`

I also gained deeper understanding of `lambda expressions`, `Comparator`, `HashMap.put/get/getOrDefault`, `StringBuilder`, `try-catch`, and the CRUD pattern through repeated Q&A sessions recorded in prompts.md.

---

## 7. What Java concept are you still unsure about?

I am still not fully confident about **polymorphism in practice**. I understand the definition — using a superclass or interface reference to point to different subclass objects — but I only used it lightly in this project (storing `currentUser` as `Person`, implementing `Searchable` on `SearchService`). I would need more practice with scenarios where polymorphism is the main design strategy rather than just a supporting feature.

I also want to understand **multi-threading** and **JUnit testing** better — neither was used in this console-based project, but they are important Java skills for larger applications.

---

## 8. Did AI make the project easier, harder, or both? Explain.

**Both.**

Easier because: the AI sped up tedious work — writing boilerplate code (model classes, CRUD methods, menu switch cases), generating the dataset with realistic Honor of Kings heroes and equipment, and formatting output strings. It also served as a 24/7 Java tutor — when I forgot what `put()` does or how `compare()` works, I could ask immediately without searching through textbooks.

Harder because: using AI responsibly meant extra work — I had to record every prompt in prompts.md, document agent roles in agent-log.md, verify every piece of generated code, write a reflection, and maintain git evidence. This transparency took more time than just coding alone. I also had to learn to phrase prompts precisely — vague prompts got useless responses, and specific prompts took practice to write well.

Overall, the AI made this project possible within the time I had. But the documentation and verification requirements meant I couldn't just "copy-paste" — I had to actually understand the code.

---

## 9. Which parts of the final project were mainly written by you?

- The `plan.md` structure and content — I decided the overall architecture, class relationships, and development timeline with AI feedback
- The `DataInitializer.java` dataset — AI suggested hero/equipment names, but I verified the team sizes, stat distributions, and data consistency
- The `prompts.md` records — I curated every prompt entry, writing the "My Decision" section personally for each
- Bug fixes — I identified the `readOptionalString` UX bug and the `addMatch` stats sync gap through manual testing, then directed the AI to fix them
- The final review of all code — I read every class and method, asking questions about anything I didn't understand

---

## 10. Which parts were mainly generated or heavily assisted by AI?

- The 7 model classes (`Person`, `Player`, `Admin`, `Hero`, `Equipment`, `Team`, `MatchRecord`) — AI generated the full implementation based on my plan
- The 3 enum classes (`HeroType`, `MatchResult`, `EquipmentType`)
- The service layer — `GameDataManager`, `SearchService`, `RankingService`, `FileStorageService`
- The console menu system in `Main.java` — the extensive switch-case structure with 20+ menu methods was AI-generated
- The `InputHelper` utility class
- The test case templates in `docs/test-cases.md`
- The README, plan, and design documentation — AI provided initial drafts that I reviewed and modified

However, for all AI-generated parts, I reviewed the code, asked questions about every unfamiliar keyword (recorded in 27 prompt entries), compiled and tested after each change, and directed specific fixes when bugs were found. No code was accepted without understanding it first.
