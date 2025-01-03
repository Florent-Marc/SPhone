package com.dev.sphone.mod.client.gui.layout;

import fr.aym.acsguis.component.layout.BorderedGridLayout;
import fr.aym.acsguis.component.layout.PanelLayout;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.style.ComponentStyleManager;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssValue;
import fr.aym.acsguis.cssengine.positionning.Size.SizeValue;
import fr.aym.acsguis.utils.GuiConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple grid layout
 *
 * @see BorderedGridLayout
 */
public class CustomGridLayout implements PanelLayout<ComponentStyleManager> {
    private final Map<ComponentStyleManager, Integer> cache = new HashMap<>();
    private final Map<ComponentStyleManager, Integer> cacheY = new HashMap<>();
    private int nextIndex;
    private int totalHeight;

    private final SizeValue width, height, spacing;
    private final GridDirection direction;
    private final int elementsPerLine;
    private GuiPanel container;

    /**
     * @param width           Tile width, in pixels
     * @param height          Tile height, in pixels
     * @param spacing         Space between tiles, in all directions, in pixels
     * @param direction       Primary direction of the alignment (direction of a "line")
     * @param elementsPerLine Number of elements on each "lines", use -1 to automatically fill the lines
     */
    public CustomGridLayout(int width, int height, int spacing, GridDirection direction, int elementsPerLine) {
        this(new SizeValue(width, GuiConstants.ENUM_SIZE.ABSOLUTE), new SizeValue(height, GuiConstants.ENUM_SIZE.ABSOLUTE), new SizeValue(spacing, GuiConstants.ENUM_SIZE.ABSOLUTE), direction, elementsPerLine);
        // Retro-compatibility (-1 was 100%)
        if(width == -1)
            this.width.setRelative(1, CssValue.Unit.RELATIVE_INT);
        if(height == -1)
            this.height.setRelative(1, CssValue.Unit.RELATIVE_INT);
    }

    /**
     * @param width           Tile width, relative between 0 and 1 (0.5 = 50%)
     * @param height          Tile height, relative between 0 and 1 (0.5 = 50%)
     * @param spacing         Space between tiles, in all directions, relative between 0 and 1 (0.5 = 50%)
     * @param direction       Primary direction of the alignment (direction of a "line")
     * @param elementsPerLine Number of elements on each "lines", use -1 to automatically fill the lines
     */
    public CustomGridLayout(float width, float height, float spacing, GridDirection direction, int elementsPerLine) {
        this(new SizeValue(width, GuiConstants.ENUM_SIZE.RELATIVE), new SizeValue(height, GuiConstants.ENUM_SIZE.RELATIVE), new SizeValue(spacing, GuiConstants.ENUM_SIZE.RELATIVE), direction, elementsPerLine);
    }

    /**
     * @param width           Tile width
     * @param height          Tile height
     * @param spacing         Space between tiles, in all directions
     * @param direction       Primary direction of the alignment (direction of a "line")
     * @param elementsPerLine Number of elements on each "lines", use -1 to automatically fill the lines
     */
    public CustomGridLayout(SizeValue width, SizeValue height, SizeValue spacing, GridDirection direction, int elementsPerLine) {
        this.width = width;
        this.height = height;
        this.spacing = spacing;
        this.direction = direction;
        this.elementsPerLine = elementsPerLine;
    }

    /**
     * Creates a simple column layout with one element per line
     *
     * @param height  Element height, in pixels
     * @param spacing Space between elements, in pixels
     * @return A new column layout
     */
    public static CustomGridLayout columnLayout(int height, int spacing) {
        return new CustomGridLayout(new SizeValue(1, GuiConstants.ENUM_SIZE.RELATIVE), new SizeValue(height, GuiConstants.ENUM_SIZE.ABSOLUTE), new SizeValue(spacing, GuiConstants.ENUM_SIZE.ABSOLUTE), GridDirection.HORIZONTAL, 1);
    }

    /**
     * Creates a simple column layout with one element per line
     *
     * @param height  Element height, relative between 0 and 1 (0.5 = 50%)
     * @param spacing Space between elements, relative between 0 and 1 (0.5 = 50%)
     * @return A new column layout
     */
    public static CustomGridLayout columnLayout(float height, float spacing) {
        return new CustomGridLayout(new SizeValue(1, GuiConstants.ENUM_SIZE.RELATIVE), new SizeValue(height, GuiConstants.ENUM_SIZE.RELATIVE), new SizeValue(spacing, GuiConstants.ENUM_SIZE.RELATIVE), GridDirection.HORIZONTAL, 1);
    }

    @Override
    public float getX(ComponentStyleManager target) {
        if (!cache.containsKey(target)) {
            cache.put(target, nextIndex);
            nextIndex++;
        }
        float elementsPerLine = this.elementsPerLine;
        if (direction == GridDirection.HORIZONTAL && elementsPerLine == -1) {
            elementsPerLine = target.getParent().getRenderWidth() / getWidth(target);
        }
        float spacing = this.spacing.computeValue(container.getWidth(), container.getHeight(), container.getWidth());
        return direction == GridDirection.HORIZONTAL ? (getWidth() + spacing) * (cache.get(target) % elementsPerLine) : (getWidth() + spacing) * (cache.get(target) / elementsPerLine);
    }

    @Override
    public float getY(ComponentStyleManager target) {
        if (!cache.containsKey(target)) {
            cache.put(target, nextIndex);
            nextIndex++;
        }

        float elementsPerLine = this.elementsPerLine;
        if (direction == GridDirection.VERTICAL && elementsPerLine == -1) {
            elementsPerLine = target.getParent().getRenderHeight() / getHeight(target);
        }

        if(!cacheY.containsKey(target)) {
            cacheY.put(target, totalHeight);
            totalHeight += getHeight(target) + 5;
        }

        return cacheY.get(target);
    }

    @Override
    public float getWidth(ComponentStyleManager target) {
        return getWidth();
    }

    @Override
    public float getHeight(ComponentStyleManager target) {
        float height = 0;

        if(((GuiPanel) target.getOwner()).getChildComponents().isEmpty()){
            ((GuiPanel)target.getOwner()).flushComponentsQueue();
        }

        for(Object componentChild : ((GuiPanel)target.getOwner()).getChildComponents()){
            if(componentChild instanceof GuiLabel){
                float len = 10 + (Math.max(21, ((GuiLabel)componentChild).getText().length()));
                height += len;
            }
        }
        return height;
    }

    @Override
    public void clear() {
        cache.clear();
        cacheY.clear();
        totalHeight = 0;
        nextIndex = 0;
    }

    public float getWidth() {
        return width.computeValue(GuiFrame.resolution.getScaledWidth(), GuiFrame.resolution.getScaledHeight(), container.getWidth());
    }

    @Override
    public void setContainer(GuiPanel container) {
        if (this.container != null)
            throw new IllegalArgumentException("Layout already used in " + this.container);
        this.container = container;
    }

    public enum GridDirection {
        HORIZONTAL, VERTICAL
    }
}