# Spring Cloud Destino

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://img.shields.io/maven-central/v/org.eglessness.cloud/spring-cloud-destino-dependencies.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:org.egolessness.cloud%20AND%20a:spring-cloud-destino-dependencies)

English | [简体中文](./README-zh.md)

README:

- [Introduction](#introduction)
- [How to Use](#how-to-use)
- [Config Examples](#config-examples)
- [Additional Dependencies For Script](#additional-dependencies-for-script)

## Introduction

Spring Cloud Destino is a microservice quick starter which implements Spring Cloud SPI.

## How to Use

Spring Cloud Destino have been released to the Maven Central repository, just need to import
dependencies.

> Supported Version: Spring Cloud 2021.0

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

## Config Examples
```` yml
spring:
  cloud:
    destino:
      discovery:
        servers: 192.168.10.10:8200, 192.168.10.12:8300/base-ctx
        namespace: qiwulun
        group: dechongfu
        cluster-name: yingdiwang
        username: admin
        password: admin
      scheduling:
        enabled: true
        execute-thread-count: 4
````

## Additional Dependencies For Script

Spring Cloud Destino is not import dependencies for script job. If necessary, can be imported independently (in accordance with JSR-223 specifications).

**Additional Import for Groovy Script**
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

**Additional Import for Python Script**
````
    <dependencies>
        <dependency>
            <groupId>org.python</groupId>
            <artifactId>jython-standalone</artifactId>
            <version>2.7.3</version>
        </dependency>
    </dependencies>
````
