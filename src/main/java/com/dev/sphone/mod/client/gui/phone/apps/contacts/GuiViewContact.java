package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.packets.server.PacketGetUniqueConv;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketSendRequestCall;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.ComponentRenderContext;
import fr.aym.acsguis.utils.GuiTextureSprite;
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

public class GuiViewContact extends GuiBase {

    private final Contact contact;

    public GuiViewContact(GuiScreen parent, Contact contact) {
        super(parent);
        this.contact = contact;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(getRoot());

        GuiLabel appTitle = new GuiLabel("Contact");
        appTitle.setCssId("app_title");
        getRoot().add(appTitle);

        GuiLabel buttonEdit = new GuiLabel("✎");
        buttonEdit.setCssId("button_add");
        getRoot().add(buttonEdit);
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditContact(this.getGuiScreen(), contact).getGuiScreen());
        });

        GuiPanel message = new GuiPanel();
        message.setCssClass("message");
        getRoot().add(message);
        message.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketGetUniqueConv(contact));
        });

        GuiPanel call = new GuiPanel();
        call.setCssClass("call");
        getRoot().add(call);
        call.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketSendRequestCall(contact.getNumero(), contact.getName() + " " + contact.getLastname()));
        });

        if (contact.getPhoto().equals("empty")) {
            GuiLabel contactAvatar = new GuiLabel("");
            contactAvatar.setCssId("view_contact_avatar");
            try {
                contactAvatar.getStyle().getCustomizer().setTexture(
                    new GuiTextureSprite(new ResourceLocation("sphone", "textures/ui/icons/nohead.png"))
                );
            } catch (Throwable t) {
                // Fallback ou log caso necessário
            }
            getRoot().add(contactAvatar);
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
                    }
                    GlStateManager.popMatrix();
                }
            };
            screen.setCssId("view_contact_avatar");
            getRoot().add(screen);
        }

        GuiLabel name = new GuiLabel(I18n.format("sphone.contacts.lastname") + " : " + contact.getName());
        name.setCssId("name");
        getRoot().add(name);

        GuiLabel lastname = new GuiLabel(I18n.format("sphone.contacts.firstname") + " : " + (contact.getLastname().isEmpty() ? "Non renseigné" : contact.getLastname()));
        lastname.setCssId("lastname");
        getRoot().add(lastname);

        GuiLabel phone = new GuiLabel(I18n.format("sphone.contacts.number") + " : " + contact.getNumero());
        phone.setCssId("phone");
        getRoot().add(phone);

        if (!contact.getNotes().isEmpty()) {
            GuiLabel notes = new GuiLabel(I18n.format("sphone.contacts.note") + " : " + contact.getNotes());
            notes.setCssId("notes");
            getRoot().add(notes);
        }
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contacts.css"));
        return styles;
    }
}
