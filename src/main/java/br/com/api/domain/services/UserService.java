package br.com.api.domain.services;

import br.com.api.domain.models.PermissionModel;
import br.com.api.domain.models.UserModel;
import br.com.api.domain.repositories.PermissionRepository;
import br.com.api.domain.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    final UserRepository userRepository;
    final PermissionRepository permissionRepository;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder, PermissionRepository permissionRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
    }

    public UserModel createUser(UserModel data){
        var user = this.userRepository.findUserModelByUsername(data.getUsername());
        if(user != null){
          throw new Error("user already exists!");
        }
        var passwordEncripty  = this.passwordEncoder.encode(data.getPassword());
        data.setPassword(passwordEncripty);
        List<PermissionModel> permissionModels = new ArrayList<>();
        permissionModels.add(this.permissionRepository.findPermissionModelByDescription("ROLE_USER"));
        data.setPermissions(permissionModels);
        var responseUser = this.userRepository.save(data);
        responseUser.setPassword(null);
        return responseUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByUsername(username);
        if(user != null) {
            return user;
        }else{
            throw new UsernameNotFoundException("Username "+username+" not found!");
        }
    }
}
