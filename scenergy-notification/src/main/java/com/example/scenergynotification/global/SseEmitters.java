package com.example.scenergynotification.global;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SseEmitters {

	private static final long TIMEOUT = 60 * 1000;
	private static final long RECONNECTION_TIMEOUT = 1000L;
	private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	public SseEmitter subscribe(Long id) {
		SseEmitter emitter = createEmitter();
		emitter.onTimeout(emitter::complete);

		emitter.onError(e -> {
			emitter.complete();
		});

		emitterMap.put(id, emitter);

		emit(id, "SSE connected", "connect");
		return emitter;
	}

	//emit
	public void emit(Long id, Object eventPayload, String eventType) {
		SseEmitter emitter = emitterMap.get(id);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name(eventType)
					.id(String.valueOf("id-1"))
					.data(eventPayload, MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				log.error("failure send media position data, id={}, {}", id, e.getMessage());
			}
		}
	}

	// public void broadcast(EventPayload eventPayload) {
	//     emitterMap.forEach((id, emitter) -> {
	//         try {
	//             emitter.send(SseEmitter.event()
	//                 .name("broadcast event")
	//                 .id("broadcast event 1")
	//                 .reconnectTime(RECONNECTION_TIMEOUT)
	//                 .data(eventPayload, MediaType.APPLICATION_JSON));
	//             log.info("sended notification, id={}, payload={}", id, eventPayload);
	//         } catch (IOException e) {
	//             //SSE 세션이 이미 해제된 경우
	//             log.error("fail to send emitter id={}, {}", id, e.getMessage());
	//         }
	//     });
	// }

	private SseEmitter createEmitter() {
		return new SseEmitter(TIMEOUT);
	}
}