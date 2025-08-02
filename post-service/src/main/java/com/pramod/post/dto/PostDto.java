package com.pramod.post.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private String caption;
    private String imageUrl;
    private String userId;
}
