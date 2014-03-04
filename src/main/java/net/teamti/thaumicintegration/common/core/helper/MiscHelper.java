package net.teamti.thaumicintegration.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.codechicken.core.vec.Vector3;

public final class MiscHelper
{

	public static double pointDistanceSpace(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
	}

	public static MinecraftServer server()
	{
		return MinecraftServer.getServer();
	}

	public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier)
	{
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

		if (finalVector.mag() > 1)
			finalVector.normalize();

		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

	public static AspectList multiplyAspectList(AspectList list, double multiplier)
	{
		AspectList newList = list.copy();
		if (multiplier == 1)
			return newList;

		for (Aspect aspect : newList.aspects.keySet())
			newList.aspects.put(aspect, (int) ((double) newList.aspects.get(aspect) * multiplier));

		return newList;
	}

	public static void printCurrentStackTrace(String message)
	{
		if (message != null)
			System.out.println(message);

		for (StackTraceElement element : Thread.currentThread().getStackTrace())
			System.out.println(element);
	}

}
