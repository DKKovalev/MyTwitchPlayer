package com.twitchplaya.psicho.mytwitchplayer.Assets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;

import com.twitchplaya.psicho.mytwitchplayer.R;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

public class CustomMediaController extends FrameLayout {

    private MediaPlayerControl mediaController;
    private Context context;
    private ViewGroup viewGroup;
    private View rootView;
    private TextView timePlayed;
    private boolean isShowing;
    private boolean isPlaying;
    private static final int defaultTimeout = 3000;
    private boolean isFromXML;
    private boolean areListenersSet;
    private ImageButton playButton;
    private ImageButton fullScreenButton;
    private StringBuilder stringBuilder;
    private Formatter formatter;


    private Handler handler = new MessageHandler(this);

    public CustomMediaController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        rootView = null;
        isFromXML = false;
    }

    public CustomMediaController(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        if (rootView != null) {
            initControllerView(rootView);
        }
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        mediaController = player;
        updatePausePlay();
        updateFullscreen();
    }

    public void setAnchorView(ViewGroup view) {
        this.viewGroup = view;
        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        removeAllViews();
        View view1 = makeControllerView();
        addView(view1, layoutParams);
    }

    protected View makeControllerView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.custom_media_controller, null);
        initControllerView(rootView);
        return rootView;
    }

    private void initControllerView(View view) {
        fullScreenButton = (ImageButton) view.findViewById(R.id.fullscreen);
        if (fullScreenButton != null) {
            fullScreenButton.requestFocus();
            fullScreenButton.setOnClickListener(fullscreenListener);
        }

        playButton = (ImageButton) view.findViewById(R.id.pause);
        if (playButton != null) {
            playButton.requestFocus();
            playButton.setOnClickListener(playListener);
        }

        timePlayed = (TextView) view.findViewById(R.id.time);
        stringBuilder = new StringBuilder();
        formatter = new Formatter(stringBuilder, Locale.getDefault());
    }

    public void show() {
        show(defaultTimeout);
    }

    public void show(int timeout) {
        if (isShowing && viewGroup != null) {
            if (playButton != null) {
                playButton.requestFocus();
            }

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);
            viewGroup.addView(this, layoutParams);
            isShowing = true;
        }
        updatePausePlay();
        updateFullscreen();

        Message msg = handler.obtainMessage(1);
        if (timeout != 0) {
            handler.removeMessages(1);
            handler.sendMessageDelayed(msg, timeout);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void hideControlls() {
        if (viewGroup == null) {
            return;
        }
        try {
            viewGroup.removeView(this);
        } catch (IllegalArgumentException e) {

        }
        isShowing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        show(defaultTimeout);
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        show(defaultTimeout);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mediaController == null) {
            return true;
        }

        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(defaultTimeout);
                if (playButton != null) {
                    playButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && mediaController.isPlaying()) {
                mediaController.start();
                updatePausePlay();
                show(defaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_MUTE || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hideControlls();
            }
            return true;
        }

        show(defaultTimeout);
        return super.dispatchKeyEvent(event);
    }

    private View.OnClickListener playListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(defaultTimeout);
        }
    };

    private View.OnClickListener fullscreenListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doFullscreen();
            show(defaultTimeout);
        }
    };

    public void updatePausePlay() {
        if (mediaController == null || playButton == null || rootView == null) {
            return;
        }

        if (mediaController.isPlaying()) {
            //TODO: Вставить картинки проигрыша/остановки
        }
    }

    public void updateFullscreen() {
        if (mediaController == null || playButton == null || rootView == null) {
            return;
        }

        if (mediaController.isPlaying()) {
            //TODO: Вставить картинки проигрыша/остановки
        }
    }

    public void doPauseResume() {
        if (mediaController == null) {
            return;
        }
        if (mediaController.isPlaying()) {
            mediaController.pause();
        } else {
            mediaController.start();
        }
        updatePausePlay();
    }

    public void doFullscreen() {
        if (mediaController == null) {
            return;
        }

        mediaController.toggleFullscreen();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (playButton != null) {
            playButton.setEnabled(enabled);
        }
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        void toggleFullscreen();

        boolean isPlaying();

        int getBufferPercentage();
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<CustomMediaController> mView;

        MessageHandler(CustomMediaController view) {
            mView = new WeakReference<CustomMediaController>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomMediaController view = mView.get();
            if (view == null || view.mediaController == null) {
                return;
            }

            switch (msg.what) {
                case 1:
                    view.hideControlls();
                    break;
            }
        }
    }
}
