package com.example.lovegame_project;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothConnectionManager implements Runnable{

	private BluetoothServerSocket mBtServerSocket;
	private BluetoothSocket btSocket;
	private final String NAME = "LOVE";
	private final UUID mUUID;
	private final String TAG = "Bluetooth Connection Manager";
	private static boolean alive = true;
	private Context context;
	
	public BluetoothServerSocket getmBtServerSocket() {
		return mBtServerSocket;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public BluetoothConnectionManager(Context context) {
		
		Log.i(TAG, "Bluetooth Connection Manager criado com sucesso");
		
		this.context = context;
		mUUID = UUID.fromString("b4c5bcd0-a9ec-11e2-9e96-0800200c9a66");
		
		BluetoothServerSocket tmp = null;
		
		try {
			tmp = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(NAME, mUUID);
		} catch (IOException e) {
			MinhasCoisas.Show("Erro ao criar servidor Bluetooth");
		
		}
		mBtServerSocket = tmp;
		
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	@Override
	public void run() {
		try {
			
			btSocket = mBtServerSocket.accept();
			
			Log.i(TAG, "Aceitou uma nova conexão");
			MinhasCoisas.Show("Aceitou uma nova conexão.");
			
		} catch (IOException e) {
			
			Log.i(TAG, "Erro ao tentar receber socket de conexão");
			MinhasCoisas.Show("Erro ao aceitar conexão. Tente novamente");
			e.printStackTrace();
		} 
			
		if(btSocket != null)
		{
			
			Cliente cs = new Cliente(btSocket, false);
			MinhasCoisas.setCliente(cs);
			// Configuramos o primeiro a responder
			
			JogoEmSi.get().setTurn(true);
			try {
				mBtServerSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}					
			
		}
	}		
	
	
	public static void Cancel()
	{
		
		alive = false;
	}
}
