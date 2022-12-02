package kz.narxoz.monamieproject.services;

import kz.narxoz.monamieproject.models.Users;
import kz.narxoz.monamieproject.repositories.RoleRepository;
import kz.narxoz.monamieproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {


   Users getUserByEmail(String email);
   Users createUser(Users user);
}
