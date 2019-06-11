package com.yaocj;

import com.yaocj.config.AppWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 应用启动入口
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class AppStart {
	public static void main(String[] args) {
		SpringApplication.run(AppWebConfig.class, args);
	}
}
