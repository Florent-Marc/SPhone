package fr.sandji.sphone.client.phone.apps.settings;

import com.google.gson.Gson;

import java.io.*;

public class PhoneSettings {
    public static int phone_number;
    public static int phone_background;
    public static int phone_ring;

    public PhoneSettings(int phone_number, int phone_background, int phone_ring) {
        this.phone_number = phone_number;
        this.phone_background = phone_background;
        this.phone_ring = phone_ring;
    }

    public void savePhoneSettings(PhoneSettings data) {
        Gson gson = new Gson();
        File file = new File("phone_settings.json");
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PhoneSettings loadPhoneSettings() {
        Gson gson = new Gson();
        File file = new File("phone_settings.json");
        try {
            PhoneSettings data = gson.fromJson(new FileReader(file), PhoneSettings.class);
            PhoneSettings phone_settings = new PhoneSettings(data.phone_number, data.phone_background, data.phone_ring);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
