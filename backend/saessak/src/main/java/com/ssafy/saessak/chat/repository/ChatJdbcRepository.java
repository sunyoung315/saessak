package com.ssafy.saessak.chat.repository;

import com.ssafy.saessak.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    //  MySql에 Redis Cache의 채팅 내역 저장
    public void batchInsertChats(List<Chat> chatList){

        String sql = "INSERT INTO chat"
                +  "(chat_content, chat_time, receiver_id, sender_id, room_id) VALUE(?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Chat chat = chatList.get(i);
                ps.setString(1, chat.getChatContent());
                ps.setString(2, chat.getChatTime());
                ps.setLong(3, chat.getReceiverId());
                ps.setLong(4, chat.getSenderId());
                ps.setLong(5, chat.getRoom().getRoomId());
            }

            @Override
            public int getBatchSize() {
                return chatList.size();
            }
        });
    }
}