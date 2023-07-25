
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.calculator;

import fr.aym.acsguis.component.textarea.GuiFloatField;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCalculator extends GuiBase {

    public GuiCalculator(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        GuiFloatField Field = new GuiFloatField(0, 999999999);
        Field.setCssId("field");
        add(Field);

        GuiLabel Button_AC = new GuiLabel("AC");
        Button_AC.setCssId("button_ac");
        add(Button_AC);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/calculator.css"));
        return styles;
    }


}
