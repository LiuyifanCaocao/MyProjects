package com.jredu.myprojects.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jredu.myprojects.R;

import java.util.ArrayList;
import java.util.List;
//音乐播放
public class MusicPlayerActivity extends Activity {
TextView music;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        music= (TextView) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView = (ListView) findViewById(R.id.musicListView);
        readMusic();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

    }
    public void readMusic(){
        Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    for (cursor.moveToFirst();cursor.isAfterLast();cursor.moveToNext()){
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String urlData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        //Song song = new Song(urlData,title,artist,size);
        list.add(title+"\n"+artist+"\n"+urlData+"\n");
    }
        cursor.close();
    }
}
