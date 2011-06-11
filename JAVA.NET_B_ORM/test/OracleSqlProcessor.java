/** 
 * oracle page sql Processor 
 */  
public class OracleSqlProcessor implements SqlProcessor {  
    public String pageSqlProcess(String sql) {  
        StringBuilder sBuilder = new StringBuilder();  
        sBuilder.append("select * from ( select a.*, rownum row_num from (").append(sql).append(") a where rownum <=  ? ) WHERE row_num > ? ");  
        return sBuilder.toString();  
    }  
}  