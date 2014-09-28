package com.clone.simplelocator;

import java.util.ArrayList;

public class GroupList extends ArrayList<GroupConfiguration> {
	public int countGroups() {
		return size();
	}

	public void swapGroups(int i1, int i2) {
		GroupConfiguration g1 = (GroupConfiguration) get(i1);
		GroupConfiguration g2 = (GroupConfiguration) get(i2);

		set(i1, g2);
		set(i2, g1);
	}

	public GroupConfiguration getByUsername(String username) {
		for (GroupConfiguration group : this) {
			if (group.getUsernames().contains(username)) {
				return group;
			}
		}
		return null;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.GroupList
 * 
 * JD-Core Version: 0.7.0.1
 */