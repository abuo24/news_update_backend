package com.news.update.controller;


import com.news.update.entity.Admins;
import com.news.update.entity.Role;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.LoginRequest;
import com.news.update.repository.AdminsRepository;
import com.news.update.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminsRepository adminsRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginVM) {
        Admins user = adminsRepository.findByUsername(loginVM.getUsername()).get();
        if (user == null) {
            return new ResponseEntity(new Result(false, "User not available"), BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword()));
            Set<Role> roleList = new HashSet<>(user.getRoles());
            String token = jwtProvider.createToken(user.getUsername(),roleList);
            Map<Object, Object> map = new HashMap<>();
            map.put("succes", true);
            map.put("username", user.getUsername());
            map.put("token", token);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return new ResponseEntity(new Result(false, e.getLocalizedMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getme")
    public ResponseEntity getUser(HttpServletRequest request) {
        Admins user = adminsRepository.findByUsername(jwtProvider.getUser(jwtProvider.resolveToken(request))).get();
        return user != null ? ResponseEntity.ok(new ResultSucces(true, user))
                : (new ResponseEntity(new Result(false, "token is invalid"), BAD_REQUEST));
    }


}
