package com.dev.sphone.mod.client.gui.phone.apps.weather;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.phone.Weather;
import com.dev.sphone.mod.utils.Utils;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuiWeather extends GuiBase {

    private final Weather weather;

    public GuiWeather(GuiScreen parent, Weather weather) {
        super(parent);
        this.weather = weather;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        style.setBackgroundColor(Color.TRANSLUCENT);

        add(getRoot());

        GuiLabel city = new GuiLabel("Archipel de Baltia");
        city.setCssId("city");
        getRoot().add(city);

        GuiLabel date = new GuiLabel(Utils.getCurrentDateFormat("dd/MM/yyyy", null, 0));
        date.setCssId("date");
        getRoot().add(date);

        String[] types = getWeather(weather);
        String weatherType = types[0];
        String forecastType = types[1];
        String forecastTime = types[2];

        GuiPanel weatherIcon = new GuiPanel();
        weatherIcon.setCssId("weatherIcon");
        weatherIcon.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/weather/"+getTypeFormat(weatherType)+".png")));
        getRoot().add(weatherIcon);

        GuiLabel weatherLabel = new GuiLabel(weatherType);
        weatherLabel.setCssId("weatherLabel");
        getRoot().add(weatherLabel);


        GuiLabel forecastLabel = new GuiLabel("Prévision météorologique");
        forecastLabel.setCssId("forecastLabel");
        getRoot().add(forecastLabel);


        GuiLabel forecastTimeLabel = new GuiLabel(forecastTime);
        forecastTimeLabel.setCssId("forecastTimeLabel");
        getRoot().add(forecastTimeLabel);

        GuiPanel forecastIcon = new GuiPanel();
        forecastIcon.setCssId("forecastIcon");
        forecastIcon.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/weather/"+getTypeFormat(forecastType)+".png")));
        getRoot().add(forecastIcon);

        GuiLabel forecastName = new GuiLabel(forecastType);
        forecastName.setCssId("forecastName");
        getRoot().add(forecastName);

        this.add(getRoot());
    }

    public String getTypeFormat(String weatherType){
        switch (weatherType) {
            case "Ensoleillé":
            case "Soleil":
                return "sun";
            case "Clair":
                return "cloud";
            case "Pluie":
                return "rain";
            case "Orage":
                return "thunder";
            case "Neige":
                return "snow";
            default:
                return "";
        }
    }

    public String[] getWeather(Weather weather) {
        int clearTime = weather.getClearTime();
        int rainTime = weather.getRainTime();
        int thunderTime = weather.getThunderTime();
        boolean isRaining = weather.isRaining();
        boolean isThundering = weather.isThundering();
        System.out.println(clearTime + " / " + rainTime + " / " + thunderTime + " / " + isRaining + " / " + isThundering);
        String current;
        String after;
        String afterTime;
        if(isRaining){
            if(isThundering){
                current = "Orage";
                if(rainTime < thunderTime) {
                    after = "Soleil";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                }else{
                    after = "Pluie";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            }else{
                current = "Pluie";
                if(thunderTime > rainTime) {
                    after = "Soleil";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                }else{
                    after = "Orage";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            }
        }else{
            if(clearTime != 0) {
                if (clearTime / (20 * 60) <= 10) {
                    current = "Clair";
                    after = "Pluie";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, clearTime / 20);
                } else {
                    current = "Ensoleillé";
                    after = "Pluie";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, clearTime / 20);
                }
            }else{
                if(rainTime < thunderTime){
                    if(rainTime / (20*60) <= 10) {
                        current = "Clair";
                    }else{
                        current = "Ensoleillé";
                    }
                    after = "Pluie";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                }else{
                    if(thunderTime / (20*60) <= 10) {
                        current = "Clair";
                    }else{
                        current = "Ensoleillé";
                    }
                    after = "Orage";
                    afterTime = Utils.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            }
        }
        return new String[]{current, after, afterTime};
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/weather.css"));
        return styles;
    }


}
