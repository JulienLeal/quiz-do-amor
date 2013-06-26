package com.example.lovegame_project;

import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JogoEmSi {

	private static JogoEmSi instance;
	private String TAG = "Jogo em Si";
	public int pontos;
	public int rodada = 1;
	private String[] pecas;
	private boolean turn = false;

	public static String[] handleString;

	public boolean getTurn()
	{
		return this.turn;
	}
	public void setTurn(boolean turn)
	{
		this.turn = turn;
	}
	private JogoEmSi() {
	}

	public static JogoEmSi get()
	{
		if(instance == null)
		{
			instance = new JogoEmSi();
		}

		return instance;
	}

	public void Send(String mensagem)
	{

		try{
			MinhasCoisas.getCliente().write(mensagem);

		}catch(Exception e)
		{
			Log.i(TAG, "Erro", e);
			MinhasCoisas.Show("Desculpas. Tente novamente.");
		}
	}

	public void Handle(String mensagem)
	{	
		this.handleString = null;

		// Tratamento da mensagem
		this.handleString = mensagem.split("/");


		// Criar Constantes em uma classe para isso;
		if(handleString[0].equals("Pergunta"))
		{	
			try{
				MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
					@Override
					public void run() {

						((Chat)MinhasCoisas.getCurrentActivity()).ChangePergunta(JogoEmSi.handleString[1]);

					}
				});}catch(Exception e)
				{
					Log.i(TAG, "Erro ao fazer cast da activity Chat");
				}

		}else if(handleString[0].equals("Botao Certo"))
		{
			this.turn = false;
			this.rodada++;
			this.pontos++;
			this.TestarFim();
			MinhasCoisas.Show(String.valueOf(pontos) + " pontos");
			MinhasCoisas.Show(String.valueOf( this.rodada));

			try{
				MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
					@Override
					public void run() {

						((Chat)MinhasCoisas.getCurrentActivity()).ChangeVisualization(turn);
						((Chat)MinhasCoisas.getCurrentActivity()).NovaPergunta();
					}
				});}
			catch(Exception e)
			{
				Log.i(TAG, "Erro ao fazer cast da activity Chat");
			}


		}else if(handleString[0].equals("Botao Errado"))
		{			
			this.turn = false;	
			this.rodada++;
			MinhasCoisas.Show(String.valueOf(pontos) + " pontos");
			MinhasCoisas.Show(String.valueOf( this.rodada));
			this.TestarFim();
			try{
				MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
					@Override
					public void run() {

						((Chat)MinhasCoisas.getCurrentActivity()).ChangeVisualization(turn);
						((Chat)MinhasCoisas.getCurrentActivity()).NovaPergunta();
					}
				});}catch(Exception e)
				{
					Log.i(TAG, "Erro ao fazer cast da activity Chat");
				}

		}else if(handleString[0].equals("Resposta"))
		{
			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					String resposta = "";
					for(int i = 1; i < JogoEmSi.handleString.length; i++)
					{
						resposta += JogoEmSi.handleString[i];
					}

					((Chat)MinhasCoisas.getCurrentActivity()).SetResposta(resposta);
				}
			});
		}
	}


	public void TestarFim()
	{
		if(this.rodada == 10)
		{
			ChangeLayout.getInstance().changeLayout(MinhasCoisas.currentActivity, FimDeJogo.class);	
		}
	}

	public void Reset2()
	{
		this.instance = new JogoEmSi();
	}
	public void Reset()
	{
		this.pontos = 0;
		this.rodada = 0;
		this.turn = false;
		this.handleString = null;
	}
}
