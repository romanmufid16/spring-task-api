package romanmufid.springmongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import romanmufid.springmongo.dto.*;
import romanmufid.springmongo.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<UserResponse>> register(@RequestBody RegisterRequestDto request) {
        UserResponse response = authService.register(request);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }

    @PostMapping("/login")
    public WebResponse<TokenResponse> login(@RequestBody LoginRequestDto request) {
        TokenResponse response = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }
}
