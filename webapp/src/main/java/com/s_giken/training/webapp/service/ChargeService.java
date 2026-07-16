package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s_giken.training.webapp.exception.AttributeErrorException;
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

    @Override
    public Optional<Charge> findById(Long chargeid) {
        return chargeRepository.findById(chargeid);
    }

    @Override
    public void add(Charge charge) {
        if (charge.getChargeid() != null) {
            throw new AttributeErrorException("料金idが指定されていると登録できません。");
        }
        chargeRepository.add(charge);
    }

    @Override
    public void update(Charge charge) {
        if (charge.getChargeid() == null) {
            throw new AttributeErrorException("料金idが指定されていません。");
        }
        chargeRepository.update(charge);
    }

    @Override
    @Transactional
    public void deleteById(Long chargeid) {
        chargeRepository.deleteById(chargeid);
    }
}
