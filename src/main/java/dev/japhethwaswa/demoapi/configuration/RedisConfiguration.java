package dev.japhethwaswa.demoapi.configuration;

import dev.japhethwaswa.demoapi.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {

    //start generic
    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(Application.dotenv.get("REDIS_HOST"));
        configuration.setPassword(Application.dotenv.get("REDIS_PASSWORD"));
        configuration.setPort(Application.dotenv.get("REDIS_PORT") != null ? Integer.parseInt(Application.dotenv.get("REDIS_PORT")) : 6379);
        return new LettuceConnectionFactory(configuration);
    }


    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("UserCreated-Topic");
    }

    //end generic


    //start listener
    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisMessageSubscriber(), "onMessage");
    }

    @Bean
    RedisMessageListenerContainer container() {
        final RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(lettuceConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter(), topic());
        return redisMessageListenerContainer;
    }

    //end listener


    //start publisher
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }

    @Bean
    IMessagePublisher messagePublisher() {
        return new RedisMessagePublisher(redisTemplate(lettuceConnectionFactory()), topic());
    }

    //end publisher

}
