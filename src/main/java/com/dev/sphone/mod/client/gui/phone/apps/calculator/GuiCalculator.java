package com.dev.sphone.mod.client.gui.phone.apps.calculator;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@AppDetails(type = AppType.DEFAULT)
public class GuiCalculator extends GuiBase {

    public GuiCalculator(GuiScreen parent) {
        super(parent);
    }

    int x = 0;
    int y = 0;
    String operator = "";
    boolean checkEqual = false;

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(this.getRoot());

        GuiLabel labelTotal = new GuiLabel("0");
        labelTotal.setCssId("field");
        labelTotal.addTickListener(() -> {
            if(checkEqual){
                labelTotal.setText(String.valueOf(x));
            }else {
                if (operator.isEmpty()) {
                    labelTotal.setText(String.valueOf(x));
                } else {
                    labelTotal.setText(x + operator);
                }
                if (y != 0) {
                    labelTotal.setText(x + operator + y);
                }
            }
        });
        getRoot().add(labelTotal);

        GuiScrollPane buttons_list = new GuiScrollPane();
        buttons_list.setCssClass("buttons_list");
        buttons_list.setLayout(new GridLayout(67, 69, -1, GridLayout.GridDirection.HORIZONTAL, 4));

        List<String> buttons = new ArrayList<>();
        buttons.add("C");
        buttons.add("del");
        buttons.add("%");
        buttons.add("/");

        buttons.add("7");
        buttons.add("8");
        buttons.add("9");
        buttons.add("*");

        buttons.add("4");
        buttons.add("5");
        buttons.add("6");
        buttons.add("-");

        buttons.add("1");
        buttons.add("2");
        buttons.add("3");
        buttons.add("+");

        buttons.add("0");
        buttons.add(".");
        buttons.add("^");
        buttons.add("=");

        for (int i = 0; i < buttons.size(); i++) {
            String button = buttons.get(i);
            GuiLabel buttonLabel = new GuiLabel(button);
            buttonLabel.setCssClass("button");
            if (i % 4 == 3) {
                buttonLabel.setCssId("button_2");
            } else {
                buttonLabel.setCssId("button_1");
            }
            buttons_list.add(buttonLabel);
            buttonLabel.addClickListener((xB, yB, b) -> {
                switch (button) {
                    case "C":
                        if (checkEqual) {
                            y = 0;
                            operator = "";
                            checkEqual = false;
                        }

                        x = 0;
                        y = 0;
                        operator = "";
                        break;
                    case "del":
                        if (checkEqual) {
                            y = 0;
                            operator = "";
                            checkEqual = false;
                        }

                        if (operator.isEmpty()) {
                            x = Integer.parseInt(String.valueOf(x).length() > 1 ? String.valueOf(x).substring(0, String.valueOf(x).length() - 1) : String.valueOf(0));
                        } else if (y != 0) {
                            y = Integer.parseInt(String.valueOf(y).length() > 1 ? String.valueOf(y).substring(0, String.valueOf(y).length() - 1) : String.valueOf(0));
                        } else {
                            operator = "";
                        }

                        break;
                    case "%":
                    case "/":
                    case "*":
                    case "-":
                    case "+":

                    case "^":
                        if (checkEqual) {
                            y = 0;
                            operator = "";
                            checkEqual = false;
                        }

                        operator = button;
                        break;
                    case "=":
                        checkEqual = true;
                        if (operator.equals("+")) x = (x + y);
                        if (operator.equals("-")) x = (x - y);
                        if (operator.equals("*")) x = (x * y);
                        if (operator.equals("/")) x = (x / y);
                        if (operator.equals("%")) x = (x % y);
                        if (operator.equals("^")) x = (int) Math.pow(x, y);
                        break;

                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        if (operator.isEmpty()) {
                            if (String.valueOf(x).length() < 5) {
                                if (checkEqual) {
                                    y = 0;
                                    operator = "";
                                    checkEqual = false;
                                }
                                x = Integer.parseInt(x + button);
                            }
                        } else {
                            if (checkEqual) {
                                y = 0;
                                operator = "";
                                checkEqual = false;
                            }
                            if (String.valueOf(y).length() < 5) {
                                y = Integer.parseInt(y + button);
                            }
                        }
                        break;

                }
            });
        }

        getRoot().add(buttons_list);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/calculator.css"));
        return styles;
    }

}
