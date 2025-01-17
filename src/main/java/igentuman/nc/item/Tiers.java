package igentuman.nc.item;

import java.util.function.Supplier;

import igentuman.nc.content.materials.Materials;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import static igentuman.nc.datagen.recipes.recipes.AbstractRecipeProvider.ingotIngredient;
import static igentuman.nc.setup.registration.NCItems.LITHIUM_ION_CELL;

public enum Tiers implements Tier {
   TOUGH(6, 10000, 12.0F, 10.0F, 22, () -> {
      return ingotIngredient(Materials.tough_alloy);
   }),
   QNP(7, 50000, 20.0F, 14.0F, 25, () -> {
      return Ingredient.of(LITHIUM_ION_CELL.get());
   });

   private final int level;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final LazyLoadedValue<Ingredient> repairIngredient;

   private Tiers(int pLevel, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
      this.level = pLevel;
      this.uses = pUses;
      this.speed = pSpeed;
      this.damage = pDamage;
      this.enchantmentValue = pEnchantmentValue;
      this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
   }

   public int getUses() {
      return this.uses;
   }

   public float getSpeed() {
      return this.speed;
   }

   public float getAttackDamageBonus() {
      return this.damage;
   }

   public int getLevel() {
      return this.level;
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }
}
