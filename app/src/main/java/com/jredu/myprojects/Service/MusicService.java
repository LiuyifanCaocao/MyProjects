package com.jredu.myprojects.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.jredu.myprojects.R;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

    public MusicService() {

    }

    //自定义类
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.bieli);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer.start();
        return new MyBinder();//绑定成功后，返回了自定义的对象

    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

}
