package kz.narxoz.monamieproject.services.impl;

import kz.narxoz.monamieproject.models.Roles;
import kz.narxoz.monamieproject.models.Users;
import kz.narxoz.monamieproject.repositories.RoleRepository;
import kz.narxoz.monamieproject.repositories.UserRepository;
import kz.narxoz.monamieproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepository.findByEmail(username);
    if(user != null){
      return user;
    }else{
      throw new UsernameNotFoundException("User not found");
    }
  }

  @Override
  public Users getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Users createUser(Users user) {
    Users checkUser = userRepository.findByEmail(user.getEmail());
    if(checkUser==null){
      Roles role = roleRepository.findByRole("ROLE_USER");
      if(role!=null){
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
      }
    }
    return null;
  }
}
