package com.zy.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Name;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-24 17:20
 **/
//用的jackjson
// 对象序列化是一个用于将对象状态转换为字节流的过程，
// 可以将其保存到磁盘文件中或通过网络发送到任何其他程序；
// 从字节流创建对象的相反的过程称为反序列化。
// 而创建的字节流是与平台无关的，在一个平台上序列化的对象可以在不同的平台上反序列化。
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @Nullable
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
/**
 * 可以把复杂的对象数组，集合序列化反序列化
 */

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        String name;
        Integer age;
}


    public static void main(String[] args) {
//        User user=new User("kate",11);
        //to string
        //序列化
//        String json = toString(user);
//        System.out.println("json==="+json);
        //反序列化
//        User user1=toBean(json,User.class);
//        System.out.println("user1===="+user1);
        //toList
 /*       String json="[22,-22,11,32,0]";
        List<Integer> list = toList(json, Integer.class);
        System.out.println("list==="+list);
        //tomap
        //language=JSON
        String jsons="{\n" +
                "  \"name\": \"jack\",\"age\": \"22\"\n" +
                "}";
        Map<String, String> map = toMap(jsons, String.class, String.class);
        System.out.println("map==="+map);*/
        //复杂XX
        String json3="[{\n" +
                "  \"name\": \"zhangsan\",\n" +
                "  \"age\": \"16\"\n" +
                "},{\n" +
                "  \"name\": \"lisi\",\n" +
                "  \"age\": \"22\"\n" +
                "}]";
        List<Map<String,String>> maps = nativeRead(json3, new TypeReference<List<Map<String, String>>>() {
        });
      for (Map<String,String>mapl:maps){
          System.out.println("mapl===="+mapl);
      }
    }
}
