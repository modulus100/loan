package craft.beer.loan.handlers.async;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

public class AsyncPing implements Command<CompletableFuture<String>> {

    @Component
    static class Handler implements Command.Handler<AsyncPing, CompletableFuture<String>> {

        @Override
        public CompletableFuture<String> handle(AsyncPing command) {
            return CompletableFuture.completedFuture("works async");
        }
    }
}
