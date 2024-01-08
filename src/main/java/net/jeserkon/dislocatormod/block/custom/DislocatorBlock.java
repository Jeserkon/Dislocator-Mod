
package net.jeserkon.dislocatormod.block.custom;

import net.jeserkon.dislocatormod.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.Random;


import java.util.List;


public class DislocatorBlock extends Block {
    private int DislocatorTPTargetX;
    private int DislocatorTPTargetZ;
    private boolean SomethingTeleported;
    private boolean IsItem;
    private int k;
    private Random rand = new Random();

    public DislocatorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {

        if (pLevel instanceof ServerLevel && pEntity.canChangeDimensions() && !pEntity.isOnPortalCooldown()) {
            ResourceKey<Level> resourcekey = pLevel.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel)pLevel).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return;
            }
            //Check if dim is overworld
            if (!(pLevel.dimension() == Level.OVERWORLD)){
                return;
            }

            //Checks for Leather Armor to disable the dislocator for you
            if (pEntity instanceof Player) {
                Player pPlayer = (Player)pEntity;
                if(pPlayer.getInventory().getArmor(0).is(Items.LEATHER_BOOTS)||pPlayer.getInventory().getArmor(1).is(Items.LEATHER_LEGGINGS)||pPlayer.getInventory().getArmor(2).is(Items.LEATHER_CHESTPLATE)||pPlayer.getInventory().getArmor(3).is(Items.LEATHER_HELMET)){
                    pPlayer.displayClientMessage(Component.translatable("tooltip.dislocatormod.rescue_from_dislocator_block.client_message"),false);
                    pEntity.setPortalCooldown();
                    return;
                }
            }

            //Calculating random coordinates to teleport to
            DislocatorTPTargetX = ((rand.nextInt(40) - 19)) * 200000 + 100000;
            DislocatorTPTargetZ = ((rand.nextInt(40) - 19)) * 200000 + 100000;

            BlockPos blockpos = null;

            //Checking for a block to teleport onto
            for(int k = pLevel.getMaxBuildHeight() - 1; k > (blockpos == null ? pLevel.getMinBuildHeight() : blockpos.getY()); --k) {
                BlockPos blockpos1 = new BlockPos(DislocatorTPTargetX, k, DislocatorTPTargetZ);
                BlockState blockstate = pLevel.getBlockState(blockpos1);
                if (blockstate.isCollisionShapeFullBlock(pLevel, blockpos1) && (!blockstate.is(Blocks.BEDROCK))) {
                    blockpos = blockpos1;
                    break;
                }
            }

            //Get fire resistance and water breathing for basic protection after teleport
            if (pEntity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)pEntity;
                livingentity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 2000));
                livingentity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000));
            }

            SomethingTeleported = true;

            //Diffrent sound and particles for Items
            if (pEntity instanceof ItemEntity){
                IsItem = true;
            } else {
                IsItem = false;
            }

            //Teleport to target
            pEntity.teleportTo(blockpos.getX(),blockpos.getY()+1,blockpos.getZ());

            pEntity.setPortalCooldown();

            if (pEntity instanceof Player) {
            Player pPlayer = (Player)pEntity;
            pPlayer.sendSystemMessage(Component.nullToEmpty(pPlayer.getScoreboardName() + " is now at a better place!"));
            pPlayer.displayClientMessage(Component.translatable("tooltip.dislocatormod.dislocator_block.client_message"),false);
            }

        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (SomethingTeleported) {
            if (IsItem) {
                DislocatorSoundAndParticle(pLevel, pPos, pRandom, ParticleTypes.HEART, SoundEvents.GENERIC_EAT, 0.5F);
            } else {
                DislocatorSoundAndParticle(pLevel, pPos, pRandom, ParticleTypes.PORTAL, SoundEvents.BEEHIVE_ENTER, 1.0F);
            }
        }
        SomethingTeleported = false;
    }

    private void DislocatorSoundAndParticle(Level pLevel, BlockPos pPos, RandomSource pRandom, ParticleOptions pParticleData, SoundEvent pSound, float pVolume){

        pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, pSound, SoundSource.BLOCKS, pVolume, pRandom.nextFloat() * 0.4F + 0.8F, false);

        if (pParticleData == ParticleTypes.PORTAL) { k = 16; } else { k = 5; }

        for(int i = 0; i < k; ++i) {
            double d0 = (double) pPos.getX() + pRandom.nextDouble();
            double d1 = (double) pPos.getY() + pRandom.nextDouble() + 0.7F;
            double d2 = (double) pPos.getZ() + pRandom.nextDouble();
            double d3 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;

            pLevel.addParticle(pParticleData, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.dislocatormod.dislocator_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

}


 