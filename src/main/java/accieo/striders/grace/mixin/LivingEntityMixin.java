package accieo.striders.grace.mixin;

import accieo.striders.grace.StridersGrace;
import accieo.striders.grace.util.VectorUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Inject(method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInLava()Z"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isFallFlying()Z")
            ))
    private void modifyLavaSpeed(Vec3d movementInput, CallbackInfo ci) {
        ItemStack feetStack = getEquippedStack(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getLevel(StridersGrace.STRIDERS_GRACE, feetStack);
        if (level != 0) {
            switch (level) {
                case 1 -> this.setVelocity(VectorUtil.movementInputToVelocity(movementInput, 0.07f, this.getYaw()));
                case 2 -> this.setVelocity(VectorUtil.movementInputToVelocity(movementInput, 0.14f, this.getYaw()));
                case 3 -> this.setVelocity(VectorUtil.movementInputToVelocity(movementInput, 0.21f, this.getYaw()));
            }
        }

    }
}
