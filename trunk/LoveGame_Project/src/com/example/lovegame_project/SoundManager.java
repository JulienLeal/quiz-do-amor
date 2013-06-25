package com.example.lovegame_project;

import java.util.HashMap;

import javax.xml.transform.Source;

import android.R.raw;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.util.Log;

public class SoundManager {

	private String TAG = "Sound Manager";
	
	private AudioManager audioManager;
	
	private HashMap<String, MediaPlayer> songs;
	private static SoundManager instance;
	
	private SoundManager() {
		audioManager = (AudioManager)MinhasCoisas.getCurrentActivity().getSystemService(Context.AUDIO_SERVICE);
		this.songs = new HashMap<String, MediaPlayer>();
	}
	public static SoundManager getInstance()
	{
		if(instance == null)
		{
			instance = new SoundManager();
		}
		
		return instance;
	}

	public void playSound(final int source, String name)
	{
		
		final MediaPlayer mp = MediaPlayer.create(MinhasCoisas.getCurrentActivity(), source);
		
		try{
			mp.setLooping(true);
			mp.start();
			this.songs.put(name, mp);
		}catch(Exception e)
		{
			mp.stop();
			Log.i(TAG, "Erro no som"); 
		}
	}
	public void StopSong(String name)
	{
		try{
		this.songs.get(name).stop();
		}catch(Exception e)
		{
			
		}
	}
	public void StopAllSongs()
	{

	}
	
}
