package org.example.Models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;
import org.example.Enums.Area;
import org.hibernate.Hibernate;

@Entity
@Table(name = "projects")
@Data
@RequiredArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long ID;

  @Column(name = "name",unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "author")
  private String author;

  @Column(name = "author_id")
  private Long authorId;

  @Column(name = "image_id")
  private Long imageId;

  @Column(name = "area")
  private Area area;

  @ElementCollection(targetClass = String.class, fetch =FetchType.EAGER)
  @CollectionTable(name = "projects_tasks", joinColumns = @JoinColumn(name = "project_id"))
  private Set<String> tasks = new HashSet<>();

  @ElementCollection(targetClass = Long.class, fetch =FetchType.EAGER)
  @CollectionTable(name = "ordered_project", joinColumns = @JoinColumn(name = "project_id"))
  private Set<Long> orderedUsers = new HashSet<>();

  @ToString.Exclude
  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinTable
  private Set<User> user = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Project project = (Project) o;
    return ID != null && Objects.equals(ID, project.ID);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
