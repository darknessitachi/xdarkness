package com.abigdreamer.java.net.data;

import java.sql.SQLException;
import java.util.ArrayList;

import com.abigdreamer.java.net.OperateType;
import com.abigdreamer.java.net.orm.AbstractSchema;
import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.sql.IDataAccess;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Executor;

public class Transaction {
	private int bConnFlag;
	protected boolean outerConnFlag = false;
	protected IDataAccess dataAccess;
	protected ArrayList list = new ArrayList();
	protected String backupOperator;
	protected String backupMemo;
	protected String exceptionMessage;
	protected ArrayList executorList = new ArrayList(4);

	public Transaction() {
		bConnFlag = 0;
	}

	public void setDataAccess(IDataAccess dAccess) {
//		this.dataAccess = dAccess;
		this.outerConnFlag = true;
	}

	public void add(QueryBuilder qb) {
		// mapx.put(qb, OperateType.BACKUP);// TODO OR OperateType.SQL ???
		this.list.add(new Object[] { qb, OperateType.SQL });
	}

	public void add(Schema schema, OperateType type) {
		// mapx.put(schema, type);
		this.list.add(new Object[] { schema, type });
	}

	public void add(SchemaSet set, OperateType type) {
		// mapx.put(set, type);
		this.list.add(new Object[] { set, type });
	}

	public boolean commit() {
		return commit(true);
	}

	

	protected boolean executeObject(Object obj, int type) throws SQLException {
		if ((obj instanceof QueryBuilder)) {
			((QueryBuilder) obj).executeNoQuery();
		} else if ((obj instanceof Schema)) {
			Schema s = (Schema) obj;
			if (type == 1) {
				if (!s.insert())
					return false;
			} else if (type == 2) {
				if (!s.update())
					return false;
			} else if (type == 3) {
				if (!s.delete())
					return false;
			} else if (type == 4) {
				if (!s.backup(this.backupOperator, this.backupMemo))
					return false;
			} else if (type == 5) {
				if (!s.deleteAndBackup(this.backupOperator, this.backupMemo))
					return false;
			} else if ((type == 6) && (!s.deleteAndInsert())) {
				return false;
			}
		} else if ((obj instanceof SchemaSet)) {
			SchemaSet s = (SchemaSet) obj;
			if (type == 1) {
				if (!s.insert())
					return false;
			} else if (type == 2) {
				if (!s.update())
					return false;
			} else if (type == 3) {
				if (!s.delete())
					return false;
			} else if (type == 4) {
				if (!s.backup(this.backupOperator, this.backupMemo))
					return false;
			} else if (type == 5) {
				if (!s.deleteAndBackup(this.backupOperator, this.backupMemo))
					return false;
			} else if ((type == 6) && (!s.deleteAndInsert())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * public boolean commit(boolean setAutoCommitStatus) {

		boolean NoErrFlag = true;
		try {

			for (int i = 0; i < this.list.size(); i++) {
				Object[] arr = (Object[]) this.list.get(i);
				Object obj = arr[0];
				int type = ((Integer) arr[1]).intValue();
				if (!executeObject(obj, type)) {
					NoErrFlag = false;
					return false;
				}
			}
			this.dataAccess.commit();
			this.list.clear();
		} catch (Exception e) {
			e.printStackTrace();
			this.exceptionMessage = e.getMessage();
			NoErrFlag = false;
			return false;
		} finally {
			if (!NoErrFlag)
				try {
					this.dataAccess.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			try {
				if ((!this.outerConnFlag) || (setAutoCommitStatus))
					this.dataAccess.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (!this.outerConnFlag)
				try {
					this.dataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		if (!NoErrFlag)
			try {
				this.dataAccess.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		try {
			if ((!this.outerConnFlag) || (setAutoCommitStatus))
				this.dataAccess.setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (!this.outerConnFlag) {
			try {
				this.dataAccess.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < this.executorList.size(); i++) {
			Executor executor = (Executor) this.executorList.get(i);
			executor.execute();
		}
		return true;
	}
	 * @param setAutoCommitStatus
	 * @return
	 */
	public boolean commit(boolean setAutoCommitStatus) {

//		if (!this.outerConnFlag) {
//			this.dataAccess = new DataAccess();
//		}
		boolean NoErrFlag = true;
		try {
//			if ((!this.outerConnFlag) || (setAutoCommitStatus))
//				this.dataAccess.setAutoCommit(false);


			for (int i = 0; i < this.list.size(); i++) {
				Object[] arr = (Object[]) this.list.get(i);
				Object obj = arr[0];

				if (obj instanceof QueryBuilder) {
					((QueryBuilder) obj).executeNoQuery();
				} else {
					OperateType operateType = ((OperateType) arr[1]);
					AbstractSchema s = null;

					if (obj instanceof AbstractSchema) {
						s = (AbstractSchema) obj;
					}

					if (operateType == OperateType.INSERT)
						NoErrFlag = s.insert();
					if (operateType == OperateType.UPDATE)
						NoErrFlag = s.update();
					if (operateType == OperateType.DELETE)
						NoErrFlag = s.delete();
					if (operateType == OperateType.BACKUP)
						NoErrFlag = s.backup(this.backupOperator,
								this.backupMemo);
					if (operateType == OperateType.DELETE_AND_BACKUP)
						NoErrFlag = s.deleteAndBackup(this.backupOperator,
								this.backupMemo);
					if (operateType == OperateType.DELETE_AND_INSERT)
						NoErrFlag = s.deleteAndInsert();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.exceptionMessage = e.getMessage();
			NoErrFlag = false;
			return false;
		} finally {
			// if (!(NoErrFlag))
			// try {
			// this.mDataAccess.rollback();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
			// try {
			// if ((this.bConnFlag == 0) || (setAutoCommitStatus))
			// this.mDataAccess.setAutoCommit(true);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
			// if (this.bConnFlag == 0)
			// try {
			// this.mDataAccess.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}

		return true;
	}

	public void clear() {
		// mapx.clear();
		this.list.clear();
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getBackupMemo() {
		return backupMemo;
	}

	public void setBackupMemo(String backupMemo) {
		backupMemo = backupMemo;
	}

	public String getBackupOperator() {
		return backupOperator;
	}

	public void setBackupOperator(String backupOperator) {
		backupOperator = backupOperator;
	}

	public ArrayList getOperateList() {
		return this.list;
	}

	public void addExecutor(Executor executor) {
		this.executorList.add(executor);
	}

}
