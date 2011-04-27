package com.abigdreamer.java.net.ssi;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public interface SSIExternalResolver {
	 void addVariableNames(Collection paramCollection);

	 String getVariableValue(String paramString);

	 void setVariableValue(String paramString1,
			String paramString2);

	 Date getCurrentDate();

	 long getFileSize(String paramString, boolean paramBoolean)
			throws IOException;

	 long getFileLastModified(String paramString,
			boolean paramBoolean) throws IOException;

	 String getFileText(String paramString, boolean paramBoolean)
			throws IOException;

	 void log(String paramString, Throwable paramThrowable);
}
