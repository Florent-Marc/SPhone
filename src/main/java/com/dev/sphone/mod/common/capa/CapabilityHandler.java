package com.dev.sphone.mod.common.capa;


import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketSyncCapa;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;
import java.util.List;

public class CapabilityHandler {

    @CapabilityInject(IStats.class)
    public static final Capability<IStats> STATS = null;

    //sync
    public static void syncPlayer(EntityPlayer player, List<EntityPlayerMP> receivers){
        if(player.hasCapability(STATS,null)){
            final PacketSyncCapa packet = new PacketSyncCapa(player.getCapability(STATS,null).getMoney(),player.getEntityId());
            receivers.forEach(receiver -> {
                SPhone.network.sendTo(packet, (EntityPlayerMP) receiver);
            });
        }
    }


    public static class Storage implements Capability.IStorage<IStats> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IStats> capability, IStats instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("money", instance.getMoney());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IStats> capability, IStats instance, EnumFacing side, NBTBase nbt) {
            instance.setMoney(((NBTTagCompound) nbt).getInteger("money"));
        }
    }

}
