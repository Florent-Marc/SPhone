/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.ui.phone.Home;
import fr.sandji.sphone.mod.ui.phone.apps.contacts.Contacts;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientEventHandler {

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.DEBUG.isPressed()) {
            List<Contact> test = new ArrayList<Contact>();
            test.add(new Contact("Markus", "Kane", 14256, "Super Mec", "0hSandji"));
            test.add(new Contact("MK", "Kane", 14256, "Super Mec", "MK_16"));
            test.add(new Contact("Paris", "Kane", 14256, "Super Mec", "Zoutesou"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));
            test.add(new Contact("Superfsqd", "Kane", 14256, "Super Mec", "Afhistos"));

            Minecraft.getMinecraft().displayGuiScreen(new Contacts(test).getGuiScreen());
        }
        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            ACsGuiApi.asyncLoadThenShowGui("Home", Home::new);
        }
    }

}
