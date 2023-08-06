package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findAllBy();

}