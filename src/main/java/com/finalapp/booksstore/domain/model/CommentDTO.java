package com.finalapp.booksstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotNull(message = "The content of the comments cannot be NULL!")
    @Size(min = 2, max = 256, message = "Content of the comments should be between 2 and 256 characters!")
    private String content;
    private LocalDateTime time;

    @NotNull()
    @Min(2)
    private String userName;

    public CommentDTO(CommentDTO comment) {

    }
}
