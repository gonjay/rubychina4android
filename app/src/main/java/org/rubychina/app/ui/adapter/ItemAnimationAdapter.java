package org.rubychina.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import org.rubychina.app.MyApp;
/**
 * Created by mac on 14-1-29.
 */
public class ItemAnimationAdapter extends AnimationAdapter {

    private float mTranslationY = 150;

    private float mRotationX = 8;

    private long mDuration;

    public ItemAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
        mDuration = MyApp.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime);
    }

    @Override
    protected long getAnimationDelayMillis() {
        return 30;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return mDuration;
    }

    @Override
    public Animator[] getAnimators(ViewGroup parent, View view) {
        return new Animator[] {
                ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0)
        };
    }

//    @Override
//    protected void prepareAnimation(View view) {
//        view.setTranslationY(mTranslationY);
//        view.setRotationX(mRotationX);
//    }
}
