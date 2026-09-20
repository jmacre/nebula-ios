# NEBULA

A fast-paced arcade space shooter for Android and iOS, built solo in Java on libGDX. I started it in September 2022 and have maintained it since: 294 commits over roughly four years, currently at v2.6.3 (Android versionCode 51).

<!-- TODO: add a screenshot or short gameplay GIF here, e.g. ![Gameplay](docs/gameplay.gif) -->

**Download:** [Google Play](https://play.google.com/store/apps/details?id=jm.games.nebula) · App Store: _TODO: add link_

<!-- TODO (optional): add real download / rating numbers once you've checked the consoles -->

## What it is

You fly a ship through waves of enemy ships, eyebats and laser traps, collect power-ups (missiles, a beam weapon, bombs, an hourglass), and earn gems to spend in an in-game shop on new ships and bullet skins. Gems can also be earned by watching a rewarded ad or bought as an in-app purchase.

I built it to see one project all the way through: design, gameplay tuning, art integration, store releases on two platforms, monetization, and ongoing maintenance as the platforms changed underneath it.

## Tech stack

| Area | Choice |
| --- | --- |
| Language / framework | Java, [libGDX](https://libgdx.com/) 1.13.5 |
| Platforms | Android (minSdk 21, targetSdk 35) and iOS (RoboVM 2.3.20) |
| Build | Gradle multi-module (`core`, `android`, `ios`) |
| Audio | miniaudio (via `games.rednblack.miniaudio`) |
| Graphics | Sprite sheets and animations, custom GLSL shaders (`android/assets/shader`), FreeType fonts |
| Monetization | AdMob rewarded ads, in-app purchases through gdx-pay (Google Play Billing 6.1.0 on Android, StoreKit on iOS) |
| Telemetry | Firebase Crashlytics and Analytics |
| Persistence | libGDX `Preferences` for high score, gems, unlocked items and settings |

## Architecture

```
nebula-ios/
├── core/      Game logic, rendering, UI, assets. About 8k lines of Java, shared by both platforms.
├── android/   AndroidLauncher, AdMob and Play Billing wiring, assets, manifest, Gradle config.
└── ios/       IOSLauncher, RoboVM config, Info.plist, StoreKit and ads wiring.
```

All gameplay lives in `core`. The launchers are thin. Anything that has to touch a platform SDK, such as showing a rewarded ad, goes through one interface, `IActivityRequestHandler`, which each launcher implements. `core` never imports an Android or iOS SDK class, so the game code has a single implementation and each platform only owns its own integration.

Rough map of `core/src/jm/games/nebula`:

- `Main` — app entry point, screen management, asset loading.
- `MainMenu` and `GameInterface` — title screen, shop, HUD and purchase flow.
- `MainGame` — the game screen: spawning, update loop, rendering, power-up handling.
- `Enemy`, `Bullet`, `ItemDrop`, `Explosion`, and the `*Pool` classes — game entities and their object pools.
- `Collision` — hit detection.
- `Assets` and `Prefs` — asset registry and local persistence.

## Key technical decisions

**Object pooling for anything spawned during play.** Bullets, enemy bullets, enemies, explosions and item drops each have a libGDX `Pool` (`BulletPool`, `EnemyPool`, and so on). On mobile the garbage collector is what turns heavy fire into dropped frames, so entities are reused rather than allocated per shot.

**One platform seam.** The `IActivityRequestHandler` interface described above keeps ad and other SDK code out of `core`. Adding a platform-specific feature means adding a method to the interface and implementing it twice.

**Lightweight collision.** Hit detection uses libGDX geometry (`Rectangle`, `Polygon`, `Intersector`) with a cheap nearby-check first, rather than a full physics engine. A shooter like this needs overlap tests, not simulation, so I skipped the cost and complexity of Box2D.

**Shaders for feedback.** Enemies flash when hit (`enemy_blink`) and the screen inverts while the hourglass power-up is active (`invert`), both done with small GLSL programs instead of extra sprite variants.

**ID-based shop and progression.** Ships, bullet skins and gem balances are persisted through `Prefs` and referenced by IDs, so the shop UI and save data share one representation.

## Release history highlights

- **Sept 2022:** project started. Early commits already cover moving from `java.util.ArrayList` to libGDX `Array` and adding a nearby-check to collision and an explosion pool, all aimed at cutting allocations and per-frame work.
- **Jan 2023:** in-app purchases and rewarded ads added (1.1.x).
- **2024:** 2.0 and 2.1 (iOS fixes), through 2.3.
- **June 2025:** 2.6 series, including faster enemy laser animation, power-up dial fixes, and separate sprites for the beam and enemy bullets.
- **April 2026 (v2.6.3):** Google Play now requires 16 KB page-size support and a higher target API. I moved to targetSdk 35 and packaged native libraries uncompressed (`useLegacyPackaging = false` in `android/build.gradle`) to keep the app listed.

## Known limitations and what I'd do next

- `MainGame.java` grew into a large class that owns spawning, update, rendering and input for the game screen. Given more time I would split it into small systems (spawner, collision, renderer, power-up manager) and add unit tests around the spawner and collision logic, which are the most testable pieces.
- Some Firebase modules (Auth, Database, Storage) are declared in `android/build.gradle` from an earlier online-leaderboard idea that was never shipped. Only Crashlytics and Analytics are used. I'd remove the unused ones.
- There are no automated tests or CI yet. The first thing I would add is a headless libGDX test for the pooling and collision code plus a GitHub Actions build.
- iOS releases trail Android by a little (the iOS project config is still at 2.6.2).

## Building

You need a JDK, the Android SDK for the Android target, and macOS with Xcode for the iOS target (RoboVM).

```bash
# Android debug build
./gradlew android:assembleDebug

# iOS (from macOS)
./gradlew ios:launchIPhoneSimulator
```

You will also need your own `local.properties` pointing at the Android SDK. AdMob unit IDs and Firebase config in this repo belong to the published app; swap in your own if you fork it.
