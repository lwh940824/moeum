package com.moeum.moeum.api.ledger.investSetting.controller;

import com.moeum.moeum.api.ledger.investSetting.service.InvestSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "투자 집계 설정")
@RestController
@RequestMapping("/api/invest-setting")
@RequiredArgsConstructor
public class InvestSettingController {

    private final InvestSettingService investSettingService;

    //
}
