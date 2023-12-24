package net.jeserkon.dislocatormod.datagen;

import net.jeserkon.dislocatormod.DislocatorMod;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DislocatorMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

}
