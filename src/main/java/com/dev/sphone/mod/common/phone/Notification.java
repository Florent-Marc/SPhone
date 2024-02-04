package com.dev.sphone.mod.common.phone;

import com.dev.sphone.mod.client.gui.phone.AppManager;
import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Notification implements ISerializable, ISerializablePacket {
    AppManager.App relatedApp;
    String title;
    String content;
    String icon;
    NotificationType type;
    long sendTime;

    public Notification(AppManager.App relatedApp, String title, String content, String icon, NotificationType type, long sendTime) {
        this.relatedApp = relatedApp;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.type = type;
        this.sendTime = sendTime;
    }

    public AppManager.App getRelatedApp() {
        return relatedApp;
    }

    public void setRelatedApp(AppManager.App relatedApp) {
        this.relatedApp = relatedApp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{
                relatedApp,
                title,
                content,
                icon,
                type,
                sendTime
        };
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        relatedApp = (AppManager.App) objects[0];
        title = (String) objects[1];
        content = (String) objects[2];
        icon = (String) objects[3];
        type = (NotificationType) objects[4];
        sendTime = (long) objects[5];
    }

    public enum NotificationType {
        PERSISTENT,
        TEMPORARY,
        DANGER
    }
}
