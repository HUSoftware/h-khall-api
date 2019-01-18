package h.dao.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import h.model.Db;

public final class DbUtil
{
  public static Db.Row mapRow(ResultSet inRs, int inRowNum) throws SQLException
  {
    return row(inRs, columns(inRs.getMetaData()));
  }

  public static Db.Rows mapRows(ResultSet inRs, int inRowNum) throws SQLException
  {
    return rows(inRs, columns(inRs.getMetaData()));
  }

  private static Db.Rows rows(ResultSet inRs, List<String> inColumns) throws SQLException
  {
    Db.Rows ret = new Db.Rows();

    while (inRs.next())
    {
      Db.Row row = new Db.Row(inColumns.size());
      for (String value : inColumns)
      {
        row.put(value, inRs.getObject(value));
      }
      ret.add(row);
    }

    return ret;
  }

  private static Db.Row row(ResultSet inRs, List<String> inColumns) throws SQLException
  {
    Db.Row ret = new Db.Row(inColumns.size());

    for (String value : inColumns)
    {
      ret.put(value, inRs.getObject(value));
    }

    return ret;
  }

  private static List<String> columns(ResultSetMetaData inMetaData) throws SQLException
  {
    List<String> ret = new ArrayList<String>(inMetaData.getColumnCount());
    for (int i = 1; i <= inMetaData.getColumnCount(); i++)
    {
      ret.add(inMetaData.getColumnName(i));
    }
    return ret;
  }

}
