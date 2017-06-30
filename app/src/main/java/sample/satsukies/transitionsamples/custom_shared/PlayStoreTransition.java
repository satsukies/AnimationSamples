package sample.satsukies.transitionsamples.custom_shared;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by a14745 on 2017/06/29.
 */
public class PlayStoreTransition extends Transition {

  private Context context;

  public PlayStoreTransition(Context context) {
    this.context = context;
  }

  public PlayStoreTransition(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private void captureValues(TransitionValues transitionValues) {
    View view = transitionValues.view;

    //親view内での相対位置（左上、右下）の取得
    Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    transitionValues.values.put("bounds", rect);
    Log.d("Anim:bounds", rect.toString());

    //画面上での位置取得
    int[] position = new int[2];
    view.getLocationInWindow(position);
    transitionValues.values.put("position", position);
    Log.d("Anim:position", "[" + position[0] + "." + position[1] + "]");
  }

  @Override public void captureStartValues(@NonNull TransitionValues transitionValues) {
    View view = transitionValues.view;

    if (view.getWidth() <= 0 || view.getHeight() <= 0) {
      //viewサイズがないのでいらない
      return;
    }

    //viewにサイズがある状態
    captureValues(transitionValues);
    Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);
    transitionValues.values.put("image", bitmap);
  }

  @Override public void captureEndValues(@NonNull TransitionValues transitionValues) {
    View view = transitionValues.view;

    if (view.getWidth() <= 0 || view.getHeight() <= 0) {
      //viewサイズがないのでいらない
      return;
    }
    //viewにサイズがある状態
    captureValues(transitionValues);
  }

