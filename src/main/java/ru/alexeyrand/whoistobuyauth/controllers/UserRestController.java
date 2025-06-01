package ru.alexeyrand.whoistobuyauth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexeyrand.whoistobuybase.controllers.BaseRestController;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuybase.services.BaseService;
import ru.alexeyrand.whoistobuybase.services.UserService;

@RestController()
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/users")
public class UserRestController extends BaseRestController<User> {

    private final UserService userService;

    @Override
    public BaseService<User> getService() {
        return userService;
    }
}
