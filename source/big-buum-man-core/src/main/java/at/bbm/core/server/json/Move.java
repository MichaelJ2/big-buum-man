package at.bbm.core.server.json;

public class Move extends JsonObject {

    private static final String TYPE = "mov";
    private static final String DATA = "\"dir\":\"%s\"";

    public Move(final String paramDirection) {
        super(TYPE, String.format(DATA, paramDirection));
    }
}
