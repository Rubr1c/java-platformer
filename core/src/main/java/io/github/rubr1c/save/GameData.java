package io.github.rubr1c.save;

import java.util.HashSet;
import java.util.Set;

public class GameData {
    private Set<Integer> unlockedLevels;

    public GameData() {
        unlockedLevels = new HashSet<>();
        unlockedLevels.add(1);
    }

    public Set<Integer> getUnlockedLevels() {
        return unlockedLevels;
    }

    public void setUnlockedLevels(Set<Integer> unlockedLevels) {
        this.unlockedLevels = unlockedLevels;
    }

    public void unlockLevel(int level) {
        unlockedLevels.add(level);
    }

    public boolean isLevelUnlocked(int level) {
        return unlockedLevels.contains(level);
    }
}
