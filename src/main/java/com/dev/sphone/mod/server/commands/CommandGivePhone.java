package com.dev.sphone.mod.server.commands;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.items.ItemSim;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandGivePhone extends CommandBase {
    @Override
    public String getName() {
        return "phone";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/phone <simcode>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "phone");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        EntityPlayer p = (EntityPlayer) sender;
        ItemPhone item;
        if (p.getHeldItemMainhand().getItem() instanceof ItemPhone) {
            item = (ItemPhone) p.getHeldItemMainhand().getItem();
        } else {
            return;
        }
        ItemSim sim = (ItemSim) ItemPhone.getSimCardItem(p.getHeldItemMainhand()).getItem();
        ItemStack simStack = ItemPhone.getSimCardItem(p.getHeldItemMainhand());
        String para = args[0];
        if (para.equals("setsim")) {
            if (args.length == 2) {
                if (sim == null) {
                    p.sendMessage(new TextComponentTranslation("sphone.error.no_sim"));
                } else {
                    sim.setSimCard(p, p.getHeldItemMainhand(), Integer.parseInt(args[1]));
                }
            } else {
                p.sendMessage(new TextComponentTranslation("sphone.error.no_sim"));
            }
        }
        if (para.equals("setnumero")) {
            if (args.length == 2) {
                if (sim == null) {
                    p.sendMessage(new TextComponentTranslation("sphone.error.no_sim"));
                } else {
                    sim.setNumero(p, p.getHeldItemMainhand(), args[1]);
                }
            } else {
                p.sendMessage(new TextComponentTranslation("sphone.error.no_sim"));
            }
        }

        if (para.equals("getnumero")) {
            p.sendMessage(new TextComponentString(Objects.requireNonNull(MethodesBDDImpl.getDatabaseInstance().getNumero(UtilsServer.getSimCard(p)))));
        }
        if (para.equals("nbt")) {
            p.sendMessage(new TextComponentString(p.getHeldItemMainhand().getTagCompound().toString()));
        }
        if (para.equals("call")) {
            p.sendMessage(new TextComponentTranslation("sphone.feature.later"));
        }
        if (para.equals("quitcall")) {
            p.sendMessage(new TextComponentTranslation("sphone.feature.later"));
        }
        if (para.equals("sendmessage")) {
            p.sendMessage(new TextComponentTranslation("sphone.feature.later"));
        }
        if (para.equals("apps")) {
            p.sendMessage(new TextComponentString(GuiHome.getDownloadedApps(p.getHeldItemMainhand()).toString()));
        }
        if(para.equals("addapp")) {

            ItemStack phoneStack = p.getHeldItemMainhand();
            if(phoneStack.getTagCompound() == null) {
                phoneStack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound tag = phoneStack.getTagCompound();

            NBTTagCompound apps = tag.getCompoundTag("apps");
            apps.setString(args[1], args[2]);
            tag.setTag("apps", apps);
            phoneStack.setTagCompound(tag);
            p.sendMessage(new TextComponentString("OK."));

        }
        if (para.equals("debg")) {
            try {
                MethodesBDDImpl.getDatabaseInstance().prepapreDatabase();
                p.sendMessage(new TextComponentString("OK."));
            } catch (DatabaseException e) {
                p.sendMessage(new TextComponentString("Internal error."));
                p.sendMessage(new TextComponentString(e.getMessage()));
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        //setsim,setnumero,call,quitcall,sendmessage
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "setsim", "setnumero", "call", "quitcall", "sendmessage");
        }

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
