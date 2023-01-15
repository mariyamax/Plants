package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Enums.Area;
import org.example.Enums.University;
import org.example.Models.Images;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Repos.ProjectRepo;
import org.example.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepo projectRepository;
    private final UserRepo userRepo;

    @Autowired
    private final ImagesService imagesService;


    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void createProject(User user, Project project, Area area) {
        project.setAuthorId(user.getID());
        project.getUser().add(user);
        project.setAuthor(user.getUsername());
        project.setArea(area);
        if (project.getDescription().isEmpty() || project.getName().isEmpty()) {
            return;
        }
        projectRepository.save(project);
        user.getProjects().add(projectRepository.save(project));
        userRepo.save(user);
    }

    public Project findById(Long id) {
        return projectRepository.findByID(id);
    }

    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    public List<Project> filterByName(String name) {
        List<Project> all = projectRepository.findAll();
        List<Project> toReturn = new ArrayList<>();
        if (!all.isEmpty()) {
            toReturn = all.stream().filter(project -> project.getName().contains(name)).toList();
        } else {
            return null;
        }
        if (toReturn.isEmpty()) {
            return null;
        } else {
            return toReturn;
        }
    }

    public void addImage(Project resource, MultipartFile file) {
        Images image = new Images();
        image.toImageEntity(file);
        imagesService.save(image);
        resource.setImageId(image.getID());
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> filterByNameAndArea(String name, Area area) {
        List<Project> all = filterByName(name);
        if (all != null) {
            return all.stream().filter(project -> project.getArea().equals(area)).toList();
        } else {
            return null;
        }
    }

    public List<Project> filterByArea(Area area) {
        List<Project> all = projectRepository.findAll();
        List<Project> toReturn = new ArrayList<>();
        if (!all.isEmpty()) {
            toReturn = all.stream().filter(project -> project.getArea().equals(area)).toList();
        } else {
            return null;
        }
        if (toReturn.isEmpty()) {
            return null;
        } else {
            return toReturn;
        }
    }

    public void addOrderedUser(Project project,User user) {
        project.getOrderedUsers().remove(user);
        project.getUser().add(user);
        projectRepository.save(project);
    }
}
