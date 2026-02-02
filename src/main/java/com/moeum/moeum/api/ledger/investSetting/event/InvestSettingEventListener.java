package com.moeum.moeum.api.ledger.investSetting.event;

import com.moeum.moeum.api.ledger.category.event.CategoryCreatedEvent;
import com.moeum.moeum.api.ledger.investSetting.dto.InvestSettingCreateDto;
import com.moeum.moeum.api.ledger.investSetting.service.InvestSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvestSettingEventListener {

    private final InvestSettingService investSettingService;

    @EventListener
    public void handleCategoryCreated(CategoryCreatedEvent categoryCreatedEvent) {
        investSettingService.create(categoryCreatedEvent.userId(),
                InvestSettingCreateDto.builder()
                        .categoryId(categoryCreatedEvent.categoryId())
                        .build()
        );
    }
}
