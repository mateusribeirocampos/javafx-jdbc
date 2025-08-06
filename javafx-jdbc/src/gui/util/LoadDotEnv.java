package gui.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import db.DbException;

public class LoadDotEnv {
	
	public static Properties loadEnvProperties() {
		try (FileInputStream fs = new FileInputStream(".env")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

}
