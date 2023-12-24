package net.jeserkon.dislocatormod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class ReturnStoneItem extends Item {
    private Random rand = new Random();
    public ReturnStoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (pLevel instanceof ServerLevel && pPlayer.canChangeDimensions() && !pPlayer.isOnPortalCooldown()) {
            ResourceKey<Level> resourcekey = pLevel.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel) pLevel).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            }

            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;

            BlockPos blockPos = serverPlayer.getRespawnPosition();

            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

            if (serverPlayer.getRespawnDimension() != pLevel.dimension() ) {
                ResourceKey<Level> resourcekey1 = serverPlayer.getRespawnDimension();
                serverlevel = ((ServerLevel)pLevel).getServer().getLevel(resourcekey1);
                pPlayer.teleportTo(serverlevel, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), RelativeMovement.ALL, pPlayer.getYRot(),pPlayer.getXRot());
            }
            pLevel = ((ServerPlayer) pPlayer).serverLevel();

            //If not already set a spawnpoint
            if (blockPos == null) {
                for (int k = pLevel.getMaxBuildHeight() - 1; k > pLevel.getMinBuildHeight(); --k) {
                    BlockPos blockPos1 = new BlockPos(0, k, 0);
                    BlockState blockstate = pLevel.getBlockState(blockPos1);
                    if (blockstate.isCollisionShapeFullBlock(pLevel, blockPos1) && (!blockstate.is(Blocks.BEDROCK))) {
                        blockPos = blockPos1;
                        pPlayer.teleportTo(blockPos.getX(),k+1,blockPos.getZ());
                        pPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100));
                        pPlayer.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100));
                        break;
                    }
                }
            } else {
                pPlayer.teleportTo(blockPos.getX(),blockPos.getY()+1,blockPos.getZ());
            }

            pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer,
                    player -> player.broadcastBreakEvent(player.getUsedItemHand()));

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        pPlayer.setPortalCooldown();

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.dislocatormod.respawn_stone.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}

