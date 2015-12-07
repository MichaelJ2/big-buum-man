package at.bbm.core.server.json;

public class Register extends JsonObject {

    public Register(final String paramName) {
        super("reg", "\"name\":\"" + paramName + "\"");
    }
}
