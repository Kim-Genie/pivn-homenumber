package com.homenumber.print_2.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.homenumber.print_2.adapter.ItemDecoration.StickHeaderItemDecoration;
import com.homenumber.print_2.adapter.base.BaseViewHolder;
import com.homenumber.print_2.vo.MyHomeNumberVo;
import com.homenumber.print_2_2.R;
import com.homenumber.print_2_2.databinding.LayoutRecyclerMyhomenumberContentBinding;
import com.homenumber.print_2_2.databinding.LayoutRecyclerMyhomenumberHeaderBinding;

import java.util.List;

/**
 * 협력사 adapter
 */
public class MyHomeNumberListAdapter extends RecyclerView.Adapter<BaseViewHolder<MyHomeNumberVo>> implements StickHeaderItemDecoration.StickyHeaderInterface{

    /**
     * Adapter Global variable
     */
    private Context mContext;
    private List<MyHomeNumberVo> dataList;

    /**
     * 생성자
     */
    public MyHomeNumberListAdapter(Context context, List<MyHomeNumberVo> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    /**
     * 필수 Generate  :: 새로운 View를 생성하는 메소드
     */
    @NonNull
    @Override
    public BaseViewHolder<MyHomeNumberVo> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_myhomenumber_header, parent, false);
                return new HeaderHolder(headerView);

            case 1:
                View childView = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_myhomenumber_content, parent, false);
                return new ViewHolder(childView);

            default:
                View defaultView = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_myhomenumber_content, parent, false);
                return new ViewHolder(defaultView);
        }
    }



    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    /**
     * 필수 Generate  :: getView 부분을 담당하는 메소드
     */
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        holder.bind(dataList.get(position), position);
    }

    /**
     * 필수 Generate  :: 전체 아이템 갯수
     */
    @Override
    public int getItemCount() {
        return dataList.size() ;
    }

    /**
     * 추가 :: dataList를 돌려줍니다.
     */
    public void setDataList(List<MyHomeNumberVo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if(this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition = itemPosition - 1;
        } while (itemPosition >= 0);

        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if( dataList.get(headerPosition).type == 0) {
            return R.layout.layout_recycler_myhomenumber_header;
        } else {
            return R.layout.layout_recycler_myhomenumber_content;
        }
    }
    /**
     * 아이템 Type
     */
    @Override
    public long getItemId(int position) {
        return dataList.get(position).type;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    @Override
    public boolean isHeader(int itemPosition) {

        return dataList.get(itemPosition).type == 0;
    }

    /**
     * 뷰 활용을 위한 Viewholder
     */
    class ViewHolder extends BaseViewHolder<MyHomeNumberVo> {

        /**
         * item_sticky_header -> Binding
         */
//        LayoutRecyclerRecommendContentBinding mBinding;
        LayoutRecyclerMyhomenumberContentBinding mBinding;

        private ViewHolder(@NonNull View view) {
            super(view);

            // set binding
            mBinding = DataBindingUtil.bind(view);
        }
        @Override
        public void bind(MyHomeNumberVo recommendVo, Integer position) {

            mBinding.txtNomalHn.setText(recommendVo.itemVo.getNomalHn());
            mBinding.txtGoldHn.setText(recommendVo.itemVo.getGoldHn());
            mBinding.txtBlngHn.setText(recommendVo.itemVo.getBlngHn());


        }
    }
    // HeaderItem Binding에 사용되는 Holder
     class HeaderHolder extends BaseViewHolder<MyHomeNumberVo> {

        LayoutRecyclerMyhomenumberHeaderBinding binding;
        /**
         * 생성자
         */
        private HeaderHolder(@NonNull View view) {
            super(view);

            // set binding
            binding = DataBindingUtil.bind(view);
        }

        @Override
        public void bind(MyHomeNumberVo itemVo, Integer position) {

        }


    }


}
