package edu.uni.cs.syntaxdesigns.module;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.IOException;
import java.lang.reflect.Type;

public class JacksonConverter implements Converter {

    private static final String MIME_TYPE = "application/json; charset=UTF-8";
    private final ObjectMapper objectMapper;

    public JacksonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override public Object fromBody(TypedInput body, Type type) {
        try {
            final JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return objectMapper.readValue(body.in(), javaType);
        } catch (IOException e) {
            return null;
        }
    }

    @Override public TypedOutput toBody(Object object) {
        try {
            final String json = objectMapper.writeValueAsString(object);
            return new TypedByteArray(MIME_TYPE, json.getBytes("UTF-8"));
        } catch (IOException e) {
            return null;
        }
    }
}
