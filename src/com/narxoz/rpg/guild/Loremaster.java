package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for legends, curses, and historical records.
 */
public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void shareLore(String topic, String payload) {
        say("Loremaster", "shares archival guidance on '" + topic + "': " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = sourceName(from);
        switch (topic) {
            case GuildTopics.ORDERS ->
                    say("Loremaster", "pulls records that support " + sender + "'s mission order: " + payload);
            case GuildTopics.SCOUTING ->
                    say("Loremaster", "compares the scout report with old maps and songs: " + payload);
            case GuildTopics.LORE ->
                    say("Loremaster", "interprets omens, curses, and forgotten names: " + payload);
            default ->
                    say("Loremaster", "files an unusual question from " + sender + ": " + payload);
        }
    }
}
