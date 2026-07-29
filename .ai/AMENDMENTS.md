# AMENDMENTS

> Log of every plan mutation. Tier 1 entries are applied and logged here; Tier 2/3 are proposed via escalation and logged on approval.

<!-- Newest first. Format: date · tier · what changed · why. -->

- 2026-07-17 · Tier 1 · T-001 verification plan narrowed: DoD c3's denied-permission runtime check is reclassified from "machine (adb)" to "human decision required" (E-003). All other criteria machine-verified this iteration. · The only attached device (physical API 35, OEM-restricted) rejects `pm grant/revoke` and reverts `appops` denial from shell — no adb path to a permission-denied state exists; no emulator capability is granted. The DoD itself allows "manual/adb check", so the manual half is escalated rather than the DoD amended.

- 2026-07-17 · Tier 1 · T-001 split across iterations: implementation (done, iteration 2) vs. verification (build + adb, next iteration). · The human-granted capabilities cannot take effect until the human materializes the ledger files (`knowledge/capabilities.json`, `.ai/capabilities.json`) — the Runtime denies engine writes to them by design, and permissions are fixed per invocation. Escalated as E-002.
