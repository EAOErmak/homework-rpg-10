package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for orders and mission coordination.
 */
public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        say("Captain", "issues a council order on '" + topic + "': " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = sourceName(from);
        switch (topic) {
            case GuildTopics.ORDERS ->
                    say("Captain", "turns the council directive into marching orders: " + payload);
            case GuildTopics.SCOUTING ->
                    say("Captain", "adjusts formation using the latest route report: " + payload);
            case GuildTopics.SUPPLIES ->
                    say("Captain", "approves the logistics request and budget: " + payload);
            case GuildTopics.HEALING ->
                    say("Captain", "slows the advance plan to protect wounded heroes: " + payload);
            case GuildTopics.LORE ->
                    say("Captain", "adds the lore warning to the mission briefing: " + payload);
            default ->
                    say("Captain", "records a miscellaneous report from " + sender + ": " + payload);
        }
    }
}
