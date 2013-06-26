package com.example.lovegame_project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FimDeJogo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Adjust screen definitions 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		setContentView(R.layout.activity_fim_de_jogo);

		//MinhasCoisas.setCurrentActivity(this);
		
		SetBgAndScore();
		
	}
	private void SetBgAndScore()
	{
		RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.layout_fimdejogo);
		
		int pontos = JogoEmSi.get().pontos;
		
		TextView textpontos = (TextView)findViewById(R.id.texto_pontos);
		textpontos.setText(String.valueOf(pontos + " pontos"));
		
		if(pontos <= 1)
		{
			myLayout.setBackgroundResource(R.drawable.fim_0a1_acertos);
		}else if(pontos <= 5)
		{
			myLayout.setBackgroundResource(R.drawable.fim_2a5_acertos);
		}else if(pontos <= 8)
		{
			myLayout.setBackgroundResource(R.drawable.fim_6a8_acertos);
		}else
		{
			myLayout.setBackgroundResource(R.drawable.fim_9a10_acertos);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fim_de_jogo, menu);
		return true;
	}

}
