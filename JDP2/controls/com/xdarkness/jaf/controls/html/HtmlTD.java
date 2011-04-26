// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlTD.java

package com.xdarkness.jaf.controls.html;

import java.util.regex.Matcher;

//            HtmlElement, HtmlTable, HtmlTR

public class HtmlTD extends HtmlElement
{

    public HtmlTD()
    {
        this(null);
    }

    public HtmlTD(HtmlTR parent)
    {
        this.parent = parent;
        ElementType = "TD";
        TagName = "td";
    }

    public void setWidth(int width)
    {
        Attributes.put("width", new Integer(width));
    }

    public int getWidth()
    {
        return ((Integer)Attributes.get("width")).intValue();
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

    public String getBgColor()
    {
        return (String)Attributes.get("bgColor");
    }

    public void setBackgroud(String backgroud)
    {
        Attributes.put("backgroud", backgroud);
    }

    public String getBackgroud()
    {
        return (String)Attributes.get("backgroud");
    }

    public String getVAlign()
    {
        return (String)Attributes.get("vAlign");
    }

    public void setVAlign(String vAlign)
    {
        Attributes.put("vAlign", vAlign);
    }

    public void setColSpan(String colSpan)
    {
        setAttribute("colSpan", colSpan);
    }

    public String getColSpan()
    {
        return getAttribute("colSpan");
    }

    public void setRowSpan(String rowSpan)
    {
        setAttribute("rowSpan", rowSpan);
    }

    public String getRowSpan()
    {
        return getAttribute("rowSpan");
    }

    public int getCellIndex()
    {
        for(int i = 0; i < ParentElement.Children.size(); i++)
            if(ParentElement.Children.get(i).equals(this))
                return i;

        throw new RuntimeException("\u5F97\u5230RowIndex\u65F6\u53D1\u751F\u9519\u8BEF");
    }

    public void parseHtml(String html)
        throws Exception
    {
        Matcher m = HtmlTable.PTD.matcher(html);
        if(!m.find())
            throw new Exception(TagName + "\u89E3\u6790html\u65F6\u53D1\u751F\u9519\u8BEF");
        TagName = m.group(1);
        String attrs = m.group(OperateType.UPDATE);
        Attributes.clear();
        Children.clear();
        Attributes = parseAttr(attrs);
        InnerHTML = m.group(3).trim();
        if(parent != null)
        {
            String newHtml = parent.restoreInnerTable(InnerHTML);
            if(newHtml.equals(InnerHTML))
            {
                if(parent.getParent() != null)
                    setInnerHTML(parent.getParent().restoreInnerTable(InnerHTML));
            } else
            {
                setInnerHTML(newHtml);
            }
        }
    }

    public HtmlTR getParent()
    {
        return parent;
    }

    public boolean isHead()
    {
        return TagName.equalsIgnoreCase("th");
    }

    public void setHead(boolean isHead)
    {
        if(isHead)
            TagName = "th";
        else
            TagName = "tr";
    }

    private HtmlTR parent;
}
