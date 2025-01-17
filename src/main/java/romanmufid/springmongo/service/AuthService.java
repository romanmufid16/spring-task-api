package romanmufid.springmongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import romanmufid.springmongo.dto.LoginRequestDto;
import romanmufid.springmongo.dto.RegisterRequestDto;
import romanmufid.springmongo.dto.TokenResponse;
import romanmufid.springmongo.dto.UserResponse;
import romanmufid.springmongo.entity.User;
import romanmufid.springmongo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ValidationService validationService;
    @Autowired
    private JwtService jwtService;

    public UserResponse register(RegisterRequestDto request) {
        validationService.validate(request);
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());

        userRepository.save(user);

        return UserResponse.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .build();
    }

    public TokenResponse login(LoginRequestDto request) {
        validationService.validate(request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String token = jwtService.generateToken(user);

        return TokenResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }
}
