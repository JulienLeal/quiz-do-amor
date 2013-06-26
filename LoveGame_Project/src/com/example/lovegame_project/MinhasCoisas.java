package com.example.lovegame_project;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Toast;

public class MinhasCoisas{
	
	public static Activity currentActivity;
	private static Cliente cliente;
	private static Typeface gameFonte;
	
	
	public static Typeface getGameFonte() {
		return gameFonte;
	}
	public static void setGameFonte(Typeface gameFonte) {
		MinhasCoisas.gameFonte = gameFonte;
	}
	public static Cliente getCliente() {
		return cliente;
	}
	public static void setCliente(Cliente cliente) {
		MinhasCoisas.cliente = cliente;
	}
	public static Activity getCurrentActivity() {
		return currentActivity;
	}
	public static void setCurrentActivity(Activity act)
	{
		currentActivity = act;
	}
	public static void Show(final String msg)
	{
		currentActivity.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				Toast.makeText(currentActivity, msg, Toast.LENGTH_SHORT ).show();	
			}
		});
	}

}
