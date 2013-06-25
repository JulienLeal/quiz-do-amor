package com.example.lovegame_project;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class mainClass {

	static JsonPut jp;
	public int RandomInt;
	public Random1 random;
	/**
	 * @param args
	 */
	public mainClass() 
	{	
		random = new Random1();
		
		
		jp = JsonPut.getInstance();
		
		jp.getJson("Pergunta"+random.randomInt());

		
	}

}
