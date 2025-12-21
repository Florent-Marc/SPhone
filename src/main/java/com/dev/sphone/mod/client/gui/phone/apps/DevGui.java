package com.dev.sphone.mod.client.gui.phone.apps;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiImageSelector;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiShowImage;
import com.dev.sphone.mod.common.items.ItemPhone;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dev.sphone.mod.common.items.ItemSim.SIM_KEY_TAG;

public class DevGui extends GuiBase {

    public DevGui(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        getBackground().add(new GuiLabel("Loaded with delay...").setCssCode("a","top: 20px;"));
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
                getBackground().add(new GuiLabel("Loaded with delay...").setCssCode("b","top: 40px;"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        GuiPanel yesno = new GuiPanel();
        yesno.setCssCode("d","top: 60px; left: 50px; width: 50px; height: 50px; background-color: green;");
        yesno.addClickListener((mouseX, mouseY, mouseButton) -> {
            GuiYesNo guiyesno = new GuiYesNo(new GuiYesNoCallback() {
                @Override
                public void confirmClicked(boolean result, int id) {
                    System.out.println("Result: " + result + ", id: " + id);
                }
            }, "aa", "bb", 0);
            mc.displayGuiScreen(guiyesno);
        });
        getBackground().add(yesno);

        GuiPanel imageSelector = new GuiPanel();
        imageSelector.setCssCode("b","top: 60px; left: 100px; width: 50px; height: 50px; background-color: red;");
        imageSelector.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiImageSelector(this.getGuiScreen(), (id, texture) -> {
                GuiLabel label = new GuiLabel("Image " + id);
                label.setCssCode("c","top: 10px; left: 50px;");
                File[] files = com.dev.sphone.mod.utils.UtilsClient.getAllPhoneScreenshots(); // ADICIONADO
                label.addClickListener((mouseX1, mouseY1, mouseButton1) ->
                    Minecraft.getMinecraft().displayGuiScreen(new GuiShowImage(files[id]).getGuiScreen())
                );
            }).getGuiScreen());
        });
        getBackground().add(imageSelector);

        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player.getHeldItemMainhand().getItem() instanceof ItemPhone) {
            ItemStackHandler handler;
            ItemStack hold = (player.getHeldItemMainhand());
            if (hold.hasTagCompound()) {
                if (hold.getTagCompound().hasKey("inventory")) {
                    handler = new ItemStackHandler(1);
                    handler.deserializeNBT(hold.getTagCompound().getCompoundTag("inventory"));
                } else {
                    handler = new ItemStackHandler(1);
                    hold.getTagCompound().setTag("inventory", handler.serializeNBT());
                }

                GuiLabel phoneNumber = new GuiLabel("nbtel " + handler.getStackInSlot(0).getTagCompound().getString("numero"));
                GuiLabel simNumber = new GuiLabel("nbsim " + handler.getStackInSlot(0).getTagCompound().getInteger(SIM_KEY_TAG));
                GuiLabel settings = new GuiLabel("settings " + getSettings().toString());
                phoneNumber.setCssCode("a","top: 150px; left: 50px;");
                simNumber.setCssCode("b","top: 175px; left: 50px;");
                settings.setCssCode("c","top: 200px; left: 50px;");

                getBackground().add(phoneNumber);
                getBackground().add(simNumber);
                getBackground().add(settings);
            }
        }
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        return styles;
    }
}
