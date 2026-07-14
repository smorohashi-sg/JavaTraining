package com.s_giken.training.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchCondition;
import com.s_giken.training.webapp.repository.IChargeRepository;

@Service
public class ChargeService implements IChargeService {

    private final IChargeRepository chargeRepository;

    public ChargeService(IChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @Override
    public List<Charge> findAll() {
        return chargeRepository.findAll();
    }

    @Override
    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition) {

        String charge = chargeSearchCondition.getCharge();

        return chargeRepository.selectByNameWildcard(charge);
    }
}
