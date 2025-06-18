package ru.tbank.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.tbank.backend.dto.HintResponse;
import ru.tbank.backend.mapper.ContextMapper;
import ru.tbank.backend.mapper.NoteMapper;
import ru.tbank.backend.repository.NoteRepository;
import ru.tbank.backend.repository.NoteTriggerRepository;
import ru.tbank.backend.repository.UserRepository;
import ru.tbank.backend.service.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class HintNoteSchedulerService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteTriggerRepository noteTriggerRepository;
    private final NotificationService notificationService;
    private final NoteMapper noteMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${hints.url}")
    private String url;

    public void testHints() {
        var users = userRepository.findAll();

        for (var user : users) {
            var notes = noteRepository.findByUserId(user.getId());

            if (notes.isEmpty()) {
                continue;
            }

            var context = ContextMapper.mapToContextList(notes);

            log.info("контекст:" + context.toString());

            try {
                ResponseEntity<HintResponse> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(context),
                        HintResponse.class
                );

                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                    log.error("Request failed for user {} with status: {}",
                            user.getId(), responseEntity.getStatusCode());
                    continue;
                }

                HintResponse response = responseEntity.getBody();
                if (response != null && response.getNote() != null && response.getHintText() != null) {
                    notificationService.sendNotification(
                            user.getId(),
                            response.getNote().getText(),
                            response.getHintText()
                    );
                } else {
                    log.warn("Empty or invalid response body for user {}", user.getId());
                }

            } catch (RestClientException e) {
                log.error("Error while getting hint for user " + user.getId(), e);
                if (e instanceof HttpClientErrorException) {
                    HttpClientErrorException httpEx = (HttpClientErrorException) e;
                    log.error("HTTP error: {}, Response body: {}",
                            httpEx.getStatusCode(), httpEx.getResponseBodyAsString());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 */3 * * *")
    public void checkTriggers() {
        testHints();
    }
}