package com.xdarkness.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xdarkness.framework.ICallbackStatement;
import com.xdarkness.framework.sql.DataAccess;

public class DataAccessTest {

	private DataAccess dataAccess;

	@Before
	public void setUp() {
		dataAccess = new DataAccess();
	}

	@Test
	public void getConnection() {
		assertTrue(dataAccess.getConnection() != null);
	}

	public void setAutoCommit() throws SQLException {
		dataAccess.setAutoCommit(true);
	}

	public void commit() throws SQLException {
		dataAccess.commit();
	}

	public void rollback() throws SQLException {
		dataAccess.rollback();
	}

	public void close() throws SQLException {
		dataAccess.close();
	}

	@Test
	public void executeQuery() throws SQLException {
		assertTrue((Boolean) dataAccess.executeQuery("SELECT ID FROM TEST",
				new ICallbackStatement() {

					@Override
					public Object execute(XConnection connection,
							PreparedStatement stmt, ResultSet rs)
							throws SQLException {
						return rs.next();
					}
				}) == Boolean.TRUE);
	}

	/**
	 * # List<Integer> l1 = new
	 * ArrayList<Integer>(){{add(1);add(OperateType.UPDATE);add(3);add(4);}}; # # List l =
	 * l1.getClass().newInstance(); # System.out.println(l);
	 * 
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	@SuppressWarnings("serial")
	@Test
	public void executeBatch() throws SQLException {
		dataAccess.executeBatch("INSERT INTO Test(id) VALUES(?)",
				new ArrayList<ArrayList<Integer>>() {
					{
						for (int i = 0; i < 20; i++) {
							ArrayList<Integer> array = new ArrayList<Integer>();
							array.add(i + 1);
							add(array);
						}
					}
				});
	}

	@SuppressWarnings("serial")
	@Test
	public void executeUpdate() throws SQLException {
		dataAccess.executeNoQuery("UPDATE Test SET id=? WHERE id=?",
				new ArrayList<Integer>() {
					{
						add(5);
						add(50);
					}
				});
	}

	@SuppressWarnings("serial")
	@Test
	public void executeUpdateBatch() throws SQLException {
		dataAccess.executeNoQuery("UPDATE Test SET id=? WHERE id=?",
				new ArrayList<ArrayList<Integer>>() {
					{
						for (int i = 0; i < 20; i++) {
							ArrayList<Integer> array = new ArrayList<Integer>();
							array.add(i + 1);
							array.add(i + 2);
							add(array);
						}
					}
				}, true);
	}

	@Test
	public void executeDataTable()
			throws SQLException{
		dataAccess.executeDataTable("SELECT * FROM Test WHERE id%2=0", null);
	}

	@SuppressWarnings("serial")
	@Test
	public void executeOneValue() throws SQLException{
		assertTrue((Integer)dataAccess.executeOneValue("SELECT * FROM Test WHERE id=?", new ArrayList<Integer>(){
			{
				add(15);
			}
		})==15);
	}

}
