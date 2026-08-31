package net.nu11une.bobberbegone;

import com.mojang.blaze3d.platform.InputConstants;
import dev.kikugie.fletching_table.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.nu11une.bobberbegone.config.BBConfig;
import net.nu11une.bobberbegone.versioning.VersionedIdentifier;
import org.jetbrains.annotations.NotNull;
//? if >=1.21.6 {
/*import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.renderer.RenderPipelines;
*///?} else {
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.renderer.RenderType;
//?}
//? if >=26.1 {
/*import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.GuiGraphics;
//?}

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.network.chat.Component.translatable;


@Entrypoint("client")
public class BobberBegone implements ClientModInitializer {
    @NotNull private static final Duration KEY_COOLDOWN = Duration.ofSeconds(1);
    //? if >=1.21.9 {
    /*@NotNull private static final KeyMapping.Category KEY_CATEGORY_MAIN = KeyMapping.Category.register(VersionedIdentifier.of("main"));
    *///?} else {
    @NotNull private static final String KEY_CATEGORY_MAIN = "key.category.bobberbegone.main";
    //?}

    public static BobberBegone MOD;

    public boolean isHooked = false;

    @NotNull private final Map<String, Long> lastKeyPresses = new HashMap<>();
    public boolean visible = true;

    public BobberBegone() {
        MOD = this;
    }

    @Override
    public void onInitializeClient() {
        BBConfig.HANDLER.load();
        BBConfig.HANDLER.instance().validate();

        // Keybinds
        final KeyMapping toggleVisibilityKey = new KeyMapping(
                "key.bobberbegone.toggle_visibility",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_B,
                KEY_CATEGORY_MAIN);
        //? if >=26.1 {
        /*KeyMappingHelper.registerKeyMapping(toggleVisibilityKey);
        *///?} else {
        KeyBindingHelper.registerKeyBinding(toggleVisibilityKey);
        //?}

        // Keybind reactions
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Toggle visibility
            while (toggleVisibilityKey.consumeClick()) {
                final String name = toggleVisibilityKey.getName();
                final Long lastPress = lastKeyPresses.get(name);
                if (lastPress != null && System.currentTimeMillis() - lastPress < KEY_COOLDOWN.toMillis()) continue;
                lastKeyPresses.put(name, System.currentTimeMillis());

                visible = !visible;
                if (client.player != null) {
                    final Component message = translatable("bobberbegone.toggle_visibility." + (visible ? "visible" : "invisible"))
                            .withStyle(visible ? ChatFormatting.GREEN : ChatFormatting.RED);
                    //? if >=26.1 {
                    /*client.player.sendOverlayMessage(message);
                    *///?} else {
                    client.player.displayClientMessage(message, true);
                    //?}
                }
            }
        });

        // Crosshair indicator
        //? if >=1.21.6 {
        /*HudElementRegistry.attachElementAfter(VanillaHudElements.CROSSHAIR, VersionedIdentifier.of("textures/gui/bobber_icon.png"), (graphics, tick) -> renderCrosshairIcon(graphics));
        *///?} else {
        HudRenderCallback.EVENT.register((graphics, tick) -> renderCrosshairIcon(graphics));
        //?}
    }

    //? if >=26.1 {
    /*private void renderCrosshairIcon(@NotNull GuiGraphicsExtractor graphics) {
    *///?} else {
    private void renderCrosshairIcon(GuiGraphics graphics) {
    //?}
        if (!isHooked || !BBConfig.HANDLER.instance().icon.enabled) return;

        final Minecraft client = Minecraft.getInstance();
        //? if >=26.2 {
        /*if (client.gui.hud.isHidden()) return;
        *///?} else {
        if (client.options.hideGui) return;
        //?}
        if (!client.options.getCameraType().isFirstPerson()) return;

        // Center x/y with size so icon's size scales nicely (doesn't move when resized)
        final int size = BBConfig.HANDLER.instance().icon.size;
        final int x = ((graphics.guiWidth() - size) / 2) + BBConfig.HANDLER.instance().icon.x;
        final int y = ((graphics.guiHeight() - size) / 2) + BBConfig.HANDLER.instance().icon.y;
        graphics.blit(
                //? if >=1.21.6 {
                /*RenderPipelines.GUI_TEXTURED,
                *///?} else {
                RenderType::guiTextured,
                //?}
                VersionedIdentifier.of("textures/gui/bobber_icon.png"),
                x, y, 0f, 0f,
                size, size, size, size);
    }
}
