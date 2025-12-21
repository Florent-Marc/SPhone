package com.dev.sphone.mod.client.gui.phone.apps.weather;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.phone.Weather;
import com.dev.sphone.mod.utils.UtilsServer;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AppDetails(type = AppType.DEFAULT)
public class GuiWeather extends GuiBase {

    private final Weather weather;

    public GuiWeather(GuiScreen parent, Weather weather) {
        super(parent);
        this.weather = weather;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        // Fundo transparente usando Customizer!
        getStyleCustomizer().setBackgroundColor(0x00000000);

        add(getRoot());

        GuiLabel city = new GuiLabel(I18n.format("sphone.meteo.cityname"));
        city.setCssId("city");
        getRoot().add(city);

        GuiLabel date = new GuiLabel(UtilsServer.getCurrentDateFormat("dd/MM/yyyy", null, 0));
        date.setCssId("date");
        getRoot().add(date);

        String[] types = getWeather(weather);
        String weatherType = types[0];
        String forecastType = types[1];
        String forecastTime = types[2];

        GuiPanel weatherIcon = new GuiPanel();
        weatherIcon.setCssId("weatherIcon");
        // Usa Customizer para o ícone!
        weatherIcon.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/weather/" + getTypeFormat(weatherType) + ".png"))
        );
        getRoot().add(weatherIcon);

        GuiLabel weatherLabel = new GuiLabel(I18n.format("sphone.meteo.types." + weatherType));
        weatherLabel.setCssId("weatherLabel");
        getRoot().add(weatherLabel);

        GuiLabel forecastLabel = new GuiLabel(I18n.format("sphone.meteo.prevs"));
        forecastLabel.setCssId("forecastLabel");
        getRoot().add(forecastLabel);

        GuiLabel forecastTimeLabel = new GuiLabel(forecastTime);
        forecastTimeLabel.setCssId("forecastTimeLabel");
        getRoot().add(forecastTimeLabel);

        GuiPanel forecastIcon = new GuiPanel();
        forecastIcon.setCssId("forecastIcon");
        forecastIcon.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/weather/" + getTypeFormat(forecastType) + ".png"))
        );
        getRoot().add(forecastIcon);

        GuiLabel forecastName = new GuiLabel(I18n.format("sphone.meteo.types." + forecastType));
        forecastName.setCssId("forecastName");
        getRoot().add(forecastName);
    }

    public String getTypeFormat(String weatherType) {
        switch (weatherType) {
            case "sunny":
            case "sun":
                return "sun";
            case "clear":
                return "cloud";
            case "rain":
                return "rain";
            case "thunder":
                return "thunder";
            case "snow":
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
        String current;
        String after;
        String afterTime;
        if (isRaining) {
            if (isThundering) {
                current = "thunder";
                if (rainTime < thunderTime) {
                    after = "sun";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                } else {
                    after = "rain";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            } else {
                current = "rain";
                if (thunderTime > rainTime) {
                    after = "sun";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                } else {
                    after = "thunder";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            }
        } else {
            if (clearTime != 0) {
                if (clearTime / (20 * 60) <= 10) {
                    current = "clear";
                    after = "rain";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, clearTime / 20);
                } else {
                    current = "sunny";
                    after = "rain";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, clearTime / 20);
                }
            } else {
                if (rainTime < thunderTime) {
                    if (rainTime / (20 * 60) <= 10) {
                        current = "clear";
                    } else {
                        current = "sunny";
                    }
                    after = "rain";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, rainTime / 20);
                } else {
                    if (thunderTime / (20 * 60) <= 10) {
                        current = "clear";
                    } else {
                        current = "sunny";
                    }
                    after = "thunder";
                    afterTime = UtilsServer.getCurrentDateFormat("HH:mm", TimeUnit.SECONDS, thunderTime / 20);
                }
            }
        }
        return new String[]{current, after, afterTime};
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/weather.css"));
        return styles;
    }
}
