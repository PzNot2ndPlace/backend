package ru.tbank.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tbank.backend.dto.NoteDtoWithTriggers;
import ru.tbank.backend.dto.NoteProjection;
import ru.tbank.backend.mapper.NoteMapper;
import ru.tbank.backend.repository.NoteRepository;
import ru.tbank.backend.repository.NoteTriggerRepository;
import ru.tbank.backend.service.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTriggerSchedulerService {

    private final NoteRepository noteRepository;
    private final NoteTriggerRepository noteTriggerRepository;
    private final NotificationService notificationService;
    private final NoteMapper noteMapper;

    @Scheduled(cron = "0 * * * * *")
    public void checkTriggers() {
        List<NoteProjection> timeTriggers = noteRepository.findTimeTriggers();

        List<NoteDtoWithTriggers> triggers = timeTriggers.stream()
                .map(it -> noteMapper.mapGroupToNoteDtoWithTriggers(List.of(it))).toList();

        for (var trigger : triggers) {
            var userId = trigger.getUserId();

            notificationService.sendNotification(
                    userId,
                    "Напоминание",
                    "Просили напомнить вам: " + trigger.getText()
            );

            var noteTriggerEntity = noteTriggerRepository.findByTriggerId(trigger.getTriggers().getFirst().getId());

            noteTriggerEntity.setIsReady(true);
            noteTriggerRepository.save(noteTriggerEntity);
        }
    }
}