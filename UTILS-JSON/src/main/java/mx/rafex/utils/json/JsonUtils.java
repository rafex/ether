package mx.rafex.utils.json;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static Gson obtenerConvertidorJson(final GsonBuilder builder, final boolean serializeNulls) {

        if (serializeNulls) {
            builder.serializeNulls();
        }

        return builder.create();
    }

    public static Gson obtenerConvertidorJson() {
        return obtenerConvertidorJson(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping(), false);
    }

    public static <T> T aJson(final String json, final Class<T> clazz) {
        return JsonUtils.obtenerConvertidorJson().fromJson(json, clazz);
    }

    public static String aJson(final Object object) {
        return JsonUtils.obtenerConvertidorJson().toJson(object);
    }

    public static String aJsonExcludeFieldsWithoutExposeAnnotation(final Object object) {
        return JsonUtils.obtenerConvertidorJson(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().disableHtmlEscaping(), false).toJson(object);
    }

    public static void validarJson(final String esquemaJson, final String json, final Class<?> clazz) {
        final JSONObject jsonSchema = new JSONObject(new JSONTokener(clazz.getResourceAsStream(esquemaJson)));
        final JSONObject jsonSubject = new JSONObject(new JSONTokener(json));
        final Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
    }

}
