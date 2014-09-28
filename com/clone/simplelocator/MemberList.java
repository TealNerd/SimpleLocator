package com.clone.simplelocator;

import java.util.List;

public class MemberList {
	private List members;

	public MemberList(List members) {
		this.members = members;
	}

	public void add(String name) {
		this.members.add(name);
	}

	public String get(int i) {
		return (String)this.members.get(i);
	}

	public int countGroups() {
		return this.members.size();
	}

	public void swapGroups(int i1, int i2) {
		String s1 = (String) this.members.get(i1);
		String s2 = (String) this.members.get(i2);

		this.members.set(i1, s2);
		this.members.set(i2, s1);
	}

	public void remove(int i) {
		this.members.remove(i);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.MemberList
 * 
 * JD-Core Version: 0.7.0.1
 */