/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.packets.server.PacketEditContact;
import fr.sandji.sphone.mod.common.packets.server.PacketEditNote;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiNewContact extends GuiBase {

    public GuiNewContact(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(getRoot());

        GuiLabel AppTitle = new GuiLabel("Ajouter Un Contact");
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiTextField name = new GuiTextField();
        name.setCssClass("textarea");
        name.setMaxTextLength(16);
        name.setCssId("nom");
        name.setHintText("Nom");
        getRoot().add(name);

        GuiTextField lastName = new GuiTextField();
        lastName.setCssClass("textarea");
        lastName.setMaxTextLength(16);
        lastName.setCssId("prenom");
        lastName.setHintText("Prénom");
        getRoot().add(lastName);

        GuiTextField numero = new GuiTextField();
        numero.setCssClass("textarea");
        numero.setCssId("numero");
        numero.setHintText("555-1234");
        //numero.setRegexPattern(Pattern.compile("^-?\\d+$"));
        getRoot().add(numero);

        GuiTextField notes = new GuiTextField();
        notes.setCssClass("textarea");
        notes.setCssId("notes");
        notes.setHintText("Notes");
        getRoot().add(notes);

        GuiPanel ButtonAdd = new GuiPanel();
        ButtonAdd.setCssClass("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(!name.getText().isEmpty() && !numero.getText().isEmpty()) {
                SPhone.network.sendToServer(new PacketEditContact(new Contact(-1, name.getText(), lastName.getText(), numero.getText(), notes.getText()), "add"));
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

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newcontact.css"));
        return styles;
    }

}
