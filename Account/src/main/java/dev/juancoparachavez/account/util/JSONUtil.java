package dev.juancoparachavez.account.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.juancoparachavez.account.exception.CustomExceptions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtil() {
    }

    public static String toJSON(Object object) throws Exception {
        try {
            return convertToJSONString(object);
        } catch (JsonProcessingException e) {
            throw new Exception(e);
        }
    }


    public static <T> T fromJson(String message, Class<T> clazz) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.readValue(message, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new CustomExceptions.GenericException(e.getMessage());
        }
    }

    public static String convertToJSONString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static <T> T deserialize(String message, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(message, clazz);
    }

    public static <T> List<T> deserializeList(String message, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(message, getObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
