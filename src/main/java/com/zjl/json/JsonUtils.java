package com.zjl.json;

import java.util.Map;

import com.google.gson.Gson;

public class JsonUtils {
	private static final Gson GSON = new Gson();
	public static String map2Json(Map m) {
		return GSON.toJson(m);
	}
}
