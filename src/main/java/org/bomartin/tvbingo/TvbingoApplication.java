package org.bomartin.tvbingo;

import org.bomartin.tvbingo.service.ShowService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TvbingoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvbingoApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider showTools(ShowService showService) {
		return MethodToolCallbackProvider.builder().toolObjects(showService).build();
	}
}
