package com.lumiring.updateManager.dao.user;

import com.lumiring.updateManager.domain.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDao {
//    private final UserJpa repository;
    private final UserJdbc userJdbc;

//    public void save(User user){
//        repository.save(user);
//    }

//    public User findByEmail(String email){
//        return repository.findByEmail(email);
//    }


    public void insertNewUser(User user) {
        userJdbc.insertNewUser(user);
    }

    /**
     * @return null if user not found
     */
    public User getUserByUsername(String username){
        return userJdbc.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userJdbc.getUserByEmail(email);
    }

    public User getUserById(Long userId){
        return userJdbc.getUserById(userId);
    }

    public boolean isExistsUsername(String username) {
        return userJdbc.isExistsUsername(username);
    }

    public boolean isExistsEmail(String email) {
        return userJdbc.isExistsEmail(email);
    }


    public List<User> getUserProfileList(Integer page, Integer size) {
        return userJdbc.getUserProfileList(page, size);
    }

    public long getTotalUsers() {
        return userJdbc.getTotalUsers();
    }

    public User updateUserCreds(User user) {
        return userJdbc.updateUserCreds(user);
    }

    public void deleteUser(Long userId) {
        userJdbc.deleteUser(userId);
    }


//    public Set<Role> getRolesByUserId(Long id) {return userJdbcTemplate.getRolesByUserId(id);}

}
