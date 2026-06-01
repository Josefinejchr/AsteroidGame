package dk.sdu.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        loadPluginLayer();

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ModuleConfig.class);
        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();
    }

    /**
     * Loads modules from the plugins/ folder into a separate ModuleLayer
     * at runtime. This allows new enemy or feature modules to be added
     * by simply dropping a jar into the plugins folder — no recompilation
     * of Core or any other module is needed.
     *
     * This resolves the split package problem: modules in the plugin layer
     * are isolated in their own ClassLoader namespace, so they cannot
     * conflict with modules on the main module path.
     */
    private void loadPluginLayer() {
        try {
            Path pluginsDir = Path.of("plugins");
            if (!pluginsDir.toFile().exists()) {
                System.out.println("=== No plugins folder found, skipping layer load");
                return;
            }

            ModuleFinder pluginFinder = ModuleFinder.of(pluginsDir);
            ModuleLayer parentLayer = ModuleLayer.boot();

            List<String> pluginModuleNames = pluginFinder
                    .findAll()
                    .stream()
                    .map(m -> m.descriptor().name())
                    .toList();

            if (pluginModuleNames.isEmpty()) {
                System.out.println("=== No plugin modules found in plugins folder");
                return;
            }

            Configuration config = parentLayer
                    .configuration()
                    .resolve(pluginFinder, ModuleFinder.of(), pluginModuleNames);

            ClassLoader scl = ClassLoader.getSystemClassLoader();
            ModuleLayer pluginLayer = parentLayer
                    .defineModulesWithOneLoader(config, scl);

            System.out.println("=== Plugin layer loaded successfully");
            System.out.println("=== Modules in plugin layer: " + pluginModuleNames);

        } catch (Exception e) {
            System.err.println("=== Plugin layer failed to load: " + e.getMessage());
        }
    }
}