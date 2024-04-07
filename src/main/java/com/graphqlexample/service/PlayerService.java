package com.graphqlexample.service;

import com.graphqlexample.model.Player;
import com.graphqlexample.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {
    private List<Player> players = new ArrayList<>();
    AtomicInteger id = new AtomicInteger();

    public PlayerService(List<Player> players) {
        this.players = players;
        this.id = id;
    }

    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id) {
        return players.stream().filter(player -> player.Id() == id).findFirst();
    }

    public Player create(String name, Team team) {
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player update(Integer id, String name, Team team) {
        Player updatePlayer = new Player(id, name, team);
        Optional<Player> optional = players.stream().filter(c -> c.Id() == id).findFirst();
        if (optional.isPresent()) {
            Player player = optional.get();
            int index = players.indexOf(player);
            players.set(index, updatePlayer);
        } else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatePlayer;
    }

    public Player delete(Integer id) {
        Player player = players.stream().filter(c -> c.Id() == id).findFirst().orElseThrow(() -> new IllegalArgumentException());
        players.remove(player);
        return player;

    }

    @PostConstruct
    private void init() {
        players.add(new Player(id.incrementAndGet(), "MS Dhoni", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Rohit Sharma", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Shubman Gill", Team.GT));
        players.add(new Player(id.incrementAndGet(), "Pat Cummins", Team.SRH));
        players.add(new Player(id.incrementAndGet(), "Virat Kohli", Team.RCB));
    }
}

