package com.example.lovegame_project;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

	private BluetoothAdapter mBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Activity", "Start");
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		MinhasCoisas.setCurrentActivity(this);
		
		mBT = BluetoothAdapter.getDefaultAdapter();
		if(mBT != null){
			MinhasCoisas.Show( "Este telefone possui tecnologia Bluetooth");
			if(mBT.isEnabled())
			{
				Button b = (Button) findViewById(R.id.activateBtButton);
				b.setText(R.string.turn_bt_off);
			}
			
		}else
		{
			MinhasCoisas.Show( "Este telefone não possui tecnologia Bluetooth");
			finish();
		}
		
		
		
	}
	
	public void onClick_ActivateBluetooth(View v)
	{
		
		if (!mBT.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST.REQUEST_ENABLE_BT);
		    Button b = (Button) findViewById(R.id.activateBtButton);
			b.setText(R.string.turn_bt_off);
		}else
		{
			mBT.disable();
			Button b = (Button) findViewById(R.id.activateBtButton);
			b.setText(R.string.turn_bt_on);
			
			MinhasCoisas.Show("Bluetooth desligado");
		}
		
	}
	
	public void onClick_leave(View v)
	{
		this.finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(REQUEST.REQUEST_ENABLE_BT == requestCode)
			{
				MinhasCoisas.Show("Bluetooth ligado");
				Button b = (Button) findViewById(R.id.activateBtButton);
				b.setText(R.string.turn_bt_off);
			}
		}else
		{
			MinhasCoisas.Show("erro ao tentar ligar Bluetooth");
		}
	}
	
	public void onClick_Scan(View v)
	{
		Intent scanIntent = new Intent(this, DeviceList.class);
		startActivity(scanIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		if(mBT.isEnabled())
		this.mBT.disable();
		
	}
}
