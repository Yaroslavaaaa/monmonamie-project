package kz.narxoz.monamieproject.repositories;

import kz.narxoz.monamieproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional

public interface UserRepository extends JpaRepository<Users, Long> {
//
  Users findByEmail(String email);
//  Users createUser(Users users);

}

