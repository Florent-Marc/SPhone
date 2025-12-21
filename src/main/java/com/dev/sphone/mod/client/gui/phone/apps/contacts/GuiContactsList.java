package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.ComponentRenderContext;
import fr.aym.acsguis.utils.GuiTextureSprite;
import com.dev.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiContactsList extends GuiBase {

    private final List<Contact> contacts;

    public GuiContactsList(GuiScreen parent, List<Contact> contacts) {
        super(parent);
        this.contacts = contacts;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        GuiLabel AppTitle = new GuiLabel(I18n.format("sphone.contacts.title"));
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonAdd = new GuiLabel("+");
        ButtonAdd.setCssId("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewContact(this.getGuiScreen()).getGuiScreen());
        });
        getBackground().add(ButtonAdd);

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 60, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (Contact contact : contacts) {
            GuiPanel contactPanel = new GuiPanel();
            contactPanel.setCssClass("contact_background");
            contactPanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiViewContact(this.getGuiScreen(), contact).getGuiScreen());
            });

            if (contact.getPhoto().equals("empty")) {
                GuiLabel contactAvatar = new GuiLabel("");
                contactAvatar.setCssId("contact_avatar");

                // CORRIGIDO: setTexture usando Customizer!
                try {
                    contactAvatar.getStyle().getCustomizer().setTexture(
                        new GuiTextureSprite(new ResourceLocation("sphone", "textures/ui/icons/nohead.png"))
                    );
                } catch (Throwable t) {
                    // fallback ou log
                }

                contactPanel.add(contactAvatar);
            } else {
                DynamicTexture texture = UtilsClient.base64ToDynamicTexture(contact.getPhoto());
                GuiLabel screen = new GuiLabel("") {
                    @Override
                    public void drawBackground(int mouseX, int mouseY, float partialTicks, ComponentRenderContext context) {
                        super.drawBackground(mouseX, mouseY, partialTicks, context);
                        ScaledResolution scaledResolution = new ScaledResolution(mc);
                        int screenWidth = scaledResolution.getScaledWidth();
                        int screenHeight = scaledResolution.getScaledHeight();

                        int x = (int) (getScreenX() + getWidth() / 2f);
                        int y = (int) (getScreenY() + getHeight() / 2f);

                        GlStateManager.pushMatrix();
                        if (texture != null) {
                            GlStateManager.bindTexture(texture.getGlTextureId());
                        }
                        GlStateManager.translate(x, y, 0);
                        GlStateManager.scale(0.077f, 0.235f, 0.3f);
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glTexCoord2f(0.0F, 0.0F);
                        GL11.glVertex3f(-screenWidth, -screenHeight, 0.0F);
                        GL11.glTexCoord2f(0.0F, 1.0F);
                        GL11.glVertex3f(-screenWidth, screenHeight, 0.0F);

                        GL11.glTexCoord2f(1.0F, 1.0F);
                        GL11.glVertex3f(screenWidth, screenHeight, 0.0F);
                        GL11.glTexCoord2f(1.0F, 0.0F);
                        GL11.glVertex3f(screenWidth, -screenHeight, 0.0F);
                        GL11.glEnd();
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                        GlStateManager.popMatrix();
                    }
                };
                screen.setCssId("contact_avatar");
                contactPanel.add(screen);
            }

            GuiLabel ContactName = new GuiLabel(contact.getName() + " " + contact.getLastname());
            ContactName.setCssId("contact_name");
            contactPanel.add(ContactName);

            contacts_list.add(contactPanel);
        }

        getBackground().add(contacts_list);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contactslist.css"));
        return styles;
    }
}
