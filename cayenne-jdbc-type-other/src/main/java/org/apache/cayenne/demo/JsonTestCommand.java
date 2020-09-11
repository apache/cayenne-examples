package org.apache.cayenne.demo;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.demo.model.TestJson;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SQLExec;

class JsonTestCommand extends CommandWithMetadata {

    @Inject
    Provider<ServerRuntime> serverRuntimeProvider;

    @Inject
    ObjectMapper objectMapper;

    public JsonTestCommand() {
        super(CommandMetadata.builder(JsonTestCommand.class).build());
    }

    @Override
    public CommandOutcome run(Cli cli) {

        ServerRuntime serverRuntime = serverRuntimeProvider.get();
        ObjectContext context = serverRuntime.newContext();
        SQLExec.query("DELETE FROM test_json").execute(context);

        createJsonObject(context);

        listKeys(context);

        getKey(context);

        return CommandOutcome.succeeded();
    }

    private void createJsonObject(ObjectContext context) {
        TestJson testJson = context.newObject(TestJson.class);
        ObjectNode objectNode = new ObjectNode(objectMapper.getNodeFactory());
        objectNode.put("test", 123);
        objectNode.put("field1", "abc");
        objectNode.put("field2", false);
        testJson.setJson(objectNode);
        context.commitChanges();
    }

    private void listKeys(ObjectContext context) {
        List<String> keys = ObjectSelect.query(TestJson.class)
                .column(TestJson.JSON.function("json_object_keys", String.class))
                .select(context);

        System.out.println("JSON keys: ");
        keys.forEach(k -> System.out.println("\t" + k));
    }

    private void getKey(ObjectContext context) {
        List<Integer> testValues = ObjectSelect.query(TestJson.class)
                .column(TestJson.JSON.operator("->", Integer.class, "test"))
                .select(context);

        System.out.println("JSON values for path 'test':");
        testValues.forEach(v -> System.out.println("\t" + v));
    }
}
