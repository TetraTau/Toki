package net.tetratau.toki.mixin;

import net.minecraft.server.Bootstrap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
	@Inject(method = "bootStrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;bootStrap(Ljava/lang/Runnable;)V"))
	private static void inject(CallbackInfo ci) {
		System.out.println("Successfully injected!");
	}
}
