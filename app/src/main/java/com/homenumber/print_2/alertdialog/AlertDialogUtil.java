package com.homenumber.print_2.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;

import com.homenumber.print_2.interfaces.IDialogListCallback;
import com.homenumber.print_2.interfaces.IStringCallback;
import com.homenumber.print_2.listener.ButtonSelectorListener;
import com.homenumber.print_2.util.ToastUtil;
import com.homenumber.print_2_2.R;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Dialog을 띄우기 위한 커스텀 Class
 * @author 나비이쁜이
 * @since 2019.08.19
 */
public class AlertDialogUtil {

	/**
	 * AlertDialog
     ************************************************************************************************************************************************/
	private static AlertDialog dialog;

    /**
     * 선택사항 단일 팝업
     ************************************************************************************************************************************************/
    public static void showSingleDialog(Context context, @NonNull String message, DialogInterface.OnClickListener positiveCallback) {
        dismissDialog();

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getText(R.string.str_notice));
        alert_builder.setMessage(message);
        alert_builder.setPositiveButton(context.getText(R.string.str_confirm), positiveCallback);



        dialog = alert_builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 주소록 저장 여부 파악 팝업
     ************************************************************************************************************************************************/
    public static void showDoubleDialog(Context context, @NonNull String message, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener negativeCallback) {
        dismissDialog();

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getText(R.string.str_notice));
        alert_builder.setMessage(message);
        alert_builder.setPositiveButton(context.getText(R.string.str_close), positiveCallback);
        alert_builder.setNegativeButton(context.getText(R.string.str_save_close), negativeCallback);

        dialog = alert_builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 주소록 삭제 여부 파악 팝업
     ************************************************************************************************************************************************/
    public static void showAddressDeleteDialog(Context context, @NonNull String message, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener negativeCallback) {
        dismissDialog();

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getText(R.string.str_notice));
        alert_builder.setMessage(message);
        alert_builder.setPositiveButton(context.getText(R.string.str_cancle), positiveCallback);
        alert_builder.setNegativeButton(context.getText(R.string.str_address_delete), negativeCallback);

