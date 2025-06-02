package org.bomartin.tvbingo.service;

import org.bomartin.tvbingo.model.Show;
import org.bomartin.tvbingo.repository.ShowRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    
    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    
    @Tool(description = "Create a new show")
    public Show createShow(
            @ToolParam(description = "Show object to create") Show show
    ) {
        return showRepository.save(show);
    }
    
    @Tool(description = "Get a show by ID")
    public Optional<Show> getShow(
            @ToolParam(description = "Show ID, e.g. 3") Long id
    ) {
        return showRepository.findById(id);
    }
    
    @Tool(description = "Get all shows")
    public List<Show> getAllShows() {
        return StreamSupport.stream(showRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    
    @Tool(description = "Update an existing show. Provide a Show object with updated fields (must include id)")
    public Show updateShow(
            @ToolParam(description = "Show object with updated fields (must include id)") Show show
    ) {
        if (show.getId() == null) {
            throw new IllegalArgumentException("Show ID must not be null for updates");
        }
        return showRepository.save(show);
    }
    
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
} 