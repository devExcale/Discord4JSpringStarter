package ovh.excale.discord4jspring.listeners;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ovh.excale.discord4jspring.commands.SlashCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SlashCommandListener implements ApplicationContextAware {

	private ApplicationContext context;

	public Mono<Void> handle(ChatInputInteractionEvent event) {
		//Convert our list to a flux that we can iterate through
		return Flux.fromIterable(context
						.getBeansOfType(SlashCommand.class)
						.values())
				//Filter out all commands that don't match the name this event is for
				.filter(command -> command
						.getName()
						.equals(event.getCommandName()))
				//Get the first (and only) item in the flux that matches our filter
				.next()
				//Have our command class handle all logic related to its specific command.
				.flatMap(command -> command.handle(event));
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
