package com.innovitech.example.serviceImpl;

import com.innovitech.example.database.entity.User;
import com.innovitech.example.database.repository.UserDAO;
import com.innovitech.example.domain.UserDTO;
import com.innovitech.example.services.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

@Default
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private JwtTokenProvider jwtTokenProvider;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public String login(UserDTO userDTO) {
        try {
            User user = userDAO.findByUsername(userDTO.getUsername());
            if (user.getPassword().equals(userDTO.getPassword())) {
                return jwtTokenProvider.createToken(user.getUsername(), "role");
            } else {
                return "wrong password";
            }
        } catch (NoResultException e) {
            return "wrong username";
        }
    }

    @Override
    public List getAll() {
        return userDAO.findAll();
    }

    @Override
    public String registration(UserDTO userDTO) {
        if (requiredValidator(userDTO.getPassword(), userDTO.getUsername())) {
            try {
                User user = modelMapper.map(userDTO, User.class);
                userDAO.persist(user);
                return "success";
            } catch (Exception e) {
                if (e.getCause().getCause() != null) {
                    if(e.getCause().getCause().getClass().equals(ConstraintViolationException.class)){
                        return "unique exception";
                    }
                }
                return "fatal error";
            }
        }
        return "every field required";
    }

    @Override
    public String update(UserDTO userDTO) {
        if (requiredValidator(userDTO.getPassword(), userDTO.getUsername())) {
            try {
                User user = modelMapper.map(userDTO, User.class);
                userDAO.update(user);
                return "success";
            } catch (Exception e) {
                if (e.getCause().getCause() != null) {
                    if(e.getCause().getCause().getClass().equals(ConstraintViolationException.class)){
                        return "unique exception";
                    }
                }
                return "fatal error";
            }
        }
        return "every field required";
    }

    @Override
    public String delete(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userDAO.remove(user);
        return "ok";
    }

    private boolean requiredValidator(String... requiredStrings) {
        for (String s : requiredStrings) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }
}
