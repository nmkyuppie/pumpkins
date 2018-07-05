package com.equiniti.pumpkins;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:pumpkins.properties","classpath:test.properties"})
public class Properties {
	@Value( "${dev-tomcat-URL}" )
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
