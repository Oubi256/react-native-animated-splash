package com.blitzapp.animatedsplash.animation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import static android.content.ContentValues.TAG;
import static com.blitzapp.animatedsplash.animation.Splash.animateObjectLength;
import static com.blitzapp.animatedsplash.animation.Splash.animationhide;
import static com.blitzapp.animatedsplash.animation.Splash.dismissDialog;
import static com.blitzapp.animatedsplash.animation.Splash.hideanimateObjectLength;
import static com.blitzapp.animatedsplash.animation.Splash.hideanimatedObjectList;
import static com.blitzapp.animatedsplash.animation.Splash.hidecounter;
import static com.blitzapp.animatedsplash.animation.Splash.jsCalled;
import static com.blitzapp.animatedsplash.animation.Splash.shouldHide;
import static com.blitzapp.animatedsplash.animation.Splash.mHandler;
import static com.blitzapp.animatedsplash.animation.Splash.counter;
import static com.blitzapp.animatedsplash.animation.Splash.savedReactContext;
import static com.blitzapp.animatedsplash.animation.Splash.runAnimation;

public class AnimateObject {

    private String animationType;
    private int animationDuration;
    private boolean setFillAfter;
    // Translate animation
    private float fromXDelta;
    private float toXDelta;
    private float fromYDelta;
    private float toYDelta;

    public AddImageView object, nextObject;

    private int priority;
    public boolean isLastObject;
    public boolean isLoop;
    public boolean isLoopReverse;

    // for rotate animation
    private float startFromValue;
    private float endToValue;
    private View animationView;

    // For translate animation
    public AnimateObject(AddImageView object, String animationType, int animationDuration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, boolean loop, boolean loopReverse, int priority) {
        this.animationType = animationType;
        this.animationDuration = animationDuration;
        this.setFillAfter = true;
        this.fromXDelta = fromXDelta;
        this.toXDelta = toXDelta;
        this.fromYDelta = fromYDelta;
        this.toYDelta = toYDelta;
        this.isLoop = loop;
        this.isLoopReverse = loopReverse;

        this.priority = priority;
        this.object = object;
        Log.d(TAG, "AnimateObject: priority " + this.priority);
    }

    public AnimateObject(AddImageView object, String animationType, int animationDuration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, int priority) {
        this.animationType = animationType;
        this.animationDuration = animationDuration;
        this.setFillAfter = true;
        this.fromXDelta = fromXDelta;
        this.toXDelta = toXDelta;
        this.fromYDelta = fromYDelta;
        this.toYDelta = toYDelta;
        this.priority = priority;
        this.object = object;
    }

    // For rotate animation
    public AnimateObject(AddImageView object, String animationType, int animationDuration, float fromValue, float toValue, boolean loop, boolean loopReverse, int priority) {
        this.animationType = animationType;
        this.animationDuration = animationDuration;
        this.setFillAfter = true;
        this.isLoop = loop;
        this.isLoopReverse = loopReverse;

        this.startFromValue = fromValue;
        this.endToValue = toValue;
        this.priority = priority;
        this.object = object;
        Log.d(TAG, "AnimateObject: priority " + this.priority);
    }


    // For rotate animation
    public AnimateObject(AddImageView object, String animationType, int animationDuration, float fromValue, float toValue, int priority) {
        this.animationType = animationType;
        this.animationDuration = animationDuration;
        this.setFillAfter = true;
        this.startFromValue = fromValue;
        this.endToValue = toValue;
        this.priority = priority;
        this.object = object;
    }

    public AddImageView getNextObject() {
        return nextObject;
    }

    public String getAnimationType() {
        return animationType;
    }

