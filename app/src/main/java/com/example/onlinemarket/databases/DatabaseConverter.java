package com.example.onlinemarket.databases;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class DatabaseConverter {
    @TypeConverter
    public static String conListStrToStr(List<String> stringList) {
        if (stringList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.toJson(stringList, type);
    }

    @TypeConverter
    public static List<String> conStrToListStr(String str) {
        if (str == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(str, type);
    }

    @TypeConverter
    public static Date conLongToDate(Long value){
        return value==null ? null : new Date(value);
    }

    @TypeConverter
    public static Long conLongToDate(Date date){
        return date==null ? null : date.getTime();
    }
}
