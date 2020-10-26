package com.example.accountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountbook.bean.FilterBean;
import com.example.accountbook.widget.TypeLabelGridLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TypeLabelActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TypeLabelGridLayout lglLabel;
    private TextView tvReset;
    private TextView tvConfirm;
    private List<FilterBean> typeLabelLists;
//    private boolean isMul;
    private Intent intent;
    private List<FilterBean.TableMode> tabList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_label);
        intent = getIntent();
        tabList = (List<FilterBean.TableMode>) intent.getSerializableExtra("tabs");
        //isMul=getIntent().getBooleanExtra("isMul",false);
        initView();
        initData();
        initAction();
    }
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lglLabel = (TypeLabelGridLayout) findViewById(R.id.lgl_label);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData() {
        //流式布局数据
        typeLabelLists = new ArrayList<>();
        List<FilterBean.TableMode> list0 = new ArrayList<>();
        list0.add(new FilterBean.TableMode("全部类别"));
        list0.add(new FilterBean.TableMode("一级分类"));
        list0.add(new FilterBean.TableMode("二级分类"));
        list0.add(new FilterBean.TableMode("全部收入"));
        list0.add(new FilterBean.TableMode("全部支出"));
        list0.add(new FilterBean.TableMode("其他全部"));
        typeLabelLists.add(new FilterBean("基础", new FilterBean.TableMode("全部类别"), list0));
        List<FilterBean.TableMode> list = new ArrayList<>();
        list.add(new FilterBean.TableMode("工资收入"));
        list.add(new FilterBean.TableMode("利息收入"));
        list.add(new FilterBean.TableMode("加班收入"));
        list.add(new FilterBean.TableMode("奖金收入"));
        list.add(new FilterBean.TableMode("投资收入"));
        list.add(new FilterBean.TableMode("兼职收入"));
        list.add(new FilterBean.TableMode("礼金收入"));
        list.add(new FilterBean.TableMode("中奖收入"));
        list.add(new FilterBean.TableMode("意外来钱"));
        list.add(new FilterBean.TableMode("经营所得"));
        typeLabelLists.add(new FilterBean("收入", new FilterBean.TableMode("全部类别"), list));
        List<FilterBean.TableMode> list1 = new ArrayList<>();
        list1.add(new FilterBean.TableMode("衣服饰品"));
        list1.add(new FilterBean.TableMode("食品酒水"));
        list1.add(new FilterBean.TableMode("学习进修"));
        list1.add(new FilterBean.TableMode("居家物业"));
        list1.add(new FilterBean.TableMode("行车交通"));
        list1.add(new FilterBean.TableMode("交流通讯"));
        list1.add(new FilterBean.TableMode("休闲娱乐"));
        list1.add(new FilterBean.TableMode("人情往来"));
        list1.add(new FilterBean.TableMode("医疗保健"));
        list1.add(new FilterBean.TableMode("其他杂项"));
        list1.add(new FilterBean.TableMode("金融保险"));
        typeLabelLists.add(new FilterBean("支出", new FilterBean.TableMode("全部类别"), list1));
        List<FilterBean.TableMode> list2 = new ArrayList<>();
        list2.add(new FilterBean.TableMode("转账"));
        list2.add(new FilterBean.TableMode("借贷"));
        typeLabelLists.add(new FilterBean("其他", new FilterBean.TableMode("全部类别"), list2));

        AddinModel();
        lglLabel.setMulEnable(true);
        lglLabel.setColumnCount(3);
        lglLabel.setLabelBg(R.drawable.flow_popup);
        lglLabel.setGridData(typeLabelLists);
    }
    private void AddinModel() {
        for (FilterBean model : typeLabelLists) {
            for (FilterBean.TableMode tab : model.getTabs()) {
                if (inTabList(tab)) {
                    model.addLabel(tab);
                }
            }
        }
    }
    private boolean inTabList(FilterBean.TableMode tab0) {
        for (FilterBean.TableMode tab : tabList) {
            if (tab.getName().equals(tab0.getName())) {
                return true;
            }
        }
        return false;
    }
    private void initAction() {
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        lglLabel.resetData();
                        initData();
                    }
                });
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("rcw","label="+lglLabel.getLabelData());
                intent.putExtra("tabs", (Serializable) lglLabel.getTabList());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
