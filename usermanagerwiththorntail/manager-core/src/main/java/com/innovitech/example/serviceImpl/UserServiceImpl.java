package com.innovitech.example.serviceImpl;

import com.innovitech.example.database.entity.User;
import com.innovitech.example.database.repository.AddressDAO;
import com.innovitech.example.database.repository.UserDAO;
import com.innovitech.example.domain.SearchUserQueryRequest;
import com.innovitech.example.domain.UserDTO;
import com.innovitech.example.services.UserService;
import com.innovitech.example.validator.Validator;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Default
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private JwtTokenProvider jwtTokenProvider;

    @Inject
    private AddressDAO addressDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public String login(UserDTO userDTO) {
        try {
            User user = userDAO.findByUsername(userDTO.getUsername());
            if (user.getPassword().equals(DigestUtils.sha256Hex(userDTO.getPassword()))) {
                return jwtTokenProvider.createToken(user.getUsername());
            } else {
                return "wrong password";
            }
        } catch (NoResultException e) {
            return "wrong username";
        }
    }

    @Override
    public List getAll(SearchUserQueryRequest request) {
        return userDAO.getUsers(request).stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String registration(UserDTO userDTO) {
        if (!Validator.requiredValidator(userDTO.getPassword(), userDTO.getUsername())) {
            return "every field required";
        }
        try {
            User user = modelMapper.map(userDTO, User.class);
            user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            userDAO.persist(user);
            return "success";
        } catch (Exception e) {
            return Validator.exceptionHandler(e);
        }
    }

    @Override
    public String update(UserDTO userDTO) {
        if (!Validator.requiredValidator(userDTO.getPassword(), userDTO.getUsername())) {
            return "every field required";
        }
        try {
            User user = modelMapper.map(userDTO, User.class);
            if (!userDAO.find(user.getId()).getPassword().equals(user.getPassword())) {
                user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            }
            userDAO.update(user);
            return "success";

        } catch (Exception e) {
            return Validator.exceptionHandler(e);
        }
    }

    @Override
    public String delete(UserDTO userDTO) {
        User user = userDAO.find(userDTO.getId());
        addressDAO.removeByUser(user);
        userDAO.remove(user);
        return "ok";
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        try {
            return modelMapper.map(userDAO.findByUsername(username), UserDTO.class);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override public long getUserCount() {
        return userDAO.getUserCount();
    }
}
