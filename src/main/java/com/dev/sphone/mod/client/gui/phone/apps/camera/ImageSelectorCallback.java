package com.dev.sphone.mod.client.gui.phone.apps.camera;

import net.minecraft.client.renderer.texture.DynamicTexture;

public interface ImageSelectorCallback {
    void onImageSelected(int id, DynamicTexture texture);
}
