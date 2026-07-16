package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "SELECT * FROM T_CHARGE ORDER BY charge_id";
        List<Charge> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    @Override
    public List<Charge> selectByNameWildcard(String charge) {
        String sql = "SELECT * FROM T_CHARGE WHERE name LIKE ? ORDER BY charge_id";
        Object[] args = { "%" + charge + "%" };
        int[] argsTypes = { Types.VARCHAR };
        return jdbcTemplate.query(sql, args, argsTypes, rowMapper);

    }

    @Override
    public Optional<Charge> findById(Long chargeid) {
        String sql = "SELECT * FROM T_CHARGE WHERE charge_id = ?";
        Object[] args = { chargeid };
        int[] argsTypes = { Types.BIGINT };

        Charge charge;
        try {
            charge = jdbcTemplate.queryForObject(sql, args, argsTypes, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            charge = null;
        }

        return Optional.ofNullable(charge);
    }

    @Override
    public int add(Charge charge) {
        Long chargeid = charge.getChargeid();
        if (chargeid == null) {
            chargeid = jdbcTemplate.queryForObject("SELECT nextval('t_charge_seq')", Long.class);
            charge.setChargeid(chargeid);
        }

        String sql = """
            INSERT INTO T_CHARGE (
            charge_id,
            name,
            amount,
            start_date,
            end_date,
            created_at,
            modified_at)
            VALUES(
            ?,
            ?,
            ?,
            ?,
            ?,
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
            )
            """;
        int processed_count = jdbcTemplate
                .update(sql, chargeid, charge.getName(), charge.getAmount(), charge.getStartDate(),
                        charge.getEndDate());

        return processed_count;
    }

    @Override
    public int update(Charge charge) {
        String sql = """
            UPDATE T_CHARGE
            SET name = ?,
                amount = ?,
                start_date = ?,
                end_date = ?,
                modified_at = CURRENT_TIMESTAMP
            WHERE charge_id = ?
            """;
        int processed_count = jdbcTemplate
                .update(sql, charge.getName(), charge.getAmount(), charge.getStartDate(), charge.getEndDate(),
                        charge.getChargeid());

        return processed_count;
    }

    @Override
    public int deleteById(Long chargeid) {
        String sql = "DELETE FROM T_CHARGE WHERE charge_id = ? ";

        int processed_count = jdbcTemplate.update(sql, chargeid);

        return processed_count;
    }

}
