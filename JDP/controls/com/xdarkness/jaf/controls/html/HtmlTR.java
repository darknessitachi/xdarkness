// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlTR.java

package com.xdarkness.jaf.controls.html;

import java.util.ArrayList;
import java.util.regex.Matcher;

import com.xdarkness.common.util.StringUtil;

//            HtmlElement, HtmlTD, HtmlTable

public class HtmlTR extends HtmlElement
{

    public HtmlTR()
    {
        this(null);
    }

    public HtmlTR(HtmlTable parent)
    {
        pList = null;
        this.parent = parent;
        ElementType = "TR";
        TagName = "tr";
    }

    public void addTD(HtmlTD td)
    {
        addChild(td);
    }

    public HtmlTD getTD(int index)
    {
        return (HtmlTD)Children.get(index);
    }

    public void removeTD(int index)
    {
        if(index < 0 || index > Children.size())
        {
            throw new RuntimeException("\u9519\u8BEF\u7684\u7D22\u5F15");
        } else
        {
            Children.remove(index);
            return;
        }
    }

    public void setHeight(int height)
    {
        Attributes.put("height", new Integer(height));
    }

    public int getHeight()
    {
        return ((Integer)Attributes.get("height")).intValue();
    }

    public void setAlign(String align)
    {
        Attributes.put("align", align);
    }

    public String getAlign()
    {
        return (String)Attributes.get("align");
    }

    public void setBgColor(String bgColor)
    {
        Attributes.put("bgColor", bgColor);
    }

    public String getVAlign()
    {
        return (String)Attributes.get("vAlign");
    }

    public void setVAlign(String vAlign)
    {
        Attributes.put("vAlign", vAlign);
    }

    public int getRowIndex()
    {
        for(int i = 0; i < ParentElement.Children.size(); i++)
            if(ParentElement.Children.get(i).equals(this))
                return i;

        throw new RuntimeException("\u5F97\u5230RowIndex\u65F6\u53D1\u751F\u9519\u8BEF");
    }

    public void parseHtml(String html)
        throws Exception
    {
        Matcher m = HtmlTable.PTR.matcher(html);
        if(!m.find())
            throw new Exception("TR\u89E3\u6790html\u65F6\u53D1\u751F\u9519\u8BEF");
        String attrs = m.group(1);
        String tds = m.group(2).trim();
        Attributes.clear();
        Children.clear();
        m = HtmlTable.PInnerTable.matcher(tds);
        for(int lastEndIndex = 0; m.find(lastEndIndex); lastEndIndex = m.end())
        {
            if(pList == null)
                pList = new ArrayList();
            pList.add(m.group(0));
        }

        if(pList != null)
        {
            for(int i = 0; i < pList.size(); i++)
                tds = StringUtil.replaceEx(tds, pList.get(i).toString(), "<!--_SKY_INNERTABLE_PROTECTED_" + i + "-->");

        }
        Attributes = parseAttr(attrs);
        m = HtmlTable.PTDPre.matcher(tds);
        for(int lastEndIndex = 0; m.find(lastEndIndex); lastEndIndex = m.end())
        {
            String t = tds.substring(m.start(), m.end());
            HtmlTD td = new HtmlTD(this);
            td.parseHtml(t);
            addTD(td);
        }

    }

    public String restoreInnerTable(String html)
    {
        if(pList == null || pList.size() == 0)
            return html;
        String arr[] = StringUtil.splitEx(html, "<!--_SKY_INNERTABLE_PROTECTED_");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < arr.length; i++)
            if(StringUtil.isNotEmpty(arr[i]))
            {
                if(i != 0)
                {
                    int index = Integer.parseInt(arr[i].substring(0, arr[i].indexOf("-")));
                    sb.append(pList.get(index).toString());
                    arr[i] = arr[i].substring(arr[i].indexOf(">") + 1);
                }
                sb.append(arr[i]);
            }

        return sb.toString();
    }

    public HtmlTable getParent()
    {
        return parent;
    }

    private HtmlTable parent;
    private ArrayList pList;
}
