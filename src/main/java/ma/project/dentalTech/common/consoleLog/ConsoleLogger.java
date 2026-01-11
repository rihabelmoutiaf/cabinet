package ma.project.dentalTech.common.consoleLog;

public final class ConsoleLogger {

    private ConsoleLogger() {
    }

    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
