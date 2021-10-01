package ovh.excale.discord4jspring;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ovh.excale.discord4jspring.listeners.SlashCommandListener;

@SpringBootApplication
public class DiscordBotApplication implements CommandLineRunner {

	public static void main(String[] args) {

		// Start Spring Application
		new SpringApplicationBuilder(DiscordBotApplication.class)
				.bannerMode(Mode.OFF)
				.build()
				.run(args);

	}

	private final GatewayDiscordClient gatewayClient;
	private final SlashCommandListener slashCommandListener;

	// All params are autowired with Spring
	public DiscordBotApplication(GatewayDiscordClient gatewayClient, SlashCommandListener slashCommandListener) {
		this.gatewayClient = gatewayClient;
		this.slashCommandListener = slashCommandListener;
	}

	@Override
	public void run(String... args) {

		gatewayClient
				.on(ChatInputInteractionEvent.class, slashCommandListener::handle)
				.subscribe();

		gatewayClient
				.onDisconnect()
				.block();

	}

}
