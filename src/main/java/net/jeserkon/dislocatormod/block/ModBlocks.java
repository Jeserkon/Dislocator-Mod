package net.jeserkon.dislocatormod.block;

import net.jeserkon.dislocatormod.DislocatorMod;
import net.jeserkon.dislocatormod.block.custom.DislocatorBlock;
import net.jeserkon.dislocatormod.block.custom.UniversalRespawnAnchorBlock;
import net.jeserkon.dislocatormod.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DislocatorMod.MOD_ID);

    public static final RegistryObject<Block> DISLOCATOR_BLOCK = registerBlock("dislocator_block",
            () -> new DislocatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> UNIVERSAL_RESPAWNANCHOR_BLOCK = registerBlock("universal_respawnanchor_block",
            () -> new UniversalRespawnAnchorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return Moditems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
