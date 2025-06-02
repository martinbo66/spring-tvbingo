package org.bomartin.tvbingo.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import org.bomartin.tvbingo.model.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureEmbeddedDatabase(provider = DatabaseProvider.ZONKY)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class ShowRepositoryTest {

    @Autowired
    private ShowRepository showRepository;

    @Test
    void whenSaveShow_thenShowIsCreated() {
        // Given
        Show show = Show.builder()
                .showTitle("Test Show Unique 1")
                .gameTitle("Test Game")
                .centerSquare("Center Square")
                .phrases(Arrays.asList("Phrase 1", "Phrase 2", "Phrase 3"))
                .build();

        // When
        Show savedShow = showRepository.save(show);

        // Then
        assertNotNull(savedShow.getId());
        assertEquals("Test Show Unique 1", savedShow.getShowTitle());
        assertEquals("Test Game", savedShow.getGameTitle());
        assertEquals("Center Square", savedShow.getCenterSquare());
        assertEquals(3, savedShow.getPhrases().size());
    }

    @Test
    void whenFindById_thenReturnShow() {
        // Given
        Show show = Show.builder()
                .showTitle("Test Show Unique 2")
                .gameTitle("Another Game")
                .centerSquare("Another Square")
                .phrases(Arrays.asList("Phrase A", "Phrase B"))
                .build();
        Show savedShow = showRepository.save(show);

        // When
        Optional<Show> foundShow = showRepository.findById(savedShow.getId());

        // Then
        assertTrue(foundShow.isPresent());
        assertEquals(savedShow.getShowTitle(), foundShow.get().getShowTitle());
        assertEquals(savedShow.getGameTitle(), foundShow.get().getGameTitle());
    }

    @Test
    void whenFindAll_thenReturnAllShows() {
        // Given
        Show show1 = Show.builder()
                .showTitle("Test Show Unique 3")
                .gameTitle("Game 1")
                .phrases(Arrays.asList("P1", "P2"))
                .build();
        Show show2 = Show.builder()
                .showTitle("Test Show Unique 4")
                .gameTitle("Game 2")
                .phrases(Arrays.asList("P3", "P4"))
                .build();
        showRepository.save(show1);
        showRepository.save(show2);

        // When
        List<Show> shows = StreamSupport.stream(showRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // Then
        assertFalse(shows.isEmpty());
        assertTrue(shows.size() >= 2);
    }

    @Test
    void whenUpdateShow_thenShowIsUpdated() {
        // Given
        Show show = Show.builder()
                .showTitle("Test Show Unique 5")
                .gameTitle("Original Game")
                .phrases(Arrays.asList("Original Phrase"))
                .build();
        Show savedShow = showRepository.save(show);

        // When
        savedShow.setShowTitle("Test Show Unique 6");
        savedShow.setGameTitle("Updated Game");
        Show updatedShow = showRepository.save(savedShow);

        // Then
        assertEquals("Test Show Unique 6", updatedShow.getShowTitle());
        assertEquals("Updated Game", updatedShow.getGameTitle());
    }

    @Test
    void whenDeleteShow_thenShowIsRemoved() {
        // Given
        Show show = Show.builder()
                .showTitle("Test Show Unique 7")
                .gameTitle("Game to Delete")
                .phrases(Arrays.asList("Delete This"))
                .build();
        Show savedShow = showRepository.save(show);

        // When
        showRepository.deleteById(savedShow.getId());

        // Then
        Optional<Show> deletedShow = showRepository.findById(savedShow.getId());
        assertTrue(deletedShow.isEmpty());
    }

    @Test
    void whenExistsByShowTitle_thenReturnTrue() {
        // Given
        String showTitle = "Test Show Unique 8";
        Show show = Show.builder()
                .showTitle(showTitle)
                .gameTitle("Some Game")
                .build();
        showRepository.save(show);

        // When
        boolean exists = showRepository.existsByShowTitle(showTitle);

        // Then
        assertTrue(exists);
    }

    @Test
    void whenExistsByShowTitleExceptId_thenReturnFalse() {
        // Given
        Show show = Show.builder()
                .showTitle("Test Show Unique 9")
                .gameTitle("Some Game")
                .build();
        Show savedShow = showRepository.save(show);

        // When
        boolean exists = showRepository.existsByShowTitleExceptId("Test Show Unique 9", savedShow.getId());

        // Then
        assertFalse(exists);
    }
} 