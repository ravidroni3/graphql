package com.graphqlexample;

import com.graphqlexample.model.Player;
import com.graphqlexample.model.Team;
import com.graphqlexample.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerServiceTest {

    @Mock
    private List<Player> players;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        players = new ArrayList<>();
        players.add(new Player(1, "MS Dhoni", Team.CSK));
        players.add(new Player(2, "Rohit Sharma", Team.MI));
        players.add(new Player(3, "Shubman Gill", Team.GT));
        players.add(new Player(4, "Pat Cummins", Team.SRH));
        players.add(new Player(5, "Virat Kohli", Team.RCB));
        playerService = new PlayerService(players);
    }

    @Test
    public void testFindAll() {
        List<Player> allPlayers = playerService.findAll();
        assertEquals(players.size(), allPlayers.size());
    }

    @Test
    public void testFindOneExistingPlayer() {
        Optional<Player> playerOptional = playerService.findOne(1);
        assertTrue(playerOptional.isPresent());
        assertEquals("MS Dhoni", playerOptional.get().getName());
    }

    @Test
    public void testFindOneNonExistingPlayer() {
        Optional<Player> playerOptional = playerService.findOne(10);
        assertTrue(playerOptional.isEmpty());
    }

    @Test
    public void testCreatePlayer() {
        Player newPlayer = playerService.create("New Player", Team.CSK);
        assertNotNull(newPlayer);
        assertEquals(1, newPlayer.getId());
        assertEquals("New Player", newPlayer.getName());
        assertEquals(Team.CSK, newPlayer.getTeam());
    }

    @Test
    public void testUpdateExistingPlayer() {
        Player updatedPlayer = playerService.update(1, "Updated Name", Team.CSK);
        assertNotNull(updatedPlayer);
        assertEquals(1, updatedPlayer.getId());
        assertEquals("Updated Name", updatedPlayer.getName());
        assertEquals(Team.CSK, updatedPlayer.getTeam());

        Player foundPlayer = playerService.findOne(1).orElse(null);
        assertNotNull(foundPlayer);
        assertEquals(1, foundPlayer.getId());
        assertEquals("Updated Name", foundPlayer.getName());
        assertEquals(Team.CSK, foundPlayer.getTeam());
    }

    @Test
    public void testUpdateNonExistingPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerService.update(10, "Invalid Name", Team.CSK));
    }

    @Test
    public void testDeleteExistingPlayer() {
        Player deletedPlayer = playerService.delete(1);
        assertNotNull(deletedPlayer);
        assertEquals(1, deletedPlayer.getId());
        assertEquals("MS Dhoni", deletedPlayer.getName());
        assertEquals(Team.CSK, deletedPlayer.getTeam());

        assertEquals(4, players.size()); // Check that the player was removed from the list
        assertFalse(playerService.findOne(1).isPresent()); // Ensure the player no longer exists
    }

    @Test
    public void testDeleteNonExistingPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerService.delete(10));
    }
}
