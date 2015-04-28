package com.acme.oms.server.jetty;

import static java.lang.String.format;

/**
 * ATM Xtend can't deal with complex enums.
 */
enum Command {
    start("Start the server (default)", new Runnable() {
        @Override
        public void run() {
            OrderEmbeddedServer.start();
        }
    }), 
    stop("Stop the server gracefully", new Runnable() {
        @Override
        public void run() {
            OrderEmbeddedServer.stop();
        }
    }), 
    usage("Display this manual", new Runnable() {
        @Override
        public void run() {
            OrderEmbeddedServer.usage();
        }
    });
    private final String msg;
    private final Runnable command;
    private Command(String msg, Runnable command) {
        this.msg = msg;
        this.command = command;
    }
    public String getMessage() {
        return format("- %-9s %s", name(), msg);
    }
    public Runnable getCommand() {
        return command;
    }
    public static Command find(String name) {
        for(Command c:values()) {
            if (c.name().equals(name)) return c;
        }
        return null;
    }
    public void run() {
        command.run();
    }
}