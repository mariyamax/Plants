package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Models.Images;
import org.example.Models.User;
import org.example.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
private final UserRepo userRepository;

private final PasswordEncoder passwordEncoder;

@Autowired
private final ImagesService imagesService;

    public boolean createUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void saveUser(User user){
        userRepository.save(user);}

    public List<User> getAllUsers(){
        return userRepository.findAll();}

    public User findByUsername(String username){
    return userRepository.findByUsername(username);}


    public void addImage(User resource, MultipartFile file) {
        Images image = new Images();
        image.toImageEntity(file);
        imagesService.save(image);
        resource.setImageId(image.getID());
    }

    public User findByID(Long id) {
        return userRepository.findByID(id);
    }

    public List<User> findByIds(Set<Long> ids) {
        List<User> toReturn = new ArrayList<>();
        for (Long id: ids) {
            toReturn.add(userRepository.findByID(id));
        }
        return toReturn;
    }
}
