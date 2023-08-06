package com.epam.esm.auth.repository;

import com.epam.esm.auth.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findAllBy();

}