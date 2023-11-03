package com.mxln.serviceorder.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {

    public static void main(String[] args) {
        MysqlGenerator mysqlGenerator = new MysqlGenerator();
        mysqlGenerator.generatorOrder();
    }

    private void generatorOrder(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-order?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root","root")
                .globalConfig(builder -> {
                    builder.author("mxln").fileOverride().outputDir("D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln\\serviceorder");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mxln.serviceorder").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("order_info");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
