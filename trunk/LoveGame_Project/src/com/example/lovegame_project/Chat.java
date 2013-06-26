package com.example.lovegame_project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Chat extends Activity {

	private static final String TAG = "CHAT";

	private String pergunta;
	
	private Button bt_enviar;
	private EditText edit_resposta;
	private TextView textv_pergunta;
	private TextView textv_resposta;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Criar classe para mudar orientacao e mexer com full screen etc
		// Screen adjustments
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		setContentView(R.layout.activity_chat);
		
		MinhasCoisas.setCurrentActivity(this);
		
		
		this.bt_enviar = (Button)findViewById( R.id.bt_enviar);
		this.edit_resposta = (EditText)findViewById( R.id.edit_resposta);
		this.textv_pergunta = (TextView)findViewById(R.id.pergunta);
		this.textv_resposta = (TextView)findViewById(R.id.resposta);
		
		if(this.bt_enviar != null)
		{
			MinhasCoisas.Show("n Nulo");
			this.bt_enviar.setText("Thauan Lopes");
		}
	}

	public void ChangeVisualization(boolean turn)
	{

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	public void onClick_SendButton(View v)
	{
		Log.i(TAG, "tentativa de enviar mensagem");
		EditText editText = (EditText) findViewById(R.id.edit_resposta);
		
		
		JogoEmSi.get().Send(editText.getText().toString());
	}

	public void ChangeInterface(boolean turn)
	{
		if(turn)
		{
			this.bt_enviar.setEnabled(true);
			this.edit_resposta.setEnabled(true);
			this.textv_resposta.setEnabled(false);
			
		}else
		{
			this.bt_enviar.setEnabled(false);
			this.edit_resposta.setEnabled(false);
			this.textv_resposta.setEnabled(true);
		}
	}
	
	public void MudarPergunta()
	{

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//MinhasCoisas.getCliente().cancel();

	}
}
