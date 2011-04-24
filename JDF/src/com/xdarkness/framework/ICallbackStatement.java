package com.xdarkness.framework;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xdarkness.framework.connection.XConnection;

/**
 * Create on May 14, 2010 1:41:05 PM
 * @author XDarkness
 * @version 1.0
 */
public interface ICallbackStatement {

	Object execute(XConnection connection, PreparedStatement stmt, ResultSet rs) throws SQLException ;
}
