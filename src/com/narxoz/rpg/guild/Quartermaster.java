package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for gear, supplies, and rewards.
 */
public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        say("Quartermaster", "requests stock coordination on '" + topic + "': " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = sourceName(from);
        switch (topic) {
            case GuildTopics.ORDERS ->
                    say("Quartermaster", "packs rations and spare gear after " + sender + "'s order: " + payload);
            case GuildTopics.SCOUTING ->
                    say("Quartermaster", "updates travel kits from the scout report: " + payload);
            case GuildTopics.SUPPLIES ->
                    say("Quartermaster", "opens the armory ledger and confirms stock: " + payload);
            case GuildTopics.HEALING ->
                    say("Quartermaster", "sets aside bandages and potion crates: " + payload);
            default ->
                    say("Quartermaster", "logs an uncategorized request from " + sender + ": " + payload);
        }
    }
}
