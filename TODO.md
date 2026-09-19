- [ ] Seep Effects (particles and sounds)
    - [ ] Swimming Sounds
    - [ ] Splash Sounds
        - [ ] Small
        - [ ] Big
    - [ ] Splash Particles
    - [ ] Bucket sounds
    - [ ] Underseep Sounds
    - [X] Underseep particles
    - [ ] Submerge sounds
    - [ ] Resurface sounds
    - [ ] Flowing sounds
    - [X] Ambient particles (I imagine seep to be bubbly?)
    - [ ] Ambient sounds
    - Worth noting Create doesn't even bother with all this for Chocolate and Honey so if I can't get it working,
      I can save it for a future update.
- [ ] Seep bucket dispenser behavior
- [ ] Seep cauldron
- [ ] Under seep overlay
- [ ] Figure out wait repairing celerium tools/armor doesn't show up in JEI
- [ ] Fix Create support for Bulk Seeping not showing up in JEI
- [ ] Datagen
    - [X] Fix block families
    - [ ] whatever the hell is going on with armor trims
- [ ] ALMTags namespacing
- [ ] Seepstone family assets
- [ ] Opossums
- [ ] Shady dealers
- [ ] Horrendous Hideout
- [ ] Worldgen
    - [ ] Seep
    - [X] Celerium
- [ ] Item Imbuing
- [X] Music (for music disc)
    - [X] Music Disc
- [ ] Advancements
- [ ] Fix whatever the hell is going on with Celerium trims (missing premutations? not making those by hand...)
- [ ] Flail
- [ ] Speed Pad
    - [ ] Speed Pad Effect
- [ ] Launch Pad
- [ ] Custom Celerium block sounds
- [ ] Custom Seepstone block sounds
- [ ] Make seep drag entities more (like water)
- Celerium Textures
    - [ ] Replace armor placeholders
    - [ ] Adjust random pixels on axe texture
    - [X] Ores
- [ ] Seep Crystals
- [ ] Celerium Horse Armor
- [ ] Fix Creative inventory
    - in an awful order
    - Could probably benfit from multiple tabs
    - missing immunity potions/arrow from A Little More tab
- [ ] Pattern Block
  - [X] Visible pattern seams on edge of block.
  - [X] Weird culling when rendering thin faces.
  - [ ] figuring out how the hell to make the item model work properly
  - [X] Handling rotation (currently it extends pillar block, which is like logs and stuff, but this makes the rotation of the side faces ambigious)
    - > Current idea is to make it store an explicit "facing" direction instead, on top of a 4 way rotation for that facing direction. But it gets confusing when you place it sideways cause like... how will it know which way to rotate it?
  - [ ] making the chunk re-render if the block data changes 
  - [X] making the patterns data driven (ideally they'd read from banner patterns and try to pull a texture, but use a missing texture when there's none)
  - [X] Considering making pattern blocks more advanced:
    - > I have one last idea actually
      instead you crafting these in a loom
      you just make the base pattern block
      and click each face with a banner that has the patterns you want
      and then there's a recipie to apply a pattern from one pattern block to an empty one
  - [ ] Crafting (new gui block?)
# TASKS

- [ ] Various TODOs within files
    - TODOs in the following files: `ALittleMoreClient`, `SeepFanProcessingType`, `ALMJEI`,
      `SeepTransformationRecipeCategory`,
      `EnseepenedPearlEntity`, `FluidInteractionsEvents`, `TargetHurtTimeEvents`, `ALMArmorMaterials`, `ALMAttributes`,
      `ALMItems`, `Render3DHelper`, and `ALMTags`.
- [ ] Item Tagging
- [ ] Block Tagging
- [ ] File Organization/Cleanup (the goal is to have files for the same feature in the same folder, and that one file
  has one tasks)
- [ ] Loot Tables (UGHHHHHH)