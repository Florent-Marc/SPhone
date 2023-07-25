/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiNewContact extends GuiBase {

    private final List<Contact> contacts;

    public GuiNewContact(GuiScreen parent, List<Contact> contacts) {
        super(parent);
        this.contacts = contacts;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        GuiLabel AppTitle = new GuiLabel("Ajouter un contact");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiTextField NameField = new GuiTextField();
        NameField.setCssClass("prenom");
        NameField.setHintText(" Prénom");
        getBackground().add(NameField);

        GuiTextField LastNameField = new GuiTextField();
        LastNameField.setCssClass("nom");
        LastNameField.setHintText(" Nom");
        getBackground().add(LastNameField);

        GuiTextField NumeroField = new GuiTextField();
        NumeroField.setCssClass("numero");
        NumeroField.setHintText(" 555-1234");
        NumeroField.addTickListener(() -> {
            if (!NumeroField.getText().equals("")) {
                if (isValidInput(NumeroField.getText())) {

                }
            }
        });
        getBackground().add(NumeroField);

        GuiTextField notes = new GuiTextField();
        notes.setCssClass("notes");
        notes.setHintText(" Notes");
        getBackground().add(notes);

        GuiPanel ButtonAdd = new GuiPanel();
        ButtonAdd.setCssClass("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            contacts.add(new Contact(NameField.getText(), LastNameField.getText(), Integer.valueOf(NumeroField.getText()), notes.getText()));
            Gson gson = new Gson();
            String jsonString = gson.toJson(contacts, new TypeToken<List<Contact>>(){}.getType());
            //SPhone.network.sendToServer(new PacketUpdateContacts(jsonString));
        });
        getBackground().add(ButtonAdd);
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
