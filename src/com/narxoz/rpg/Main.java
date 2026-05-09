package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildTopics;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

/**
 * Entry point for Homework 10: The Adventurers' Guild: Iterator + Mediator.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        List<Hero> party = List.of(
                new Hero("Aldric", 120, 20, 26, 14, 80),
                new Hero("Lyra", 85, 70, 18, 8, 140),
                new Hero("Borin", 150, 10, 21, 18, 55)
        );

        System.out.println("Party:");
        for (Hero hero : party) {
            System.out.println(" - " + hero);
        }

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Goblin Supply Raid", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Cursed Monastery Ruins", QuestPriority.HIGH, 340, true));
        questLog.add(new Quest("Escort the Alchemist Caravan", QuestPriority.LOW, 90, false));
        questLog.add(new Quest("Wolves at Frostpass", QuestPriority.NORMAL, 140, false));
        questLog.add(new Quest("Seal the Crypt of Ash", QuestPriority.URGENT, 500, true));
        questLog.add(new Quest("Bandit King Bounty", QuestPriority.HIGH, 420, false));

        System.out.println();
        System.out.println("=== Iterator Demo ===");
        printTraversal("Arrival order", questLog.ordered());
        printTraversal("Reverse order", questLog.reverse());
        printTraversal("Priority >= HIGH", questLog.priorityAtLeast(QuestPriority.HIGH));
        printTraversal("Reward sorted", questLog.rewardSorted());

        GuildHall hall = new GuildHall();
        Quartermaster quartermaster = new Quartermaster("Mira", hall);
        Scout scout = new Scout("Tavin", hall);
        Healer healer = new Healer("Sister Elowen", hall);
        Captain captain = new Captain("Roderick", hall);
        Loremaster loremaster = new Loremaster("Seraphine", hall);

        System.out.println();
        System.out.println("=== Mediator Demo ===");
        captain.issueOrder(GuildTopics.ORDERS, "Review only contracts the current party can survive.");
        scout.reportRoute(GuildTopics.SCOUTING, "North pass is open, but the monastery trail is covered in fog.");
        quartermaster.requestSupplies(GuildTopics.SUPPLIES, "Prepare silver bolts, cold-weather cloaks, and rope.");
        healer.prepareAid(GuildTopics.HEALING, "Load trauma kits, antidotes, and two spare mana tonics.");
        loremaster.shareLore(GuildTopics.LORE, "The Ash Crypt answers to moonlit steel and warding hymns.");

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println();
        System.out.println("Final result: " + result);
    }

    private static void printTraversal(String label, QuestIterator iterator) {
        System.out.println(label + ":");
        while (iterator.hasNext()) {
            System.out.println(" - " + iterator.next());
        }
    }
}
