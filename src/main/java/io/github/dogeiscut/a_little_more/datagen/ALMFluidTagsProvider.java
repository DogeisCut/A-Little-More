package io.github.dogeiscut.a_little_more.datagen;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMFluids;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ALMFluidTagsProvider extends FluidTagsProvider {

    public ALMFluidTagsProvider(@NotNull PackOutput output,
                                @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ALittleMore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ALMTags.Fluids.SEEP)
                .add(ALMFluids.SEEP.still().get())
                .add(ALMFluids.SEEP.flowing().get());
        tag(ALMTags.Fluids.C_SEEP)
                .add(ALMFluids.SEEP.still().get())
                .add(ALMFluids.SEEP.flowing().get());
    }
}
