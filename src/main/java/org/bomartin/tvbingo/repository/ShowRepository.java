package org.bomartin.tvbingo.repository;

import org.bomartin.tvbingo.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ShowRepository {
    private final JdbcTemplate jdbcTemplate;
    
    private static final String INSERT_SHOW = 
        "INSERT INTO tvbingo_schema.shows (show_title, game_title, center_square, phrases) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SHOW = 
        "UPDATE tvbingo_schema.shows SET show_title = ?, game_title = ?, center_square = ?, phrases = ? WHERE id = ?";
    private static final String DELETE_SHOW = 
        "DELETE FROM tvbingo_schema.shows WHERE id = ?";
    private static final String SELECT_SHOW = 
        "SELECT id, show_title, game_title, center_square, phrases FROM tvbingo_schema.shows WHERE id = ?";
    private static final String SELECT_ALL_SHOWS = 
        "SELECT id, show_title, game_title, center_square, phrases FROM tvbingo_schema.shows";
    private static final String EXISTS_BY_SHOW_TITLE = 
        "SELECT COUNT(*) > 0 FROM tvbingo_schema.shows WHERE show_title = ?";
    private static final String EXISTS_BY_SHOW_TITLE_EXCEPT_ID = 
        "SELECT COUNT(*) > 0 FROM tvbingo_schema.shows WHERE show_title = ? AND id != ?";
    
    private final RowMapper<Show> showRowMapper = (rs, rowNum) -> {
        Array phrasesArray = rs.getArray("phrases");
        List<String> phrases = Arrays.asList((String[]) phrasesArray.getArray());
        
        return Show.builder()
                .id(rs.getLong("id"))
                .showTitle(rs.getString("show_title"))
                .gameTitle(rs.getString("game_title"))
                .centerSquare(rs.getString("center_square"))
                .phrases(phrases)
                .build();
    };

    @Autowired
    public ShowRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Show save(Show show) {
        if (show.getId() == null) {
            return insert(show);
        }
        return update(show);
    }

    private Show insert(Show show) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SHOW, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, show.getShowTitle());
            ps.setString(2, show.getGameTitle());
            ps.setString(3, show.getCenterSquare());
            Array phraseArray = connection.createArrayOf("text", show.getPhrases().toArray());
            ps.setArray(4, phraseArray);
            return ps;
        }, keyHolder);

        show.setId(keyHolder.getKey().longValue());
        return show;
    }

    private Show update(Show show) {
        int updated = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SHOW);
            ps.setString(1, show.getShowTitle());
            ps.setString(2, show.getGameTitle());
            ps.setString(3, show.getCenterSquare());
            Array phraseArray = connection.createArrayOf("text", show.getPhrases().toArray());
            ps.setArray(4, phraseArray);
            ps.setLong(5, show.getId());
            return ps;
        });
        
        if (updated == 0) {
            throw new RuntimeException("Show not found with id: " + show.getId());
        }
        
        return show;
    }

    public Optional<Show> findById(Long id) {
        List<Show> results = jdbcTemplate.query(SELECT_SHOW, showRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Show> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SHOWS, showRowMapper);
    }

    public void deleteById(Long id) {
        int deleted = jdbcTemplate.update(DELETE_SHOW, id);
        if (deleted == 0) {
            throw new RuntimeException("Show not found with id: " + id);
        }
    }

    public boolean existsByShowTitle(String showTitle) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            EXISTS_BY_SHOW_TITLE, Boolean.class, showTitle));
    }

    public boolean existsByShowTitleExceptId(String showTitle, Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            EXISTS_BY_SHOW_TITLE_EXCEPT_ID, Boolean.class, showTitle, id));
    }
} 