package com.homenumber.print_2.animationhelp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 좋아요 선택시에 대한 애니메이션 효과를 부여하는 Util
 * @author 나비이쁜이
 * @since 2019.04.10
 */
public class HeartUtil {

    /**
     * 애니메이션 효과가 적용될 이미지뷰
     ************************************************************************************************************************************************/
    private AppCompatImageView heartWhite, heartRed;

    /*
    AccelerateDecelerateInterpolator            :: 점점 빠르고 느리게
    AccelerateInterpolator                      :: 점점 빠르게
    DecelerateInterpolator                      :: 점점 느리게
    OvershootInterpolator                       :: 도착 위치를 조금 지나쳤다가 도착 위치로 이동
    AnticipateInterpolator                      ;; 시작 위치를 조금 뒤로 당겼다 이동
    AnticipateOvershootInterpolator             ;; 위 둘을 동시에 이동
    BounceInterpolator                          ;; 도착 위치에서 튕김
     */

    /**
     * Interpolator
     ************************************************************************************************************************************************/
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();

    /**
     * 생성자
     ************************************************************************************************************************************************/
    public HeartUtil(AppCompatImageView heartWhite, AppCompatImageView heartRed) {
        this.heartWhite = heartWhite;
        this.heartRed = heartRed;
    }

    /**
     * 버튼 행동에 따른 작업 - 좋아요 버튼
     ************************************************************************************************************************************************/
    public void toggleLike() {
        // 개별적인 애니메이션이 아닌 통합적으로 정리하여 애니메이션을 적용할 경우 사용
        AnimatorSet animationSet =  new AnimatorSet();

        if(heartRed.getVisibility() == View.VISIBLE) {
            // Target은 하얀색 하트입니다. 하얀색 하트의 크기를 0.1 비율로 축소합니다.
            heartWhite.setScaleX(0.1f);
            heartWhite.setScaleY(0.1f);

            // X축 Animator를 설정합니다.
            // Target을 scaleY로 시작할 각도에서 애니메이션이 종료될 시점의 각도를 지정합니다.
            // heartWhite -> scaleX -> 0.1f start -> 1f end
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(heartWhite, View.SCALE_X, 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);

            // Y축 Animator를 설정합니다.
            // Target을 scaleY로 시작할 각도에서 애니메이션이 종료될 시점의 각도를 지정합니다.
            // heartWhite -> scaleY -> 0.1f start -> 1f end
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(heartWhite, View.SCALE_Y, 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);

            // View를 설정합니다.
            heartRed.setVisibility(View.INVISIBLE);
            heartWhite.setVisibility(View.VISIBLE);

            // 설정한 Animator를 AnimationSet에 함께 지정합니다.
            animationSet.playTogether(scaleDownY, scaleDownX);
        } else if(heartRed.getVisibility() == View.INVISIBLE) {
            // Target은 빨강색 하트입니다. 빨강색 하트의 크기를 0.1 비율로 축소합니다.
            heartRed.setScaleX(0.1f);
            heartRed.setScaleY(0.1f);

            // X축 Animator를 설정합니다.
            // Target을 scaleY로 시작할 각도에서 애니메이션이 종료될 시점의 각도를 지정합니다.
            // heartRed -> scaleX -> 0.1f start -> 1f end
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(heartRed, View.SCALE_X, 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);

            // Y축 Animator를 설정합니다.
            // Target을 scaleY로 시작할 각도에서 애니메이션이 종료될 시점의 각도를 지정합니다.
            // heartRed -> scaleY -> 0.1f start -> 1f end
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(heartRed, View.SCALE_Y, 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);

            // View를 설정합니다.
            heartRed.setVisibility(View.VISIBLE);
            heartWhite.setVisibility(View.INVISIBLE);

            // 설정한 Animator를 AnimationSet에 함께 지정합니다.
            animationSet.playTogether(scaleDownY, scaleDownX);
        }

        // AnimatonSet을 시작합니다.
        animationSet.start();
    }
}
