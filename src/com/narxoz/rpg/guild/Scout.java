package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for route reports and reconnaissance.
 */
public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        say("Scout", "reports field intelligence on '" + topic + "': " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = sourceName(from);
        switch (topic) {
            case GuildTopics.ORDERS ->
                    say("Scout", "marks a route plan after " + sender + "'s order: " + payload);
            case GuildTopics.SCOUTING ->
                    say("Scout", "cross-checks patrol notes and hazard markers: " + payload);
            case GuildTopics.LORE ->
                    say("Scout", "matches legends to the terrain before departure: " + payload);
            default ->
                    say("Scout", "stores a side report from " + sender + ": " + payload);
        }
    }
}
