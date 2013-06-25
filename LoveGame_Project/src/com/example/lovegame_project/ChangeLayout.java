package com.example.lovegame_project;

import android.app.Activity;
import android.content.Intent;

public class ChangeLayout {
	
	private static ChangeLayout instance;
	private static Intent trocatela;
	
	public static ChangeLayout getInstance()
	{
		if(instance == null)
		{
			instance = new ChangeLayout();
		}
		
		return instance;
		
	}
	
	public void changeLayout(Activity Class1,Class<?> Class2)
	{
		trocatela = new Intent(Class1,Class2);	
		Class1.startActivity(trocatela);
		//Class1.finish();
	}
}
