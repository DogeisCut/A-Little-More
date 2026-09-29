
> [!NOTE]
> Some of this is mildly outdated with the mod itself. Some information is also missing (such as crafting recipes, or how some things work) so that they can be experimented with in the mod itself.

"A Little More" is a 1.21.1 NeoForge focusing vanilla+ style additions in DogeisCut's (my) style!

While there isn't a specific theme here, the goal is to expand the Minecraft world in interesting ways, and to ensure these mechanics are connected to both vanilla mechanics and each other to avoid feeling out of place and uninteresting.
# Features
## Opossums
they drop string and rarely opossum tails which can be brewed into potions of immunity, disabling the ability to get any new effects while that effect is active.

On low health or attacked, opossums will play dead, much like axolotls. They will play dead until regenerating to max health.

Opossums will go after berry bushes much like foxes. They will also hold and consume any food items given to them.

Baby opossums will ride nearby adult opossums, making a stack. Baby opossums will only leave the stack if: the possum below it leaves the stack, any of the opossums (including the parent) are damaged, are being lured with food, or if they grow up.

Opossums are immune to the withering (or poison, or all effects. I haven't decided) effect.

Also undecided, will be scrapped for the first release if proven to be too hard, opossums can climb up certain blocks (like logs) and hang from certain blocks by the tail (like leaves or chains).
## Celerium
A fictional sea-foam colored ore following a theme of "speed." Celerium ore can only be mined with iron tools and above. Celerium armor increases the wearer's movement speed by a small amount. Celerium tools are iron-tier but mine blocks much faster than iron. A Celerium sword is equal to a stone sword in terms of damage but has almost no swing cooldown. All Celerium also increase movement speed by a small margin when held.

Celerium blocks also increase movement speed when stepped on. Celerium can be combined with pistons to make a dash pad, touching a speed pad will increase player speed dramatically for a few seconds after touching it and will not consume hunger while sprinting on it either. It will also force the player forward and sprinting for the duration of the effect.

Celerium tools also have a reduced damage cooldown (handled through a new attribute)
## Item Imbuing 
With an imbuing template, it's possible to "imbue" an item (any item) with the trait of a mineral, or potentially other resources, at a smithing table. Only one imbuement can exist for a given item and attempting to add a new one replaces the old one. 

All imbuements stack throughout multiple items (for instance, multiple ghast tear imbuements on each piece of armor for a full set gives you 4 extra hearts.). Imbuements should be designed with this in mind. This does not mean one item can stack multiple of the same imbuements, you can't even apply more than one imbuement to a single item, and imbuing a stack of items will only give you one instance of the effect when held.

Imbuements should also be mildly designed as sidegrades, of course more expensive imbuement materials should be better, but wood shouldn't become useless as soon as you get netherite for example.

For armor (and other wearable items, like carved pumpkins or wolf armor), imbuements apply when worn. Other items just apply when held (in the offhand or otherwise).

With an expensive recipe (currently undecided, will likely be a multi-step recipe), it's possible to make an imbuement badge, which applies its effects as long as its somewhere in the inventory. Imbuement badges only stack to one (the item itself that is, not its effects).

| Resource      | Template                    | Effect                                                                                                                  |
| :------------ | --------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| Wood          | Imbuement Template          | Longer entity and block reach.                                                                                          |
| Cobblestone   | Imbuement Template          | Knockback resistance.                                                                                                   |
| Copper        | Imbuement Template          | TODO                                                                                                                    |
| Gold          | Imbuement Template          | Increased luck and cheaper trades. Affects all possible loot tables it can.                                             |
| Iron          | Imbuement Template          | Armor increase on wearer/hold                                                                                           |
| Celerium      | Imbuement Template          | Slight movement speed increase on wearer/hold                                                                           |
| Diamond       | Imbuement Template          | Doubles durability/uses of an item. On regular items, it instead makes them impossible to destroy in dropped item form. |
| Netherite     | Imbuement Template          | A really small amount of a bunch of stuff.                                                                              |
| Redstone Dust | Advanced Imbuement Template | TODO (Maybe something with actual redstone use...?)                                                                     |
| Ghast Tear    | Advanced Imbuement Template | Gives an additional heart                                                                                               |
| Flint         | Advanced Imbuement Template | Taking damage ignites the attacker, longer for more.                                                                    |
| Seepite Shard | Advanced Imbuement Template | TODO                                                                                                                    |

Imbuement templates can be crafted, but advanced Imbuement templates can only be found in loot chests.
## Seep
A strange opaque magenta/periwinkle fluid found in underground pockets within The End. Touching it inflicts Levitation III.

These pockets will generate as long deep caverns filled with Seep to the bottom of the island, sometimes even making endstone spikes on the surface or the bottom to contain the seep. These caverns generate one block below the surface and rarely expose the seep.

Curiously, items sink fast in Seep. Some items even react with the stuff, transforming into new items if submerged long enough. Seep crystals grow at the bottom, when mined directly, drops as Seepite shards. Sometimes Seep crystals contain various rare items, preserving what's left of what the end used to be, dropping those instead. Breaking the support block or unsubmerging these crystals destroys them, dropping nothing. If only you could get down there to mine and collect things directly...

- Ender Pearl -> Enseepened Pearl: Acts exactly like an ender pearl, but will not deal fall damage or spawn ender mites.
- Seep Torch -> A periwinkle torch with a dark purple base
- Cobblestone -> Endstone
- Stone -> Seepstone
- Music Disc (Any) -> Music Disc (Just A Little More)
- Amethyst Shard -> Seep Crystal
- Amethyst Cluster -> Seep Crystal Cluster
- TODO: More Item transformations

When Seep mixes with lava, Seepstone is created, a periwinkle building block able to be crafted and stonecut into many decorative forms. Can be combined with a speed pad to create a launch pad, sending the player forwards and up, depending on the Redstone signal given to the bottom (height) and the side (distance).

A bucket of Seep can be mixed with Seepite shards to create a pass-through-able block (Seepslime) that forces creative flight while inside. 

Items rendered in Seep Crystals have their rotation randomized based on the cords of the block.

Imbuement badges and Imbuement Templates require Seep Crystals to craft.
## Shady Dealer
A cloaked creature (bipedal opossum?) found only in the most dangerous overworld locations. The darkest caves, Ancient Cities, abandoned villages, and a new structure: Horrendous Hideout. Much like a villager, the shady dealer will trade with you. The shady dealer however has great deals, accepting just a few Emerald *nuggets* for valuable items. 

Careful about how long you take though, as they might just disappear, and you'll have to face what they've been hiding from...

## Flail
A new weapon, crafted with Celerium, a unique handle, and something similar to a heavy core. Hold right click to swing a spike-ball around yourself, release to launch the spike-ball in your direction, stopping only on blocks and piecing all mobs with high damage.

Enchants include:
- **Multiplication**: Adds another spike-ball for each level
- **Impact**: Spike-ball hitting a wall will release an AOE shockwave. Increasing in size and base damage percent per level.
- **Orbit**: Swing charge time for max speed is increased per level.
- **Inertia**: Flail has a re-action force that moves the player too.
## Music Disc
A brand new music disc containing a track composed just for this mod by yours truly!
## Advancements
Several advancements for interacting with features in the mod.
- **Rabies Free**: Craft and drink a potion of immunity, and dodge an effect.
- **Skull Crusher**: Obtain a flail.
- **Black Market**: Trade with the Shady Dealer.
- **Swimming in Secrets**: Swim in Seep while under the effects of Immunity.
- **Running Around at The Speed of Sound**: Obtain maximum possible speed via Celerium.
- **A Quick Sprint**: Use a dash pad.
- **Around The World**: Be under the effects of a dash pad for over a minute.
- **Catch Me!**: Use a flail to suspend yourself in the air by catching it behind blocks.
- **Minor Enhancements**: Use any imbuement template.
- **Weightless Flight**: Swim in Seepslime.
- **Upgrades, People**: Transform an item in Seep.
- **Player Pinball**: Use a launch pad.
- **Erased History**: Mine a Seep crystal with something in it.