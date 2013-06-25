package com.example.lovegame_project;

import android.widget.Toast;

public class DebugLog {
	private static DebugLog instance;
	public static DebugLog Get_Instance()
	{
		return instance;
		
	}
	
	private DebugLog()
	{
		
	}
	
	void Mensagem(String msg) {
		//  apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(Love_Game.context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
