# Drawers

A recreation of Functional Storage drawers in PaperMC

## Building

To build the plugin, run

```sh
mvn package
```

## Usage

1. Install plugin jar into your `plugins/` folder
2. Restart your server

## TODO (First release)

- [x] Max capacity
- [x] Explosion breaks block
- [x] Breaking drops contents
    - [x] Breaking on the sides should work
    - [x] Breaking from the front should drop items
- [x] No way to open gui, no matter what
- [x] Vertical drawers working
- [x] Config for max capacity
- [x] Save new drawers into a data file (just location)
- [x] Recipe supports all plank types (either a tag or manually for
  every type)
- [x] Drop drawer on block break
- [x] Can remove items after drawer is empty

## TODO (future)

- [ ] Upgrades (i.e., iron, diamond, etc)
- [ ] Double right click to add all in current player inventory
- [ ] Void Upgrade (cactus)
- [ ] Upgrade GUI
- [x] Infinite drawers
- [ ] Cleanup command to delete all drawers and their summoned display
  entities

## Known Bugs

- [ ] When breaking drawers in creative, the item still drops
