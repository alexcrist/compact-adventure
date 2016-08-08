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

            case 3:
                player = new Player(.5f, .5f);
                entities = new Entity[5];
                entities[0] = player;
                entities[1] = new Skeleton(.25f, .25f, player);
                entities[2] = new Skeleton(.75f, .75f, player);
                entities[3] = new Skeleton(2f, 2f, player);
                entities[4] = new Skeleton(-2f, -2f, player);
                break;

            case 4:
                player = new Player(.5f, .5f);
                entities = new Entity[7];
                entities[0] = player;
                entities[1] = new Skeleton(.25f, .25f, player);
                entities[2] = new Skeleton(.25f, .25f, player);
                entities[3] = new Skeleton(.25f, .25f, player);
                entities[4] = new Skeleton(.25f, .25f, player);
                entities[5] = new Skeleton(.25f, .25f, player);
                entities[6] = new Skeleton(.25f, .25f, player);
                break;

            case 5:
                player = new Player(.5f, .5f);
                entities = new Entity[100];
                entities[0] = player;

                for (int i = 0; i < 99; i++) {
                    float dist = 1 + i * .1f;
                    float angle = (float) (Math.random() * 2 * Math.PI);
                    float x = dist * (float) Math.cos(angle);
                    float y = dist * (float) Math.sin(angle);

                    entities[i + 1] = new Skeleton(x, y, player);
                }
                break;

            case 6:
                player = new Player(.5f, .5f);
                entities = new Entity[1000];
                entities[0] = player;

                for (int i = 0; i < 999; i++) {
                    float dist = 1 + i * .01f;
                    float angle = (float) (Math.random() * 2 * Math.PI);
                    float x = dist * (float) Math.cos(angle);
                    float y = dist * (float) Math.sin(angle);

                    entities[i + 1] = new Skeleton(x, y, player);
                }
                break;

            default:
                player = null;
                entities = null;
                break;
        }

        return new World(player, entities);
    }
}