    public AddImageView getObject() {
        return object;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public boolean isSetFillAfter() {
        return setFillAfter;
    }

    public float getFromXDelta() {
        return fromXDelta;
    }

    public float getToXDelta() {
        return toXDelta;
    }

    public float getFromYDelta() {
        return fromYDelta;
    }

    public float getToYDelta() {
        return toYDelta;
    }

    public boolean isLastObject() {
        return isLastObject;
    }


    public int getPriority() {
        return priority;
    }

    public float getRotateStartDegree() {
        return startFromValue;
    }

    public float getRotateEndDegree() {
        return endToValue;
    }

    public void slideAnimation(AddImageView object, final AddImageView nextObject, Boolean isLoop, Boolean isLoopReverse) {

        View view = object.getImageView();

        TranslateAnimation slideImage = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        slideImage.setDuration(animationDuration);
        slideImage.setFillAfter(setFillAfter);
        view.setVisibility(View.VISIBLE);
        if (isLoop) {
            if (isLoopReverse) slideImage.setRepeatMode(slideImage.REVERSE);
            slideImage.setRepeatCount((int) Double.POSITIVE_INFINITY);
        }

        view.startAnimation(slideImage);
        slideImage.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "shouldHide slide: " + counter + "length");

                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd slide: " + nextObject);


                    runAnimation();


                } else {

                    Log.d(TAG, "shouldHide slide:isLastObject" + animateObjectLength);
                    if (counter == animateObjectLength) {
                        shouldHide = true;
                        if (jsCalled == true) {
                            Log.d(TAG, "shouldHide slide:jsCalled" + jsCalled);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        } else {

                            return;
                        }

                    }
                }

            }


            @Override
            public void onAnimationRepeat(Animation animation) {
                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate: " + nextObject);

                    runAnimation();
                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }
            }
        });

    }

    public void slideAnimation(AddImageView object, final AddImageView nextObject) {

        View view = object.getImageView();

        TranslateAnimation slideImage = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        slideImage.setDuration(animationDuration);
        slideImage.setFillAfter(setFillAfter);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(slideImage);
        slideImage.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (nextObject != null && hidecounter < hideanimateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate next: " + counter);


                    animationhide();
//                    return;


                } else {
                    if (hidecounter == hideanimateObjectLength) {
                        dismissDialog();
                    } else {

                        return;
                    }
                }

            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void rotateAnimation(AddImageView object, final AddImageView nextObject) {
        View view = object.getImageView();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, startFromValue, endToValue);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(animationDuration);
        view.setVisibility(View.VISIBLE);

        rotation.start();

        rotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (nextObject != null && hidecounter < hideanimateObjectLength) {


                    animationhide();


                } else {
                    if (hidecounter == hideanimateObjectLength) {
                        dismissDialog();
                    } else {

                        return;
                    }
                }
            }


        });
    }

    public void fadeAnimation(AddImageView object, final AddImageView nextObject) {

        View view = object.getImageView();
        view.setAlpha(1);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", startFromValue, endToValue);
        fadeIn.setInterpolator(new LinearInterpolator()); //add this
        fadeIn.setDuration(animationDuration);
        view.setVisibility(View.VISIBLE);
        if (isLoop) {
            if (isLoopReverse) fadeIn.setRepeatMode(fadeIn.REVERSE);
            fadeIn.setRepeatCount(ValueAnimator.INFINITE);
        }
        fadeIn.start();
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (nextObject != null && hidecounter < hideanimateObjectLength) {


                    animationhide();


                } else {
                    if (hidecounter == hideanimateObjectLength) {
                        dismissDialog();
                    } else {

                        return;
                    }
                }

            }


            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void scaleAnimation(AddImageView object, final AddImageView nextObject) {
        View view = object.getImageView();
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("scaleX", fromXDelta, toXDelta),
                PropertyValuesHolder.ofFloat("scaleY", fromYDelta, toYDelta));
        scale.setDuration(animationDuration);
        view.setVisibility(View.VISIBLE);
        scale.start();
        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (nextObject != null && hidecounter < hideanimateObjectLength) {


                    animationhide();
                } else {
                    if (hidecounter == hideanimateObjectLength) {
                        dismissDialog();
                    } else {

                        return;
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void fadeAnimation(AddImageView object, final AddImageView nextObject, Boolean isLoop, Boolean isLoopReverse) {

        View view = object.getImageView();
        view.setAlpha(1);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", startFromValue, endToValue);
        fadeIn.setInterpolator(new LinearInterpolator()); //add this
        fadeIn.setDuration(animationDuration);
        view.setVisibility(View.VISIBLE);
        if (isLoop) {
            if (isLoopReverse) fadeIn.setRepeatMode(fadeIn.REVERSE);
            fadeIn.setRepeatCount(ValueAnimator.INFINITE);
        }
        fadeIn.start();
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "shouldHide slide: " + counter + "length");

                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd slide: " + nextObject);


                    runAnimation();


                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }


            }


            @Override
            public void onAnimationRepeat(Animator animation) {

                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate: " + nextObject);

                    runAnimation();
                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }
            }
        });

    }

    public void rotateAnimation(AddImageView object, final AddImageView nextObject, Boolean isLoop, Boolean isLoopReverse) {
        View view = object.getImageView();
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, startFromValue, endToValue);
        rotation.setDuration(animationDuration);
        view.setVisibility(View.VISIBLE);
        if (isLoop) {
            if (isLoopReverse) rotation.setRepeatMode(rotation.REVERSE);
//            rotation.setRepeatMode(ValueAnimator.RESTART);
            rotation.setRepeatCount(ValueAnimator.INFINITE);
            rotation.setInterpolator(new LinearInterpolator());
        }
        rotation.start();

        rotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "shouldHide rotate: " + counter + "length");
                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate next: " + counter);


                    runAnimation();


                } else {
                    if (counter == animateObjectLength) {
                        Log.d(TAG, "onAnimationEnd: " + jsCalled);
                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {
                        return;
                    }
                }


            }


            @Override
            public void onAnimationRepeat(Animator animation) {

                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate: " + nextObject);

                    runAnimation();
                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }
            }
        });

    }

    public void scaleAnimation(AddImageView object, final AddImageView nextObject, Boolean isLoop, Boolean isLoopReverse) {

        View view = object.getImageView();
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("scaleX", fromXDelta, toXDelta),
                PropertyValuesHolder.ofFloat("scaleY", fromYDelta, toYDelta));
        scale.setDuration(animationDuration);
//        scale.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        scale.setInterpolator(new LinearInterpolator());
        if (isLoop) {
            if (isLoopReverse) scale.setRepeatMode(scale.REVERSE);
            scale.setRepeatCount((int) Double.POSITIVE_INFINITY);
        }
        scale.start();
        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "shouldHide scale check:" + animateObjectLength);
                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd scale: " + nextObject);


                    Log.d(TAG, "onAnimationEnd: scaling " + counter);

                    runAnimation();


                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }


            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (nextObject != null && counter < animateObjectLength) {

                    Log.d(TAG, "onAnimationEnd rotate: " + nextObject);

                    runAnimation();
                } else {
                    if (counter == animateObjectLength) {

                        shouldHide = true;
                        if (jsCalled == true) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animationhide();

                                }
                            }, Splash.hideDelay);

                        }
                    } else {

                        return;
                    }
                }
            }
        });

    }
}

