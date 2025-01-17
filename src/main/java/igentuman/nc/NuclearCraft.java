package igentuman.nc;

import igentuman.nc.handler.event.server.WorldEvents;
import igentuman.nc.handler.command.CommandNcPlayerRadiation;
import igentuman.nc.handler.command.CommandNcVeinCheck;
import igentuman.nc.handler.config.CommonConfig;
import igentuman.nc.radiation.data.PlayerRadiation;
import igentuman.nc.radiation.data.RadiationEvents;
import igentuman.nc.radiation.data.RadiationManager;
import igentuman.nc.radiation.data.WorldRadiation;
import igentuman.nc.network.PacketHandler;
import igentuman.nc.setup.ClientSetup;
import igentuman.nc.setup.ModSetup;
import igentuman.nc.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Set;

@Mod(NuclearCraft.MODID)
public class NuclearCraft {

    public static final Logger LOGGER = LogManager.getLogger();
    public boolean isNcBeStopped = false;
    public static final WorldEvents worldTickHandler = new WorldEvents();
    public static final String MODID = "nuclearcraft";
    public static NuclearCraft instance;
    private final PacketHandler packetHandler;

    /**
     * Sorry but has to load config before registration stage
     */
    @SuppressWarnings("unchecked")
    private void forceLoadConfig()
    {
        try {
            Method openConfig = ConfigTracker.INSTANCE.getClass()
                    .getDeclaredMethod("openConfig", ModConfig.class, Path.class);
            openConfig.setAccessible(true);
            Field configSets = ConfigTracker.INSTANCE.getClass().getDeclaredField("configSets");
            configSets.setAccessible(true);
            EnumMap<ModConfig.Type, Set<ModConfig>> configSetsValue = (EnumMap<ModConfig.Type, Set<ModConfig>>) configSets.get(ConfigTracker.INSTANCE);
            ModConfig ncConfig = null;
            for(ModConfig config : configSetsValue.get(ModConfig.Type.COMMON)) {
                if(config.getModId().equals(MODID)) {
                    ncConfig = config;
                    break;
                }
            }
            openConfig.invoke(ConfigTracker.INSTANCE, ncConfig, FMLPaths.CONFIGDIR.get());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.error("Unable to force load NC config. And this is why:");
            LOGGER.error(e);
        }
    }

    public NuclearCraft() {
        instance = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.spec);
        packetHandler = new PacketHandler();
        forceLoadConfig();
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::gameShuttingDownEvent);
        ModSetup.setup();
        Registration.init();
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(this::registerClientEventHandlers));
    }

    public static PacketHandler packetHandler() {
        return instance.packetHandler;
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON)
            CommonConfig.setLoaded();
    }

    private void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(CommandNcPlayerRadiation.register());
        event.getDispatcher().register(CommandNcVeinCheck.register());
    }
    private void registerClientEventHandlers(FMLClientSetupEvent event) {
        ClientSetup.registerEventHandlers(event);
    }

    public static ResourceLocation rl(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    private void serverStopped(ServerStoppedEvent event) {
        NuclearCraft.instance.isNcBeStopped = true;
        //stop capability tracking
        RadiationEvents.stopTracking();
        for(ServerLevel level: event.getServer().getAllLevels()) {
            RadiationManager.clear(level);
        }
    }
    private void gameShuttingDownEvent(GameShuttingDownEvent event) {
        NuclearCraft.instance.isNcBeStopped = true;
    }

    private void serverStarted(ServerStartedEvent event) {
        NuclearCraft.instance.isNcBeStopped = false;
        RadiationEvents.startTracking();
    }



    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(WorldRadiation.class);
        event.register(PlayerRadiation.class);
    }
}
