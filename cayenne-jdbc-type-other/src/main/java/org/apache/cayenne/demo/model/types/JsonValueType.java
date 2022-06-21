package org.apache.cayenne.demo.model.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.access.types.ValueObjectType;
import org.apache.cayenne.value.Json;
import org.apache.cayenne.di.Inject;

/**
 * This value type allows to use Jackson's {@link ObjectNode} directly as a Json value
 */
public class JsonValueType implements ValueObjectType<ObjectNode, Json> {

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Class<Json> getTargetType() {
        return Json.class;
    }

    @Override
    public Class<ObjectNode> getValueType() {
        return ObjectNode.class;
    }

    @Override
    public ObjectNode toJavaObject(Json value) {
        try {
            return (ObjectNode) objectMapper.readTree(value.getRawJson());
        } catch (Exception e) {
            throw new CayenneRuntimeException("Unable to parse JSON", e);
        }
    }

    @Override
    public Json fromJavaObject(ObjectNode object) {
        return new Json(object.toString());
    }

    @Override
    public String toCacheKey(ObjectNode object) {
        return object.toString();
    }
}
