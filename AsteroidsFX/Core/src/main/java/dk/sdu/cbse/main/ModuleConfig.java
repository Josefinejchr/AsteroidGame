package dk.sdu.cbse.main;

import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


//Wires the three service lists into Game as injected dependencies.
@Configuration
class ModuleConfig {

    @Bean
    public List<IGamePluginService> pluginServices() {
        return ServiceLoader.load(IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    @Bean
    public Game game() {
        System.out.println("=== Plugins found: " + pluginServices().size());
        System.out.println("=== Processors found: " + entityProcessingServices().size());
        System.out.println("=== PostProcessors found: " + postEntityProcessingServices().size());
        return new Game(
                pluginServices(),
                entityProcessingServices(),
                postEntityProcessingServices()
        );
    }

}