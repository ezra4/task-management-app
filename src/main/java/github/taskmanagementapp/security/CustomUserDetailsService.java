package github.taskmanagementapp.security;

import github.taskmanagementapp.model.Role;
import github.taskmanagementapp.model.UserEntity;
import github.taskmanagementapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role.name().equals("ROLE_ADMIN"))
            authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
        else authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        return authorities;
    }
}
