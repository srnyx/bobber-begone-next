package net.nu11une.bobberbegone.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(net.minecraft.client.render.entity.FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {
	@Inject(at = @At("HEAD"), method = "shouldRender(Lnet/minecraft/entity/projectile/FishingBobberEntity;Lnet/minecraft/client/render/Frustum;DDD)Z", cancellable = true)
	public void renderCallback(FishingBobberEntity fishingBobberEntity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = MinecraftClient.getInstance().player;
		if ((MinecraftClient.getInstance().options == null || MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) && fishingBobberEntity.getHookedEntity() == player) {
			cir.setReturnValue(false);
		}
	}
}
