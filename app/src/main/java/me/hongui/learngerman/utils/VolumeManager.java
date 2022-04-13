package me.hongui.learngerman.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class VolumeManager implements LifecycleObserver, Handler.Callback ,MediaPlayer.OnCompletionListener{
    private static final int MSG_START=1;
    private static final int MSG_PLAY=2;
    private static final int MSG_STOP=3;
    private static final int DELAY=128;

    private ImageView progress;
    private Handler handler;
    private MediaPlayer player;
    private Context context;
    private int resource;
    private int level;
    private int current;

    public VolumeManager(int level){
        this.level=level;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(){
        if(null!=player){
            if(player.isPlaying()){
                handler.sendEmptyMessage(MSG_STOP);
                player.stop();
            }
            player.release();
            player=null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void release(){
        stop();
        player=null;
        handler=null;
        context=null;
    }

    public void prepare(Context context,String id){
        stop();
        this.context=context.getApplicationContext();
        if(null==handler){
            handler = new Handler(this);
        }
        resource=context.getResources().getIdentifier(id, "raw", context.getPackageName());
    }

    public void play(ImageView progress){
        stop();

        player = MediaPlayer.create(context, resource);
        player.setOnCompletionListener(this);

        this.progress=progress;
        current=0;
        player.start();
        handler.sendEmptyMessage(MSG_START);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_START:{
                handler.sendEmptyMessageDelayed(MSG_PLAY,DELAY);
                break;
            }

            case MSG_PLAY:{
                if(null==progress){
                    return true;
                }
                progress.setImageLevel(current);
                current+=1;
                if(current>level){
                    current=0;
                }
                handler.sendEmptyMessageDelayed(MSG_PLAY, DELAY);
                break;
            }

            case MSG_STOP: {
                handler.removeMessages(MSG_PLAY);
                if(null==progress){
                    return true;
                }
                progress.setImageLevel(0);
                progress = null;
                break;
            }
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        handler.sendEmptyMessage(MSG_STOP);
    }
}
