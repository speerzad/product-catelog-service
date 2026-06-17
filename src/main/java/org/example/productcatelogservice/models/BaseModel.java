package org.example.productcatelogservice.models;

import java.util.Date;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    private Long id;
    private String name;
    private Date createdAt;
    private Date lastUpdatedAt;
    private State state;
}
