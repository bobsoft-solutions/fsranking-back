package me.bobsoft.fsranking.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBConnectionManager {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBConnectionManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



}
