package com.fourkmiles.dbdoc;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
                .fileOutputDir("C:\\Users\\DELL\\Desktop")
                // 打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(EngineFileType.HTML)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();

        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version("1.0.0")
                .description("深圳CRM开发数据库文档")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();

        // 执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig(){
        // 忽略表名
        List<String> ignoreTableName = Arrays.asList("aa","test_group","group_capacity","his_config_info","roles","t_indicator_apply_train",
                "t_indicator_deactivation_user","t_indicator_dr_pass_rate","t_indicator_dsp_account","t_indicator_dsp_application_advertiser_info",
                "t_indicator_dsp_application_log","t_indicator_dsp_brand_asin_apply","t_indicator_dsp_asin_apply","t_indicator_dsp_country",
                "t_indicator_dsp_distribute","t_indicator_dsp_fmdp_review_log","t_indicator_dsp_import_log","t_indicator_dsp_lineitem_category",
                "t_indicator_dsp_preferential","t_indicator_dsp_report_advertiser_log","t_indicator_dsp_report_audience",
                "t_indicator_dsp_report_campaign_asin","t_indicator_dsp_report_campaign_brand","t_indicator_dsp_report_campaign_creatives","t_indicator_dsp_report_campaign_lineitems",
                "t_indicator_dsp_report_campaign_orders","t_indicator_dsp_report_campaign_product","t_indicator_dsp_report_custom_beta","t_indicator_dsp_report_custom_copy",
                "t_indicator_dsp_report_inventory","t_indicator_dsp_report_log","t_indicator_dsp_terms","t_indicator_function_use","t_indicator_sales_statistic","t-config",
                "users","t_auto_advertisement_statistic","t_del_user","t_indicator_am_performance","t_indicator_crm_contract_fmdp_dsp_notify_copy1",
                "t_indicator_crm_customer_query_log","t_indicator_dictionary_calendar","t_indicator_dsp_budget","t_indicator_system_config_business_invoices",
                "t_indicator_system_config_login_mode","t_indicator_crm_contract_modify_log","t_indicator_dsp_distribute_apply_email_log","t_indicator_gpm_feedback",
                "t_indicator_gpm_user","t_indicator_system_menu","t_indicator_dsp_user","t_indicator_dsp_user_area","t_indicator_dsp_user_beta","t_indicator_dsp_user_bind","t_indicator_dsp_user_competitorexp",
                "t_indicator_dsp_user_log","t_indicator_dsp_user_sub","t_indicator_dsp_user_sub_advertiser","t_indicator_official_account","t_indicator_follow_status","t_indicator_replenishment_full"
                ,"t_indicator_replenishment_user_detail"

        );
        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = Arrays.asList("t_indicator_del","t_indicator_dsp_finances","t_indicator_assess","config_info","t_indicator_ad_",
                "t_indicator_dsp_invoice_","t_indicator_asin_","t_indicator_apply_train","t_indicator_am_performance_","t_indicator_assess_","t_indicator_brand_",
                "t_indicator_demo_","t_indicator_dialog_","t_indicator_distribut","t_indicator_dsp_apply_","t_indicator_dsp_clinic","t_indicator_dsp_compare",
                "t_indicator_dsp_competitor","t_indicator_dsp_contact","t_indicator_dsp_i","t_indicator_dsp_modify_advertiser",
                "t_indicator_dsp_order","t_indicator_remind","t_indicator_review","t_indicator_dsp_payment","t_indicator_dsp_receive","t_indicator_dsp_s","t_indicator_i","t_indicator_k",
                "t_indicator_m","t_indicator_p","t_indicator_se","t_indicator_school","t_indicator_si","t_indicator_tag_","t_indicator_temp","t_indicator_t",
                "t_indicator_u","t_indicator_v","t_indicator_w","t_s","t_v","tagsystem","tenant","xxl_job","con","t_indicator_account","t_indicator_activity","t_indicator_advertise_ppc",
                "t_indicator_dsp_achievement_rate","t_indicator_dsp_user_follow_up_record");
        // 忽略表后缀
        List<String> ignoreSuffix = Arrays.asList("_test","czb_","_bak","_copy1","_version");

        return ProcessConfig.builder()
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }
}
