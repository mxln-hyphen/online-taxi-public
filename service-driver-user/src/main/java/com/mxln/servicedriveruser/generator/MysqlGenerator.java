package com.mxln.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {

    public static void main(String[] args) {
        MysqlGenerator mysqlGenerator = new MysqlGenerator();
        mysqlGenerator.generatorDriverCarBindingRelationship();
    }

    private void generatorCar(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder -> {
                    builder.author("mxln").fileOverride().outputDir("D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln\\servicedriveruser");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mxln.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("car");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private void generatorDriverCarBindingRelationship(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root","root")
                .globalConfig(builder -> {
                    builder.author("mxln").fileOverride().outputDir("D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mxln.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\develop\\workspace\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mxln\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_car_binding_relationship");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
