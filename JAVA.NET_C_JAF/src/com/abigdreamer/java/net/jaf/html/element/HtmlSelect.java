package com.abigdreamer.java.net.jaf.html.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Darkness 
 * create on 2011 2011-1-7 下午04:11:26
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class HtmlSelect extends HtmlElement {
	public HtmlSelect() {
		this.ElementType = "SELECT";
		this.TagName = "select";
	}
    
    public void parseHtml(String html) throws Exception {
    	html = html.replaceAll("\t", "").replaceAll("\n", "").trim();
		Pattern pattern = super.getParrtenByTagName();
		Matcher m = pattern.matcher(html);
		if (!(m.find())) {
			throw new Exception(this.TagName + "解析html时发生错误");
		}
		String attrs = m.group(1);

		this.Attributes.clear();
		this.Children.clear();

		this.Attributes = parseAttr(attrs);

		this.InnerHTML = m.group(2).trim();
		
		
		
		pattern = Pattern.compile("<option\\s+?(\\\"|\\'?)value\\1\\s*?=(\\\"|\\'?)(.*?)\\2>(.*?)</option>", Pattern.CASE_INSENSITIVE);
		int lastEndIndex = 0;
		Matcher matcher = pattern.matcher(this.InnerHTML);
		while (matcher.find(lastEndIndex)) {
			String optionString = this.InnerHTML.substring(matcher
					.start(), matcher.end());
			
			Option option = new Option();
			
//			for (int i = 0; i < matcher.groupCount()+1; i++) {
//				System.out.println("" + i + "==>" + matcher.group(i));
//			}
			option.setAttribute("value", matcher.group(3).replaceAll("\"", "").replace("selected", "").replace("=", "").trim());
			option.InnerHTML = matcher.group(4);

			boolean selected = optionString.toLowerCase().indexOf("selected") != -1;
			if(selected) {
				this.setSelectedOption(option);
			}
			this.Children.add(option);
			lastEndIndex = matcher.end();
		}
		
		if(this.getSelectedOption() == null && this.Children.size() > 0) {
			this.setSelectedOption((Option)this.Children.get(0));
		}

	}
    
    private HtmlSelect.Option selectedOption;
    
    public class Option extends HtmlElement {
    	public Option() {
    		ElementType = "OPTION";
    		TagName = "option";
    	}
    }

	public HtmlSelect.Option getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(HtmlSelect.Option selectedOption) {
		this.selectedOption = selectedOption;
	}
}
