package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.repository.mapper.ChargeRowMapper;

@Repository
public class ChargeRepository implements IChargeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Charge> rowMapper;

    public ChargeRepository(JdbcTemplate jdbcTemplate, ChargeRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Charge> findAll() {
        String sql = "SELECT * FROM T_CHARGE";
        List<Charge> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    @Override
    public List<Charge> selectByNameWildcard(String charge) {
        String sql = "SELECT * FROM T_CHARGE WHERE name LIKE ?";
        Object[] args = { "%" + charge + "%" };
        int[] argsTypes = { Types.VARCHAR };
        return jdbcTemplate.query(sql, args, argsTypes, rowMapper);

    }

}
