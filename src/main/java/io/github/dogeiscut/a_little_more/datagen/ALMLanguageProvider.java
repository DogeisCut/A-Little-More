package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ALMLanguageProvider extends LanguageProvider {

    private static final Map<String, String> OVERRIDES = new HashMap<>();

    static {
        OVERRIDES.put("celerium_block", "Block of Celerium");
        OVERRIDES.put("music_disc_just_a_little_more", "Music Disc");
    }

    public ALMLanguageProvider(PackOutput output, String locale) {
        super(output, ALittleMore.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        ALMItems.ITEMS.getEntries().forEach(holder -> autoName(holder.get()));
        ALMBlocks.BLOCKS.getEntries().forEach(holder -> autoName(holder.get()));

        addStaticEntries();
    }

    private void autoName(Item item) {
        if (item instanceof net.minecraft.world.item.BlockItem) return;
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        add(item, OVERRIDES.getOrDefault(path, titleCase(path)));
    }

    private void autoName(Block block) {
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        add(block, OVERRIDES.getOrDefault(path, titleCase(path)));
    }

    public static String titleCase(String path) {
        String[] words = path.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.isEmpty()) continue;
            if (i > 0) sb.append(' ');
            if (i > 0 && SMALL_WORDS.contains(w)) {
                sb.append(w);
            } else {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1).toLowerCase(Locale.ROOT));
            }
        }
        return sb.toString();
    }

    private static final java.util.Set<String> SMALL_WORDS =
            java.util.Set.of("of", "the", "a", "an", "in", "at", "to", "and");

    private void addStaticEntries() {
        add("itemGroup." + ALittleMore.MOD_ID, "A Little More");
        add("tooltip." + ALittleMore.MOD_ID + ".imbued_with", "Imbued With:");

        add("fluid." + ALittleMore.MOD_ID + ".seep", "Seep");
        add("fluid." + ALittleMore.MOD_ID + ".flowing_seep", "Flowing Seep");

        add("attributes." + ALittleMore.MOD_ID + ".target_hurt_time", "Target Hurt Time");

        add("effect." + ALittleMore.MOD_ID + ".immunity", "Immunity");
        add("effect." + ALittleMore.MOD_ID + ".dashing", "Dashing");
        potionNames("immunity", "Immunity");

        add("enchantment." + ALittleMore.MOD_ID + ".multiplication", "Multiplication");
        add("enchantment." + ALittleMore.MOD_ID + ".impact", "Impact");
        add("enchantment." + ALittleMore.MOD_ID + ".orbit", "Orbit");
        add("enchantment." + ALittleMore.MOD_ID + ".inertia", "Inertia");

        add("entity." + ALittleMore.MOD_ID + ".opossum", "Opossum");
        add("entity." + ALittleMore.MOD_ID + ".shady_dealer", "Shady Dealer");
        add("entity." + ALittleMore.MOD_ID + ".spike_ball", "Spike Ball");
        add("entity." + ALittleMore.MOD_ID + ".enseepened_pearl", "Enseepened Pearl");

        add("jukebox_song." + ALittleMore.MOD_ID + ".just_a_little_more", "DogeisCut - Just A Little More");
        add("trim_material." + ALittleMore.MOD_ID + ".celerium", "Celerium");

        add(ALittleMore.MOD_ID + ".recipe.seep_transformation", "Seep Transformation");
        add(ALittleMore.MOD_ID + ".recipe.seep_transformation.seep", "Dropping an Item Into Seep");
        add(ALittleMore.MOD_ID + ".recipe.fan_seeping", "Bulk Seeping");
        add(ALittleMore.MOD_ID + ".recipe.fan_seeping.fan", "Fan behind Seep");

        advancement("rabies_free", "Rabies Free",
                "Craft and drink a Potion of Immunity, and dodge an effect.");
        advancement("skull_crusher", "Skull Crusher", "Obtain a Flail.");
        advancement("black_market", "Black Market", "Trade with a Shady Dealer.");
        advancement("swimming_in_secrets", "Swimming in Secrets",
                "Swim in Seep while under the effects of Immunity.");
        advancement("running_around_at_the_speed_of_sound", "Running Around at The Speed of Sound",
                "Obtain the maximum possible speed through Celerium.");
        advancement("a_quick_sprint", "A Quick Sprint", "Use a Dash Pad.");
        advancement("around_the_world", "Around The World",
                "Be under the effects of a Dash Pad for over a minute.");
        advancement("catch_me", "Catch Me!",
                "Use a Flail to suspend yourself in the air by catching it behind a block.");
        advancement("minor_enhancements", "Minor Enhancements", "Use any Imbuement Template.");
        advancement("weightless_flight", "Weightless Flight", "Swim in Seepslime.");
        advancement("upgrades_people", "Upgrades, People", "Transform an item in Seep.");
        advancement("player_pinball", "Player Pinball", "Use a Launch Pad.");
        advancement("erased_history", "Erased History", "Mine a Seep Crystal with something in it.");
    }

    public void potionNames(String effect, String name) {
        add("item.minecraft.potion.effect." + effect, "Potion of " + name);
        add("item.minecraft.splash_potion.effect." + effect, "Splash Potion of " + name);
        add("item.minecraft.lingering_potion.effect." + effect, "Lingering Potion of " + name);
        add("item.minecraft.tipped_arrow.effect." + effect, "Arrow of " + name);
    }

    public void advancement(String id, String title, String description) {
        add("advancement." + ALittleMore.MOD_ID + "." + id + ".title", title);
        add("advancement." + ALittleMore.MOD_ID + "." + id + ".desc", description);
    }
}
