package com.dev.sphone.mod.client.gui.layout;

import fr.aym.acsguis.component.layout.PanelLayout;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssValue;
import fr.aym.acsguis.cssengine.positionning.Size.SizeValue;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.component.style.InternalComponentStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple grid layout
 */
public class CustomGridLayout implements PanelLayout<InternalComponentStyle> {
    private final Map<InternalComponentStyle, Float> cache = new HashMap<>();
    private final Map<InternalComponentStyle, Float> cacheY = new HashMap<>();
    private float nextIndex;
    private float totalHeight;

    private final SizeValue width, height, spacing;
    private final GridDirection direction;
    private final float elementsPerLine;
    private GuiPanel container;

    public CustomGridLayout(SizeValue width, SizeValue height, SizeValue spacing, GridDirection direction, float elementsPerLine) {
        this.width = width;
        this.height = height;
        this.spacing = spacing;
        this.direction = direction;
        this.elementsPerLine = elementsPerLine;
    }

    public CustomGridLayout(float width, float height, float spacing, GridDirection direction, float elementsPerLine) {
        this(
            new SizeValue(width, GuiConstants.ENUM_SIZE.ABSOLUTE),
            new SizeValue(height, GuiConstants.ENUM_SIZE.ABSOLUTE),
            new SizeValue(spacing, GuiConstants.ENUM_SIZE.ABSOLUTE),
            direction,
            elementsPerLine
        );
        if (width == -1)
            this.width.setRelative(1, CssValue.Unit.RELATIVE_TO_PARENT);
        if (height == -1)
            this.height.setRelative(1, CssValue.Unit.RELATIVE_TO_PARENT);
    }

    public static CustomGridLayout columnLayout(float height, float spacing) {
        return new CustomGridLayout(
            new SizeValue(1, GuiConstants.ENUM_SIZE.RELATIVE),
            new SizeValue(height, GuiConstants.ENUM_SIZE.ABSOLUTE),
            new SizeValue(spacing, GuiConstants.ENUM_SIZE.ABSOLUTE),
            GridDirection.HORIZONTAL,
            1
        );
    }

    @Override
    public float getX(InternalComponentStyle target) {
        if (!cache.containsKey(target)) {
            cache.put(target, nextIndex++);
        }
        float elements = this.elementsPerLine;
        if (direction == GridDirection.HORIZONTAL && elements == -1) {
            elements = container.getWidth() / getWidth();
        }
        float spacingVal = spacing.computeValue(container.getWidth(), container.getHeight(), container.getWidth());
        return direction == GridDirection.HORIZONTAL ?
            (getWidth() + spacingVal) * (cache.get(target) % elements) :
            (getWidth() + spacingVal) * (cache.get(target) / elements);
    }

    @Override
    public float getY(InternalComponentStyle target) {
        if (!cache.containsKey(target)) {
            cache.put(target, nextIndex++);
        }
        float elements = this.elementsPerLine;
        if (direction == GridDirection.VERTICAL && elements == -1) {
            elements = container.getHeight() / getHeight(target);
        }
        if (!cacheY.containsKey(target)) {
            cacheY.put(target, totalHeight);
            totalHeight += getHeight(target) + 5;
        }
        return cacheY.get(target);
    }

    @Override
    public float getWidth(InternalComponentStyle target) {
        return getWidth();
    }

    @Override
    public float getHeight(InternalComponentStyle target) {
        float h = 0;
        if (target.getOwner() instanceof GuiPanel) {
            GuiPanel panel = (GuiPanel) target.getOwner();
            if (panel.getChildComponents().isEmpty()) {
                panel.flushComponentsQueue();
            }
            for (Object child : panel.getChildComponents()) {
                if (child instanceof GuiLabel) {
                    GuiLabel label = (GuiLabel) child;
                    float len = 10 + Math.max(21, label.getText().length());
                    h += len;
                }
            }
        }
        return h;
    }

    @Override
    public void clear() {
        cache.clear();
        cacheY.clear();
        totalHeight = 0;
        nextIndex = 0;
    }

    public float getWidth() {
        if (container == null) return 0;
        GuiFrame frame = container.getGui().getFrame();
        return width.computeValue(
            frame.getResolution().getScaledWidth(),
            frame.getResolution().getScaledHeight(),
            container.getWidth()
        );
    }

    @Override
    public void setContainer(GuiPanel container) {
        if (this.container != null)
            throw new IllegalArgumentException("Layout already used in " + this.container);
        this.container = container;
    }

    @Override
    public void onChildSizeChange(InternalComponentStyle child) {
        // Implement if needed
    }

    public enum GridDirection {
        HORIZONTAL, VERTICAL
    }
}
