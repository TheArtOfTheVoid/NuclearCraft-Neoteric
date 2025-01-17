package igentuman.nc.content.processors;

import igentuman.nc.block.entity.processor.NCProcessorBE;
import igentuman.nc.container.NCProcessorContainer;
import igentuman.nc.client.gui.processor.NCProcessorScreen;
import igentuman.nc.content.processors.config.ProcessorSlots;
import igentuman.nc.recipes.AbstractRecipe;
import igentuman.nc.recipes.serializers.NcRecipeSerializer;
import igentuman.nc.recipes.type.NcRecipe;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

import static igentuman.nc.compat.GlobalVars.RECIPE_CLASSES;

public class ProcessorBuilder <M extends NCProcessorContainer, U extends Screen & MenuAccess<M>>{
    public ProcessorPrefab processor;
    private ProcessorBuilder() { }

    public static ProcessorBuilder make(String name)
    {
        ProcessorBuilder builder = new ProcessorBuilder();
        builder.processor = new ProcessorPrefab(name);
        return builder;
    }

    public static ProcessorBuilder make(String name, int inFluids, int inItems, int outFluids, int outItems)
    {
        ProcessorBuilder builder = new ProcessorBuilder();
        builder.processor = new ProcessorPrefab(name, inFluids, inItems, outFluids, outItems);
        builder.container(NCProcessorContainer.class);
        if(FMLEnvironment.dist.isClient()){
            builder.screen(NCProcessorScreen::new);
        }
        return builder;
    }

    public ProcessorBuilder<M, U> container(Class container) {
        processor.setContainer(container);
        return this;
    }

    public ProcessorBuilder blockEntity(BlockEntityType.BlockEntitySupplier<? extends NCProcessorBE> be)
    {
        processor.setBlockEntity(be);
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public ProcessorBuilder screen(MenuScreens.ScreenConstructor<M, U> screenConstructor)
    {
        processor.setScreenConstructor(screenConstructor);
        return this;
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public ProcessorBuilder<M, U> screen(Object screenConstructor)
    {
        return this;
    }

    public ProcessorPrefab<?, ?> build()
    {
        return processor;
    }


    public ProcessorBuilder<?, ?> slotsConfig(ProcessorSlots config)
    {
        processor.slotsConfig = config;
        return this;
    }

    public ProcessorBuilder<?, ?> progressBar(int i) {
        processor.progressBar = i;
        return this;
    }

    public ProcessorBuilder<?, ?> recipeSerializer(Supplier<RecipeSerializer<? extends AbstractRecipe>> sup) {
        processor.recipeSerializerSupplier = sup;
        return this;
    }

    public ProcessorBuilder<?, ?> recipe(NcRecipeSerializer.IFactory<? extends NcRecipe> factory) {
        processor.recipeSerializerSupplier = () -> new NcRecipeSerializer<>(factory);

        return this;
    }

    public ProcessorBuilder<?, ?> upgrades(boolean energy, boolean speed) {
        processor.supportEnergyUpgrade = energy;
        processor.supportSpeedUpgrade = speed;
        return this;
    }

    public ProcessorBuilder<?, ?> withCatalyst() {
        processor.supportsCatalyst = true;
        return this;
    }

    public ProcessorBuilder<?, ?> setHiddenSlots(Integer... i) {
        for(int id: i) {
            processor.hiddenSlots.add(id);
        }
        return this;
    }
}
