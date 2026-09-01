package net.nu11une.bobberbegone;

import dev.faststats.ErrorTracker;
import dev.faststats.data.Metric;
import dev.faststats.fabric.FabricContext;
import net.nu11une.bobberbegone.config.BBConfig;
import org.jetbrains.annotations.NotNull;


public class Stats {
    @NotNull public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();

    public Stats() {
        new FabricContext.Factory(BuildProperties.MOD_ID, "63f6037c71feaa29b91515d4a4ba4b2b")
                .errorTrackerService(ERROR_TRACKER)
                .metrics(factory -> {
                    // Config
                    factory.addMetric(Metric.object("config", () -> BBConfig.HANDLER.instance().toJson()));

                    return factory.create();
                })
                .create();
    }
}
