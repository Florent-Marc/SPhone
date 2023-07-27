package com.dev.sphone.mod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

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
        if(additionalTime != 0 && additionalTime != -1) {
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

}
