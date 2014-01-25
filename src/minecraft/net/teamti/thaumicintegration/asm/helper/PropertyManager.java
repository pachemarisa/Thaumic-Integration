package net.teamti.thaumicintegration.asm.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.teamti.thaumicintegration.asm.TICorePlugin;

public class PropertyManager
{

	private PropertyManager()
	{
	} // Singleton

	public static final String propFileName = "TIPreloader.cfg";

	public static boolean getOrCreateProps() throws PropAccessException
	{
		File fp = new File("config/" + propFileName);
		Properties props = new Properties();
		if (fp.exists())
		{
			// Attempt read
			try
			{
				TICorePlugin.logger.info("Found a properties file. Attempting load...");
				props.load(new FileInputStream(fp));

				TICorePlugin.logger.info("Loaded properties successfully. Using specified settings.");
			}
			catch (IOException ex)
			{
				throw new PropAccessException();
			}
		}
		else
		{
			// Attempt creation
			try
			{
				if (fp.createNewFile())
				{
					TICorePlugin.logger.info("Creating new properties file, as none found...");

					props.store(new FileOutputStream(fp), null);
					TICorePlugin.logger.info("Created properties file; using defaults this run.");
				}
				else
				{
					throw new PropAccessException();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				throw new PropAccessException();
			}
		}

		return false;
	}

	public static class PropAccessException extends Exception
	{
	}

}
