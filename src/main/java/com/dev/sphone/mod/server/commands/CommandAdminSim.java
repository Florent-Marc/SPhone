package com.dev.sphone.mod.server.commands;

import com.dev.sphone.api.events.SimRegisterEvent;
import com.dev.sphone.mod.common.items.ItemSim;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;

public class CommandAdminSim extends CommandBase {
    @Override
    public String getName() {
        return "adminSIM";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/adminSIM <number>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "adminSIM");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        if (args.length != 1) {
            player.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }

        ItemStack held = player.getHeldItemMainhand();
        if (!(held.getItem() instanceof ItemSim) || ItemSim.getSimCard(held) != 0) {
            UtilsServer.sendErrorChat(player, "Você precisa estar com um SIM vazio na mão.", false);
            return;
        }

        String number = args[0];
        if (MethodesBDDImpl.getDatabaseInstance().checkNumber(number)) {
            UtilsServer.sendErrorChat(player, "Este número já está em uso.", false);
            return;
        }

        int sim = UtilsServer.getRandomNumber(1000, 9999);
        boolean added = MethodesBDDImpl.getDatabaseInstance().addSim(sim, number);
        if (!added) {
            for (int i = 0; i < 50 && !added; i++) {
                sim = UtilsServer.getRandomNumber(1000, 9999);
                added = MethodesBDDImpl.getDatabaseInstance().addSim(sim, number);
            }
        }

        if (!added) {
            UtilsServer.sendErrorChat(player, "Não foi possível criar o SIM, tente novamente.", false);
            return;
        }

        MinecraftForge.EVENT_BUS.post(new SimRegisterEvent(player, String.valueOf(sim), number));
        ItemSim.setSimCard(player, held, sim);
        ItemSim.setNumero(player, held, number);
    }
}