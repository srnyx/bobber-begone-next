package net.nu11une.bobberbegone.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.nu11une.bobberbegone.versioning.VersionedIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.network.chat.Component.translatable;


public class ConfigScreen {
    @NotNull
    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(translatable("bobberbegone.config.title"))
                .category(ConfigCategory.createBuilder().name(translatable("bobberbegone.config.category.general"))
                        .option(Option.<Integer>createBuilder()
                                .name(translatable("bobberbegone.config.opacity.label"))
                                .description(OptionDescription.createBuilder()
                                        .text(translatable("bobberbegone.config.opacity.description"))
                                        .image(VersionedIdentifier.of("textures/config/translucent_bobber.png"), 480, 252)
                                        .build())
                                .binding(BBConfig.HANDLER.defaults().opacity, () -> BBConfig.HANDLER.instance().opacity, newValue -> {
                                    BBConfig.HANDLER.instance().opacity = newValue;
                                    BBConfig.HANDLER.save();
                                })
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0, 100)
                                        .step(5)
                                        .formatValue(value -> {
                                            final Component valueText;
                                            if (value == 0) {
                                                valueText = translatable("bobberbegone.config.opacity.invisible");
                                            } else if (value == 100) {
                                                valueText = translatable("bobberbegone.config.opacity.opaque");
                                            } else {
                                                valueText = Component.literal(value.toString());
                                            }

                                            return translatable("bobberbegone.config.opacity.value", valueText);
                                        }))
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(translatable("bobberbegone.config.icon.group.label"))
                                .description(OptionDescription.createBuilder()
                                        .text(translatable("bobberbegone.config.icon.group.description"))
                                        .image(VersionedIdentifier.of("textures/config/crosshair_icon.png"), 125, 67)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(translatable("bobberbegone.config.icon.show.label"))
                                        .description(OptionDescription.createBuilder()
                                                .text(translatable("bobberbegone.config.icon.show.description"))
                                                .build())
                                        .binding(BBConfig.HANDLER.defaults().icon.enabled, () -> BBConfig.HANDLER.instance().icon.enabled, newValue -> {
                                            BBConfig.HANDLER.instance().icon.enabled = newValue;
                                            BBConfig.HANDLER.save();
                                        })
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .coloured(true)
                                                .onOffFormatter())
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(translatable("bobberbegone.config.icon.size.label"))
                                        .description(OptionDescription.createBuilder()
                                                .text(translatable("bobberbegone.config.icon.size.description"))
                                                .build())
                                        .binding(BBConfig.HANDLER.defaults().icon.size, () -> BBConfig.HANDLER.instance().icon.size, newValue -> {
                                            BBConfig.HANDLER.instance().icon.size = newValue;
                                            BBConfig.HANDLER.save();
                                        })
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(1, 128)
                                                .step(1))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(translatable("bobberbegone.config.icon.x.label"))
                                        .description(OptionDescription.createBuilder()
                                                .text(translatable("bobberbegone.config.icon.x.description"))
                                                .build())
                                        .binding(BBConfig.HANDLER.defaults().icon.x, () -> BBConfig.HANDLER.instance().icon.x, newValue -> {
                                            BBConfig.HANDLER.instance().icon.x = newValue;
                                            BBConfig.HANDLER.save();
                                        })
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(translatable("bobberbegone.config.icon.y.label"))
                                        .description(OptionDescription.createBuilder()
                                                .text(translatable("bobberbegone.config.icon.y.description"))
                                                .build())
                                        .binding(BBConfig.HANDLER.defaults().icon.y, () -> BBConfig.HANDLER.instance().icon.y, newValue -> {
                                            BBConfig.HANDLER.instance().icon.y = newValue;
                                            BBConfig.HANDLER.save();
                                        })
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}
