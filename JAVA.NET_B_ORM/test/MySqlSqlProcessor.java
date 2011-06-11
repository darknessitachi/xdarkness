public class MySqlSqlProcessor implements SqlProcessor {  
    
    // select * from Member limit 1000, 100 
    public String pageSqlProcess(String sql) { 
        StringBuilder sBuilder = new StringBuilder(); 
        //sBuilder.append("select * from (").append(sql).append(" ) limit ? , ?");影响了执行效率 
        sBuilder.append(sql).append(" limit ? , ?");//更简单，效率更高 

        return sBuilder.toString(); 
    } 
}  