package at.bbm.core.server.json;

abstract class JsonObject {

    private static final String JSON = "{\"type\":\"%s\",\"data\":{%s}}";

    private final String type;
    private final String data;

    protected JsonObject(final String paramType, final String paramData) {
        this.type = paramType;
        this.data = paramData;
    }

    public String toJSONString() {
        return String.format(JSON, type, data);
    }
}
