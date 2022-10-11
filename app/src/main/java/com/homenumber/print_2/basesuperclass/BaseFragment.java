package com.homenumber.print_2.basesuperclass;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.homenumber.print_2.activity.fragmentactivity.MainFragmentActivity;
import com.homenumber.print_2.alertdialog.LoadingProgressDialog;
import com.homenumber.print_2_2.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * 하위 Fragment에서 사용할 Super Class.
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public abstract class BaseFragment extends Fragment {

    /**
     * ButterKnife UI
     ************************************************************************************************************************************************/
    @BindView(R.id.txt_fragment_header)
    protected AppCompatTextView txt_fragment_header;

    /**
     * Fragment 전역 정보 인터페이스
     ************************************************************************************************************************************************/
    protected Context mContext;                                                                                     // Context

    /**
     * View Container
     ************************************************************************************************************************************************/
    protected View container;                                                                                       // Fragment Container

    /**
     * BaseFragmentActivity :: 자기 자신 Context
     ************************************************************************************************************************************************/
    protected BaseFragmentActivity activity;                                                                        // BaseFragmentActivity

    /**
     * Progress Dialog
     ************************************************************************************************************************************************/
    private LoadingProgressDialog progress;                                                                       // ProgressDialog

    /**
     * 게시판 메인 진입 후 서버 데이터 요청 여부
     ************************************************************************************************************************************************/
    protected boolean isStartedRequest = false;                                                                     // 진입 여부

    /**
     * 게시판 메인 화면 여부
     ************************************************************************************************************************************************/
    protected boolean isMainScreen = true;                                                                          // 메인 여부

    /**
     * AlertDialog
     ************************************************************************************************************************************************/
    protected static AlertDialog dialog;                                                                            // ProgressDialog

    /**
     * MainFragmentActivity
     ************************************************************************************************************************************************/
    protected MainFragmentActivity mainFragmentActivity;

    /**
     * Fragment가 화면에 붙은 경우
     ************************************************************************************************************************************************/
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        // Application 전역 정보 인터페이스
        this.mContext = context;
        this.activity = (BaseFragmentActivity) context;

        // Progress Dialog
        progress = new LoadingProgressDialog(mContext);
    }

    /**
     * uiInit 메소드를 호출함으로써 하위 Fragment에서는 uiInit 메소드만 Override하여 사용한다
     ************************************************************************************************************************************************/
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Context
        mContext = container.getContext();

        // MainFragmentActivity
        mainFragmentActivity = ((MainFragmentActivity) getActivity());

        return uiInit(inflater, container, savedInstanceState);
    }

    /**
     * Fragment uiInit
     ************************************************************************************************************************************************/
    protected abstract View uiInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 게시물 리스트 정보 초기화
     ************************************************************************************************************************************************/
    public abstract void resetPageInfo();

    /**
     * 게시글 데이터 요청
     ************************************************************************************************************************************************/
    public abstract void startRequest();

    /**
     * 현재 보여지는 Fragment를 새로고침
     ************************************************************************************************************************************************/
    public void restartFragment() {
        activity.refreshFragment();
    }

    /**
     * 현재 게시판이 메인화면인지 여부
     ************************************************************************************************************************************************/
    public boolean getIsMainScreen() {
        return isMainScreen;
    }

    /**
     * 게시판 메인 진입 후 서버 데이터 요청 여부
     ************************************************************************************************************************************************/
    public boolean getIsStartedRequest() {
        return isStartedRequest;
    }

    /**
     * 프로그레스바 시작
     ************************************************************************************************************************************************/
    public void showProgress() {
        progress.show();
    }

    /**
     * 프로그레스바 종료
     ************************************************************************************************************************************************/
    public void dismissProgress() {
        progress.dismiss();
    }

    /**
     * Dialog 종료
     ************************************************************************************************************************************************/
    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dialog = null;
            }
        }
    }
}
