package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public long userId;
    public String lab;
    public String url;
    public String containerName;
    public Workspace(long userId, String lab, String url, String containerName){
        this.userId = userId;
        this.lab = lab;
        this.url = url;
        this.containerName = containerName;
    }
}
