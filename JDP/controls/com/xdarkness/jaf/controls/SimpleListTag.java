// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleListTag.java

package com.xdarkness.jaf.controls;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.xdarkness.common.data.DataColumn;
import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.ServletUtil;

public class SimpleListTag extends BodyTagSupport
{

    public SimpleListTag()
    {
    }

    public DataRow getDataRow()
    {
        return dr;
    }

    public void transferDataRow(DataRow dr)
    {
        this.dr = dr;
    }

    public int doStartTag()
        throws JspException
    {
        index = 0;
        dr = null;
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        Mapx params = ServletUtil.getParameterMap(request);
        javax.servlet.jsp.tagext.Tag ptag = getParent();
        int i = method.lastIndexOf('.');
        String className = method.substring(0, i);
        method = method.substring(i + 1);
        Object aobj[];
        try
        {
            Class c = Class.forName(className);
            Method m = c.getMethod(method, new Class[] {
                Mapx.class, DataRow.class
            });
            Object o = null;
            if(ptag != null && (ptag instanceof SimpleListTag))
            {
                o = m.invoke(null, new Object[] {
                    params, ((SimpleListTag)ptag).getDataRow()
                });
            } else
            {
                aobj = new Object[2];
                aobj[0] = params;
                o = m.invoke(null, aobj);
            }
            if(!(o instanceof DataTable))
                throw new RuntimeException("\u8C03\u7528z:simplelist\u6307\u5B9A\u7684method\u65F6\u53D1\u73B0\u8FD4\u56DE\u7C7B\u578B\u4E0D\u662FDataTable");
            dt = (DataTable)o;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(dt.getRowCount() > 0)
        {
            dt.insertColumn(new DataColumn("_RowNo", 8));
            transferDataRow(dt.getDataRow(index++));
            return 2;
        } else
        {
            return 0;
        }
    }

    public int doAfterBody()
        throws JspException
    {
        BodyContent body = getBodyContent();
        String content = body.getString().trim();
        try
        {
            dr.set("_RowNo", new Integer(index));
            getPreviousOut().write(HtmlUtil.replaceWithDataRow(dr, content));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        if(dt.getRowCount() > index)
        {
            transferDataRow(dt.getDataRow(index++));
            body.clearBody();
            return 2;
        } else
        {
            return 0;
        }
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    private static final long serialVersionUID = 1L;
    public String method;
    public DataTable dt;
    public int index;
    public DataRow dr;
}
