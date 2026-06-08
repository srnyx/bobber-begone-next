package net.nu11une.bobberbegone.mixin;

//? if >=26.1 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
*///? } else {
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
//? }

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


//? if >=26.1 {
/*@Mixin(FishingHookRenderer.class)
public class FishingBobberEntityRendererMixin {
	@Inject(at = @At("HEAD"), method = "shouldRender(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", cancellable = true)
	public void renderCallback(FishingHook entity, Frustum culler, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
		final Minecraft client = Minecraft.getInstance();
		final Options options = client.options;
		final Player player = client.player;
		if (options.getCameraType().isFirstPerson() && entity.getHookedIn() == player) {
			cir.setReturnValue(false);
		}
	}
}
*///? } else {
@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {
	@Inject(at = @At("HEAD"), method = "shouldRender(Lnet/minecraft/entity/projectile/FishingBobberEntity;Lnet/minecraft/client/render/Frustum;DDD)Z", cancellable = true)
	public void renderCallback(FishingBobberEntity fishingBobberEntity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
		final MinecraftClient client = MinecraftClient.getInstance();
		final GameOptions options = client.options;
		final PlayerEntity player = client.player;
		if ((options == null || options.getPerspective().isFirstPerson()) && fishingBobberEntity.getHookedEntity() == player) {
			cir.setReturnValue(false);
		}
	}
}
//? }
