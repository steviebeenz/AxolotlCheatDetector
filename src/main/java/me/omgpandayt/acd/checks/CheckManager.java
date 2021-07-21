package me.omgpandayt.acd.checks;

import java.util.LinkedList;

public class CheckManager {

    private static final LinkedList<Check> checks = new LinkedList<>();

    private CheckManager() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static void registerCheck(Check check) {
        if (checks.contains(check)) return;
        checks.add(check);
    }

    public static Object[] getRegisteredChecks() {
        return checks.toArray();
    }

}
