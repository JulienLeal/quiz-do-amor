package com.example.lovegame_project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Love_Game extends Activity {
	private TextView Pergunta;
	private TextView Salvar;

	static Context context;
	CreateDB access;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		access = new CreateDB(this, "LoveGame_Perguntas", null, 1);
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_love__game);

		Pergunta = (TextView) findViewById(R.id.Pergunta);
		Salvar = (TextView) findViewById(R.id.Resposta);

		// /Trecho DBLOCAL
		Pergunta.setText(access.Queery(access.Length()));
	}

	private void Mensagem(String msg) {
		// apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public void click_Voltar(View v) {

		ChangeLayout.getInstance().changeLayout(Love_Game.this, Menu_main.class);
	}

	public void click_AvaliarSim(View v) {
		// /Trecho DBLOCAL em que avalio a resposta como correta

		access.Update(access.Length(), 1);
		Mensagem("A pergunta: '"
				+ access.Queery(access.Length()) + "' aprovada pelo usuário");

	}

	public void click_AvaliarNao(View v) {
		// /Trecho DBLOCAL em que avalio a resposta como incorreta

		access.Update(access.Length(), 2);
		Mensagem("A pergunta: '"
				+ access.Queery(access.Length()) + "' desaprovada pelo usuário");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.love__game, menu);
		return true;
	}

}
