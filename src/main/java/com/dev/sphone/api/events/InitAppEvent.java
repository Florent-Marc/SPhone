package com.dev.sphone.api.events;

import com.dev.sphone.mod.client.gui.phone.AppManager;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class InitAppEvent extends Event {

    private final List<AppManager.App> apps;

    public InitAppEvent(List<AppManager.App> apps){
        this.apps = apps;
    }

    public List<AppManager.App> getApps() {
        return apps;
    }

    public void addApp(AppManager.App app){
        apps.add(app);
    }
}
