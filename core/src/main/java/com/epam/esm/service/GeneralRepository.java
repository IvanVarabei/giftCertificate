package com.epam.esm.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class GeneralRepository {
    protected void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            int parameterIndex = i + 1;
            if (parameters[i] != null) {
                statement.setObject(parameterIndex, parameters[i]);
            } else {
                statement.setNull(parameterIndex, Types.NULL);
            }
        }
    }
}
