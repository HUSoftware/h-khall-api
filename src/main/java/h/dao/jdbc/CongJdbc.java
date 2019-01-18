package h.dao.jdbc;

import static h.dao.jdbc.DbUtil.newQuery;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import h.dao.CongDao;
import h.model.Db;

@Component
public class CongJdbc implements CongDao
{
  private MappingSqlQuery<Db.Row> mAll;

  public CongJdbc(DataSource inDataSource)
  {
    String selectAll = "select * from CONGREGATION";
    mAll = newQuery(inDataSource, selectAll);
  }

  @Override
  public Db.Rows select()
  {
    return new Db.Rows(mAll.execute());
  }
}