
package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiGeneralSettings extends GuiBase {

    public GuiGeneralSettings(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        add(getRoot());
        PhoneSettings settings = getSettings();


        GuiPanel app_container = new GuiPanel();
        app_container.setCssClass("app_container");

        GuiLabel appTitle = new GuiLabel("Paramètres");
        appTitle.setCssId("app_title");

        GuiLabel appVersion = new GuiLabel("Version : " + SPhone.VERSION);
        appVersion.setCssId("app_version");
        getRoot().add(appVersion);

        EntityPlayer player = Minecraft.getMinecraft().player;

        if(player.getHeldItemMainhand().getItem() instanceof ItemPhone) {
            ItemStackHandler handler = null;
            ItemStack hold = (player.getHeldItemMainhand());
            if (hold.hasTagCompound()) {
                if (hold.getTagCompound().hasKey("inventory")) {
                    handler = new ItemStackHandler(1);
                    handler.deserializeNBT(hold.getTagCompound().getCompoundTag("inventory"));
                } else {
                    handler = new ItemStackHandler(1);
                    hold.getTagCompound().setTag("inventory", handler.serializeNBT());
                }

                GuiLabel phoneNumber = new GuiLabel("Numéro de téléphone : " + handler.getStackInSlot(0).getTagCompound().getString("numero"));
                phoneNumber.setCssClass("nbtel");
                getRoot().add(phoneNumber);
            }


        }


        app_container.add(appTitle);
        getRoot().add(app_container);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/settings.css"));
        return styles;
    }

}
