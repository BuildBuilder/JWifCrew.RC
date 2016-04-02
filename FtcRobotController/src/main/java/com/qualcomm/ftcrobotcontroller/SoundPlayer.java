package com.qualcomm.ftcrobotcontroller;

import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;


public class SoundPlayer {
    public static SoundPlayer INSTANCE = new SoundPlayer(new SoundPool.OnLoadCompleteListener() {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {}
    });
    private SoundPool.OnLoadCompleteListener delegate;
    private SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    private HashMap<String, Integer> Songs = new HashMap<String, Integer>();
    public SoundPlayer(SoundPool.OnLoadCompleteListener Listner){
        delegate = Listner;
        if(delegate != null)
            soundPool.setOnLoadCompleteListener(delegate);
        if(DataExchange.INSTANCE.context != null){
            int sirenaID = soundPool.load(DataExchange.INSTANCE.context, R.raw.sirena,1);
            Songs.put("Sirena",sirenaID);
        }
    }
    public void play(String Name){
        if(Songs.containsKey(Name))
            soundPool.play(Songs.get(Name),1,1,0,0,1);
    }
}
