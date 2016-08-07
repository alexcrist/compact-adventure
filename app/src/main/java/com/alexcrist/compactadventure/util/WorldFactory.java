package com.alexcrist.compactadventure.util;

import com.alexcrist.compactadventure.core.World;
import com.alexcrist.compactadventure.entity.Balloon;
import com.alexcrist.compactadventure.entity.Entity;
import com.alexcrist.compactadventure.entity.Player;
import com.alexcrist.compactadventure.entity.Skeleton;

public class WorldFactory {

    public World generateWorld(int level) {

        Player player;
        Entity[] entities;

        switch (level) {

            case 1:
                player = new Player(.5f, .5f);
                entities = new Entity[5];
                entities[0] = player;
                entities[1] = new Balloon(.25f, .25f);
                entities[2] = new Balloon(.25f, .75f);
                entities[3] = new Balloon(.75f, .75f);
                entities[4] = new Balloon(.75f, .25f);
                break;

            case 2:
                player = new Player(.5f, .5f);
                entities = new Entity[2];
                entities[0] = player;
                entities[1] = new Skeleton(.25f, .25f, player);
                break;

            default:
                player = null;
                entities = null;
                break;
        }

        return new World(player, entities);
    }
}
