package at.bbm.core.network.json;

public class Register extends JsonObject {

    private static final String TYPE = "reg";
    private static final String DATA = "\"name\":\"%s\"";

    public Register(final String paramName) {
        super(TYPE, String.format(DATA, paramName));
    }
}
