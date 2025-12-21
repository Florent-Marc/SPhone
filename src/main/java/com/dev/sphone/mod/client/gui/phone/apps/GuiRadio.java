package com.dev.sphone.mod.client.gui.phone.apps;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemRadio;
import com.dev.sphone.mod.common.packets.client.PacketTuneRadio;
import com.dev.sphone.mod.common.register.SoundRegister;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.audio.PositionedSoundRecord;

import java.util.Collections;
import java.util.List;

public class GuiRadio extends GuiFrame {

    private String inputFreq = "";
    private GuiLabel visor;
    private static final int MAX_DIGITS = 3; // Frequência de 1 até 999

    public GuiRadio() {
        super(new GuiScaler.Identity()); // Posição absoluta

        // Painel principal
        GuiPanel frame = new GuiPanel();
        frame.setCssClass("frame_0");
        add(frame);

        // Visor de frequência (onde mostra o número)
        visor = new GuiLabel("000");
        visor.setCssClass("radio-visor");
        frame.add(visor);

        // Painel dos botões numéricos
        GuiPanel buttons = new GuiPanel();
        buttons.setCssClass("radio-buttons");
        buttons.setLayout(new GridLayout(35, 35, 1, GridLayout.GridDirection.HORIZONTAL, 3));
        frame.add(buttons);

        // Adiciona os botões 1-9, *, 0, DEL
        String[][] keys = {
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"},
                {"*", "0", "DEL"}
        };
        for (String[] row : keys) {
            for (String key : row) {
                final String keyCopy = key; // Capture corretamente no lambda!
                GuiButton btn = new GuiButton(key);
                if (key.equals("DEL"))
                    btn.setCssClass("radio-btn-del");
                else if (key.equals("*"))
                    btn.setCssClass("radio-btn-star");
                else
                    btn.setCssClass("radio-btn");

                btn.addClickListener((mx, my, mb) -> {
                    handleButton(keyCopy);
                });
                buttons.add(btn);
            }
        }
    }

    /**
     * Lógica dos botões: números, DEL e *
     */
    private void handleButton(String key) {
        if (key.matches("[0-9]")) {
            if (inputFreq.length() < MAX_DIGITS) {
                inputFreq += key;
            }
        } else if (key.equals("DEL")) {
            if (!inputFreq.isEmpty()) {
                inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
            }
        } else if (key.equals("*")) {
            if (inputFreq.isEmpty()) return;

            int freq;
            try {
                freq = Integer.parseInt(inputFreq);
            } catch (NumberFormatException e) {
                Minecraft.getMinecraft().player.sendMessage(
                        new TextComponentString("Frequência inválida!")
                );
                return;
            }
            if (freq < 0 || freq > 999) {
                Minecraft.getMinecraft().player.sendMessage(
                        new TextComponentString("A frequência deve ser entre 0 e 999.")
                );
                return;
            }

            // Salva no item (apenas se o jogador estiver segurando rádio)
            ItemStack heldItem = Minecraft.getMinecraft().player.getHeldItemMainhand();
            ItemRadio.setFrequency(heldItem, freq);

            // Toca o som
            Minecraft.getMinecraft().getSoundHandler().playSound(
                    PositionedSoundRecord.getMasterRecord(SoundRegister.SINTONIZANDO, 1.0F)
            );

            // Envia o packet para o servidor
            SPhone.network.sendToServer(new PacketTuneRadio(freq));

            // Fecha a GUI
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
        // Atualiza o visor
        visor.setText(inputFreq.isEmpty() ? "000" : inputFreq);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(
                new ResourceLocation("sphone:css/radio.css")
        );
    }
}
