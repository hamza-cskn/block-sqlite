package mc.obliviate.bloksqliteapi.sqlutils;

import mc.obliviate.bloksqliteapi.SQLHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLTable {

	private final HashMap<String, SQLField> columns = new LinkedHashMap();
	private final String tableName;
	private final String iDField;

	public SQLTable(final String tableName, final String iDField) {
		this.tableName = tableName;
		this.iDField = iDField;
	}

	public SQLTable addField(final String fieldName, final DataType dataType, final boolean isNotNull, final boolean isUnique, final boolean isPrimaryKey) {
		columns.put(fieldName, new SQLField(fieldName, dataType, isNotNull, isUnique, isPrimaryKey));
		return this;
	}

	public SQLTable addField(final String fieldName, final DataType dataType) {
		columns.put(fieldName, new SQLField(fieldName, dataType, false, false, false));
		return this;
	}

	/**
	 * @return detailed data of columns
	 * (whats names, is flagged with not null, is fl... with primary key etc.)
	 */
	protected Set<Map.Entry<String, SQLField>> getColumns() {
		return columns.entrySet();
	}


	protected int columnAmount() {
		return columns.size();
	}


	protected List<String> getColumnNames() {
		return new ArrayList(columns.keySet());
	}

	public String getTableName() {
		return tableName;
	}

	public SQLUpdateColumn createUpdate(Object id) {
		return new SQLUpdateColumn(id);
	}

	public SQLTable create() {
		SQLHandler.sqlUpdate(SQLUtils.getCreateCommand(this));
		return this;
	}

	public ResultSet select() {
		return select(null);
	}

	public ResultSet select(Object id) {
		final String sql = "SELECT * FROM " + getTableName() + (!(id == null || id.toString().isEmpty()) ? " WHERE " + iDField + " = '" + id + "'" : "");
		return SQLHandler.sqlQuery(sql);
	}

	public void update(SQLUpdateColumn sqlUpdateColumn) {
		final String sql = SQLUtils.getUpdateCommand(getTableName(), iDField, sqlUpdateColumn.getId(), sqlUpdateColumn);
		SQLHandler.sqlUpdate(sql);
	}

	public void delete(Object id) {
		final String sql = "DELETE FROM " + getTableName() + " WHERE " + iDField + " = '" + id + "'";
		SQLHandler.sqlUpdate(sql);
	}

	/**
	 * @param sqlUpdateColumn
	 * @return is column exist? (returns true if updated. returns false if inserted.)
	 */
	public boolean insertOrUpdate(SQLUpdateColumn sqlUpdateColumn) {
		boolean exist;
		if (exist(sqlUpdateColumn.getId())) {
			update(sqlUpdateColumn);
			exist = true;
		} else {
			insert(sqlUpdateColumn);
			exist = false;
		}
		return exist;
	}

	public boolean exist(final Object whereValue) {
		//final String sql = "SELECT * FROM " + getTableName() + " WHERE " + iDField + " = '" + whereValue.toString() + "'";

		try {
			boolean exist = false;
			final ResultSet rs = select(whereValue);
			while (rs.next()) {
				exist = true;
			}
			return exist;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void insert(SQLUpdateColumn sqlUpdateColumn) {
		final String sql = SQLUtils.getInsertCommand(getTableName(), sqlUpdateColumn);
		SQLUtils.debug(sql);
		try {
			final PreparedStatement pstmt = SQLHandler.getConnection().prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
