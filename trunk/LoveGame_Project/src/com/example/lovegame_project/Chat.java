package com.example.lovegame_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.widget.ImageButton;
import android.widget.TextView;

public class Chat extends Activity {

	private static final String TAG = "CHAT";

	private String pergunta;
	
	private Button bt_enviar;
	private EditText edit_resposta;
	private TextView textv_pergunta;
	private TextView textv_resposta;
	private ImageButton botaoErrado;
	private ImageButton botaoCerto;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Criar classe para mudar orientacao e mexer com full screen etc
		// Screen adjustments 
		//	Atenção: É preciso mudar em layout para horizontal, caso contrário serão 
		//	criadas duas activitys. Lembrar caso Toast.
		
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
		this.botaoCerto = (ImageButton)findViewById(R.id.botao_certo);
		this.botaoErrado = (ImageButton)findViewById(R.id.botao_errado);
		
		if(!JogoEmSi.get().getTurn())
		{
			NovaPergunta();
		}
		
		ChangeVisualization(JogoEmSi.get().getTurn());

	}
	public void ChangePergunta(String pergunta)
	{
		this.textv_pergunta.setText(pergunta);
	}
	
	public void NovaPergunta()
	{
		String pergunta = PegarPergunta();
		String protocol = "Pergunta/";
		JogoEmSi.get().Send(protocol + pergunta);
		this.textv_pergunta.setText(pergunta);
	}
	public String PegarPergunta()
	{
		InputStream is =(getResources().openRawResource(R.raw.mydata));
		String strContent;
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbfFileContents = new StringBuffer();
        String line = null;
       
        //read file line by line
        try {
			while( (line = bReader.readLine()) != null){
			        sbfFileContents.append(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        //finally convert StringBuffer object to String!
        strContent = sbfFileContents.toString();
        
        String jsonString = strContent;
        Random1 rnd = new Random1();
        
		mainClass.jp = JsonPut.getInstance();
		mainClass.jp.declareObject(jsonString);
		
		return mainClass.jp.getJson("Pergunta"+rnd.randomInt());
	}
	
	public void ChangeVisualization(boolean turn)
	{
		if(turn){
			// Turno de responder
			this.bt_enviar.setEnabled(true);
			this.bt_enviar.setVisibility(View.VISIBLE);
			
			this.edit_resposta.setEnabled(true);
			this.edit_resposta.setVisibility(View.VISIBLE);
			
			this.textv_resposta.setVisibility(View.INVISIBLE);
			
			this.botaoCerto.setEnabled(false);
			this.botaoCerto.setVisibility(View.INVISIBLE);
			
			this.botaoErrado.setEnabled(false);
			this.botaoErrado.setVisibility(View.INVISIBLE);
			
		}else
		{
			// Turno de corrigir
			this.bt_enviar.setEnabled(false);
			this.bt_enviar.setVisibility(View.INVISIBLE);
			
			this.edit_resposta.setEnabled(false);
			this.edit_resposta.setVisibility(View.INVISIBLE);
			
			this.textv_resposta.setVisibility(View.VISIBLE);
			
			this.botaoCerto.setEnabled(true);
			this.botaoCerto.setVisibility(View.VISIBLE);
			
			this.botaoErrado.setEnabled(true);
			this.botaoErrado.setVisibility(View.VISIBLE);	
		}
	}

	public void SetResposta(String resposta)
	{
		this.textv_resposta.setText(resposta);
	}
	
	
	public void onClickCerto(View v)
	{
		
	}
	public void onClickErrado(View v)
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
	
	public void MudarPergunta()
	{

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}
