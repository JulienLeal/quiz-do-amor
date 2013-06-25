package com.example.lovegame_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Menu_main extends Activity {

	BluetoothAdapter btAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Screen adjustments
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		MinhasCoisas.setCurrentActivity(this);
		setContentView(R.layout.activity_menumain);

		//MinhasCoisas.setGameFonte(Typeface.createFromAsset(getAssets(),"fonts/fonte.TTF"));
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();


		ImageButton	btcomecar = (ImageButton)findViewById (R.id.bt_comecar);
		ImageButton btinstrucoes = (ImageButton)findViewById (R.id.bt_instrucoes);
		ImageButton btcreditos = (ImageButton)findViewById (R.id.bt_creditos);

		MinhasCoisas.Show("Bem vindo");
		PegarPergunta();
		//Start button 
		btcomecar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try{

					SoundManager.getInstance().playSound(R.raw.test_sound, "Teste");

					if(btAdapter != null)
					{
						//MinhasCoisas.Show("Este telefone possui tecnologia Bluetooth");

						if (!btAdapter.isEnabled()) {

							Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivityForResult(enableBtIntent, REQUEST.REQUEST_ENABLE_BT);
						}else
						{
							// Change Activity
							ChangeLayout.getInstance().changeLayout(Menu_main.this, DeviceList.class);
							return;
						}	
					}else
					{
						MinhasCoisas.Show("Este telefone não possui tecnologia Bluetooth");
					}

				}catch(Exception e)
				{
					Mensagem("erro"+e.getMessage());
				}	
			}	
		});

	}
	public void click_Creditos(View v)
	{
		ChangeLayout.getInstance().changeLayout(Menu_main.this,Creditos.class);

	}
	public void click_Instrucoes(View v)
	{
		ChangeLayout.getInstance().changeLayout(Menu_main.this,Instrucoes.class);

	}
	public void PegarPergunta()
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
		
		mainClass.jp = JsonPut.getInstance();
		mainClass.jp.declareObject(jsonString);
		//Resources res = getResources();
		
		MinhasCoisas.Show(mainClass.jp.getJson("Pergunta"+2));	
	
	}
	public void click_Achievements(View v)
	{
		ChangeLayout.getInstance().changeLayout(Menu_main.this,Achievements_menu.class);
	}
	public void click_enviar_pergunta(View v)
	{
		ChangeLayout.getInstance().changeLayout(Menu_main.this,MandarPergunta.class);
	}
	private void Mensagem(String msg) {
		//apenas informa no rodapé inferior da tela do Android o ocorrido
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		try{
			this.btAdapter.disable();
		}catch(Exception e){}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}
