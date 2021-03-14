package net.tycooncraft.messagebuilder.utils.logging;

public class Logger {

    public static void console(String message) {
        System.out.println("[MessageBuilder] " + message);
    }

    public static void console(String message, Exception exception) {
        System.out.println("[MessageBuilder] " + message + " - Error message: " + exception.getMessage());
    }

}
