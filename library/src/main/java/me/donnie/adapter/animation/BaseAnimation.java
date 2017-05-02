package me.donnie.adapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * @author donnieSky
 * @created_at 2017/5/2.
 * @description
 */

public interface BaseAnimation {

    Animator[] getAnimators(View view);

}
