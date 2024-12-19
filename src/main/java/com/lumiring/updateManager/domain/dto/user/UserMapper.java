package com.lumiring.updateManager.domain.dto.user;

import java.sql.Array;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper implements RowMapper<User> {
    @Override
    public @NotNull User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User.UserBuilder userBuilder = User.builder();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);

            switch (columnName) {
                case "id" -> userBuilder.id(rs.getLong("id"));
                case "username" -> userBuilder.username(rs.getString("username"));
                case "email" -> userBuilder.email(rs.getString("email"));
                case "company" -> userBuilder.company(rs.getString("company"));
                case "password" -> userBuilder.password(rs.getString("password"));
                case "roles" -> userBuilder.roles(getRolesFromResultSet(rs, "roles"));
            }
        }

        return userBuilder.build();
    }

    private Set<Role> getRolesFromResultSet(ResultSet rs, String columnLabel) throws SQLException {
        Array rolesArray = rs.getArray(columnLabel);
        if (rolesArray != null) {
            Object[] rolesObjArray = (Object[]) rolesArray.getArray();
            String[] roles = Arrays.copyOf(rolesObjArray, rolesObjArray.length, String[].class);
            return Arrays.stream(roles)
                    .map(this::mapToRole)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private Role mapToRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role value: " + role);
            return null;
        }
    }
}

