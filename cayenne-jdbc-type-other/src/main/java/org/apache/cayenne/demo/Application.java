package org.apache.cayenne.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootique.BQCoreModule;
import io.bootique.BQModuleProvider;
import io.bootique.Bootique;
import io.bootique.cayenne.v42.CayenneModule;
import io.bootique.di.BQModule;
import io.bootique.di.BaseBQModule;
import org.apache.cayenne.configuration.server.ServerModule;
import org.apache.cayenne.demo.model.types.JsonValueType;

public class Application extends BaseBQModule implements BQModuleProvider {

    public static void main(String[] args) {
        Bootique.app(args)
                .args("--config=classpath:config.yml")
                .module(b -> BQCoreModule.extend(b)
                        .addCommand(GeoTestCommand.class)
                        .addCommand(JsonTestCommand.class)
                )
                .module(b -> CayenneModule.extend(b)
                        .addModule(binder -> binder.bind(ObjectMapper.class).toProviderInstance(ObjectMapper::new))
                        .addModule(binder -> ServerModule.contributeValueObjectTypes(binder).add(JsonValueType.class))
                )
                .autoLoadModules()
                .exec()
                .exit();
    }

    @Override
    public BQModule module() {
        return new Application();
    }

}
