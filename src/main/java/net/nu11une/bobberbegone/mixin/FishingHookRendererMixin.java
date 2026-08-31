package net.nu11une.bobberbegone.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.nu11une.bobberbegone.accessor.FishingHookRenderStateAccessor;
import net.nu11une.bobberbegone.BobberBegone;
import net.nu11une.bobberbegone.config.BBConfig;
import net.nu11une.bobberbegone.versioning.VersionedIdentifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if >=1.21.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import org.objectweb.asm.Opcodes;
*///?} else {
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.injection.Redirect;
//?}
//? if >=1.21.11 {
/*import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
*///?} else {
import net.minecraft.client.renderer.RenderType;
//?}


@Mixin(FishingHookRenderer.class)
public class FishingHookRendererMixin {
	@Unique @NotNull private static final String bobberBegone$HOOK_TEXTURE
			//? if >=26.1 {
			/*= "textures/entity/fishing/fishing_hook.png";
			*///?} else {
			= "textures/entity/fishing_hook.png";
			//?}
	@Unique @NotNull private static final RenderType bobberBegone$TRANSLUCENT
			//? if >=1.21.11 {
			/*= RenderTypes.entityTranslucent(VersionedIdentifier.ofMinecraft(bobberBegone$HOOK_TEXTURE));
			*///?} else {
			= RenderType.entityTranslucent(VersionedIdentifier.ofMinecraft(bobberBegone$HOOK_TEXTURE));
			//?}

	@Unique private static boolean bobberBegone$hookedToSelf;

	@Inject(
			at = @At("HEAD"),
			method = "shouldRender(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z",
			cancellable = true)
	public void bobberBegone$shouldRender(FishingHook entity, Frustum culler, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
		final Entity hookedIn = entity.getHookedIn();
		if (hookedIn == null || hookedIn != Minecraft.getInstance().player) return;
		BobberBegone.MOD.isHooked = true;

		if (!BobberBegone.MOD.visible || BBConfig.HANDLER.instance().opacity <= 0) cir.setReturnValue(false);
	}

	@Inject(
			at = @At("TAIL"),
			method = "extractRenderState(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;F)V")
	public void bobberBegone$extractRenderState(FishingHook entity, FishingHookRenderState state, float partialTick, CallbackInfo ci) {
		final Entity hookedIn = entity.getHookedIn();
		final boolean hookedToSelf = hookedIn != null && hookedIn == Minecraft.getInstance().player;
		((FishingHookRenderStateAccessor) state).bobberBegone$hookedToSelf(hookedToSelf);
		bobberBegone$hookedToSelf = hookedToSelf;
	}

	//? if >=1.21.9 {
	/*@ModifyExpressionValue(
			//? if >=26.1 {
			/^method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
			^///?} else {
			method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
			//?}
			at = @At(value = "FIELD",
					//? if >=1.21.11 {
					/^target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;RENDER_TYPE:Lnet/minecraft/client/renderer/rendertype/RenderType;",
					^///?} else {
					target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;RENDER_TYPE:Lnet/minecraft/client/renderer/RenderType;",
					//?}
					opcode = Opcodes.GETSTATIC))
	private RenderType bobberBegone$swapRenderType(RenderType original, FishingHookRenderState state) {
		if (!((FishingHookRenderStateAccessor) state).bobberBegone$hookedToSelf()) return original;
		return BBConfig.HANDLER.instance().opacity < 100 ? bobberBegone$TRANSLUCENT : original;
	}

	// For bobberBegone$bobberAlpha
	@WrapOperation(
			//? if >=26.1 {
			/^method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
			^///?} else {
			method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
			//?}
			at = @At(value = "INVOKE", ordinal = 0,
					//? if >=1.21.11 {
					/^target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitCustomGeometry(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;)V"))
					^///?} else {
					target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitCustomGeometry(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;)V"))
					//?}
	private void bobberBegone$scopeHookedState(SubmitNodeCollector collector, PoseStack pose, RenderType type, SubmitNodeCollector.CustomGeometryRenderer renderer, Operation<Void> original, FishingHookRenderState state) {
		final boolean hookedToSelf = ((FishingHookRenderStateAccessor) state).bobberBegone$hookedToSelf();
		original.call(collector, pose, type, (SubmitNodeCollector.CustomGeometryRenderer) (matrices, vertices) -> {
			final boolean prev = bobberBegone$hookedToSelf;
			bobberBegone$hookedToSelf = hookedToSelf;
			try {
				renderer.render(matrices, vertices);
			} finally {
				bobberBegone$hookedToSelf = prev;
			}
		});
	}
	*///?} else {
	@Redirect(
			method = "render(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
	@NotNull
	private VertexConsumer bobberBegone$translucentBuffer(@NotNull MultiBufferSource source, RenderType original) {
		if (!bobberBegone$hookedToSelf) return source.getBuffer(original);

		return source.getBuffer(BBConfig.HANDLER.instance().opacity < 100 ? bobberBegone$TRANSLUCENT : original);
	}
	//?}

	@ModifyArg(
			method = "vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;IFIII)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
			index = 0)
	private static int bobberBegone$bobberAlpha(int color) {
		if (!bobberBegone$hookedToSelf) return color;
		final int alpha = Math.round(BBConfig.HANDLER.instance().opacity / 100f * 255f);
		return (alpha << 24) | (color & 0x00FFFFFF);
	}
}
