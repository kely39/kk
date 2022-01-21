package com.kk.d.base.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成工具
 *
 * @author kk
 * @date 2019/12/26
 **/
public class CodeGenerator {

    public static final String projectPath = "D:\\workspace\\kk\\server\\base";
    public static final String outPutSuffixPath = "/src/main/java";
    public static final String author = "kk";
    public static final String dbUrl = "jdbc:mysql://localhost:3306/kk?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String userName = "root";
    public static final String password = "123456";
    public static final String parentPackage = "com.kk.d.base";
    public static final String tableName = "sys_area";
    public static final String tablePrefix = "";
    public static final String deleteFieldName = "del_flag";
    public static final boolean isFirst = true; //不是第一次生成需改成false

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(buildGlobalConfig());
        // 数据源配置
        mpg.setDataSource(buildDataSourceConfig());
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackage);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("poPath", "model.po");
                this.setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出

        //自定义xml输出路径
        String xmlTempPath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig(xmlTempPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        if (!isFirst) {
            // 不生成单表
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            templateConfig.setController(null);
            templateConfig.setXml(null);
        }
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setSuperControllerClass("com.kk.d.framework.web.BaseController");
        //strategy.setSuperEntityColumns("id");
        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        strategy.setRestControllerStyle(true);
        if (StrUtil.isNotBlank(deleteFieldName)) {
            strategy.setLogicDeleteFieldName(deleteFieldName);
        }
        strategy.setInclude(tableName);
        strategy.setTablePrefix(tablePrefix);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    private static DataSourceConfig buildDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(dbUrl);
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        return dsc;
    }

    private static GlobalConfig buildGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + outPutSuffixPath);
        gc.setFileOverride(true);
        // XML ResultMap
        gc.setBaseResultMap(false);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setIdType(IdType.ID_WORKER);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        return gc;
    }

}
