package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.GuildTopics;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

/**
 * Orchestrates a planning session that uses both Iterator and Mediator.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;
        int notificationsBefore = hall instanceof GuildHall guildHall ? guildHall.getNotificationCount() : 0;

        System.out.println();
        System.out.println("=== War Council Run ===");
        System.out.println("Party roster: " + summarizeParty(party));

        System.out.println("-- Ordered Review --");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest quest = ordered.next();
            questsTraversed++;
            System.out.println("Reviewing: " + quest);
            hall.dispatch(GuildTopics.ORDERS, null,
                    "Review contract '" + quest.getTitle() + "' for party " + summarizeParty(party));
            messagesRouted++;
        }

        System.out.println("-- High Priority Planning --");
        QuestIterator urgentBoard = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (urgentBoard.hasNext()) {
            Quest quest = urgentBoard.next();
            questsTraversed++;
            System.out.println("Escalated: " + quest);

            hall.dispatch(GuildTopics.SCOUTING, null,
                    "Scout the approach to '" + quest.getTitle() + "'. Reward: " + quest.getRewardGold() + " gold.");
            messagesRouted++;

            hall.dispatch(GuildTopics.SUPPLIES, null,
                    "Prepare specialty gear for '" + quest.getTitle() + "'.");
            messagesRouted++;

            if (quest.isUrgent()) {
                hall.dispatch(GuildTopics.HEALING, null,
                        "Stage emergency treatment kits for '" + quest.getTitle() + "'.");
                messagesRouted++;
            }

            if (needsLoreSupport(quest)) {
                hall.dispatch(GuildTopics.LORE, null,
                        "Research lore warnings tied to '" + quest.getTitle() + "'.");
                messagesRouted++;
            }
        }

        int membersNotified = hall instanceof GuildHall guildHall
                ? guildHall.getNotificationCount() - notificationsBefore
                : 0;

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private boolean needsLoreSupport(Quest quest) {
        String title = quest.getTitle().toLowerCase();
        return title.contains("cursed")
                || title.contains("crypt")
                || title.contains("ruins")
                || title.contains("monastery");
    }

    private String summarizeParty(List<Hero> party) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < party.size(); i++) {
            Hero hero = party.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(hero.getName())
                    .append("(hp=")
                    .append(hero.getHp())
                    .append(", atk=")
                    .append(hero.getAttackPower())
                    .append(", def=")
                    .append(hero.getDefense())
                    .append(')');
        }
        return builder.toString();
    }
}
