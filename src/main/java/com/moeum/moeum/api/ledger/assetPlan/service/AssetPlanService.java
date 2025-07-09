package com.moeum.moeum.api.ledger.assetPlan.service;

import com.moeum.moeum.api.ledger.assetPlan.repository.AssetPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetPlanService {

    private final AssetPlanRepository assetPlanRepository;
}