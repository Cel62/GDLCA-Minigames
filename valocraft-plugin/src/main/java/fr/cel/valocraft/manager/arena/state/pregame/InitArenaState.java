package fr.cel.valocraft.manager.arena.state.pregame;

import fr.cel.valocraft.ValoCraft;
import fr.cel.valocraft.listener.state.StateListenerProvider;
import fr.cel.valocraft.manager.arena.Arena;
import fr.cel.valocraft.manager.arena.state.ArenaState;

public class InitArenaState extends ArenaState {

    public InitArenaState(Arena arena) {
        super(arena);
    }

    @Override
    public void onEnable(ValoCraft main) {
        super.onEnable(main);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public StateListenerProvider getListenerProvider() {
        return null;
    }
    
}