package com.lc.template.utils.picker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.contrarywind.interfaces.IPickerViewData;
import com.lc.template.R;
import com.lc.template.utils.TextUtil;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Wei Ting
 * on 2024/4/18
 * Description
 */
public class PickerViewTool {
    private static Context mContext;

    private static OptionsPickerView optionsPickerView;
    private static TimePickerView datePickerView;

    //性别选择
    public static List<String> sexList() {
        List<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        return list;
    }

    public static void showPickerView(Context context, final List<IPickerViewData> itemList, final PickerType type, final String title, final String cancle, final OnItemViewClickCallBack callBack) {
        mContext = context;

        optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                switch (type) {

                    case IDENTITY://身份
                        callBack.onItemViewClickCallBack(options1, type.IDENTITY.name(), itemList.get(options1));
                        break;
                    case SEX://性别
                        callBack.onItemViewClickCallBack(0, type.SEX.name(), sexList().get(options1));
                        break;
                }
            }
        }).setLayoutRes(R.layout.picker_dialog_time_layout, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView titleTv = (TextView) v.findViewById(R.id.dialog_time_title_tv);
                final TextView tvSubmit = (TextView) v.findViewById(R.id.dialog_time_complete_tv);
                TextView cancel = (TextView) v.findViewById(R.id.dialog_time_cancle_tv);
                if (!TextUtil.isNull(title)) {
                    titleTv.setText(title);
                }
                if (!TextUtil.isNull(cancle)) {
                    cancel.setText(cancle);
                }
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsPickerView.returnData();
                        optionsPickerView.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsPickerView.dismiss();
                    }
                });

            }
        }).setDividerColor(context.getResources().getColor(R.color.line_light))//设置分割线的颜色
                .setTextColorCenter(context.getResources().getColor(R.color.text_black))//设置选中项的颜色
                .setBgColor(context.getResources().getColor(R.color.transparent)) // 滚轮背景颜色 Night mode
                //          .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(true)//点击背的地方不消失
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.2f).isDialog(true).build(); // 设置两横线之间的间隔倍数
        Dialog mDialog = optionsPickerView.getDialog();
        //        mDialog.setCancelable(false);
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            params.leftMargin = params.rightMargin = 0;
            optionsPickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //使自定义dialog的高度为包含内容的高度
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay();
            layoutParams.width = d.getWidth();
            dialogWindow.setAttributes(layoutParams);
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }


        }

        switch (type) {
            case IDENTITY:
                optionsPickerView.setPicker(itemList);
                break;
            case SEX:
                optionsPickerView.setPicker(sexList());
                break;
        }

        optionsPickerView.show();
    }


    /**
     * @param context
     * @param start_time 开始时间 这个是当有选择开始时间和结束时间的时候  用来拦截结束时间大于开始时间的 如果单一选择时间 传空就是默认
     * @param type       暂时没用上 预留
     * @param year       是否显示年
     * @param month      是否显示月
     * @param day        是否显示日
     * @param callBack   回调
     */
    public static void onShowDatePickerView(Context context, String start_time, String end_time, final String type, boolean year, boolean month, boolean day, final OnItemViewClickCallBack callBack) {
        mContext = context;
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar startDate = Calendar.getInstance();
        Calendar seleteDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        if (StringUtils.isEmpty(start_time)) {
            startDate.set(1970, 0, 1);
        } else {
            String[] start = start_time.split("-");
            startDate.set(Integer.valueOf(start[0]), Integer.valueOf(start[1]) - 1, Integer.valueOf(start[2]));
        }
        if (!TextUtil.isNull(end_time)) {
            String[] end = end_time.split("-");
            endDate.set(Integer.valueOf(end[0]), Integer.valueOf(end[1]) - 1, Integer.valueOf(end[2]));
        } else {
            endDate.set(seleteDate.get(Calendar.YEAR), seleteDate.get(Calendar.MONTH), seleteDate.get(Calendar.DATE));
        }
        if (type == "birthday") {
            seleteDate.set(2000, 10, 1);
        } else {
            seleteDate.set(seleteDate.get(Calendar.YEAR), seleteDate.get(Calendar.MONTH),
                    seleteDate.get(Calendar.DATE));
        }
        datePickerView = new TimePickerBuilder(context, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                callBack.onItemViewClickCallBack(0, type, date);
            }
        }).setDate(seleteDate).setRangDate(startDate, endDate).setLayoutRes(R.layout.picker_dialog_datetime_layout,
                new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.dialog_datetime_complete_tv);
                        TextView cancel = (TextView) v.findViewById(R.id.dialog_datetime_cancle_tv);
                        TextView titleTv = (TextView) v.findViewById(R.id.picker_title_tv);
                        titleTv.setText(type);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datePickerView.returnData();
                                datePickerView.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datePickerView.dismiss();
                            }
                        });

                    }
                }).setDividerColor(context.getResources().getColor(R.color.line))//设置分割线的颜色
                .setTextColorCenter(context.getResources().getColor(R.color.text_black))//设置选中项的颜色
                .setBgColor(context.getResources().getColor(R.color.transparent)) // 滚轮背景颜色 Night mode
                .setContentTextSize(15)//滚轮文字大小
                .setType(new boolean[]{year, month, day, type.equals("hhmm") ? true : false, type.equals("hhmm") ? true : false, false}).isCenterLabel(false) //是否只显示中间选中项的label
                // 文字，false则每项item全部都带有label。
                .setLineSpacingMultiplier(3.2f).isDialog(true).build(); // 设置两横线之间的间隔倍数
        Dialog mDialog = datePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            params.leftMargin = params.rightMargin = 0;
            datePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //使自定义dialog的高度为包含内容的高度
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay();
            layoutParams.width = d.getWidth();
            dialogWindow.setAttributes(layoutParams);
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }


        }
        datePickerView.show();
    }


    /**
     * @param context
     * @param start_time 开始时间 这个是当有选择开始时间和结束时间的时候  用来拦截结束时间大于开始时间的 如果单一选择时间 传空就是默认
     * @param type       暂时没用上 预留
     * @param callBack   回调
     */
    public static void onShowDatePickerView(Context context, String start_time, String end_time, String type, String select_time, boolean[] times, OnItemViewClickCallBack callBack) {
        mContext = context;
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar seleteDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        if (TextUtils.isEmpty(select_time)) {
            seleteDate.setTime(new Date());
        } else {
            String[] select = select_time.split("-");
            if (select[2].length() > 2) {
                select[2] = select[2].substring(0, 2);
            }
            seleteDate.set(Integer.parseInt(select[0]), Integer.parseInt(select[1]) - 1, Integer.parseInt(select[2]));
        }

        if (TextUtils.isEmpty(start_time)) {
            startDate.set(1970, 0, 1);
        } else {
            String[] start = start_time.split("-");
            if (start[2].length() > 2) {
                start[2] = start[2].substring(0, 2);
            }
            startDate.set(Integer.parseInt(start[0]), Integer.parseInt(start[1]) - 1, Integer.parseInt(start[2]));
        }

        if (TextUtils.isEmpty(end_time)) {
            endDate.setTime(new Date());
        } else {
            String[] end = end_time.split("-");
            if (end[2].length() > 2) {
                end[2] = end[2].substring(0, 2);
            }
            endDate.set(Integer.parseInt(end[0]), Integer.parseInt(end[1]) - 1, Integer.parseInt(end[2]));
        }

        datePickerView = new TimePickerBuilder(context, (date, v) -> callBack.onItemViewClickCallBack(0, type, date)).setDate(seleteDate).setRangDate(startDate, endDate).setLayoutRes(R.layout.picker_dialog_datetime_layout,
                v -> {
                    TextView tvSubmit = v.findViewById(R.id.dialog_datetime_complete_tv);
                    TextView cancel = v.findViewById(R.id.dialog_datetime_cancle_tv);
                    TextView titleTv = v.findViewById(R.id.picker_title_tv);

                    if (type.equals("birthday")) {
                        titleTv.setText("选择生日");
                    } else if (type.equals("time")) {
                        titleTv.setText("选择时间");
                    } else {
                        titleTv.setText(type);
                    }

                    tvSubmit.setOnClickListener(v1 -> {
                        datePickerView.returnData();
                        datePickerView.dismiss();
                    });

                    cancel.setOnClickListener(v12 -> datePickerView.dismiss());

                }).setDividerColor(context.getResources().getColor(R.color.line_light))//设置分割线的颜色
                .setTextColorCenter(context.getResources().getColor(R.color.text_black))//设置选中项的颜色
                .setBgColor(context.getResources().getColor(R.color.transparent)) // 滚轮背景颜色 Night mode
                .setContentTextSize(15)//滚轮文字大小
                .setType(times).isCenterLabel(false) //是否只显示中间选中项的label
                // 文字，false则每项item全部都带有label。
                .setLineSpacingMultiplier(3.2f).isDialog(true).build(); // 设置两横线之间的间隔倍数
        Dialog mDialog = datePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            params.leftMargin = params.rightMargin = 0;
            datePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //使自定义dialog的高度为包含内容的高度
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay();
            layoutParams.width = d.getWidth();
            dialogWindow.setAttributes(layoutParams);
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        datePickerView.show();
    }


    /**
     * 选择地址
     * */
    public static void showAddressPicker(Context context, List<String> options1Items, List<List<String>>options2Items, List<List<List<String>>>options3Items, final String title, final String cancle, final OnItemViewClickCallBack callBack) {
        mContext = context;
        optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                ChooseAddressData timeData = new ChooseAddressData();
                timeData.province_name=options1Items.get(options1);
                timeData.city_name=options2Items.get(options1).get(option2);
                timeData.area_name=options3Items.get(options1).get(option2).get(options3);

                callBack.onItemViewClickCallBack(0, "chooseaddress", timeData);
            }
        }).setCyclic(false,false,false).setLayoutRes(R.layout.picker_dialog_address_layout, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView titleTv = (TextView) v.findViewById(R.id.picker_age_title_tv);
                final TextView tvSubmit = (TextView) v.findViewById(R.id.picker_age_complete_tv);
                TextView cancel = (TextView) v.findViewById(R.id.picker_age_cancle_tv);
                if (!TextUtil.isNull(title)) {
                    titleTv.setText(title);
                }
                if (!TextUtil.isNull(cancle)) {
                    cancel.setText(cancle);
                }
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsPickerView.returnData();
                        optionsPickerView.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        callBack.onItemViewClickCallBack(0, "age_range", "");
                        optionsPickerView.dismiss();
                    }
                });
            }
        }).setDividerColor(context.getResources().getColor(R.color.line_light))//设置分割线的颜色
                .setTextColorCenter(context.getResources().getColor(R.color.text_black))//设置选中项的颜色
                .setBgColor(context.getResources().getColor(R.color.transparent)) // 滚轮背景颜色 Night mode
                //          .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(true)//点击背的地方不消失
                .setCyclic(true, true, true)
                .setContentTextSize(15)//滚轮文字大小
                .setLineSpacingMultiplier(3.2f).isDialog(true).build(); // 设置两横线之间的间隔倍数
        Dialog mDialog = optionsPickerView.getDialog();
        //        mDialog.setCancelable(false);
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            params.leftMargin = params.rightMargin = 0;
            optionsPickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //使自定义dialog的高度为包含内容的高度
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay();
            layoutParams.width = d.getWidth();
            dialogWindow.setAttributes(layoutParams);
            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setWindowAnimations(com.xuexiang.xui.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

        optionsPickerView.setPicker(options1Items, options2Items, options3Items);
        optionsPickerView.setSelectOptions(3, 0);
        optionsPickerView.show();
    }





}
