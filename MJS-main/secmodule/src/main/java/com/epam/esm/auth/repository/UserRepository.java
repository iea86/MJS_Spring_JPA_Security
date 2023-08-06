package com.epam.esm.auth.repository;

import com.epam.esm.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User save(User user);

    User findByName(String username);

    User findUserByEmail(String email);
}
