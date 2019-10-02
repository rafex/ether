package mx.rafex.utils.rest.contenttypes;

public enum ContentType {

    APPLICATION_JSON("application/json"), TEXT_HTML("text/html"), APPLICATION_JSON_UTF8("application/json; charset=utf-8");

    private String value;

    public String geValue() {
        return value;
    }

    private ContentType(final String value) {
        this.value = value;
    }
}
