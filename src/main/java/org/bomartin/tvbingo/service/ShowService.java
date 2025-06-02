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

/**
 * Service class for managing Show entities. Provides methods to create, retrieve, update, and delete shows.
 */
@Service
public class ShowService {
    private final ShowRepository showRepository;
    
    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    
    /**
     * Constructs a new ShowService with the given ShowRepository.
     *
     * @param showRepository the repository used for Show persistence operations
     */
    @Tool(description = "Create a new show")
    public Show createShow(
            @ToolParam(description = "Show object to create") Show show
    ) {
        return showRepository.save(show);
    }
    
    /**
     * Retrieves a show by its ID.
     *
     * @param id the ID of the show to retrieve
     * @return an Optional containing the Show if found, or empty if not found
     */
    @Tool(description = "Get a show by ID")
    public Optional<Show> getShow(
            @ToolParam(description = "Show ID, e.g. 3") Long id
    ) {
        return showRepository.findById(id);
    }
    
    /**
     * Retrieves all shows.
     *
     * @return a list of all Show entities
     */
    @Tool(description = "Get all shows")
    public List<Show> getAllShows() {
        return StreamSupport.stream(showRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    
    /**
     * Updates an existing show. The provided Show object must include an ID.
     *
     * @param show the Show object with updated fields (must include id)
     * @return the updated Show entity
     * @throws IllegalArgumentException if the Show ID is null
     */
    @Tool(description = "Update an existing show. Provide a Show object with updated fields (must include id)")
    public Show updateShow(
            @ToolParam(description = "Show object with updated fields (must include id)") Show show
    ) {
        if (show.getId() == null) {
            throw new IllegalArgumentException("Show ID must not be null for updates");
        }
        return showRepository.save(show);
    }
    
    /**
     * Deletes a show by its ID.
     *
     * @param id the ID of the show to delete
     */
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
} 