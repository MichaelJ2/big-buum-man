package at.bbm.core.server.json;

import at.bbm.core.GlobalProperties;

abstract class JsonObject {

    private static final String JSON = "{\"id\":\"%s\",\"type\":\"%s\",\"data\":{%s}}";

    private final String json;

    protected JsonObject(final String paramType, final String paramData) {
        this.json = String.format(JSON, GlobalProperties.UUID, paramType, paramData);
    }

    public String getJSON() {
        return this.json;
    }
}
