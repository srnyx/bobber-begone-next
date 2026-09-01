package net.nu11une.bobberbegone;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.kikugie.fletching_table.fabric.Entrypoint;
import net.nu11une.bobberbegone.config.ConfigScreen;
import org.jetbrains.annotations.NotNull;


@Entrypoint("modmenu")
public class ModMenuImpl implements ModMenuApi {
    @Override @NotNull
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::getConfigScreen;
    }
}
