 package com.abigdreamer.java.net.jaf.extend;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
  
 public abstract class AfterMainFilterAction
   implements IExtendAction
 {
   public static final String Type = "AfterMainFilter";
 
   public String getTarget()
   {
     return "AfterMainFilter";
   }
 
   public void execute(Object[] args)
   {
     HttpServletRequest request = (HttpServletRequest)args[0];
     HttpServletResponse response = (HttpServletResponse)args[1];
     FilterChain chain = (FilterChain)args[2];
     execute(request, response, chain);
   }
 
   public abstract void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, FilterChain paramFilterChain);
 }

          
/*    com.xdarkness.framework.extend.AfterMainFilterAction
 * JD-Core Version:    0.6.0
 */