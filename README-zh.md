# Spring Cloud Destino

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://img.shields.io/maven-central/v/org.eglessness.cloud/spring-cloud-destino-dependencies.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:org.egolessness.cloud%20AND%20a:spring-cloud-destino-dependencies)

[English](./README.md) | 简体中文

README:

- [介绍](#介绍)
- [使用示例](#使用示例)
- [配置示例](#配置示例)
- [任务脚本额外依赖](#任务脚本额外依赖)

## 介绍

Spring Cloud Destino 是基于 Spring Cloud SPI 实现的服务发现及任务调度的 Quick Starter

## 使用示例

Spring Cloud Destino 已上传到 Maven Central，引入依赖即可。

> 已支持Spring Cloud 版本：2021.0

```` xml  
    <!-- spring-cloud-destino bom  -->
    <dependencyManagement>  
        <dependency>
            <groupId>org.egolessness.cloud</groupId>
            <artifactId>spring-cloud-destino-dependencies</artifactId>
            <version>2021.0.8.0-RC</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencyManagement>  
                 
    
    <dependencies>
        <!-- spring-cloud-starter-destino-discovery dependency  -->
        <dependency>
            <groupId>org.egolessness.cloud</groupId>
            <artifactId>spring-cloud-starter-destino-discovery</artifactId>
        </dependency>
        <!-- spring-cloud-starter-destino-scheduling dependency  -->
         <dependency>
            <groupId>org.egolessness.cloud</groupId>
            <artifactId>spring-cloud-starter-destino-scheduling</artifactId>
        </dependency>
    </dependencies>
````

## 配置示例
```` yml
spring:
  cloud:
    destino:
      discovery:
        address: 192.168.10.10:8200, 192.168.10.12:8300/base-ctx
        namespace: qiwulun
        group: dechongfu
        cluster-name: yingdiwang
        username: admin
        password: admin
      scheduling:
        enabled: true
        execute-thread-count: 4
````

## 任务脚本额外依赖

Spring Cloud Destino 本着简约理念，未集成任务脚本所需依赖，如需要可自行引入(符合JSR-223规范)

**执行Groovy脚本额外引入**
````
    <dependencies>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy</artifactId>
            <version>3.0.19</version>
        </dependency>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-jsr223</artifactId>
            <version>3.0.19</version>
        </dependency>
    </dependencies>

````

**执行Python脚本额外引入**
````
    <dependencies>
        <dependency>
            <groupId>org.python</groupId>
            <artifactId>jython-standalone</artifactId>
            <version>2.7.3</version>
        </dependency>
    </dependencies>
````