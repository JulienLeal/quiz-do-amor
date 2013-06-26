package com.example.lovegame_project;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceList extends Activity {

	private static final String TAG = "DEVICE LIST";
	private static final int ENABLE_BT_REQUEST = 1;
	private static final int HANDLER_DEVICES_THREAD = 1;
	
	private static boolean isAskingDiscoverable = false;
	
	private BluetoothAdapter btAdapter;

	private ListView pairedDevicesList;
	private ListView newDevicesList;
	private ArrayList<String> pairedDevicesData;
	private ArrayList<String> newDevicesData;

	private ArrayList<BluetoothDevice> pairedDevices;
	private ArrayList<BluetoothDevice> newDevices;

	private boolean IsBroadcastReceiverRunning = false;

	private BroadcastReceiver mBroadcastReceiver;
	private Handler handler;

	private BluetoothConnectionManager btManager;

	private Intent discoverableIntent;


	@Override
	protected void onStart()
	{
		super.onStart();

		MinhasCoisas.Show("DeviceList OnStart");
		this.newDevicesData.clear();
		JogoEmSi.get().Reset2();
		this.btManager = new BluetoothConnectionManager(getApplicationContext());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		// Screen adjustments
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		setContentView(R.layout.activity_device_list);

		// Só pode-se mudar a fonte de uma view depois de setContentView
		//TextView t = (TextView)findViewById(R.id.pairedDevicesTittle);
		//t.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fonte.TTF"));
		
		Button b = (Button)findViewById(R.id.button_scan);
		b.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fonte.TTF"));
		
		MinhasCoisas.setCurrentActivity(this);
		MinhasCoisas.Show(("OnCreate DeviceList"));
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		this.pairedDevicesList = (ListView)findViewById(R.id.paired_devices);
		this.newDevicesList = (ListView)findViewById(R.id.new_devices);

		this.pairedDevicesData = new ArrayList<String>();
		this.newDevicesData = new ArrayList<String>();

		pairedDevices = new ArrayList<BluetoothDevice>();  
		newDevices = new ArrayList<BluetoothDevice>();

		if(this.discoverableIntent == null){
			this.discoverableIntent = new
					Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 360);

			startActivity(discoverableIntent);
		}

		if(btManager == null){
			btManager = new BluetoothConnectionManager(getApplicationContext()); 
		}

		// Select device on Paired List View
		this.pairedDevicesList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				MinhasCoisas.Show("Aguarde para entrar no jogo");
				Cliente cliente = new Cliente(pairedDevices.get(position), getApplicationContext(), true);
				MinhasCoisas.setCliente(cliente);

			}
		});

		// Select device on New List View
		this.newDevicesList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				MinhasCoisas.Show("Aguarde para entrar no jogo.");
				Cliente cliente = new Cliente(newDevices.get(position), getApplicationContext(), true);
				MinhasCoisas.setCliente(cliente);
			}
		});

		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();

				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					newDevicesData.add(device.getName() + "\n" + device.getAddress());
					newDevices.add(device);
					AtualizarNewDevicesList();
					Log.i("Bluetooth Scan", "+1 dispositivo pareado");

				}
			}

		};

		this.handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {

				if(msg.what == HANDLER_DEVICES_THREAD)
				{

					//AtualizarNewDevicesList();

				}

				super.handleMessage(msg);
			}
		};
	}

	public void AtualizarNewDevicesList()
	{
		ArrayList<String> tmpNewDevicesData = new ArrayList<String>();
		tmpNewDevicesData.addAll(newDevicesData);
		this.newDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list, tmpNewDevicesData));

		Log.i(TAG, "Atualizou a lista de device");
	}

	public void onClick_Scan(View v)
	{
		Scan();
	}
	private void Scan()
	{
		if(!btAdapter.isEnabled()){

			Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBt, REQUEST.REQUEST_ENABLE_BT);
		}else{

			//Começa a escanear novos dispositivos 
			if(!this.btAdapter.isDiscovering())
				this.btAdapter.startDiscovery();

			// +++ Dispositivos escaneados +++
			if(!this.IsBroadcastReceiverRunning){
				IntentFilter foundDevices = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				registerReceiver(mBroadcastReceiver, foundDevices); // Não esquecer de desregistrar antes de destruir
				this.IsBroadcastReceiverRunning = true;
			}

			// +++ Dispositivos já pareados +++
			Set<BluetoothDevice> alreadyDevices = btAdapter.getBondedDevices();
			if (alreadyDevices.size() > 0) {

				this.pairedDevicesData.clear();
				for (BluetoothDevice device : alreadyDevices) {
					pairedDevicesData.add(device.getName() + "\n" + device.getAddress());
					pairedDevices.add(device);

				}
			}

			// Põe os nomes dos dispositivos pareados na ListView de dispositivos pareados
			pairedDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list , this.pairedDevicesData));

			ArrayList<String> tmpNewDevicesData = new ArrayList<String>();
			tmpNewDevicesData.addAll(newDevicesData);
			this.newDevicesList.setAdapter(new ArrayAdapter<String>(this, R.layout.list, tmpNewDevicesData));

			//			Thread thread = new Thread(new Runnable()
			//			{
			//				@Override
			//				public void run() {
			//					float cronometro = 0;
			//					float searchTime = 10;
			//					float updateInterval = 2;
			//					float updateTime = updateInterval;
			//					while(cronometro <= searchTime)
			//					{
			//						cronometro += TimeManager.getInstance().getDeltaTime()/1000;
			//
			//						Log.i(TAG, String.valueOf(cronometro));
			//
			//						if(cronometro >= updateTime)
			//						{
			//							updateTime += updateInterval;
			//							Message msg = new Message();
			//							msg.what = HANDLER_DEVICES_THREAD;
			//							handler.sendMessage(msg);
			//						}
			//					}
			//				}
			//			});
			//			thread.start();

		}
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if(btAdapter.isDiscovering())
			btAdapter.cancelDiscovery();

		if(this.IsBroadcastReceiverRunning)
			unregisterReceiver(mBroadcastReceiver);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_list, menu);
		return true;
	}

}

