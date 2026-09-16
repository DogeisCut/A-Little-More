package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.weapons.flail.FlailItem;
import io.github.dogeiscut.a_little_more.registry.ALMItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ALMItemModelProvider extends ItemModelProvider {

    private final List<String> missingTextures = new ArrayList<>();

    public ALMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ALMItems.ITEMS.getEntries().forEach(holder -> auto(holder.get()));

        if (!missingTextures.isEmpty()) {
            ALittleMore.LOGGER.warn(
                    "[datagen] Skipped {} item model(s) with no texture yet: {}",
                    missingTextures.size(), String.join(", ", missingTextures));
        }
    }

    public void auto(Item item) {
        if (item instanceof BlockItem) return;

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "item/" + id.getPath());

        if (!existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
            missingTextures.add(texture.toString());
            return;
        }

        if (isHandheld(item)) {
            handheldItem(item);
        } else {
            basicItem(item);
        }
    }

    private boolean isHandheld(Item item) {
        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof FlailItem;
    }

    public void generated(String name, ResourceLocation texture) {
        withExistingParent(name, mcLoc("item/generated")).texture("layer0", texture);
    }

    public void handheld(String name, ResourceLocation texture) {
        withExistingParent(name, mcLoc("item/handheld")).texture("layer0", texture);
    }

    public void parented(Item item, String parent) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        withExistingParent(path, mcLoc(parent))
                .texture("layer0", modLoc("item/" + path));
    }
}
