package com.srnjak.hateoas.test.utils;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    public static String prettify(String json) {
        StringReader sr = new StringReader(json);
        JsonReader jsonReader = Json.createReader(sr);
        JsonStructure jsonStructure = jsonReader.read();

        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);

        StringWriter sw = new StringWriter();
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.write(jsonStructure);
        jsonWriter.close();

        return sw.toString();
    }
}
