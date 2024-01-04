package com.dev.sphone.mod.utils;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import fr.aym.acsguis.api.ACsGuiApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UtilsServer {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void sendActionChat(EntityPlayer player, String msg, Boolean actionbar) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + msg), actionbar);
    }

    public static void sendErrorChat(EntityPlayer player, String msg, Boolean actionbar) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.RED + msg), actionbar);
    }

    public static String getCurrentDateFormat(String format, TimeUnit timeUnit, long additionalTime) {
        Date date = new Date();
        if (additionalTime != 0 && additionalTime != -1) {
            date.setTime(date.getTime() + timeUnit.toMillis(additionalTime));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getDateOf(long date) {
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public static Date getDate(long date) {
        Date d = new Date(date);
        return d;
    }

    //get date and return long


    //get phone and return sim

    public static int getSimCard(EntityPlayer p) {
        if (p.getHeldItemMainhand().isEmpty() || !(p.getHeldItemMainhand().getItem() instanceof ItemPhone)) return 0;
        ItemPhone itemPhone = (ItemPhone) p.getHeldItemMainhand().getItem();
        return itemPhone.getSimCard(p.getHeldItemMainhand());
    }
    public static void registerAllCssFiles() {
        List<String> cssFiles = new ArrayList<>();
        String resourcePath = "assets/" + SPhone.MOD_ID + "/css/";
        InputStream stream = UtilsServer.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream != null) {
            try (java.util.Scanner scanner = new java.util.Scanner(stream)) {
                while (scanner.hasNextLine()) {
                    String fileName = scanner.nextLine().trim();
                    if (fileName.endsWith(".css")) {
                        cssFiles.add(fileName);
                    }
                }
            }
        }
        for (String fileName : cssFiles) {
            ResourceLocation resourceLocation = new ResourceLocation(SPhone.MOD_ID, "css/" + fileName);
            ACsGuiApi.registerStyleSheetToPreload(resourceLocation);
        }
    }

    public static EntityPlayerMP getPlayerFromNumber(MinecraftServer server, String number) {
        return server.getPlayerList().getPlayers().stream().filter(player -> getListOfPhoneInPlayer(player).contains(number)).findFirst().orElse(null);
    }

   public static List<String> getListOfPhoneInPlayer(EntityPlayer player) {
       List<String> numbers = new ArrayList<>();
       int inventorySize = player.inventory.getSizeInventory();

       for (int i = 0; i < inventorySize; i++) {
           ItemStack itemStack = player.inventory.getStackInSlot(i);

           if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemPhone) {
               int simCard = ItemPhone.getSimCard(itemStack);

               if (simCard != 0) {
                   numbers.add(MethodesBDDImpl.getNumero(simCard));
               }
           }
       }
       return numbers;
   }




}