/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.pojo.Choice;
import com.dht.pojo.JdbcUtils;
import com.dht.pojo.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class QuestionServices {
    public List<Question> getQuestions(int limit, String kw) throws SQLException {
        List<Question> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            
            String sql;
            PreparedStatement stm;
            if (limit == 0) {
                sql = "SELECT * FROM question WHERE content like concat('%', ?, '%')";
                if (kw == null)
                    kw = "";
                stm = conn.prepareCall(sql);
                stm.setString(1, kw);
            } else {
                sql = "SELECT * FROM question ORDER BY rand() LIMIT ?";
                stm = conn.prepareCall(sql);
                stm.setInt(1, limit);
            }
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question(rs.getString("id"), 
                        rs.getString("content"), rs.getInt("category_id"));
                
                results.add(q);
            }
            
            return results;
        }
    }
    
    public List<Choice> getChoices(String questionId) throws SQLException {
        List<Choice> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM choice WHERE question_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, questionId);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Choice c = new Choice(rs.getString("id"), rs.getString("content"), 
                        rs.getBoolean("is_correct"), rs.getString("question_id"));
                
                results.add(c);
            }
            
            return results;
        }
    }
    
    public void addQuestion(Question q, List<Choice> choices) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO question(id, content, category_id) VALUES(?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, q.getId());
            stm.setString(2, q.getContent());
            stm.setInt(3, q.getCategoryId());
            
            int r = stm.executeUpdate();
            
            if (r > 0) {
                sql = "INSERT INTO choice(id, content, is_correct, question_id) VALUES(?, ?, ?, ?)";
                stm = conn.prepareCall(sql);
                for (var c: choices) {
                    stm.setString(1, c.getId());
                    stm.setString(2, c.getContent());
                    stm.setBoolean(3, c.isCorrect());
                    stm.setString(4, q.getId());
                    
                    stm.executeUpdate();
                }
            }
            conn.commit();
        }
    }
    
    public void deleteQuestion(String questionId) throws SQLException {
         try (Connection conn = JdbcUtils.getConn()) {
             PreparedStatement stm = conn.prepareCall("DELETE FROM question WHERE id=?");
             stm.setString(1, questionId);
             stm.executeUpdate();
         }
    }
    
}
