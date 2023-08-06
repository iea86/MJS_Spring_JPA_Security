package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String username);

    List<User> findAll();

    Page<User> findAllBy(Pageable pageable);

    User findUserById(Long id);

    User save(User user);

}
