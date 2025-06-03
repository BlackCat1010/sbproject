package com.sbproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbctemplate;

    public User getByUserName(String userName){
        String sql = "SELECT TOP 1 * FROM userAccounts WHERE user_name = ? ";
        try{
            // Replacement for new RowMapper
            return jdbctemplate.queryForObject(sql, (resultSet,_)->{
                User usr = new User();
                usr.setpassWord(resultSet.getString("pass_word"));
                usr.setuserName(resultSet.getString("user_name"));
                return usr;
            },
            userName);

        }catch (EmptyResultDataAccessException e){
            return null; // No user
        }
    }
    
}
