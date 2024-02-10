package com.example.scenergynotification.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.scenergynotification.domain.notification.controller.request.OnFollowEvent;
import com.example.scenergynotification.domain.notification.controller.request.OnLikeEvent;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, OnFollowEvent> followEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OnFollowEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(OnFollowEvent.class));
		return factory;
	}

	@Bean
	public <V> ConsumerFactory<String, V> consumerFactory(
		Class<V> valueType
	) {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(),
			new ErrorHandlingDeserializer<>(new StringDeserializer()),
			new JsonDeserializer<V>(valueType, false));
	}

	@Bean
	public ConsumerFactory<String, OnFollowEvent> followEventConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(),
			new JsonDeserializer<>(OnFollowEvent.class, false));
	}

	@Bean
	public ConsumerFactory<String, OnLikeEvent> likeEventConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(),
			new JsonDeserializer<>(OnLikeEvent.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, OnLikeEvent> likeEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OnLikeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(likeEventConsumerFactory());
		return factory;
	}

	@Bean
	public Map<String, Object> consumerConfigurations() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

		config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
		config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

		return config;
	}
}