package craft.beer.loan.core.configs;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipelinr;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelinrConfiguration {

    @Bean
    Pipelinr pipeline(
            ObjectProvider<Command.Handler> commandHandlers,
            ObjectProvider<Notification.Handler> notificationHandlers) {
        return new Pipelinr()
                .with(commandHandlers::stream)
                .with(notificationHandlers::stream);
    }
}
