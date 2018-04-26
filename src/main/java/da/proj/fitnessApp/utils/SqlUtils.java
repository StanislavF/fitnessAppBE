package da.proj.fitnessApp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;

public class SqlUtils {
	
	public static String getClasspathResource(String sqlPath,
			String sqlFileName) {
		try (InputStream is = new DefaultResourceLoader().getResource(
				sqlPath + System.getProperty("file.separator") + sqlFileName)
				.getInputStream()) {
			return readFully(is, "UTF8");
		} catch (IOException e1) {
			throw new RuntimeException("Failed to read resource. "
					+ sqlFileName, e1);
		}
	}

	private static String readFully(InputStream inputStream, String encoding)
			throws IOException {
		return new String(readFully(inputStream), encoding);
	}

	private static byte[] readFully(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toByteArray();
	}

}
