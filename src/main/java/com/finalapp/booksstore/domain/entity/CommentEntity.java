package com.finalapp.booksstore.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @NotNull(message = "Provide content!")
    @Size(min = 2, max = 256, message = "Comment between 2 and 256 characters!")
    private String content;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "userID", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(targetEntity = BookEntity.class)
//    @JoinColumn(name = "id", referencedColumnName = "id")
    private BookEntity book;


}
