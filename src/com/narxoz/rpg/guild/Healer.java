package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for wounds, potions, and recovery plans.
 */
public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        say("Healer", "sends a recovery plan on '" + topic + "': " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = sourceName(from);
        switch (topic) {
            case GuildTopics.ORDERS ->
                    say("Healer", "prepares triage assignments after " + sender + "'s order: " + payload);
            case GuildTopics.HEALING ->
                    say("Healer", "mixes potions and restorative salves: " + payload);
            case GuildTopics.SUPPLIES ->
                    say("Healer", "requests herbs, linens, and antidotes from stores: " + payload);
            case GuildTopics.LORE ->
                    say("Healer", "checks the infirmary archive for curses and toxins: " + payload);
            default ->
                    say("Healer", "notes an unexpected message from " + sender + ": " + payload);
        }
    }
}
