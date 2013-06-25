package com.example.lovegame_project;

import android.util.Log;

public class TimeManager implements Runnable {

	private Thread timeThread;
	private double deltaTime;
	private double lastTimeCount;
	private final String TAG = "Time Manager";
	private boolean alive = true;
	
	private static TimeManager instance;
	
	public static TimeManager getInstance()
	{
		if(instance == null)
		{
			instance = new TimeManager();
		}
		
		return instance;
	}
	private TimeManager()
	{
		timeThread = new Thread(this);
		timeThread.start();
		
	}
	@Override
	public void run()
	{
		lastTimeCount = System.currentTimeMillis();
		
		while(alive)
		{
			deltaTime = (System.currentTimeMillis() - this.lastTimeCount);
			lastTimeCount = System.currentTimeMillis();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double getDeltaTime()
	{
		return this.deltaTime;
	}
}
