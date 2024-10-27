package org.example.datafulldisplay.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class JsonNodeTypeHandler implements TypeHandler<JsonNode> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, parameter.toString());
        } else {
            ps.setNull(i, Types.VARCHAR);
        }
    }

    @Override
    public JsonNode getResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse JSON: " + json, e);
        }
    }

    @Override
    public JsonNode getResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse JSON: " + json, e);
        }
    }

    @Override
    public JsonNode getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse JSON: " + json, e);
        }
    }
}