package net.nu11une.bobberbegone.mixin;

import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.nu11une.bobberbegone.accessor.FishingHookRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;


@Mixin(FishingHookRenderState.class)
public class FishingHookRenderStateMixin implements FishingHookRenderStateAccessor {
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