  /**
   * @param sceneRoot 遷移先activity全体
   * @return アニメーション
   */
  @Override public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
      final TransitionValues endValues) {
    //dataの取り出し作業ーーーーーーーーーーーーーーーーーーーーー
    if (startValues == null || endValues == null) {
      //valueがない奴はいらない
      return null;
    }

    Rect startBounds = (Rect) startValues.values.get("bounds");
    Rect endBounds = (Rect) endValues.values.get("bounds");

    if (startBounds == null || endBounds == null || startBounds.equals(endBounds)) {
      //位置に差がないviewはアニメーションいらない
      return null;
    }

    Bitmap startImage = (Bitmap) startValues.values.get("image");

    final View startView =
        addViewToOverlay(sceneRoot, startImage.getWidth(), startImage.getHeight(),
            new BitmapDrawable(startImage));
    final View startBallView =
        addViewToOverlay(sceneRoot, startImage.getWidth(), startImage.getHeight(),
            new ColorDrawable(Color.MAGENTA));

    final View endView = endValues.view;
    final View endBallView = addViewToOverlay(sceneRoot, endView.getWidth(), endView.getHeight(),
        new ColorDrawable(Color.MAGENTA));
    startBallView.setAlpha(0);

    //startViewの位置を計算ーーーーーーーーーーーーーーーーーーーー
    int[] sceneRootPosition = new int[2];
    sceneRoot.getLocationInWindow(sceneRootPosition);

    //保存しておいた位置
    int[] startPosition = (int[]) startValues.values.get("position");
    int[] endPosition = (int[]) endValues.values.get("position");

    //保存値から現在値を引いて差分を計算
    int transStartX = startPosition[0] - sceneRootPosition[0];
    int transStartY = startPosition[1] - sceneRootPosition[1];
    int transEndX = endPosition[0] + ((endView.getWidth() - startView.getWidth()) / 2);
    int transEndY = endPosition[1] + ((endView.getHeight() - startView.getHeight()) / 2);

    //計算結果をviewに適用
    startView.setTranslationX(transStartX);
    startView.setTranslationY(transStartY);
    startBallView.setTranslationX(transStartX);
    startBallView.setTranslationY(transStartY);

    endBallView.setTranslationX(endPosition[0] - sceneRootPosition[0]);
    endBallView.setTranslationY(endPosition[1] - sceneRootPosition[1]);

    //revealアニメーションを生成
    Animator revealStartView = createCircularReveal(startView, calculateMaxRadius(startView),
        Math.min(calculateMinRadius(startView) / 2, calculateMinRadius(endView) / 2));

    Animator revealStartBallView =
        createCircularReveal(startBallView, calculateMaxRadius(startView),
            Math.min(calculateMinRadius(startView) / 2, calculateMinRadius(endView) / 2));

    Animator revealEndBallViewOpen = createCircularReveal(endBallView,
        Math.min(calculateMinRadius(startView) / 2, calculateMinRadius(endView) / 2),
        calculateMaxRadius(endView));

    //fadeするアニメーション
    Animator fadeOutStartView = ObjectAnimator.ofFloat(startView, View.ALPHA, 1, 0);
    Animator fadeInStartBallView = ObjectAnimator.ofFloat(startBallView, View.ALPHA, 0, 1);
    Animator fadeInEndView = ObjectAnimator.ofFloat(endView, View.ALPHA, 0, 1);
    Animator fadeOutEndBallView = ObjectAnimator.ofFloat(endBallView, View.ALPHA, 1, 0);

    //各Viewの初期状態を設定
    endView.setAlpha(0);
    endBallView.setAlpha(0);

    //移動するアニメーション
    //はじめとおわりの位置を計算（対象viewの中央）
    Animator moveStartView =
        ObjectAnimator.ofFloat(startView, View.TRANSLATION_X, View.TRANSLATION_Y,
            getPathMotion().getPath(transStartX, transStartY, transEndX, transEndY));
    Animator moveStartBallView =
        ObjectAnimator.ofFloat(startBallView, View.TRANSLATION_X, View.TRANSLATION_Y,
            getPathMotion().getPath(transStartX, transStartY, transEndX, transEndY));

    //アニメーションを組み合わせる
    AnimatorSet transitionAnimation = new AnimatorSet();
    AnimatorSet transitionStep1 = new AnimatorSet();
    AnimatorSet transitionStep2 = new AnimatorSet();
    AnimatorSet transitionStep3 = new AnimatorSet();

    transitionAnimation.playSequentially(transitionStep1, transitionStep2, transitionStep3);

    //step1: 移動元Viewを丸に変形しながら、移動先Viewの中央まで移動しつつ、画像はフェードアウト、背景フェードイン
    transitionStep1.playTogether(revealStartView, revealStartBallView, moveStartView,
        moveStartBallView, fadeOutStartView, fadeInStartBallView);
    transitionStep1.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(0);
        endBallView.setAlpha(1);
      }
    });

    //step2: 背景色の丸を移動先View全体にかかるようにreveal
    transitionStep2.playTogether(revealEndBallViewOpen);
    transitionStep2.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(0);
        endBallView.setAlpha(1);
      }

      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(0);
        endBallView.setAlpha(1);
      }
    });

    //step3: 背景色をフェードアウトさせつつ、移動先Viewをフェードイン
    transitionStep3.playTogether(fadeOutEndBallView, fadeInEndView);
    transitionStep3.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(0);
        endBallView.setAlpha(1);
      }

      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(1);
        endBallView.setAlpha(0);
      }
    });

    transitionAnimation.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);

        startView.setAlpha(0);
        startBallView.setAlpha(0);
        endView.setAlpha(1);
        endBallView.setAlpha(0);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);

        //開始時の各Viewのalpha設定
        startView.setAlpha(1);
        startBallView.setAlpha(0);
        endView.setAlpha(0);
        endBallView.setAlpha(0);
      }
    });

    return transitionAnimation;
  }

  /**
   * from playanimation
   */
  private View addViewToOverlay(ViewGroup sceneRoot, int width, int height, Drawable background) {
    View view = new NoOverlapView(sceneRoot.getContext());
    view.setBackground(background);
    int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
    int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
    view.measure(widthSpec, heightSpec);
    view.layout(0, 0, width, height);
    sceneRoot.getOverlay().add(view);
    return view;
  }

  private Animator createCircularReveal(View view, float startRadius, float endRadius) {
    int centerX = view.getWidth() / 2;
    int centerY = view.getHeight() / 2;

    Animator reveal =
        ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
    return new NoPauseAnimator(reveal);
  }

  //viewに対する外接円の半径を計算する
  static float calculateMaxRadius(View view) {
    float widthSquared = view.getWidth() * view.getWidth();
    float heightSquared = view.getHeight() * view.getHeight();
    float radius = (float) Math.sqrt(widthSquared + heightSquared) / 2;
    return radius;
  }

  //viewに対する内接円の半径を計算する
  static int calculateMinRadius(View view) {
    return Math.min(view.getWidth() / 2, view.getHeight() / 2);
  }

  private static class NoPauseAnimator extends Animator {
    private final Animator mAnimator;
    private final ArrayMap<AnimatorListener, AnimatorListener> mListeners = new ArrayMap<>();

    public NoPauseAnimator(Animator animator) {
      mAnimator = animator;
    }

    @Override public void addListener(AnimatorListener listener) {
      AnimatorListener wrapper = new AnimatorListenerWrapper(this, listener);
      if (!mListeners.containsKey(listener)) {
        mListeners.put(listener, wrapper);
        mAnimator.addListener(wrapper);
      }
    }

    @Override public void cancel() {
      mAnimator.cancel();
    }

    @Override public void end() {
      mAnimator.end();
    }

    @Override public long getDuration() {
      return mAnimator.getDuration();
    }

    @Override public TimeInterpolator getInterpolator() {
      return mAnimator.getInterpolator();
    }

    @Override public ArrayList<AnimatorListener> getListeners() {
      return new ArrayList<>(mListeners.keySet());
    }

    @Override public long getStartDelay() {
      return mAnimator.getStartDelay();
    }

    @Override public boolean isPaused() {
      return mAnimator.isPaused();
    }

    @Override public boolean isRunning() {
      return mAnimator.isRunning();
    }

    @Override public boolean isStarted() {
      return mAnimator.isStarted();
    }

    @Override public void removeAllListeners() {
      mListeners.clear();
      mAnimator.removeAllListeners();
    }

    @Override public void removeListener(AnimatorListener listener) {
      AnimatorListener wrapper = mListeners.get(listener);
      if (wrapper != null) {
        mListeners.remove(listener);
        mAnimator.removeListener(wrapper);
      }
    }

    @Override public Animator setDuration(long durationMS) {
      mAnimator.setDuration(durationMS);
      return this;
    }

    @Override public void setInterpolator(TimeInterpolator timeInterpolator) {
      mAnimator.setInterpolator(timeInterpolator);
    }

    @Override public void setStartDelay(long delayMS) {
      mAnimator.setStartDelay(delayMS);
    }

    @Override public void setTarget(Object target) {
      mAnimator.setTarget(target);
    }

    @Override public void setupEndValues() {
      mAnimator.setupEndValues();
    }

    @Override public void setupStartValues() {
      mAnimator.setupStartValues();
    }

    @Override public void start() {
      mAnimator.start();
    }
  }

  private static class AnimatorListenerWrapper implements Animator.AnimatorListener {
    private final Animator mAnimator;
    private final Animator.AnimatorListener mListener;

    public AnimatorListenerWrapper(Animator animator, Animator.AnimatorListener listener) {
      mAnimator = animator;
      mListener = listener;
    }

    @Override public void onAnimationStart(Animator animator) {
      mListener.onAnimationStart(mAnimator);
    }

    @Override public void onAnimationEnd(Animator animator) {
      mListener.onAnimationEnd(mAnimator);
    }

    @Override public void onAnimationCancel(Animator animator) {
      mListener.onAnimationCancel(mAnimator);
    }

    @Override public void onAnimationRepeat(Animator animator) {
      mListener.onAnimationRepeat(mAnimator);
    }
  }

  private static class NoOverlapView extends View {
    public NoOverlapView(Context context) {
      super(context);
    }

    @Override public boolean hasOverlappingRendering() {
      return false;
    }
  }
}
