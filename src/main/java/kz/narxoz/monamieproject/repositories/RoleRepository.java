package kz.narxoz.monamieproject.repositories;

import kz.narxoz.monamieproject.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {

  Roles findByRole(String role);

}
