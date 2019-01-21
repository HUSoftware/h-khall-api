package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import h.dao.CongDao;
import h.model.Db;

@Component
public class CongSql extends AbstractSql implements CongDao
{
  private MappingSqlQuery<Db.Row> mAll;
  private MappingSqlQuery<Db.Row> mByNumber;

  public CongSql(DataSource inDataSource)
  {
    String all = "select mProfile from CONGREGATION where mProfile is not null";
    mAll = newQuery(inDataSource, all);

    String byNumber = "select mProfile from CONGREGATION where mNumber=?";
    mByNumber = newQuery(inDataSource, byNumber, Types.VARCHAR);
  }

  @Override
  public Db.Rows select()
  {
    return new Db.Rows(mAll.execute());
  }

  @Override
  public Db.Rows select(String inNumber)
  {
    return new Db.Rows(mByNumber.execute(inNumber));
  }
}