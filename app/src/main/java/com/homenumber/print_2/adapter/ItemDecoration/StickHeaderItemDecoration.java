package com.homenumber.print_2.adapter.ItemDecoration;

import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * StickHeaderItemDecoration
 * @author 나비이쁜이
 * @since 2019.09.27
 */
public class StickHeaderItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * Interface
     */
    private StickyHeaderInterface mListener;

    /**
     * Stick Header Height
     */
    private int mStickyHeaderHeight;

    /**
     * 생성자 - Adapter이 Interfaces를 참조중이라면 Adapter를 등록해도 가능
     */
    public StickHeaderItemDecoration(@NonNull StickyHeaderInterface listener) {
        mListener = listener;
    }

    /************************************************************************************************************************************************/

    /**
     * 계속해서 그려주는 OnDraw
     */
    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);

        /**
         * 1단계 :: RecyclerView의 최상단 아이템의 Header Postion을 구합니다.
         * 2단계 :: 현재 포지션의 View를 Header에 정의하여 계속해서 그려줄 수 있도록 정의합니다.
         * 3단계 ::
         */

        // 최상단 Sticky Header
        View topChild = recyclerView.getChildAt(0);
        if(topChild == null) {
            return;
        }

        // 최상단 아이템 포지션
        int topChildPosition = recyclerView.getChildAdapterPosition(topChild);
        if(topChildPosition == RecyclerView.NO_POSITION) {
            return;
        }

        // Header의 포지션
        int headerPos = mListener.getHeaderPositionForItem(topChildPosition);

        /********************************************************************************************************************************************/

        // 현재 HeaderView
        View currentHeader = getHeaderViewForItem(headerPos, recyclerView);

        // HeaderView Measure
        fixLayoutSize(recyclerView, currentHeader);

        /********************************************************************************************************************************************/

        // 닿는면 아래의 View가 headerView인지 가져옵니다. 아니라면 null입니다.
        View childInContact = getChildInContact(recyclerView, currentHeader.getBottom(), headerPos);

        // Return 받은 ChildInContact는
        if(childInContact != null && mListener.isHeader(recyclerView.getChildAdapterPosition(childInContact))) {
            moveHeader(canvas, currentHeader, childInContact);
            return;
        }

        // childInContact null이면 단순하게 그려줍니다. 아니라면 이동과 동시에 그려줍니다.
        drawHeader(canvas, currentHeader);
    }

    /**
     * Header View를 그려줍니다. Stick Haader에 접촉하지 않은 경우
     */
    private void drawHeader(Canvas canvas, View header) {
        canvas.save();
        canvas.translate(0,  0);
        header.draw(canvas);
        canvas.restore();
    }

    /**
     * Header View를 이동합니다. Stick Haader에 접촉하는 경우
     */
    private void moveHeader(Canvas canvas, View currentHeader, View nextHeader) {
        canvas.save();
        canvas.translate(0, nextHeader.getTop() - currentHeader.getHeight());
        currentHeader.draw(canvas);
        canvas.restore();
    }

    /**
     * 현재 HeaderView의 정의
     */
    private View getHeaderViewForItem(int headerPosition, RecyclerView recyclerView) {
        // Header LayoutID
        int layoutResId = mListener.getHeaderLayout(headerPosition);

        // Header View
        View header = LayoutInflater.from(recyclerView.getContext()).inflate(layoutResId, recyclerView, false);

        // Header Bind
        mListener.bindHeaderData(header, headerPosition);

        // return Header
        return header;
    }

    /**
     * 상단에 고정돠는 header를 측정하여 배치
     * 여기서 Stick Header Height가 정의
     */
    private void fixLayoutSize(ViewGroup recyclerView, View currentHeader) {
        /**
         * RecyclerView 스펙
         * widthSpec - RecyclerView의 Width - RecyclerView는 Child와 상관없이 주어짐
         * heightSpec - RecyclerView의 Height - Child가 RecyclerView에 제약이 없습니다.
         */
        int widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), View.MeasureSpec.UNSPECIFIED);

        /**
         * RecyclerView의 item 스펙 (Headers)
         */
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(), currentHeader.getLayoutParams().width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(), currentHeader.getLayoutParams().height);

        // View 수치
        currentHeader.measure(childWidthSpec, childHeightSpec);

        // 좌, 상, 우, 하(헤더 높이는 현재 헤더 높이)
        currentHeader.layout(0, 0, currentHeader.getMeasuredWidth(), mStickyHeaderHeight = currentHeader.getMeasuredHeight());
    }

    /**
     *
     * @param recyclerView          :: 현재 Recyclerview
     * @param contactPoint          :: 아래 자식뷰와의 Bottom 절대값
     * @param currentHeaderPos      :: 현재 적용중인 StickyHeader의 헤더 포지션
     */
    private View getChildInContact(RecyclerView recyclerView, int contactPoint, int currentHeaderPos) {

        //
        View childInContact = null;

        // 현재 보이는 아이템 개수만큼 작업
        for(int i = 0; i < recyclerView.getChildCount(); i++) {

            // 높이 오차(headerView와 ChildView의 관계)
            int heightTolerance = 0;

            // 일반적인 자식 View
            View child = recyclerView.getChildAt(i);

            // measure height tolerance with child if child is another header
            if(currentHeaderPos != i) {
                // 일반적인 자식 View가 StickHeader인지 판단
                boolean isChildHeader = mListener.isHeader(recyclerView.getChildAdapterPosition(child));

                // 헤더인 경우
                if(isChildHeader) {
                    // 높이 오차
                    heightTolerance = mStickyHeaderHeight - child.getHeight();
                }
            }

            // headerView가 다음 아래 자식뷰와의 차이
            int childBottomPosition;

//            LogUtil.e("child.getTop() :: " + child.getTop());           // 175
//            LogUtil.e("child.getBottom() :: " + child.getBottom());     // 350

            // 자식의 절대좌표(Top)이 0보다 큰 경우
            if(child.getTop() > 0) {
                childBottomPosition = child.getBottom() + heightTolerance;
            } else {
                childBottomPosition = child.getBottom();
            }

            // 자식뷰가 헤더뷰가 적용되는 View 여부
            if(childBottomPosition > contactPoint) {
                if(child.getTop() <= contactPoint) {
                    // HeaderView가 겹치는 순간
                    childInContact = child;
                    break;
                }
            }
        }

        return childInContact;
    }

    /************************************************************************************************************************************************/

    /**
     * Adapter interface
     */
    public interface StickyHeaderInterface {

        /**
         * This method gets called by {@link StickHeaderItemDecoration} to fetch the position of the header item in the adapter
         * that is used for (represents) item at specified position.
         * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
         * @return int. Position of the header item in the adapter.
         */
        int getHeaderPositionForItem(int itemPosition);

        /**
         * This method gets called by {@link StickHeaderItemDecoration} to get layout resource id for the header item at specified adapter's position.
         * @param headerPosition int. Position of the header item in the adapter.
         * @return int. Layout resource id.
         */
        int getHeaderLayout(int headerPosition);

        /**
         * This method gets called by {@link StickHeaderItemDecoration} to setup the header View.
         * @param header View. Header to set the data on.
         * @param headerPosition int. Position of the header item in the adapter.
         */
        void bindHeaderData(View header, int headerPosition);

        /**
         * This method gets called by {@link StickHeaderItemDecoration} to verify whether the item represents a header.
         * @param itemPosition int.
         * @return true, if item at the specified adapter's position represents a header.
         */
        boolean isHeader(int itemPosition);
    }
}
