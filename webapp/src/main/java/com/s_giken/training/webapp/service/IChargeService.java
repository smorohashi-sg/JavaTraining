package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchCondition;

public interface IChargeService {

    public List<Charge> findAll();

    public Optional<Charge> findById(Long chargeid);

    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition);

    public void add(Charge charge);

    public void update(Charge charge);

    public void deleteById(Long chargeid);
}
