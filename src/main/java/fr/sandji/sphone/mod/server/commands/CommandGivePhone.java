
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.server.commands;

import fr.sandji.sphone.mod.common.items.ItemPhone;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandGivePhone extends CommandBase {
    @Override
    public String getName() {
        return "givephone";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/givephone <simcode>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 1) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItemMainhand();
            ItemPhone.setSimCard(stack, Integer.valueOf(args[0]));
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Votre téléphone posséde maintenant la carte sim : " + ItemPhone.getSimCard(stack)));
        }
    }
}