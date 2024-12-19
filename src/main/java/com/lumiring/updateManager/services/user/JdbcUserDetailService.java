package com.lumiring.updateManager.services.user;

import com.lumiring.updateManager.dao.user.UserDao;
import com.lumiring.updateManager.domain.dto.user.User;
import com.lumiring.updateManager.domain.constant.Code;
import com.lumiring.updateManager.domain.response.exception.CommonException;
import com.lumiring.updateManager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcUserDetailService  implements UserDetailsService {

    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userDao.getUserByUsername(username));

        if (user.isEmpty()){
            throw CommonException.builder().code(Code.USER_NOT_FOUND)
                    .userMessage(String.format("Username %s not found", username)).httpStatus(HttpStatus.NOT_FOUND).build();
        }
        return new UserDetailsImpl(user.get());
    }
}
