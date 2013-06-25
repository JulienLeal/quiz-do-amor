package com.example.lovegame_project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Iterator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class JsonPut 
{
	private static JsonPut instance;
	JsonObject obj = new JsonObject();
	JsonObject jsonObject;
	
	private String message;
	private JsonPut()
	{

	}
	
	public static JsonPut getInstance()
	{
		if(instance == null)
			instance = new JsonPut();
		
		return instance;
	}
	
	
	public void declareObject(String value)
	{
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(value);
		jsonObject = (JsonObject) obj;
	}
	
	public String getJson(String key)
	{
		
		message = this.jsonObject.get(key).toString();
			System.out.println(message);			
		
		return message;
		
	}
	

}
