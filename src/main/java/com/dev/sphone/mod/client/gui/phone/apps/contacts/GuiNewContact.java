
package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiImageSelector;
import com.dev.sphone.mod.client.gui.phone.apps.camera.ImageSelectorCallback;
import com.dev.sphone.mod.common.packets.server.PacketEditContact;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiNewContact extends GuiBase {

    public static Contact contactInCreation;

    public GuiNewContact(GuiScreen parent) {
        super(parent);
    }

    private GuiTextField numero;

    @Override
    public void GuiInit() {
        super.GuiInit();
        if(contactInCreation == null) {
            contactInCreation = new Contact(-1, "", "", "", "","empty");
        }
        add(getRoot());

        GuiLabel AppTitle = new GuiLabel("Ajouter Un Contact");
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiTextField name = new GuiTextField();
        name.setCssClass("textarea");
        name.setMaxTextLength(16);
        name.setCssId("nom");
        name.setHintText("Nom");
        name.setText(contactInCreation.getName());
        getRoot().add(name);

        GuiTextField lastName = new GuiTextField();
        lastName.setCssClass("textarea");
        lastName.setMaxTextLength(16);
        lastName.setCssId("prenom");
        lastName.setHintText("Prénom");
        lastName.setText(contactInCreation.getLastname());

        getRoot().add(lastName);

         numero = new GuiTextField();
        numero.setCssClass("textarea");
        numero.setCssId("numero");
        numero.setHintText("555-1234");
        numero.setText(contactInCreation.getNumero());

        getRoot().add(numero);

        GuiTextField notes = new GuiTextField();
        notes.setCssClass("textarea");
        notes.setCssId("notes");
        notes.setHintText("Notes");
        notes.setText(contactInCreation.getNotes());
        getRoot().add(notes);

        GuiButton selectPhoto = new GuiButton("Sélectionner une photo");
        selectPhoto.setCssClass("textarea");
        selectPhoto.setCssId("photo");
        selectPhoto.addClickListener((mouseX, mouseY, mouseButton) -> {
            GuiNewContact.contactInCreation = new Contact(-1, name.getText(), lastName.getText(), numero.getText(), notes.getText());
            Minecraft.getMinecraft().displayGuiScreen(new GuiImageSelector(this.getGuiScreen(), (id, texture) -> {
                GuiNewContact.contactInCreation.setPhoto(id + "");
                selectPhoto.setText("Photo sélectionnée");
            }).getGuiScreen());
        });

        GuiPhotoElement photoprev = new GuiPhotoElement(GuiNewContact.contactInCreation.getPhoto());
        photoprev.setCssClass("textarea");
        photoprev.setCssId("photoprev");

        getRoot().add(photoprev);
        getRoot().add(selectPhoto);

        GuiPanel ButtonAdd = new GuiPanel();
        ButtonAdd.setCssClass("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(!name.getText().isEmpty() && !numero.getText().isEmpty()) {
                File[] files = UtilsClient.getAllPhoneScreenshots();
                String photoToB64= "empty";
                if(!GuiNewContact.contactInCreation.getPhoto().equals("empty")) {
                    UtilsClient.InternalDynamicTexture texture = new UtilsClient.InternalDynamicTexture(getImage(files[Integer.parseInt(GuiNewContact.contactInCreation.getPhoto())]).join());
                    photoToB64 = UtilsClient.dynamicTextureToBase64(texture);
                }
                SPhone.network.sendToServer(new PacketEditContact(new Contact(-1, name.getText(), lastName.getText(), numero.getText(), notes.getText(), photoToB64), "add"));
            }
        });
        getRoot().add(ButtonAdd);
    }


    private boolean isValidInput(String input) {
        // Expression régulière : ^-?\d+$
        // ^ : début de la chaîne
        // -? : zéro ou un caractère "-"
        // \d+ : un ou plusieurs chiffres
        // $ : fin de la chaîne

        String regex = "^-?\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
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
