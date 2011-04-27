 package com.zving.cmcore.tag;
 
 import com.zving.cmcore.template.TemplateContext;
 import java.util.ArrayList;
 
 public class LogicClause
 {
   public boolean isNot;
   public boolean isOr;
   public String Clause;
 
   public boolean isNot()
   {
     return this.isNot;
   }
 
   public void setNot(boolean isNot) {
     this.isNot = isNot;
   }
 
   public boolean isOr() {
     return this.isOr;
   }
 
   public void setOr(boolean isOr) {
     this.isOr = isOr;
   }
 
   public String getClauseString() {
     return this.Clause;
   }
 
   public void setClauseString(String clause) {
     this.Clause = clause;
   }
 
   public boolean execute(TemplateContext context) {
     ArrayList list = new ArrayList();
     parse(this.Clause, list);
 
     return false;
   }
 
   public boolean parse(String str, ArrayList list) {
     char[] cs = this.Clause.toCharArray();
     boolean isStringBegin = false;
     int varStart = 0;
     for (int i = 0; i < cs.length; ++i) {
       char c = cs[i];
       if (c == '"') {
         if (i == 0)
           isStringBegin = true;
         else if (c != '\\') {
           if (isStringBegin)
             isStringBegin = false;
           else {
             isStringBegin = true;
           }
         }
       }
       if (isStringBegin) {
         continue;
       }
       if ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '%') || (c == '>') || (c == '<') || (c == '!') || 
         (c == '=')) {
         list.add("VAR:" + this.Clause.substring(varStart, i));
         String operator = this.Clause.substring(i, i + 1);
         if ((((c == '>') || (c == '<') || (c == '!') || (c == '='))) && 
           (c != cs.length - 1) && (cs[(i + 1)] == '=')) {
           operator = this.Clause.substring(i, i + 2);
           ++i;
         }
 
         list.add("OP:" + operator);
       }
       if (c == '.') {
         ArrayList args = new ArrayList();
         for (int j = this.Clause.indexOf("(", i); j < cs.length; ++j) {
           c = cs[j];
           if ((c == '"') && 
             (c != '\\')) {
             if (isStringBegin)
               isStringBegin = false;
             else {
               isStringBegin = true;
             }
           }
 
           if (!isStringBegin) {
             continue;
           }
         }
         list.add("FUNC:" + this.Clause.substring(i + 1, this.Clause.indexOf("(")));
         list.add(args);
       }
     }
     return false;
   }
 
   public String toString() {
     return ((this.isNot) ? "!" : "") + this.Clause + ((this.isOr) ? "[OR]" : "[AND]");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.tag.LogicClause
 * JD-Core Version:    0.5.4
 */