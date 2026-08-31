//? if <=1.21.1 {
/*package net.nu11une.bobberbegone.mixin;

import net.minecraft.world.entity.projectile.FishingHook;
import net.nu11une.bobberbegone.accessor.HookedToSelfAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;


@Mixin(FishingHook.class)
public class FishingHookMixin implements HookedToSelfAccessor {
    @Unique private boolean bobberBegone$hookedToSelf;

    @Override
    public boolean bobberBegone$hookedToSelf() {
        return bobberBegone$hookedToSelf;
    }

    @Override
    public void bobberBegone$hookedToSelf(boolean value) {
        bobberBegone$hookedToSelf = value;
    }
}
*///?}
