package com.graphqlexample;

import com.graphqlexample.controller.PlayerController;
import com.graphqlexample.model.Player;
import com.graphqlexample.model.Team;
import com.graphqlexample.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void testFindAll() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Player 1", Team.CSK));
        players.add(new Player(2, "Player 2", Team.MI));

        when(playerService.findAll()).thenReturn(players);

        List<Player> result = playerController.findAll();

        assertEquals(2, result.size());
        assertEquals("Player 1", result.get(0).getName());
        assertEquals("Player 2", result.get(1).getName());
    }

    @Test
    public void testFindOne() {
        Player player = new Player(1, "Player 1", Team.CSK);
        when(playerService.findOne(1)).thenReturn(Optional.of(player));

        Optional<Player> result = playerController.findOne(1);

        assertEquals(player, result.orElse(null));
    }

    @Test
    public void testCreate() {
        Player player = new Player(1, "Player 1", Team.CSK);
        when(playerService.create("Player 1", Team.CSK)).thenReturn(player);

        Player result = playerController.create("Player 1", Team.CSK);

        assertEquals(player, result);
    }

    @Test
    public void testUpdate() {
        Player updatedPlayer = new Player(1, "Updated Player", Team.MI);
        when(playerService.update(1, "Updated Player", Team.MI)).thenReturn(updatedPlayer);

        Player result = playerController.update(1, "Updated Player", Team.MI);

        assertEquals(updatedPlayer, result);
    }

    @Test
    public void testDelete() {
        Player player = new Player(1, "Player 1", Team.CSK);
        when(playerService.delete(1)).thenReturn(player);

        Player result = playerController.delete(1);

        assertEquals(player, result);
    }

    @Test
    public void testDeleteNonExistingPlayer() {
        when(playerService.delete(anyInt())).thenThrow(new IllegalArgumentException("Invalid Player"));

        assertThrows(IllegalArgumentException.class, () -> playerController.delete(100));
    }
}
