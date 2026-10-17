# SupermarketTogehterXRAK

The repository's main project is now a Unity 6 first-person supermarket simulator prototype for Windows.

## Unity game
Open the [SupermarketTogehterXRAK project folder](SupermarketTogehterXRAK/). It generates a 3D supermarket at runtime with shelves, colorful product packs, refrigerators, checkout islands, a stock room, shopper NPCs, lighting, first-person movement and a basic host/join multiplayer menu.

- Unity version: 6000.0.40f1
- Controls: WASD, mouse look, Shift sprint, E interact, Escape cursor
- Multiplayer transport: Unity Netcode for GameObjects + Unity Transport (UDP 7777)
- Windows build workflow: [build-windows-game.yml](.github/workflows/build-windows-game.yml)

See [the game README](SupermarketTogehterXRAK/README.md) for setup and current limitations. This is an early playable prototype; economy/NPC state is not yet server-authoritative, and the generated product models are placeholders for a future PBR art pass.

## Legacy Android project
The original Android project remains under the existing Gradle files and app/ folder. It has not been deleted.
