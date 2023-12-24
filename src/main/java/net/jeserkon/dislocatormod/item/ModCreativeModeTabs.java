package net.jeserkon.dislocatormod.item;

import net.jeserkon.dislocatormod.DislocatorMod;
import net.jeserkon.dislocatormod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DislocatorMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DISLOCATORMOD_TAB = CREATIVE_MODE_TABS.register("dislocatormod_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.UNIVERSAL_RESPAWNANCHOR_BLOCK.get()))
                    .title(Component.translatable("creativetab.dislocatormod_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.DISLOCATOR_BLOCK.get());
                        pOutput.accept(ModBlocks.UNIVERSAL_RESPAWNANCHOR_BLOCK.get());

                        pOutput.accept(Moditems.RETURN_STONE.get());
                        pOutput.accept(Moditems.ESCAPE_ROPE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
