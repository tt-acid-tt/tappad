# SupermarketTogehterXRAK
Unity 6 first-person 3D supermarket co-op prototype for Windows. The shop is procedurally generated at runtime: shelf aisles, colorful grocery packs, coolers, checkout counters, stock cartons, plants, warm lighting and a warehouse zone.

## Open
1. Install Unity Hub and Unity 6000.0.40f1 or compatible Unity 6.
2. Open this folder as a Unity project and allow Package Manager to resolve dependencies.
3. Press Play. The environment and front menu are created at runtime.

## Controls
- WASD: move; mouse: look; Left Shift: sprint; E: interact; Escape: release cursor.
- Green stations restock; checkout markers process sales; warehouse cartons add stock; terminals order deliveries and upgrade the store.

## Multiplayer
The menu includes host and join buttons using Unity Netcode for GameObjects + Unity Transport on UDP 7777. Host and client need network reachability. This first version wires player hosting/joining and player prefab spawning; the economy and shoppers are currently local prototype systems and need server-authoritative state replication for production-quality co-op.

## Windows build
Use Unity Build Profiles to build Standalone Windows x86_64. The repository workflow can build a Windows player when the repository has Unity license secrets configured: UNITY_LICENSE, UNITY_EMAIL and UNITY_PASSWORD.

## Scope
This is a playable prototype foundation, not a finished commercial-scale game. The visual environment is procedural geometry; replace stand-in product blocks with authored PBR models, add audio/animations and bake lighting for a polished release.
