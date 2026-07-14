package com.s_giken.training.webapp.service;

import java.util.List;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchCondition;

public interface IChargeService {

    public List<Charge> findAll();

    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition);

}
