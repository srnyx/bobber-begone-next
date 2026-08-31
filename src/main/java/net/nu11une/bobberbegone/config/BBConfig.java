package net.nu11une.bobberbegone.config;

import com.google.gson.JsonObject;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.nu11une.bobberbegone.versioning.VersionedIdentifier;
import org.jetbrains.annotations.NotNull;


public class BBConfig {
    @NotNull public static ConfigClassHandler<BBConfig> HANDLER = ConfigClassHandler.createBuilder(BBConfig.class)
            .id(VersionedIdentifier.of("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("bobberbegone.json"))
                    .build())
            .build();

    @SerialEntry
    public int opacity = 0;

    @SerialEntry
    @NotNull public BBConfig.Icon icon = new Icon();


    public void validate() {
        opacity = Math.max(0, Math.min(100, opacity)); // don't use Math.clamp to support older Java versions
        icon.validate();

        HANDLER.save();
    }

    @NotNull
    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        json.addProperty("opacity", opacity);
        json.add("icon", icon.toJson());
        return json;
    }

    public static class Icon {
        @SerialEntry
        public boolean enabled = true;

        @SerialEntry
        public int size = 8;

        @SerialEntry
        public int x = 12;

        @SerialEntry
        public int y = 1;


        public void validate() {
            size = Math.max(1, size);
        }

        @NotNull
        public JsonObject toJson() {
            final JsonObject json = new JsonObject();
            json.addProperty("enabled", enabled);
            json.addProperty("size", size);
            json.addProperty("x", x);
            json.addProperty("y", y);
            return json;
        }
    }
}
