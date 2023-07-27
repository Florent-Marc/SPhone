package com.dev.sphone.api.events;

import com.dev.sphone.mod.client.gui.phone.AppManager;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class AppAddon extends Event {

    public final List<AppManager.App> apps;

    public AppAddon(List<AppManager.App> apps){
        this.apps = apps;
    }

}
