package com.xdarkness.framework.jaf.controls.grid;

import com.xdarkness.framework.orm.data.DataRow;

/**
 * @author Darkness 
 * create on 2010-12-15 上午11:52:06
 * @version 1.0
 * @since JDP 1.0
 */
public interface ITreeDataGrid {
	String getExpand(DataRow dr);
	String getTreeLevel(DataRow dr);
}
