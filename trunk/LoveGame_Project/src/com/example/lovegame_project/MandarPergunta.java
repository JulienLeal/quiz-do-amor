package com.example.lovegame_project;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MandarPergunta extends Activity {
	private TextView txtNomeArq;
	private TextView txtSalvar;
	CreateDB access;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		access = new CreateDB(this, "LoveGame_Perguntas", null, 1);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try {
			setContentView(R.layout.activity_mandar_pergunta);

			txtSalvar = (TextView) findViewById(R.id.nomeTxt);

		} catch (Exception e) {

		}

	}

	public void click_enviarSalvo(View v) {
		SalvarPergunta();
	}

	public void click_voltar(View v) {
		ChangeLayout.getInstance().changeLayout(MandarPergunta.this, Menu_main.class);
	}

	void SalvarPergunta() {
		// /Trecho DBLOCAL em que salvo a pergunta desejada no banco

		access.Add(access.Length() + 1, txtSalvar.getText().toString(), 0);

		Mensagem("A pergunta:'" + access.Queery(access.Length())
				+ "' foi salva com sucesso!");

	}

	private void Mensagem(String msg) {
		// apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mandar_pergunta, menu);
		return true;
	}

}