        dialog = alert_builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 로그아웃 팝업
     ************************************************************************************************************************************************/
    public static void showLogoutChoiceDialog(Context context, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener neutralCallback) {
        dismissDialog();

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setMessage(context.getString(R.string.msg_logout_quest));
        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), positiveCallback);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), neutralCallback);

        dialog = alert_builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 휴대폰 인증 팝업
     ************************************************************************************************************************************************/
    public static void showPhonenumberAcceptDialog(Context context, IStringCallback callback, View.OnClickListener textviewClick) {

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(new android.view.ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        View v = View.inflate(context, R.layout.layout_phonenumber_accept, null);

        // ButtonSelectorListener
        ButtonSelectorListener buttonSelector = new ButtonSelectorListener(textviewClick, null);

        AppCompatEditText et_accept_number = v.findViewById(R.id.et_accept_number);
        AppCompatButton btn_phonenumber_accept = v.findViewById(R.id.btn_phonenumber_accept);
        AppCompatTextView txt_retry_phonenumber_accept = v.findViewById(R.id.txt_retry_phonenumber_accept);

        btn_phonenumber_accept.setOnClickListener(v1 -> callback.CallBackClick(Objects.requireNonNull(et_accept_number.getText()).toString()));
//        txt_retry_phonenumber_accept.setOnClickListener(textviewClick);
        txt_retry_phonenumber_accept.setOnTouchListener(buttonSelector);
        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(true);
        if(dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * 주소 검색 팝업
     ************************************************************************************************************************************************/
    public static void showAddressSearchDialog(Context context, IDialogListCallback callback) {
        // DismissDialog
        dismissDialog();

        // 키보드 내리기
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(new android.view.ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        View v = View.inflate(context, R.layout.layout_address_search, null);

        AppCompatTextView txt_sido = v.findViewById(R.id.txt_sido);
        AppCompatTextView title_doro_jibun = v.findViewById(R.id.title_doro_jibun);
        CheckBox check_1 = v.findViewById(R.id.check_1);
        CheckBox check_2 = v.findViewById(R.id.check_2);
        AppCompatEditText et_sigungu = v.findViewById(R.id.et_sigungu);
        AppCompatEditText et_doro_jibun = v.findViewById(R.id.et_doro_jibun);
        AppCompatEditText et_gunmul = v.findViewById(R.id.et_gunmul);
        AppCompatButton btn_address_search = v.findViewById(R.id.btn_address_search);

        /**
         * 기본적으로 첫번째 체크박스에 체크되도록 설정합니다.
         */
        check_1.setChecked(true);

        /**
         * 시/도 선택사항
         */
        txt_sido.setOnClickListener(v12 -> {
            PopupMenu popupMenu = new PopupMenu(context, v12);
            popupMenu.getMenuInflater().inflate(R.menu.alert_addres_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle item selection
                switch (item.getItemId()) {
                    case R.id.address_0:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_1:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_2:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_3:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_4:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_5:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_6:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_7:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_8:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_9:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_10:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_11:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_12:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_13:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_14:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_15:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    case R.id.address_16:
                        txt_sido.setText(item.getTitle().toString());
                        return true;

                    default:
                        return true;
                }
            });
            popupMenu.show();
        });

        /**
         * Checkbox Listener
         */
        CompoundButton.OnCheckedChangeListener check = (buttonView, isChecked) -> {
            // 체크박스 선택시에 대하여 다른 체크박스와 타이틀명 변경에 대한 옵션을 설정합니다.
            switch (buttonView.getId()) {
                case R.id.check_1:
                    if(check_1.isChecked()) {
                        title_doro_jibun.setText(context.getString(R.string.str_search_doro_star));
                        et_doro_jibun.setHint(R.string.str_search_ex_doro);
                        check_2.setChecked(false);
                    }
                    break;

                case R.id.check_2:
                    if(check_2.isChecked()) {
                        title_doro_jibun.setText(context.getString(R.string.str_search_jibun_star));
                        et_doro_jibun.setHint(R.string.str_search_ex_jibun);
                        check_1.setChecked(false);
                    }
                    break;
            }
        };

        /**
         * Button Listener
         */
        View.OnClickListener button = v1 -> {
            // 통신에 사용할 데이터를 순서대로 지정
            String temp1 = txt_sido.getText().toString();
            String temp2 = Objects.requireNonNull(et_sigungu.getText()).toString();
            String temp3 = Objects.requireNonNull(et_doro_jibun.getText()).toString();
            String temp4 = Objects.requireNonNull(et_gunmul.getText()).toString();

            // 체크박스를 하나라도 설정하지 않은 경우
            if(!check_1.isChecked() && !check_2.isChecked()) {
                ToastUtil.showToastAsShort(context, context.getString(R.string.msg_checkbox_please));
                return;
            }

            // [시/도]가 선택인 경우
            if(temp1.equals(context.getString(R.string.str_choice))) {
                ToastUtil.showToastAsShort(context, context.getString(R.string.msg_sido_please));
                return;
            }

            // 도로명 & 지번명을 사용하지 않은 경우
            if(StringUtils.isEmpty(Objects.requireNonNull(et_doro_jibun.getText()).toString())) {
                if(check_1.isChecked()) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_doro_please));
                    return;
                }

                if(check_2.isChecked()) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_jibun_please));
                    return;
                }
            }

            // 도로명 & 지번명이 글자가 2글자가 안되는 경우
            if(et_doro_jibun.getText().toString().length() <= 1) {
                if(check_1.isChecked()) {
                    ToastUtil.showToastAsLong(context, context.getString(R.string.msg_doro_text_size_2_please));
                    return;
                }

                if(check_2.isChecked()) {
                    ToastUtil.showToastAsLong(context, context.getString(R.string.msg_jibun_text_size_2_please));
                    return;
                }
                return;
            }

            // 키보드가 올라와 있는 경우 키보드를 내립니다.
            inputMethodManager.hideSoftInputFromWindow(btn_address_search.getWindowToken(), 0);

            // callback
            if(check_1.isChecked()) {
                callback.CallBackListClick(true, temp1, temp2, temp3, temp4);
            } else if(check_2.isChecked()) {
                callback.CallBackListClick(false, temp1, temp2, temp3, temp4);
            }
        };

        // setListener
        check_1.setOnCheckedChangeListener(check);
        check_2.setOnCheckedChangeListener(check);
        btn_address_search.setOnClickListener(button);

        // setBuilder
        builder.setView(v);

        dialog = builder.create();
        dialog.setCancelable(true);
        if(dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * 키패드 설정 팝업
     ************************************************************************************************************************************************/
    public static void showKeypadChoiceDialog(Context context, int checkitem, IStringCallback positiveCallback) {
        dismissDialog();

        // 아이템 리스트
        final String [] items = {context.getString(R.string.str_sound), context.getString(R.string.str_vibrator), context.getString(R.string.str_no_effect)};

        // retrun 할 데이터
        final String[] keypad_opttion = new String[1];

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getString(R.string.msg_choice_keypad));
        alert_builder.setSingleChoiceItems(items, checkitem, (dialog, which) -> keypad_opttion[0] = items[which]);

        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), null);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), (dialog, which) -> AlertDialogUtil.dismissDialog());

        final AlertDialog alertDialog = alert_builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if(StringUtils.isEmpty(keypad_opttion[0])) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_choice_keypad));
                    return;
                }

                // retrun callback
                positiveCallback.CallBackClick(keypad_opttion[0]);
                alertDialog.dismiss();
            });
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 출력내역 자세히 보기
     ************************************************************************************************************************************************/
