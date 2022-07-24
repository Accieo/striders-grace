package accieo.striders.grace;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StridersGrace implements ModInitializer {

	public static final Enchantment STRIDERS_GRACE = new StridersGraceEnchantment();

	@Override
	public void onInitialize() {

		Registry.register(Registry.ENCHANTMENT, new Identifier("stridersgrace", "striders_grace"), STRIDERS_GRACE);

	}
}
