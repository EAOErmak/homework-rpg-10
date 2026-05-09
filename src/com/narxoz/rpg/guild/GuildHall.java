package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topic-based mediator for the Adventurers' Guild war council.
 */
public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int dispatchCount;
    private int notificationCount;

    @Override
    public void register(GuildMember member) {
        if (member instanceof Captain) {
            addSubscriber(GuildTopics.ORDERS, member);
            addSubscriber(GuildTopics.SCOUTING, member);
            addSubscriber(GuildTopics.SUPPLIES, member);
            addSubscriber(GuildTopics.HEALING, member);
            addSubscriber(GuildTopics.LORE, member);
            return;
        }

        if (member instanceof Quartermaster) {
            addSubscriber(GuildTopics.ORDERS, member);
            addSubscriber(GuildTopics.SCOUTING, member);
            addSubscriber(GuildTopics.SUPPLIES, member);
            addSubscriber(GuildTopics.HEALING, member);
            return;
        }

        if (member instanceof Scout) {
            addSubscriber(GuildTopics.ORDERS, member);
            addSubscriber(GuildTopics.SCOUTING, member);
            addSubscriber(GuildTopics.LORE, member);
            return;
        }

        if (member instanceof Healer) {
            addSubscriber(GuildTopics.ORDERS, member);
            addSubscriber(GuildTopics.HEALING, member);
            addSubscriber(GuildTopics.SUPPLIES, member);
            addSubscriber(GuildTopics.LORE, member);
            return;
        }

        if (member instanceof Loremaster) {
            addSubscriber(GuildTopics.ORDERS, member);
            addSubscriber(GuildTopics.SCOUTING, member);
            addSubscriber(GuildTopics.LORE, member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        dispatchCount++;
        String sender = from == null ? "CouncilEngine" : from.getName();
        System.out.printf("[GuildHall] topic=%s from=%s payload=%s%n", topic, sender, payload);
        for (GuildMember member : subscribersFor(topic)) {
            if (member == from) {
                continue;
            }
            notificationCount++;
            member.receive(topic, from, payload);
        }
    }

    protected void addSubscriber(String topic, GuildMember member) {
        List<GuildMember> subscribers = membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>());
        if (!subscribers.contains(member)) {
            subscribers.add(member);
        }
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }

    public int getDispatchCount() {
        return dispatchCount;
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}
