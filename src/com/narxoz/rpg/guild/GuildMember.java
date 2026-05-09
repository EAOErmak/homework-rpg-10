package com.narxoz.rpg.guild;

/**
 * Base class for all guild officers that communicate through a mediator.
 */
public abstract class GuildMember {

    private final String name;
    private final GuildMediator mediator;

    protected GuildMember(String name, GuildMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    public String getName() {
        return name;
    }

    protected GuildMediator getMediator() {
        return mediator;
    }

    protected String sourceName(GuildMember from) {
        return from == null ? "CouncilEngine" : from.getName();
    }

    protected void say(String role, String message) {
        System.out.printf("[%s:%s] %s%n", role, name, message);
    }

    public abstract void receive(String topic, GuildMember from, String payload);
}
