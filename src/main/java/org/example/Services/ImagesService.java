package org.example.Services;

import org.example.Models.Images;
import org.example.Repos.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    public Images findById(Long id) {
        return imagesRepository.findByID(id);
    }

    public void save(Images image) {
        imagesRepository.save(image);
    }

}
