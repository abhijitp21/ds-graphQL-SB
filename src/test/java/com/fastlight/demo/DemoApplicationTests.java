package com.fastlight.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private DemoApplication demoApplication; // Replace with actual beans for testing

	void applicationStartsCorrectly() {
		// Add a test to verify if the main app starts up without issues
		assertThat(demoApplication).isNotNull();
	}
}