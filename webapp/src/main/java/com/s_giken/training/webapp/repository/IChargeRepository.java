package com.s_giken.training.webapp.repository;

import java.util.List;

import com.s_giken.training.webapp.model.entity.Charge;

public interface IChargeRepository {

    List<Charge> findAll();

    List<Charge> selectByNameWildcard(String charge);

}
