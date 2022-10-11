package com.homenumber.print_2.adapter.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.homenumber.print_2.interfaces.IStringCallback;
import com.homenumber.print_2_2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 주소검색 페이지 Adapter
 * @author 나비이쁜이
 * @since 2019.07.19
 */
public class AddressSearchPageAdapter extends RecyclerView.Adapter<AddressSearchPageAdapter.ViewHolder> {

    /**
     * Adapter Global variable
     ************************************************************************************************************************************************/
    private Context mContext;
    private int resource;
    private List<String> dataList = new ArrayList<>();
    private IStringCallback callback;
    private int globalPosition;

    /**
     * 생성자
     ************************************************************************************************************************************************/
    public AddressSearchPageAdapter(Context context, int resource, int size, IStringCallback callback) {
        this.mContext = context;
        this.resource = resource;
        this.callback = callback;

        // 사이즈 개수만큼 하나씩 String으로 변환하여 dataList에 적용
        for(int i = 1 ; i <= size ; i++)
            this.dataList.add(String.valueOf(i));
    }

    /**
     * 필수 Generate  :: 새로운 View를 생성하는 메소드
     ************************************************************************************************************************************************/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(resource, parent, false);

        return new ViewHolder(v);
    }

    /**
     * 필수 Generate  :: getView 부분을 담당하는 메소드
     ************************************************************************************************************************************************/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 재활용 여부
        holder.setIsRecyclable(false);

        // 페이지
        holder.txt_page.setText(dataList.get(position));

        // 저장된 포지션에 textview를 설정
        if(position == globalPosition)
            holder.txt_page.setTextColor(Color.RED);
        else
            holder.txt_page.setTextColor(Color.BLACK);
    }

    /**
     * 필수 Generate  :: 전체 아이템 갯수
     ************************************************************************************************************************************************/
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 뷰 활용을 위한 Viewholder
     ************************************************************************************************************************************************/
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_page)
        AppCompatTextView txt_page;

        @BindView(R.id.layout_page)
        LinearLayout layout_page;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // 레이아웃을 선택하는 경우
            layout_page.setOnClickListener(v -> {
                // 현재 선택한 포지션을 저장합니다.
                globalPosition = getAdapterPosition();

                // 저장된 포지션으로 작업에 필요한 부분을 설정하기 위하여 adapter를 초기화
                notifyDataSetChanged();

                // callback
                callback.CallBackClick(txt_page.getText().toString());
            });
        }
    }
}
