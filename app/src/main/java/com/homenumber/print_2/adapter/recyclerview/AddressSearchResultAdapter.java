package com.homenumber.print_2.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.homenumber.print_2.interfaces.IDialogAddressback;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2_2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 주소검색 결과 Adapter
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class AddressSearchResultAdapter extends RecyclerView.Adapter<AddressSearchResultAdapter.ViewHolder> {

    /**
     * Adapter Global variable
     ************************************************************************************************************************************************/
    private Context mContext;
    private int resource;
    private List<RetrofitItemVo.RetrofitAddressVo> dataList;
    private IDialogAddressback callback;

    /**
     * 생성자
     ************************************************************************************************************************************************/
    public AddressSearchResultAdapter(Context context, int resource, List<RetrofitItemVo.RetrofitAddressVo> dataList, IDialogAddressback callback) {
        this.mContext = context;
        this.resource = resource;
        this.dataList = dataList;
        this.callback = callback;
    }

    /**
     * 필수 Generate  :: 새로운 View를 생성하는 메소드
     ************************************************************************************************************************************************/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, parent,false);
//        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(v);
    }

    /**
     * 필수 Generate  :: getView 부분을 담당하는 메소드
     ************************************************************************************************************************************************/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Adapter 아이템
        RetrofitItemVo.RetrofitAddressVo itemVo = dataList.get(position);

        // 재활용 여부
        holder.setIsRecyclable(false);

        // 우편번호
        holder.txt_zipcode.setText(itemVo.getRoadnameZipCode());

        // 주소_1
        holder.txt_address_1.setText(itemVo.getFullRoadnameAddr());

        // 주소_2
        holder.txt_address_2.setText(itemVo.getFullLotnumberAddr());

        // 아이템 선택
        holder.layout_address.setOnClickListener(v -> callback.CallBackClick(itemVo));
    }

    /**
     * 필수 Generate  :: 전체 아이템 갯수
     ************************************************************************************************************************************************/
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 추가 :: Return dataList
     ************************************************************************************************************************************************/
    public List<RetrofitItemVo.RetrofitAddressVo> getDataList() {
        return dataList;
    }

    /**
     * 뷰 활용을 위한 Viewholder
     ************************************************************************************************************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_address)
        LinearLayout layout_address;

        @BindView(R.id.txt_zipcode)
        AppCompatTextView txt_zipcode;

        @BindView(R.id.txt_address_1)
        AppCompatTextView txt_address_1;

        @BindView(R.id.txt_address_2)
        AppCompatTextView txt_address_2;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
