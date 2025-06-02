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

    /**
     * Constructs a new ShowService with the given ShowRepository.
     *
     * @param showRepository the repository used for Show persistence operations
     */
    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    
    /**
     * Creates a new show entity and saves it in the repository.
     *
     * @param show the Show object containing the details of the show to create
     * @return the created Show object after being saved in the repository
     */
    @Tool(name = "create_show", description = "Create a new show")
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
    @Tool(name = "get_show_by_id", description = "Get a show by ID")
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
    @Tool(name = "get_all_shows", description = "Get all shows")
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
    @Tool(
            name = "update_show",
            description = "Update an existing show. Provide a Show object with updated fields (must include id)")
    public Show updateShow(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show must not be null");
        }
        if (show.getId() == null) {
            throw new IllegalArgumentException("Show ID must not be null for updates");
        }
        if (!showRepository.findById(show.getId()).isPresent()) {
            throw new IllegalArgumentException("Show with ID " + show.getId() + " not found");
        }
        if (show.getShowTitle() != null && 
            showRepository.existsByShowTitleExceptId(show.getShowTitle(), show.getId())) {
            throw new IllegalArgumentException("Show title must be unique");
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