//    public static void showPrintLogDialog(Context context, List<RetrofitItemVo> dataList) {
//        // DismissDialog
//        dismissDialog();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(new android.view.ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
//        View v = View.inflate(context, R.layout.layout_detail_print_log, null);
//
//        RecyclerView log_detail_recyclerview = v.findViewById(R.id.log_detail_recyclerview);
//        PrintLogAdapter adapter = new PrintLogAdapter(context, R.layout.item_log_content, dataList, (isCallback, item) -> { });
//        log_detail_recyclerview.setAdapter(adapter);
//
//        AppCompatTextView txt_confirm = v.findViewById(R.id.txt_confirm);
//
//        txt_confirm.setOnClickListener(v1 -> AlertDialogUtil.dismissDialog());
//
//        // setBuilder
//        builder.setView(v);
//
//        dialog = builder.create();
//        dialog.setCancelable(true);
//        if(dialog.getWindow() != null)
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//    }

    /**
     * 택배사 설정
     ************************************************************************************************************************************************/
    public static void showDeliveryDialog(Context context, int itemcheck, IStringCallback positiveCallback) {
        dismissDialog();

        // 아이템 리스트
        final String [] items = {context.getString(R.string.str_logen), context.getString(R.string.str_cj)};

        // retrun 할 데이터
        final String[] keypad_opttion = new String[1];
        keypad_opttion[0] = items[itemcheck];

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getString(R.string.msg_choice_delivery));
        alert_builder.setSingleChoiceItems(items, itemcheck, (dialog, which) -> keypad_opttion[0] = items[which]);
        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), null);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), (dialog, which) -> AlertDialogUtil.dismissDialog());

        final AlertDialog alertDialog = alert_builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if(StringUtils.isEmpty(keypad_opttion[0])) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_choice_delivery));
                    return;
                }

                // retrun callback
                positiveCallback.CallBackClick(keypad_opttion[0]);
                alertDialog.dismiss();
            });
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    /**
     * 상품명 설정
     */
    public static void showItemName(Context context, int itemcheck, IStringCallback positiveCallback, IStringCallback positiviCallback2) {
        dismissDialog();

        // 아이템 리스트
        final String [] items = {context.getString(R.string.str_item_name_1), context.getString(R.string.str_item_name_2),context.getString(R.string.str_item_name_3)
                                ,context.getString(R.string.str_item_name_4),context.getString(R.string.str_item_name_5),context.getString(R.string.str_item_name_6)
                                ,context.getString(R.string.str_item_name_7),context.getString(R.string.str_item_name_8)};

        // retrun 할 데이터
        final String[] keypad_opttion = new String[1];
        keypad_opttion[0] = items[itemcheck];

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle("품목선택");
        alert_builder.setSingleChoiceItems(items, itemcheck, (dialog, which) -> keypad_opttion[0] = items[which]);
        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), null);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), (dialog, which) -> AlertDialogUtil.dismissDialog());

        final AlertDialog alertDialog = alert_builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if(StringUtils.isEmpty(keypad_opttion[0])) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_item_message_7));
                    return;
                }

                // retrun callback
                positiveCallback.CallBackClick(keypad_opttion[0]);
                positiviCallback2.CallBackClick(keypad_opttion[0]);

                alertDialog.dismiss();



            });
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 결제방법 설정
     */
    public static void showItemHow(Context context, int itemcheck, IStringCallback positiveCallback) {
        dismissDialog();

        // 아이템 리스트
        final String [] items = {context.getString(R.string.str_item_how_1), context.getString(R.string.str_item_how_2)};

        // retrun 할 데이터
        final String[] keypad_opttion = new String[1];
        keypad_opttion[0] = items[itemcheck];

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getString(R.string.msg_item_message_8));
        alert_builder.setSingleChoiceItems(items, itemcheck, (dialog, which) -> keypad_opttion[0] = items[which]);
        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), null);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), (dialog, which) -> AlertDialogUtil.dismissDialog());

        final AlertDialog alertDialog = alert_builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if(StringUtils.isEmpty(keypad_opttion[0])) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_item_message_8));
                    return;
                }

                // retrun callback
                positiveCallback.CallBackClick(keypad_opttion[0]);
                alertDialog.dismiss();
            });
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 배송메세지 설정
     */
    public static void showItemMessageDialog(Context context, int itemcheck, IStringCallback positiveCallback) {
        dismissDialog();

        // 아이템 리스트
        final String [] items = {context.getString(R.string.msg_item_message_1), context.getString(R.string.msg_item_message_2), context.getString(R.string.msg_item_message_3), context.getString(R.string.msg_item_message_4), context.getString(R.string.msg_item_message_5), context.getString(R.string.msg_item_message_6)};

        // retrun 할 데이터
        final String[] keypad_opttion = new String[1];
        keypad_opttion[0] = items[itemcheck];

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Widget_Material_ButtonBar_AlertDialog));
        alert_builder.setTitle(context.getString(R.string.msg_item_message));
        alert_builder.setSingleChoiceItems(items, itemcheck, (dialog, which) -> keypad_opttion[0] = items[which]);
        alert_builder.setPositiveButton(context.getString(R.string.str_confirm), null);
        alert_builder.setNegativeButton(context.getString(R.string.str_cancle), (dialog, which) -> AlertDialogUtil.dismissDialog());

        final AlertDialog alertDialog = alert_builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if(StringUtils.isEmpty(keypad_opttion[0])) {
                    ToastUtil.showToastAsShort(context, context.getString(R.string.msg_item_message));
                    return;
                }

                // retrun callback
                positiveCallback.CallBackClick(keypad_opttion[0]);
                alertDialog.dismiss();
            });
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
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
    /**
     * 선택사항 단일 팝업
     ************************************************************************************************************************************************/
    public static void orderKeypadDialog(Context context, @NonNull String message, DialogInterface.OnClickListener positiveCallback) {
        dismissDialog();

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogTheme));
        alert_builder.setTitle(context.getText(R.string.str_order_title));

        alert_builder.setMessage(message+"\n\n"+context.getText(R.string.msg_order_guide));
        alert_builder.setPositiveButton(context.getText(R.string.str_confirm), positiveCallback);

        dialog = alert_builder.create();
        dialog.setCancelable(false);
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
    }
}