package self.camel.demo;

import java.util.ArrayList;
import java.util.List;

public class TableMetaDataBean {
	private String tableName;
    private List<SqlColumn> sqlColumns = null;
	
	public TableMetaDataBean(String i_TableName) {
		sqlColumns = new ArrayList<SqlColumn>();
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SqlColumn> getSqlColumns() {
		return sqlColumns;
	}

	public void addSqlColumns(SqlColumn column) {
		this.sqlColumns.add(column);
	}

	
}
