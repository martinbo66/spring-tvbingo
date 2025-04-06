package org.bomartin.tvbingo.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    private Long id;
    
    @NotBlank(message = "Show title is required")
    private String showTitle;
    
    private String gameTitle;
    private String centerSquare;
    
    @Builder.Default
    private List<String> phrases = new ArrayList<>();
} 