package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiImageSelector;
import com.dev.sphone.mod.common.packets.server.PacketEditContact;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import com.dev.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GuiEditContact extends GuiBase {

    private final Contact contact;

    public GuiEditContact(GuiScreen parent, Contact contact) {
        super(parent);
        this.contact = contact;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(getRoot());

        GuiLabel AppTitle = new GuiLabel("Modifier");
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiTextField name = new GuiTextField();
        name.setCssClass("textarea");
        name.setMaxTextLength(16);
        name.setCssId("nom");
        name.setText(contact.getName());
        getRoot().add(name);

        GuiTextField lastName = new GuiTextField();
        lastName.setCssClass("textarea");
        lastName.setMaxTextLength(16);
        lastName.setCssId("prenom");
        if(contact.getLastname().isEmpty()){
            lastName.setHintText("Prénom");
        }else {
            lastName.setText(contact.getLastname());
        }
        getRoot().add(lastName);

        GuiTextField numero = new GuiTextField();
        numero.setCssClass("textarea");
        numero.setCssId("numero");
        numero.setText(contact.getNumero());
        //numero.setRegexPattern(Pattern.compile("^-?\\d+$"));
        getRoot().add(numero);

        GuiTextField notes = new GuiTextField();
        notes.setCssClass("textarea");
        notes.setCssId("notes");
        if(contact.getLastname().isEmpty()){
            notes.setHintText("Note");
        }else {
            notes.setText(contact.getNotes());
        }
        getRoot().add(notes);

        GuiButton selectPhoto = new GuiButton("Sélectionner une photo");
        selectPhoto.setCssClass("textarea");
        selectPhoto.setCssId("photo");
        selectPhoto.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiImageSelector(this.getGuiScreen(), (id, texture) -> {
                contact.setPhoto(id + "");
                selectPhoto.setText("Photo sélectionnée");
            }).getGuiScreen());
        });
        getRoot().add(selectPhoto);

        GuiPanel buttonEdit = new GuiPanel();
        buttonEdit.setCssClass("edit");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            File[] files = UtilsClient.getAllPhoneScreenshots();
            String photoToB64= "empty";
            if(!contact.getPhoto().equals("empty")) {
                UtilsClient.InternalDynamicTexture texture = new UtilsClient.InternalDynamicTexture(getImage(files[Integer.parseInt(contact.getPhoto())]).join());
                photoToB64 = UtilsClient.dynamicTextureToBase64(texture);
            }
            SPhone.network.sendToServer(new PacketEditContact(new Contact(contact.getId(), name.getText(), lastName.getText(), numero.getText(), notes.getText(), photoToB64), "edit"));
        });
        getRoot().add(buttonEdit);

        GuiPanel buttonDel = new GuiPanel();
        buttonDel.setCssClass("delete");
        buttonDel.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditContact(new Contact(contact.getId(), name.getText(), lastName.getText(), numero.getText(), notes.getText()), "delete"));
        });
        getRoot().add(buttonDel);
    }

    private static CompletableFuture<BufferedImage> getImage(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, HttpUtil.DOWNLOADER_EXECUTOR);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newcontact.css"));
        return styles;
    }

}
