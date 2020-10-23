package com.example.accountbook;

import com.example.accountbook.helper.PickerDataHelper;

public class FirstLaunch {
//    private PickerDataHelper pickerDataHelper = new PickerDataHelper();

    private final String[] pay_category1 = {
            "衣服饰品","食品酒水","学习进修",
            "居家物业","行车交通","交流通讯","休闲娱乐",
            "人情往来","医疗保健","其他杂项","金融保险"
    };

    private final String[][] pay_category2 = {
            {"衣服裤子","鞋帽包包","化妆饰品"},
            {"早午晚餐","烟酒茶","水果零食"},
            {"书报杂志","培训进修","数码装备"},
            {"日常用品","水电煤气","房租","物业管理","维修保养"},
            {"公共交通","打车租车","私家车费用"},
            {"座机费","手机费","上网费","邮寄费"},
            {"运动健身","腐败聚会","休闲玩乐","宠物宝贝","旅游度假"},
            {"送礼请客","孝敬家长","还人钱物","慈善捐助"},
            {"药物费","保健费","美容费","治疗费"},
            {"其他支出","意外支出","意外丢失","烂账损失"},
            {"银行手续","投资亏损","按揭还款","消费税收","利息支出","赔偿罚款"}
    };

    private final String[] universe_store_1 = {
            "所有"
    };

    private final String[][] universe_store_2 = {
            {"无商家/地点","其他","饭堂","银行","商场","超市","公交"}
    };

    private final String[] income_category1 = {
            "职业收入",
            "其他收入"
    };
    private final String[][] income_category2 = {
            {"工资收入","利息收入","加班收入","奖金收入","投资收入","兼职收入"},
            {"礼金收入","中奖收入","意外来钱","经营所得"}
    };
    private final String[] universe_project_1 = {
            "所有"
    };
    private final String[][] universe_project_2 = {
            {"无项目","红包","过年买票","回家过年","出差","公司报销","装修","旅游","腐败"}
    };
    private final String[] universe_people_1 = {
            "所有"
    };
    private final String[][] universe_people_2 = {
            {"本人","老公","老婆","子女","父母","家庭公用"}
    };
    private final String[] universe_account_1 = {
            "现金账户","信用卡","金融账户","虚拟账户",
            "负债账户","债券账户","投资账户"
    };
    private final String[][] universe_account_2 = {
            {"现金(CNY)"},
            {"信用卡(CNY)"},
            {"银行卡(CNY)"},
            {"饭卡(CNY)","支付宝(CNY)","公交卡(CNY)"},
            {"应付款项(CNY)"},
            {"应收款项(CNY)","公司报销(CNY)"},
            {"基金账户(CNY)","余额宝(CNY)","股票账户(CNY)"}
    };
    private final String[] lender_1 = {
            "所有"
    };
    private final String[][] lender_2 = {
            {"公司报销","银行"}
    };
    private final String[][] string_list_1 = {
            pay_category1,
            income_category1,
            universe_people_1,
            universe_project_1,
            universe_store_1,
            universe_account_1,
            lender_1
    };
    private final String[][][] strings_list_2 = {
            pay_category2,
            income_category2,
            universe_people_2,
            universe_project_2,
            universe_store_2,
            universe_account_2,
            lender_2
    };
    private final String[] string_name = {
            "pay_category","income_category",
            "people","project","store","account",
            "lender"
    };

    public void initPickerData(){
        for(int i=0;i<string_list_1.length;++i){
            for(int j=0;j<string_list_1[i].length;++j)
            {
                for(int k=0;k<strings_list_2[i][j].length;++k){
                    //System.out.println(string_name[i]+string_list_1[i][j]+strings_list_2[i][j][k]);
                    new PickerDataHelper().addCategory( string_name[i],string_list_1[i][j],strings_list_2[i][j][k]);
                }
            }
        }
    }
}
