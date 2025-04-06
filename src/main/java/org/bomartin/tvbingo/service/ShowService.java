package org.bomartin.tvbingo.service;

import org.bomartin.tvbingo.model.Show;
import org.bomartin.tvbingo.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    
    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }
    
    public Show createShow(Show show) {
        return showRepository.save(show);
    }
    
    public Optional<Show> getShow(Long id) {
        return showRepository.findById(id);
    }
    
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }
    
    public Show updateShow(Show show) {
        if (show.getId() == null) {
            throw new IllegalArgumentException("Show ID must not be null for updates");
        }
        return showRepository.save(show);
    }
    
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
} 