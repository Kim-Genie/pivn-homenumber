package com.homenumber.print_2.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.homenumber.print_2.Constant;
import com.homenumber.print_2.basesuperclass.GlideApp;
import com.homenumber.print_2.interfaces.IPrintLogCallback;
import com.homenumber.print_2.retrofit2.RetrofitItemVo;
import com.homenumber.print_2_2.R;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 주소록 Adapter
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class PrintLogAdapter extends RecyclerView.Adapter<PrintLogAdapter.ViewHolder> {

    /**
     * Adapter Global variable
     ************************************************************************************************************************************************/
    private Context mContext;
    private int resource;
    private List<RetrofitItemVo.RetrofitOrderNumberList> dataList;
    private IPrintLogCallback callback;
    private SimpleDateFormat inputFormat;

    /**
     * 생성자
     ************************************************************************************************************************************************/
    public PrintLogAdapter(Context context, int resource, List<RetrofitItemVo.RetrofitOrderNumberList> dataList, IPrintLogCallback callback) {
        this.mContext = context;
        this.resource = resource;
        this.dataList = dataList;
        this.callback = callback;

        // 시간 지정
        inputFormat = new  SimpleDateFormat("yy.MM.dd HH:mm:ss", Locale.KOREA);
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
        RetrofitItemVo.RetrofitOrderNumberList itemVo = dataList.get(position);

        String on = itemVo.getOrderNo();
        String[] ox = splitStringEvery(on, 4);

        String hn = itemVo.getReceiverHn();
        String[] hx = splitStringEvery(hn,3);

        // 재활용 여부
        holder.setIsRecyclable(false);

        // 받는사람 홈넘버
        holder.txt_homenumber.setText(hx[0]+"-"+hx[1]+hx[2]+hx[3]);

        // 받는사람 이름
        holder.txt_name.setText(itemVo.getReceiverNm());

        // 예약번호
        holder.txt_ordernumber.setText(ox[0] + "-" + ox[1] + "-" + ox[2]);

        // 예약시간
        holder.txt_log_time.setText(inputFormat.format(itemVo.getOrderInfoDate()));

//        // 출력회사 이미지
        if(StringUtils.equals(itemVo.getCourierCode(), Constant.PRINT_CVSNET)) {
            GlideApp.with(mContext)
                    .load(R.drawable.img_cvsnet)
//                    .centerCrop()
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_company);
        } else if(StringUtils.equals(itemVo.getCourierCode(), Constant.PRINT_CJ)) {
            GlideApp.with(mContext)
                    .load(R.drawable.img_cj)
//                    .centerCrop()
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_company);
        } else if(StringUtils.equals(itemVo.getCourierCode(), Constant.PRINT_LOGEN)) {
            GlideApp.with(mContext)
                    .load(R.drawable.img_logen)
//                    .centerCrop()
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_company);
        }else if(StringUtils.equals(itemVo.getCourierCode(), Constant.PRINT_LOTTE)) {
            GlideApp.with(mContext)
                    .load(R.drawable.img_lotte)
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_company);
        }
        // 출력내역 자세히 보기 (20개)
        holder.iv_arrow.setOnClickListener(v -> callback.CallBackClick(false, itemVo));

        // 키패드로 이동하기
        holder.layout_print_log.setOnClickListener(v -> callback.CallBackClick(true, itemVo));
    }

    /**
     * 필수 Generate  :: 전체 아이템 갯수
     ************************************************************************************************************************************************/
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 추가 :: dataList를 돌려줍니다.
     ************************************************************************************************************************************************/
    public void setDataList(List<RetrofitItemVo.RetrofitOrderNumberList> dataList) {
        this.dataList = dataList;
    }

    /**
     * 뷰 활용을 위한 Viewholder
     ************************************************************************************************************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_homenumber)
        AppCompatTextView txt_homenumber;

        @BindView(R.id.txt_name)
        AppCompatTextView txt_name;

        @BindView(R.id.txt_ordernumber)
        AppCompatTextView txt_ordernumber;

        @BindView(R.id.txt_log_time)
        AppCompatTextView txt_log_time;

        @BindView(R.id.layout_print_log)
        ConstraintLayout layout_print_log;

        @BindView(R.id.iv_company)
        AppCompatImageView iv_company;

        @BindView(R.id.iv_arrow)
        AppCompatImageView iv_arrow;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     *  String Split
     */
    // string 문자열 4글자로 끊어서 출력
    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

}
