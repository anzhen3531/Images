import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取项目当前路径
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("anzhen");
        //是否打开资源管理器
        gc.setOpen(false);
        //是否覆盖
        gc.setFileOverride(false);
        //去掉service的I前缀
        gc.setServiceImplName("%sService");
        //设置实体类策略
        //id
//        gc.setIdType(IdType.AUTO);
        //日期
//        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://:3306/imageprodect?useUnicode=true&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("anzhen3531");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //设置模块名
//        pc.setModuleName(scanner("模块名"));
        //设置包名
        pc.setParent("com.anzhen");
        //设置实体类包名 可不设 默认entity
        pc.setEntity("entity");
        //设置mapper包名 可不设 默认 mapper
//        pc.setMapper("mapper");
        //设置service 包名 可不设默认 service
//        pc.setService("service");
        //设置controller 包名 可不设默认 controller
//        pc.setController("controller");

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                //如果设置了模块名请用第一种 未设置用第二种默认就好了
//                return projectPath + "/src/main/resources/com/dameng/community/mapper" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return projectPath + "/src/main/resources/mapper"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //去除表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //去除下划线
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        //是否使用lombok开启注解
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        //设置要映射的表名
        String[] tableNames = {"a_image"};
        strategy.setInclude(tableNames);
        //开启url下划线
        strategy.setControllerMappingHyphenStyle(true);
        //驼峰命名
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        //设置逻辑删除字段
//        strategy.setLogicDeleteFieldName("deleted");
        //自动填充配置
/*        TableField gmtCreate = new TableField("gmt_create", FieldFill.INSERT);
        TableField gmtModified = new TableField("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);*/

        //乐观锁
//        strategy.setVersionFieldName("version");


        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}
