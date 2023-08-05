package com.dev.sphone.mod.utils;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.server.PacketSetAnim;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.aym.acsguis.api.ACsGuiApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Utils {

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


    public static void makeScreenPhone(int framebufferTextureId) {
        Minecraft mc = Minecraft.getMinecraft();

        File file1 = new File("phonescreenshots");
        file1.mkdir();
        GlStateManager.pushMatrix();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextureId);

        ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(mc.displayWidth / 3 * mc.displayHeight * 4);

        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);
        GlStateManager.rotate(180, 0, 0, 1);
        BufferedImage bufferedImage = new BufferedImage(mc.displayWidth / 3, mc.displayHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < mc.displayHeight; y++) {
            for (int x = 0; x < mc.displayWidth / 3; x++) {
                int i = (x + (mc.displayWidth / 3 * (mc.displayHeight - y - 1))) * 4;
                int alpha = (pixelBuffer.get(i + 3) & 0xFF) << 24;
                int red = (pixelBuffer.get(i) & 0xFF) << 16;
                int green = (pixelBuffer.get(i + 1) & 0xFF) << 8;
                int blue = pixelBuffer.get(i + 2) & 0xFF;
                int color = alpha | red | green | blue;
                bufferedImage.setRGB(x, y, color);
            }
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        File file2 = Utils.getTimestampedPNGFileForDirectory(file1);

        try {
            file2 = file2.getCanonicalFile();
            ImageIO.write(bufferedImage, "png", file2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GlStateManager.popMatrix();
        ClientEventHandler.lastPhoneScreenshot = null;
    }

    private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
        String s = DATE_FORMAT.format(new Date()).toString();
        int i = 1;

        while (true) {
            File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");

            if (!file1.exists()) {
                return file1;
            }

            ++i;
        }
    }

    public static File[] getAllPhoneScreenshots() {
        return new File("phonescreenshots").listFiles();
    }

    public static CompletableFuture<BufferedImage> getLastPhoneImage() {

        File folder = new File("phonescreenshots");
        folder.mkdir();
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        if (listOfFiles.length == 0) return null;
        File lastModifiedFile = listOfFiles[0];
        for (int i = 1; i < listOfFiles.length; i++) {
            if (lastModifiedFile.lastModified() < listOfFiles[i].lastModified()) {
                lastModifiedFile = listOfFiles[i];
            }
        }

        File finalLastModifiedFile = lastModifiedFile;
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageIO.read(finalLastModifiedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, HttpUtil.DOWNLOADER_EXECUTOR);
    }

    /**
     * @return String[] of all backgrounds (String[1] = background id, String[2] = background name)
     */

    public static String[] getBackgrounds() {
        File folder = new File("config/sphone");
        folder.mkdir();
        File file = new File("config/sphone/backgrounds.json");
        // if file doesn't exist, create it
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // write default backgrounds
            try {
                OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8);
                // TODO: Find better way to write this
                writer.write(
                        "{\n" +
                            "  \"backgrounds\": [\n" +
                            "    {\n" +
                            "      \"id\":\"acsgui\",\n" +
                            "      \"name\":\"ACS-GUI\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\":\"deauville\",\n" +
                            "      \"name\":\"Deauville\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\":\"stmichel\",\n" +
                            "      \"name\":\"St Michel\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\":\"playa\",\n" +
                            "      \"name\":\"Playa\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\":\"oscuridad\",\n" +
                            "      \"name\":\"Oscuridad\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"id\":\"iluminacion\",\n" +
                            "      \"name\":\"Illumination\"\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}"
                );
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // read file
        String json = "";
        try {
            json = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parse json
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray backgrounds = jsonObject.getAsJsonArray("backgrounds");


        String[] backgroundsArray = new String[backgrounds.size()];
        for(int i = 0; i < backgrounds.size(); i++) {
            JsonObject background = backgrounds.get(i).getAsJsonObject();
            backgroundsArray[i] = background.get("id").getAsString();
        }

        // make double value : id -> name
        for(int i = 0; i < backgrounds.size(); i++) {
            JsonObject background = backgrounds.get(i).getAsJsonObject();
            backgroundsArray[i] = background.get("id").getAsString() + ":" + background.get("name").getAsString();
        }
        return backgroundsArray;

    }

    public static boolean isUsingMod(String mainClass) {
        try {
            Class.forName(mainClass);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void leaveCamera(boolean returnHome) {
        ClientEventHandler.lastPhoneScreenshot = null;
        ClientEventHandler.isCameraActive = false;
        if (isUsingMod("com.mrcrayfish.obfuscate.Obfuscate")) SPhone.network.sendToServer(new PacketSetAnim(false));

        if (returnHome) ClientEventHandler.mc.displayGuiScreen(new GuiHome().getGuiScreen());
    }


    public static boolean hasPhone(EntityPlayer player) {
        return ItemPhone.getSimCard(player.getHeldItemMainhand()) != 0;
    }

    //get phone and return sim
    public static int getSimCard(EntityPlayer p) {
        return p.getHeldItemMainhand().hasTagCompound() ? p.getHeldItemMainhand().getTagCompound().getInteger("simcard") : 0;
    }

    public static void registerAllCssFiles() {
        List<String> cssFiles = new ArrayList<>();
        String resourcePath = "assets/" + SPhone.MOD_ID + "/css/";
        InputStream stream = Utils.class.getClassLoader().getResourceAsStream(resourcePath);
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
}