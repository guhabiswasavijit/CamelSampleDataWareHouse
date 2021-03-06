package self.camel.demo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SqlColumn {
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	private String columnName;
	private String columnType;
}
