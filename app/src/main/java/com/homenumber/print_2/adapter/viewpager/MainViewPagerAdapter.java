package com.homenumber.print_2.adapter.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.homenumber.print_2.fragment.Fragment_ItemInfo;
import com.homenumber.print_2.fragment.Fragment_Keypad;
import com.homenumber.print_2.fragment.Fragment_Log;
import com.homenumber.print_2.fragment.Fragment_Setting;

import org.jetbrains.annotations.NotNull;

/**
 * Fragment에서 사용되는 BasePagerAdapter
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    // 메인 화면에서 보여줄 페이지 리스트
    // 전체 게시판 페이지가 이 배열에서 관리됩니다
    private Fragment[] pageArr;

    /**
     * 페이지 Fragment 초기화
     **************************************************************************************************************************************/
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        pageArr = new Fragment[] {
                new Fragment_Keypad(),
                new Fragment_ItemInfo(),
                new Fragment_Log(),
                new Fragment_Setting()
        };
    }

    /**
     * Class Type을 비교하여 해당 Fragment의 position을 구한다
     **************************************************************************************************************************************/
    public int getPagePosition(Class<?> fragment) {
        int position = -1;

        if(pageArr != null) {
            for(int i=0; i<pageArr.length; i++) {
                if(fragment == pageArr[i].getClass()) {
                    position = i;
                    break;
                }
            }
        }

        return position;
    }

    /**
     * FragmentActivity에서 사용중인 현재 Fragment를 포지션으로 가져옵니다.
     **************************************************************************************************************************************/
    @NotNull @Override
    public Fragment getItem(int position) {
        if(pageArr != null) {
            return pageArr[position];
        }

        return null;
    }

    /**
     * FragmentActivity에서 사용중인 현재 Fragment의 개수를 가져옵니다.
     **************************************************************************************************************************************/
    @Override
    public int getCount() {
        if(pageArr != null) {
            return pageArr.length;
        }

        return 0;
    }
}
