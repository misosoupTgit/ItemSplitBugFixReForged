# ItemSplitBugFix: ReForged

A Minecraft mod (Fabric / Forge / NeoForge) that fixes the item-stack split/copy bug caused by **empty CustomData / empty NBT** tags preventing stacks from merging correctly.

Based on an analysis of [iwaliner](https://github.com/iwaliner)'s [Item Split Bug Fix](https://github.com/iwaliner), this ReForged edition ports the fix across many Minecraft versions and loaders. Unlike the original, it is intended for **active, ongoing updates**.

Compared to the legacy edition, the features themselves have not evolved. What changed is the toolchain: combining Architectury API with Stonecutter made multi-version and multi-loader support far easier, which in turn makes active ongoing development practical.

Maintained as **ReForged** by MisoPy.

## How It Works

Some items end up with an empty data payload (`DataComponents.CUSTOM_DATA` on 1.20.5+, or an empty NBT compound on older versions). Vanilla then treats otherwise-identical stacks as different, so split / copy / slot transfers misbehave.

This mod injects into common `ItemStack` / `Slot` paths and **strips empty custom data** before those operations, restoring normal stacking behavior.

| MC range | Detection / fix |
|---|---|
| &lt; 1.20.5 | Empty NBT → `setTag(null)` |
| ≥ 1.20.5 | Empty `CUSTOM_DATA` → remove component |

## Blacklist

Items that legitimately need empty (or quirky) data can be excluded via config.

Path: `config/itemsplitbugfixreforged-common.toml` (created on first launch)

Supports wildcards (`*`), for example:

- `minecraft:diamond` — exact match
- `*:diamond*` — any namespace containing `diamond`
- `minecraft:*` — entire `minecraft` namespace

Default entries cover known problematic / intentional cases (e.g. Alex's Caves, Hexerei, Spelunkery, spyglass, Technical Cores).

## Compatibility

- Works on both client and dedicated server (fix is applied wherever stacks move)
- Uses mixins on vanilla `ItemStack` / `Slot` only — no inventory UI rewrite
- Nearby patch versions share one jar (declared via `publish.additionalVersions`)

## Requirements

- Minecraft **1.16.5 ~ 26.1.2** (Fabric / Forge / NeoForge — see matrix below)
- **Architectury API** (required on all loaders)
- **Fabric API** on Fabric only (required by Architectury Fabric; this mod does not call Fabric API directly)

### Version matrix (compile targets)

| Loader | Primary versions |
|---|---|
| Fabric | 1.16.5 … 26.1.2 |
| Forge | 1.17.1 … 1.19.4 (**Forge 1.20.1 excluded**) |
| NeoForge | 1.20.4 … 26.1.2 |

Notes:

- Forge 1.16.5 is not supported by the current LegacyForge toolchain
- NeoForge covers 1.20.2+ (via 1.20.4+ targets + additional versions)
- Patch neighbors (e.g. 1.21.6 / 1.21.7 with the 1.21.8 jar) are not built separately

## License

MIT — see [LICENSE](LICENSE).
