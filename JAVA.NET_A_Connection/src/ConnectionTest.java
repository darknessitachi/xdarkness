

import java.sql.SQLException;

import org.junit.Test;

import com.abigdreamer.java.net.connection.XConnection;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;

public class ConnectionTest {

	@Test
	public void getConnection() throws SQLException {
		for (int i = 0; i < 10000; i++) {
			XConnection conn = XConnectionPoolManager.getConnection();
			System.out.println(i + "===" + conn);
			conn.close();
		}
	}

}
