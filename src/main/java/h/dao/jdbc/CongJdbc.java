package h.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import h.model.Db;

@Component
public class CongJdbc
{
  private MappingSqlQuery<Db.Row> mAll;

  public CongJdbc(DataSource inDataSource)
  {
    String selectAll = "select * from CONGREGATION";

    mAll = new MappingSqlQuery<Db.Row>(inDataSource, selectAll)
    {
      @Override
      protected Db.Row mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return DbUtil.mapRow(inRs, inRowNum);
      }
    };
  }

  public Db.Rows select()
  {
    return new Db.Rows(mAll.execute());
  }
}