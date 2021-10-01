package ovh.excale.discord4jspring;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordEndpointConfiguration {

	@Bean
	public GatewayDiscordClient gatewayClientBean(@Value("${env.BOT_TOKEN}") String token) {

		return DiscordClientBuilder
				.create(token)
				.build()
				.login()
				.block();

	}

	@Bean
	public RestClient restClientBean(@Value("${env.BOT_TOKEN}") String token) {

		return RestClient.create(token);

	}

}
