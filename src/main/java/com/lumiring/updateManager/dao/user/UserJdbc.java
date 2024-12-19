package com.lumiring.updateManager.dao.user;

import com.lumiring.updateManager.domain.dto.user.Role;
import com.lumiring.updateManager.domain.dto.user.User;
import com.lumiring.updateManager.domain.dto.user.UserMapper;
import com.lumiring.updateManager.domain.constant.Code;
import com.lumiring.updateManager.domain.response.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional
public class UserJdbc extends JdbcDaoSupport {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    public void insertNewUser(User user) {
        try {

            String rolesStr = user.getRoles().stream()
                    .map(Role::name)
                    .collect(Collectors.joining(",", "{", "}"));

            String userSql = "INSERT INTO profile.users (username, password, email, company, roles) " +
                    "VALUES(:username, :password, :email, :company, :roles::profile.ROLE[])";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", user.getUsername());
            params.addValue("password", user.getPassword());
            params.addValue("company", user.getCompany());
            params.addValue("email", user.getEmail());
            params.addValue("roles", rolesStr);

            namedParameterJdbcTemplate.update(userSql, params);
        } catch (DuplicateKeyException ex) {
            throw CommonException.builder()
                    .code(Code.USERNAME_BUSY)
                    .userMessage("Username or E-mail already exists")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw CommonException.builder()
                    .code(Code.BAD_CREDENTIALS)
                    .userMessage("Bad credentials")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }


    public boolean isExistsUsername(String username) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM profile.users WHERE username = ?);", Boolean.class, username));
    }

    public boolean isExistsEmail(String email) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM profile.users WHERE email = ?);", Boolean.class, email));
    }

    /**
     * @return null if user not found
     */
    public User getUserByUsername(String username) {
        try {
            String userSql = "SELECT u.id, u.username, u.email, u.password, u.company, u.roles FROM profile.users u WHERE u.username = :username ";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", username);

            User user = namedParameterJdbcTemplate.queryForObject(userSql, params, new UserMapper());

            return user;
        } catch (EmptyResultDataAccessException e) {
            log.info("Username '{}' not found", username);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        try {
            String userSql = "SELECT u.id, u.username, u.email, u.password, u.company, u.roles FROM profile.users u WHERE u.email = :email ";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("email", email);

            User user = namedParameterJdbcTemplate.queryForObject(userSql, params, new UserMapper());

            return user;
        } catch (EmptyResultDataAccessException e) {
            log.info("Email '{}' not found", email);
        }
        return null;

    }

    public User getUserById(Long userId) {
        try {
            String userSql = "SELECT u.id, u.username, u.email, u.password, u.company, u.roles FROM profile.users u WHERE u.id = :user_id ";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("user_id", userId);

            User user = namedParameterJdbcTemplate.queryForObject(userSql, params, new UserMapper());

            return user;
        } catch (EmptyResultDataAccessException e) {
            log.info("User with id '{}' not found", userId);
        }
        return null;
    }

    public List<User> getUserProfileList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        String acuSql = "SELECT id, username, email, company FROM profile.users LIMIT :size OFFSET :offset;";
        Map<String, Object> params = new HashMap<>();
        params.put("size", size);
        params.put("offset", offset);

        return namedParameterJdbcTemplate.query(acuSql, new MapSqlParameterSource(params), new UserMapper());

    }

    public Long getTotalUsers(){
        String sql = "SELECT COUNT(*) FROM profile.users;";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
    }

    public User updateUserCreds(User user) {

        String sql = "UPDATE profile.users " +
                "SET username = COALESCE(:username, username), " +
                "    password = COALESCE(:password, password), " +
                "    email = COALESCE(:email, email), " +
                "    update_time = CURRENT_TIMESTAMP " +
                "WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("email", user.getEmail());
        params.put("id", user.getId());

        namedParameterJdbcTemplate.update(sql, params);
        return user;
    }

    public void deleteUser(Long userId) {
        String acuSql = "DELETE FROM profile.users WHERE id=:id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);


        if (namedParameterJdbcTemplate.update(acuSql, new MapSqlParameterSource(params)) == 0) {
            throw CommonException.builder().code(Code.NOT_FOUND).userMessage("User not found").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        namedParameterJdbcTemplate.update(acuSql, new MapSqlParameterSource(params));
    }


}
