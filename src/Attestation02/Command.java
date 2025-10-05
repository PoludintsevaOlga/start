package Attestation02;

import java.util.List;

@FunctionalInterface
public interface Command { void execute(GameState ctx, List<String> args); }