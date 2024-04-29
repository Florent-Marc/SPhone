package com.dev.sphone.mod.utils;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.PacketSetAnim;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.TextComponentString;
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
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class UtilsClient {
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

        File file2 = getTimestampedPNGFileForDirectory(file1);

        try {
            file2 = file2.getCanonicalFile();
            ImageIO.write(bufferedImage, "png", file2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GlStateManager.popMatrix();
        ClientEventHandler.lastPhoneScreenshot = null;
    }


    public static File[] getAllPhoneScreenshots() {
        File folder = new File("phonescreenshots");
        folder.mkdir();
        return folder.listFiles();
    }


    public static int getAverageColor(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int size = width * height;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 255;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                r += (color >> 16) & 0xFF;
                g += (color >> 8) & 0xFF;
                b += color & 0xFF;
            }
        }
        r /= size;
        g /= size;
        b /= size;
        return (a << 24) | (r << 16) | (g << 8) | b;
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
        if (!file.exists()) {
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
        for (int i = 0; i < backgrounds.size(); i++) {
            JsonObject background = backgrounds.get(i).getAsJsonObject();
            backgroundsArray[i] = background.get("id").getAsString();
        }

        // make double value : id -> name
        for (int i = 0; i < backgrounds.size(); i++) {
            JsonObject background = backgrounds.get(i).getAsJsonObject();
            backgroundsArray[i] = background.get("id").getAsString() + ":" + background.get("name").getAsString();
        }
        return backgroundsArray;

    }

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    public static void leaveCamera(boolean returnHome) {
        ClientEventHandler.lastPhoneScreenshot = null;
        ClientEventHandler.isCameraActive = false;
        if (SPhone.isUsingMod("com.mrcrayfish.obfuscate.Obfuscate"))
            SPhone.network.sendToServer(new PacketSetAnim(false));

        if (returnHome) ClientEventHandler.mc.displayGuiScreen(new GuiHome().getGuiScreen());
    }

    public static File getTimestampedPNGFileForDirectory(File gameDirectory) {
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

    public static class InternalDynamicTexture extends DynamicTexture {

        public int width;
        public int height;

        public InternalDynamicTexture(BufferedImage bufferedImage) {
            super(bufferedImage);
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public static String dynamicTextureToBase64(InternalDynamicTexture dynamicTexture) {
        BufferedImage bufferedImage = new BufferedImage(dynamicTexture.getWidth(), dynamicTexture.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, dynamicTexture.getWidth(), dynamicTexture.getHeight(), dynamicTexture.getTextureData(), 0, dynamicTexture.getWidth());

        // reduce size
        bufferedImage = scale(bufferedImage, 64, 64);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static InternalDynamicTexture base64ToDynamicTexture(String base64) {
        if (base64.charAt(0) == 'e') return null;
        String[] base64Split = base64.split(",");
        if (base64Split.length != 2) return null;
        if (!base64Split[0].equals("base64")) return null;

        byte[] imageBytes = Base64.getDecoder().decode(base64Split[1]);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new InternalDynamicTexture(bufferedImage);
    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

}
