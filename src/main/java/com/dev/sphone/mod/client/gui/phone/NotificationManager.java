package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.mod.common.phone.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    public static List<Notification> notifications;

    public static void init() {
        notifications = new ArrayList<>();
    }

    public static void pushNotification(Notification notification) {

    }
}
