package com.abigdreamer.java.net.ssi;

import java.io.PrintWriter;

public abstract interface SSICommand
{
  public abstract long process(SSIMediator paramSSIMediator, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, PrintWriter paramPrintWriter)
    throws SSIStopProcessingException;
}

          
/*    com.xdarkness.framework.ssi.SSICommand
 * JD-Core Version:    0.6.0
 */