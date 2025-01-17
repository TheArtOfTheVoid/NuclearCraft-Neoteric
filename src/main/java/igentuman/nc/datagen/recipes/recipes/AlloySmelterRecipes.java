package igentuman.nc.datagen.recipes.recipes;

import igentuman.nc.content.processors.Processors;
import igentuman.nc.content.materials.Materials;
import net.minecraft.data.recipes.FinishedRecipe;
import java.util.function.Consumer;

public class AlloySmelterRecipes extends AbstractRecipeProvider {

    public static void generate(Consumer<FinishedRecipe> consumer) {
        AlloySmelterRecipes.consumer = consumer;
        ID = Processors.ALLOY_SMELTER;

        doubleToItem(ID, dustIngredient(Materials.coal, 4), dustIngredient(Materials.iron),
                ingotStack(Materials.steel));
        doubleToItem(ID, dustIngredient(Materials.copper, 3), dustIngredient(Materials.tin),
                ingotStack(Materials.bronze, 4));
        doubleToItem(ID, dustIngredient(Materials.boron), dustIngredient(Materials.steel),
                ingotStack(Materials.ferroboron, 2));
        doubleToItem(ID, dustIngredient(Materials.graphite), dustIngredient(Materials.diamond),
                ingotStack(Materials.hard_carbon, 2), 1d, 2d);
        doubleToItem(ID, dustIngredient(Materials.ferroboron), dustIngredient(Materials.lithium),
                ingotStack(Materials.tough_alloy, 2), 1.5d, 1.5d);
        doubleToItem(ID, dustIngredient(Materials.magnesium), dustIngredient(Materials.boron, 2),
                ingotStack(Materials.magnesium_diboride, 3));
        doubleToItem(ID, dustIngredient(Materials.lithium), dustIngredient(Materials.manganese_dioxide),
                ingotStack(Materials.lithium_manganese_dioxide, 2), 1.5d);
        doubleToItem(ID, dustIngredient(Materials.copper, 3), dustIngredient(Materials.silver),
                ingotStack(Materials.shibuichi, 4), 1.5d);
        doubleToItem(ID, dustIngredient(Materials.tin, 3), dustIngredient(Materials.silver),
                ingotStack(Materials.tin_silver, 4));
        doubleToItem(ID, dustIngredient(Materials.lead, 3), dustIngredient(Materials.platinum),
                ingotStack(Materials.lead_platinum, 4), 1.5d);
        doubleToItem(ID, dustIngredient(Materials.tough_alloy), dustIngredient(Materials.hard_carbon),
                ingotStack(Materials.extreme, 2));
        doubleToItem(ID, dustIngredient(Materials.boron_arsenide), dustIngredient(Materials.extreme),
                ingotStack(Materials.thermoconducting, 2), 1.5d, 1.5d);
        doubleToItem(ID, gemIngredient(Materials.silicon), dustIngredient(Materials.graphite),
                ingotStack(Materials.silicon_carbide, 2), 2d, 2d);
        doubleToItem(ID, dustIngredient(Materials.zirconium, 7), dustIngredient(Materials.tin),
                ingotStack(Materials.zircaloy, 8));
        doubleToItem(ID, dustIngredient(Materials.iron, 15), dustIngredient(Materials.carbon_manganese),
                ingotStack(Materials.hsla_steel, 16),8D, 2D);
        doubleToItem(ID, dustIngredient(Materials.molybdenum, 15), dustIngredient(Materials.zirconium),
                ingotStack(Materials.zirconium_molybdenum, 16),8D, 2D);

        doubleToItem(ID, dustIngredient(Materials.coal, 4), ingotIngredient(Materials.iron),
                ingotStack(Materials.steel));
        doubleToItem(ID, ingotIngredient(Materials.copper, 3), ingotIngredient(Materials.tin),
                ingotStack(Materials.bronze, 4));
        doubleToItem(ID, ingotIngredient(Materials.boron), ingotIngredient(Materials.steel),
                ingotStack(Materials.ferroboron, 2));
        doubleToItem(ID, ingotIngredient(Materials.ferroboron), ingotIngredient(Materials.lithium),
                ingotStack(Materials.tough_alloy, 2), 1.5d, 1.5d);
        doubleToItem(ID, ingotIngredient(Materials.magnesium), ingotIngredient(Materials.boron, 2),
                ingotStack(Materials.magnesium_diboride, 3));
        doubleToItem(ID, ingotIngredient(Materials.lithium), ingotIngredient(Materials.manganese_dioxide),
                ingotStack(Materials.lithium_manganese_dioxide, 2), 1.5d);
        doubleToItem(ID, ingotIngredient(Materials.copper, 3), ingotIngredient(Materials.silver),
                ingotStack(Materials.shibuichi, 4), 1.5d);
        doubleToItem(ID, ingotIngredient(Materials.tin, 3), ingotIngredient(Materials.silver),
                ingotStack(Materials.tin_silver, 4));
        doubleToItem(ID, ingotIngredient(Materials.lead, 3), ingotIngredient(Materials.platinum),
                ingotStack(Materials.lead_platinum, 4), 1.5d);
        doubleToItem(ID, ingotIngredient(Materials.tough_alloy), ingotIngredient(Materials.hard_carbon),
                ingotStack(Materials.extreme, 2));
        doubleToItem(ID, ingotIngredient(Materials.boron_arsenide), ingotIngredient(Materials.extreme),
                ingotStack(Materials.thermoconducting, 2), 1.5d, 1.5d);
        doubleToItem(ID, ingotIngredient(Materials.zirconium, 7), ingotIngredient(Materials.tin),
                ingotStack(Materials.zircaloy, 8));
        doubleToItem(ID, ingotIngredient(Materials.iron, 15), ingotIngredient(Materials.carbon_manganese),
                ingotStack(Materials.hsla_steel, 16),8D, 2D);
        doubleToItem(ID, ingotIngredient(Materials.molybdenum, 15), ingotIngredient(Materials.zirconium),
                ingotStack(Materials.zirconium_molybdenum, 16),8D, 2D);
    }
}
