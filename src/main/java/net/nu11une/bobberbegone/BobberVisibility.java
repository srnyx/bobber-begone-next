package net.nu11une.bobberbegone;

import net.nu11une.bobberbegone.config.BBConfig;
import org.jetbrains.annotations.Nullable;


public class BobberVisibility {
    @Nullable private Integer override;

    private static int configOpacity() {
        return BBConfig.HANDLER.instance().opacity;
    }

    public int effectiveOpacity() {
        return override != null ? override : configOpacity();
    }

    public boolean isVisible() {
        return effectiveOpacity() > 0;
    }

    public void toggle() {
        if (isVisible()) {
            override = 0;
        } else {
            override = configOpacity() > 0 ? null : 100;
        }
    }

    public void resync() {
        override = null;
    }
}
