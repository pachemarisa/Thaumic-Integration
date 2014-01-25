package net.teamti.thaumicintegration.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

import java.io.IOException;
import java.util.Iterator;

public class TITransformer implements IClassTransformer
{

	private static final String TC_CONTENT = "tconstruct.common.TContent";

	private static final String TC_MODAUTOSMELT = "tconstruct.modifiers.tools.ModAutoSmelt";

	private static final String TC_MODBUTTERTOUCH = "tconstruct.modifiers.tools.ModButtertouch";

	private static final String TI_MODAUTOSMELT = "net.teamti.thaumicintegration.asm.helper.ModAutoSmeltTI";

	private static final String TI_MODBUTTERTOUCH = "net.teamti.thaumicintegration.asm.helper.ModButtertouchTI";

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{

		if (name.equals(TC_CONTENT))
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			this.replaceInstance(classNode, TC_MODAUTOSMELT, TI_MODAUTOSMELT);

			this.replaceInstance(classNode, TC_MODBUTTERTOUCH, TI_MODBUTTERTOUCH);

			ClassWriter cw = new ClassWriter(0);
			classNode.accept(cw);

			return cw.toByteArray();
		}
		return bytes;
	}

	public void replaceInstance(ClassNode classNode, String oldInstance, String newInstance)
	{
		TICorePlugin.logger.info("class: " + classNode.name);
		TICorePlugin.logger.info("replacing " + oldInstance + " with " + newInstance);

		oldInstance = oldInstance.replace(".", "/");
		newInstance = newInstance.replace(".", "/");

		for (MethodNode methodNode : classNode.methods)
		{
			Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
			TypeInsnNode previousTypeInsnNode = null;

			while (iter.hasNext())
			{
				AbstractInsnNode node = iter.next();

				if (node instanceof TypeInsnNode)
				{
					TypeInsnNode tn = (TypeInsnNode) node;

					if (tn.desc.equals(oldInstance))
					{
						previousTypeInsnNode = tn;
					}
				}

				if (node instanceof MethodInsnNode)
				{
					MethodInsnNode mn = (MethodInsnNode) node;
					if (mn.owner.equals(oldInstance) && mn.name.equals("<init>"))
					{
						mn.owner = newInstance;
						previousTypeInsnNode.desc = newInstance;

						TICorePlugin.logger.info("found injection point: method: " + methodNode.name + " typeinsn: " + methodNode.instructions.indexOf(previousTypeInsnNode) + " methodinsn: " + methodNode.instructions.indexOf(mn));
					}
				}
			}
		}
	}

}
