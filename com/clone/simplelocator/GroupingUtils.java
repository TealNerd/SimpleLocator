package com.clone.simplelocator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class GroupingUtils {
	public static List<LocationGroup> getGroups(EntityPlayer player,
			Map<String, LocatorLocation> locations) {
		ArrayList<LocationGroup> groups = new ArrayList();

		Iterator<String> iterator = locations.keySet().iterator();
		while (iterator.hasNext()) {
			String username = (String) iterator.next();
			LocatorLocation location;
			synchronized (locations) {
				location = (LocatorLocation) locations.get(username);
			}
			if (LocatorListener.renderLocation(username, location)) {
				LocationGroup closestGroup = getClosestGroup(player, location,
						groups);
				if (closestGroup != null) {
					float dist = angularDistanceToGroup(player, location,
							closestGroup);
					if (dist < 0.1570796326794897D) {
						closestGroup.add(username);

						continue;
					}
				}
				LocationGroup group = new LocationGroup();

				group.add(username);
				group.calculateCenter();
				group.calculateYawFromPlayer(player);
				groups.add(group);
			}
		}
		Iterator<LocationGroup> groupIterator = groups.iterator();
		while (groupIterator.hasNext()) {
			LocationGroup group = (LocationGroup) groupIterator.next();

			LocationGroup closestGroup = getClosestGroup(player, group, groups);
			if (closestGroup != null) {
				float dist = angularDistanceToGroup(player, group, closestGroup);
				if (dist < 0.1570796326794897D) {
					groupIterator.remove();

					closestGroup.addAll(group);
					closestGroup.calculateCenter();
					closestGroup.calculateYawFromPlayer(player);

					continue;
				}
			}
			if (group.size() <= 1) {
				groupIterator.remove();
			} else {
				group.calculateCenter();
				group.calculateYawFromPlayer(player);
			}
		}
		return groups;
	}

	private static LocationGroup getClosestGroup(EntityPlayer player,
			ILocation location, List<LocationGroup> groups) {
		LocationGroup closest = null;
		float closestDistance = 3.4028235E+38F;
		for (LocationGroup group : groups) {
			if ((!(location instanceof LocationGroup))
					|| (!location.equals(group))) {
				float currentDistance = angularDistanceToGroup(player,
						location, group);
				if (currentDistance < closestDistance) {
					closest = group;
					closestDistance = currentDistance;
				}
			}
		}
		return closest;
	}

	private static float angularDistanceToGroup(EntityPlayer entity,
			ILocation location, LocationGroup group) {
		double xDist = location.getX() - entity.posX;
		double zDist = location.getZ() - entity.posZ;
		double xzDist = Math.sqrt(xDist * xDist + zDist * zDist);

		float yaw = (float) Math.atan2(zDist, xDist);
		float pitch = (float) Math.atan2(location.getY() - entity.posY, xzDist);

		float yawDist = group.getYawFromPlayer() - yaw;
		float pitchDist = group.getPitchFromPlayer() - pitch;
		float dist = (float) Math.sqrt(yawDist * yawDist + pitchDist
				* pitchDist);

		return dist;
	}

	private static double distanceToLocation(Entity entity, ILocation location) {
		double relX = entity.posX - location.getX();
		double relY = entity.posY - location.getY();
		double relZ = entity.posZ - location.getZ();

		return Math.sqrt(relX * relX + relY * relY + relZ * relZ);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.GroupingUtils
 * 
 * JD-Core Version: 0.7.0.1
 */