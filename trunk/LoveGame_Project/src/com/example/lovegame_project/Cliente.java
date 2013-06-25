package com.example.lovegame_project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Cliente implements Runnable{

	private Context context;
	private static final String TAG = "Cliente";

	private static BluetoothSocket mSocket;
	private UUID uuid;

	private InputStream mInputStream;
	private static OutputStream mOutputStream;

	private boolean connect = false;
	private boolean connected = true;

	private boolean turn = false;
	public Cliente(BluetoothDevice device, Context context, boolean connect) {

		Log.i(TAG, "Cliente criado com sucesso");

		this.connect = connect;
		this.context = context;
		uuid = UUID.fromString("b4c5bcd0-a9ec-11e2-9e96-0800200c9a66");

		if(device != null)
		{
			try {
				mSocket = device.createRfcommSocketToServiceRecord(uuid);
				Log.i(TAG, "Socket do device dado como parâmetro feito");
			} catch (IOException e) {

			}
		}

		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public Cliente(BluetoothSocket socket, boolean connect)
	{
		this.connect = connect;
		uuid = UUID.fromString("b4c5bcd0-a9ec-11e2-9e96-0800200c9a66");
		mSocket = socket;

		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	@Override
	public void run() {

		if(BluetoothAdapter.getDefaultAdapter().isDiscovering())
			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

		if(this.connect){
			try {
				mSocket.connect();
				connected = true;
				Log.i(TAG, "Conectado ao dispositivo");
				BluetoothConnectionManager.Cancel(); 

			} catch (IOException connectException) {

				connected = false;
				try {
					mSocket.close();
				} catch (IOException closeException) { }
				return;
			}
		}else
		{
			this.turn = true;
		}
		
		try{

			this.mInputStream = mSocket.getInputStream();
			this.mOutputStream = mSocket.getOutputStream();

			Log.i(TAG, "pegou as Streams");

			MinhasCoisas.getCurrentActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					Intent sendRoom = new Intent(MinhasCoisas.getCurrentActivity(), Chat.class);
					MinhasCoisas.getCurrentActivity().startActivity(sendRoom);
				}
			});

		}catch(IOException e)
		{

		}

		byte[] bytes = new byte[100];

		while(connected)
		{
			Log.i(TAG, "while - first of all");

			try {
				mInputStream.read(bytes);
				Log.i(TAG, "executou o metodo InputStream.read()");

				StringBuilder builder = new StringBuilder();

				for(int i = 0; i <= bytes.length; i++)
				{
					try{ builder.append((char)bytes[i]); }
					catch(Exception e){ }
				}
				Log.i(TAG, builder.toString());


				JogoEmSi.get().Handle(builder.toString());


			} catch (IOException e) {
				Log.i(TAG, "erro ao tentar ler os bytes recebidos");
				break;

			}
		}
	}

	public void cancel() {

		connected = false;
	}

	public void write(String message) {
		try {
			byte[] b = message.getBytes();
			mOutputStream.write(b);
			mOutputStream.flush();
			Log.i(TAG, "tentou enviar mensagem");
		} catch (IOException e) { Log.i(TAG, "Erro ao tentar enviar mensagem"); }
	}

}
