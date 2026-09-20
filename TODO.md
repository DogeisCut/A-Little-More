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
  - [ ] scaffold-like custom block sounds
  - [X] Weird culling when rendering thin faces.
  - [X] figuring out how the hell to make the item model work properly
  - [ ] fix block breaking cracks rendering under the patterns
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
    - [X] Stamping Table block, menu and screen (pattern block/dye/pattern item/result, pattern grid, face preview, cube preview)
    - [ ] Stamping Table art: block textures are placeholders, and the GUI is drawn with fills instead of a texture
      - ideally stuff like the scrolling Ui would look like the actual loom.
    - [X] Stamping Table recipe, and a way to obtain the base pattern block
    - [X] Base color: a blank face has to be dyed first, which sets its base color, before patterns can be added
    - [X] Way to re-dye the base color of a face that already exists
      - eh im deciding not to bother with this. You can already copy faces for free so having one sink isnt a big deal
    - [X] Pattern block item model doesn't show faces, so the Stamping Table slots only show the empty base (see above)
      - [X] kinda want to make holding shift show the backside faces in the item itself
    - [ ] Actual proper slot icons (like the loom) instead of the weird fake item thing
    - [ ] Stamping table custom sounds (For modifications, instead of just using the loom ones)
    - [ ] some sort of blanked out default state (for when there's no blocks or items in it at all) like the loom
    - [X] decide if removing a face or pasting one should move the pattern blocks to the output slot
      - current problem is the output slot actually duplicates the input slot with whatever edit you want to make
and currently pasting or removing a face is an instant action
        - but also i can see this getting annoying
    - [ ] Buttons for rotating/flipping all the patterns across the whole block (as if you rotated the block)
      - i could also make the block itself rotatable but idk if i want to do that since your facing direction alone cant
    - [ ] make banner pattern items reduce the patterns to only 1, with it being force selected, like the loom.
cover all orientations (6 cardinal directions, with 4 rotations in each)
  - [ ] make them drop with patterns (currently mining a pattern block drops a blank one)
  - [ ] fix break/mine particles being colored
  - [ ] optimize the block and items themselves by storing faces/patterns in a world-saved table that the blocks/items reference instead of direct patterns
    - this is just so a chunk of entierly the same pattern block isnt storing 9450940 copies of the same data
  - IDEA: allowing multiple dyes to be used on a single pattern for mixed colors, similar to armor dying
    - don't know what this would look like in the gui though. (accepts 9 dyes at once instead of 1? or maybe just 5? 3?)
    - also this would require changing `PatternBlockFaces` to just store a decimal color (`0xFFFFFF`, etc) instead of a dyecolor
      - so ideally i'd implement the above optimization first.
    - ALTERNATE IDEA: opacity slider for layers?
    - ALTERNATE ALTERNATE IDEA: more dyes
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