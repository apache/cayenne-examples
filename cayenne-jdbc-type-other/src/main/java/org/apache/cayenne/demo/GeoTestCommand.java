package org.apache.cayenne.demo;

import javax.inject.Inject;
import javax.inject.Provider;

import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.demo.model.TestGeo;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SQLExec;
import org.apache.cayenne.value.Wkt;

/**
 * Command that demonstrates usage of the {@link Wkt} type
 */
public class GeoTestCommand extends CommandWithMetadata {

    @Inject
    Provider<ServerRuntime> serverRuntimeProvider;

    public GeoTestCommand() {
        super(CommandMetadata.builder(GeoTestCommand.class).build());
    }

    @Override
    public CommandOutcome run(Cli cli) {
        ServerRuntime serverRuntime = serverRuntimeProvider.get();
        ObjectContext context = serverRuntime.newContext();

        SQLExec.query("DELETE FROM test_geo").execute(context);

        TestGeo testGeo = context.newObject(TestGeo.class);
        testGeo.setArea(new Wkt("POLYGON ((30 10, 40 40, 20 40, 40 50, 30 10))"));
        context.commitChanges();

        ObjectSelect.query(TestGeo.class).select(context)
                .forEach(geo -> System.out.println(geo.getArea().getWkt()));

        return CommandOutcome.succeeded();
    }

}
