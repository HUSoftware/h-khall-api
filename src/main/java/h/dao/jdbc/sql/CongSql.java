package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import h.dao.CongDao;
import h.model.Db;

@Component
public class CongSql implements CongDao
{
  private MappingSqlQuery<Db.Row> mAll;

  public CongSql(DataSource inDataSource)
  {
    String all = "select * from CONGREGATION";
    mAll = newQuery(inDataSource, all);
  }

  @Override
  public Db.Rows select()
  {
    return new Db.Rows(mAll.execute());
  }
}