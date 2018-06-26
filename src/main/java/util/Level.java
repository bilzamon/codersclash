package util;

public class Level {
	public static int calcLevel(long totalXp) {
		int level = 0;
		boolean complete = true;

		while (complete) {
			long xp = calcLevelToTotalXp(level);
			if (totalXp < xp) {
				complete = false;
			} else {
				level++;
			}
		}
		return level;
	}

	private static long calcLevelToTotalXp(int userLevel) {
		long sumXp = 0;
		for (int level = 0; level <= userLevel; level++) {
			sumXp += xpToLevelUp(level);
		}
		return sumXp;
	}

	private static int xpToLevelUp(int level) {
		return 10 * level + 10;
	}
}
