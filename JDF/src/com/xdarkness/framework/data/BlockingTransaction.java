package com.xdarkness.framework.data;

import java.sql.SQLException;

import com.xdarkness.framework.connection.XConnection;
import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.sql.DataAccess;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Executor;

public class BlockingTransaction extends Transaction {
	private boolean isExistsOpeningOperate = false;

	private static ThreadLocal current = new ThreadLocal();
	private XConnection conn;

	public BlockingTransaction() {
		if (this.dataAccess == null) {
			this.conn = XConnectionPoolManager.getConnection();
			this.conn.isBlockingTransactionStarted = true;
			this.dataAccess = new DataAccess(this.conn);
			try {
				this.dataAccess.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			bindTransactionToThread();
		}
	}

	public BlockingTransaction(DataAccess da) {
		this.dataAccess = da;
		this.conn = this.dataAccess.getConnection();
		this.conn.isBlockingTransactionStarted = true;
		try {
			this.dataAccess.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bindTransactionToThread();
	}

	public void setDataAccess(DataAccess dAccess) {
		if ((this.dataAccess != null) && (!this.outerConnFlag)) {
			throw new RuntimeException(
					"BlockingTransaction.setDataAccess():请检查代码，阻塞型事务只能在事务开始之前设置DataAccess");
		}
		super.setDataAccess(dAccess);
	}

	public void add(QueryBuilder qb) {
		executeWithBlockedConnection(qb, 7);
	}

	public void add(Schema schema, int type) {
		executeWithBlockedConnection(schema, type);
	}

	public void add(SchemaSet set, int type) {
		executeWithBlockedConnection(set, type);
	}

	public void addWithException(QueryBuilder qb) throws Exception {
		executeWithBlockedConnection(qb, 7);
	}

	public void addWithException(Schema schema, int type) throws Exception {
		executeWithException(schema, type);
	}

	public void addWithException(SchemaSet set, int type) throws Exception {
		executeWithException(set, type);
	}

	private void executeWithBlockedConnection(Object obj, int type) {
		try {
			executeObject(obj, type);
			this.isExistsOpeningOperate = true;
		} catch (SQLException e) {
			if (!this.outerConnFlag) {
				try {
					this.dataAccess.rollback();
					this.conn.isBlockingTransactionStarted = false;
				} catch (SQLException e1) {
					e1.printStackTrace();
					try {
						this.dataAccess.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				} finally {
					try {
						this.dataAccess.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			throw new RuntimeException(e);
		}
	}

	private void executeWithException(Object obj, int type) throws Exception {
		executeObject(obj, type);
		this.isExistsOpeningOperate = true;
	}

	public boolean commit() {
		return commit(true);
	}

	public boolean commit(boolean setAutoCommitStatus) {
		if (this.dataAccess != null) {
			try {
				this.dataAccess.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				this.exceptionMessage = e.getMessage();
				if (!this.outerConnFlag) {
					try {
						this.dataAccess.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				return false;
			} finally {
				try {
					if ((!this.outerConnFlag) || (setAutoCommitStatus))
						this.dataAccess.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (!this.outerConnFlag) {
					try {
						this.conn.isBlockingTransactionStarted = false;
						this.dataAccess.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				this.isExistsOpeningOperate = false;
				current.set(null);
			}
			for (int i = 0; i < this.executorList.size(); i++) {
				Executor executor = (Executor) this.executorList.get(i);
				executor.execute();
			}
		}
		return true;
	}

	public void rollback() {
		if (this.dataAccess != null) {
			try {
				this.dataAccess.rollback();
				this.isExistsOpeningOperate = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				this.conn.isBlockingTransactionStarted = false;
				this.dataAccess.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			current.set(null);
		}
	}

	private void bindTransactionToThread() {
		Object obj = current.get();
		if (obj == null)
			current.set(this);
		else
			throw new RuntimeException("同一线程中只能有一个阻塞型事务!");
	}

	public static void clearTransactionBinding() {
		Object obj = current.get();
		if (obj == null) {
			return;
		}
		BlockingTransaction bt = (BlockingTransaction) obj;
		if (bt.isExistsOpeningOperate) {
			bt.rollback();
		}
		current.set(null);
	}

	public static XConnection getCurrentThreadConnection() {
		Object obj = current.get();
		if (obj == null) {
			return null;
		}
		BlockingTransaction bt = (BlockingTransaction) obj;
		if ((bt.dataAccess == null) || (bt.dataAccess.getConnection() == null)) {
			return null;
		}
		return bt.dataAccess.getConnection();
	}
}
