package com.xyj.oa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class JacksonUtil {
    private static Logger logger = LogManager.getLogger();

    private static ObjectMapper om = ObjectMapperProvider.createObjectMapper();

    /**
     * Constructs an object via a JSON literal.
     *
     * @param content    the JSON literal contents
     * @param clazz      type of the object to construct
     * @return the object parsed by reading the JSON contents
     */
    public static <T> T fromJson(String content, Class<T> clazz) {
        try {
            return om.readValue(content, clazz);
        } catch (Exception e) {
            logger.warn("Exception in fromJson, ", e);
            return null;
        }
    }

    /**
     * Deserialize json array string directly into a List of concrete class.
     *
     * @param content something like this: [{name: x}, {name: y}]
     * @param clazz   type of the element objects
     * @return a {@linkplain java.util.List} of objects by reading the JSON contents
     */
    public static <T> List<T> fromJsonArray(String content, Class<T> clazz) {
        try {
            return om.readValue(content,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            logger.warn("Exception in fromJson, ", e);
            return null;
        }
    }

    /**
     * Serializes a Java object into JSON.
     *
     * @param value    the Java object to seralize
     * @return a {@linkplain java.lang.String} with the serialized contents
     */
    public static String toJson(Object value) {
        try {
            return om.writeValueAsString(value);
        } catch (Exception e) {
            logger.warn("Exception in toJson, ", e);
            return null;
        }
    }
}
