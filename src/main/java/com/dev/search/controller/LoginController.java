package com.dev.search.controller;

import com.dev.search.entity.User;
import com.dev.search.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public LoginController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping(value = "/loginSuccess")
    public ModelAndView loginSuccess(HttpSession session, @RequestParam(value="username") String id) {
        User user = userDetailsServiceImpl.loadUserByUsername(id);
        ModelAndView modelAndView = new ModelAndView("/search/searchView");
        session.setAttribute("loginUserName", user.getUsername());
        session.setAttribute("loginUserId", user.getId());
        return modelAndView;
    }

    @GetMapping(value = "/logOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
