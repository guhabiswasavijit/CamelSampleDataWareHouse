package self.camel.demo;

import java.util.concurrent.BlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SedaQueueConfig {

	@Bean("sedaQueue")
	public BlockingQueue addQueue() {
		java.util.concurrent.ArrayBlockingQueue<String> queue = new java.util.concurrent.ArrayBlockingQueue<String>(1);
		return queue;
	}
}
