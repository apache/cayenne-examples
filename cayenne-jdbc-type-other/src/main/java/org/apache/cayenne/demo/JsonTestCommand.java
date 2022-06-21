package org.apache.cayenne.demo;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.demo.model.TestJson;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SQLExec;
import org.apache.cayenne.value.Json;

/**
 * Command that demonstrates usage of the {@link Json} type
 */
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
        Pojo pojo = new Pojo(123, "abc", false);

        TestJson testJson = context.newObject(TestJson.class);
        testJson.setJson(objectMapper.valueToTree(pojo));
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

    /**
     * Simple value-object to convert to Json
     */
    static class Pojo {
        private final int test;
        private final String field1;
        private final boolean field2;

        Pojo(int test, String field1, boolean field2) {
            this.test = test;
            this.field1 = field1;
            this.field2 = field2;
        }

        public int getTest() {
            return test;
        }

        public String getField1() {
            return field1;
        }

        public boolean isField2() {
            return field2;
        }
    }
}
