package shadows.spawn.compat;

import com.google.common.collect.ImmutableSet;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shadows.Apotheosis;
import shadows.placebo.Placebo;
import shadows.spawn.SpawnerModifiers;
import shadows.spawn.SpawnerModule;
import shadows.spawn.compat.SpawnerWrapper.SpawnerInverseWrapper;

@JEIPlugin
public class SpawnerJEIPlugin implements IModPlugin {

	public static final String SPAWNER = "spawner_modification";

	@Override
	public void register(IModRegistry reg) {
		if (!Apotheosis.enableSpawner) return;
		reg.addRecipeCatalyst(new ItemStack(Blocks.MOB_SPAWNER), SPAWNER);

		ItemStack egg = new ItemStack(Items.SPAWN_EGG);
		ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("witch"));
		//Formatter::off
		reg.addRecipes(ImmutableSet.of(
				new SpawnerWrapper(SpawnerModifiers.minDelay, "MinSpawnDelay", -5, "jei.spw.editmindelay"),
				new SpawnerWrapper(SpawnerModifiers.maxDelay, "MaxSpawnDelay", -5, "jei.spw.editmaxdelay"),
				new SpawnerWrapper(SpawnerModifiers.spawnCount, "SpawnCount", 1, "jei.spw.editspawncount"),
				new SpawnerWrapper(SpawnerModifiers.nearbyEntities, "MaxNearbyEntities", 2, "jei.spw.editnearby"),
				new SpawnerWrapper(SpawnerModifiers.playerDist, "RequiredPlayerRange", 2, "jei.spw.editplayer"),
				new SpawnerWrapper(SpawnerModifiers.spawnRange, "SpawnRange", 1, "jei.spw.editspawn"),
				new SpawnerWrapper(SpawnerModifiers.spawnConditions, "ignore_conditions", true, "jei.spw.ignoreconditions"),
				new SpawnerWrapper(SpawnerModifiers.checkPlayers, "ignore_players", true, "jei.spw.ignoreplayers"),
				new SpawnerWrapper(SpawnerModifiers.ignoreCap, "ignore_cap", true, "jei.spw.ignorecap"),
				new SpawnerWrapper(SpawnerModifiers.redstone, "redstone_control", true, "jei.spw.redstone"),
				new SpawnerWrapper(egg, new ResourceLocation("witch"), "jei.spw.changeentity"),
				new SpawnerInverseWrapper()
				), SPAWNER);
		//Formatter:on

		reg.addIngredientInfo(new ItemStack(Blocks.MOB_SPAWNER), VanillaTypes.ITEM, Placebo.PROXY.translate("jei.spw.instructions", Enchantments.SILK_TOUCH.getTranslatedName(SpawnerModule.spawnerSilkLevel)));
		reg.addIngredientInfo(new ItemStack(Items.SPAWN_EGG), VanillaTypes.ITEM, "jei.spw.capturing");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		if (!Apotheosis.enableSpawner) return;
		reg.addRecipeCategories(new SpawnerCategory(reg.getJeiHelpers().getGuiHelper()));
	}

}
