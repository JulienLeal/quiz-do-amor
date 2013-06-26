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
	private int pontos;
	private int rodada = 1;
	
	private String pergunta;
	private String[] pecas;
	
	private boolean turn = false;
	
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
		// Tratamento da mensagem
		final String[] protocol = mensagem.split("/");
		
		// Criar Constantes em uma classe para isso;
		if(protocol[0].equals("Pergunta"))
		{
			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					((Chat)MinhasCoisas.getCurrentActivity()).ChangePergunta(protocol[1]);
				}
			});
			
		}else if(protocol[0].equals("Botao Certo"))
		{			
			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					((Chat)MinhasCoisas.getCurrentActivity()).ChangeVisualization(turn);
				}
			});
			this.turn = !turn;	
			this.pontos++;
			MinhasCoisas.Show(String.valueOf(pontos) + " pontos");
			
				
		}else if(protocol[0].equals("Botao Errado"))
		{			
			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					((Chat)MinhasCoisas.getCurrentActivity()).ChangeVisualization(turn);
				}
			});
			this.turn = !turn;	
			MinhasCoisas.Show(String.valueOf(pontos) + " pontos");
		}else if(protocol[0].equals("Resposta"))
		{
			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					((Chat)MinhasCoisas.getCurrentActivity())
				}
			});
		}
		//MinhasCoisas.Show(String.valueOf(this.turn));
	}
	
	public void Reset()
	{
		// zera todas as variáveis
	}
}
