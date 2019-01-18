package h.dao.jdbc;

import static h.dao.jdbc.DbUtil.newQuery;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import h.dao.ReportDao;
import h.model.Db;

@Component
public class ReportJdbc implements ReportDao
{
  private MappingSqlQuery<Db.Row> mPub, mCong;

  public ReportJdbc(DataSource inDataSource)
  {
    String pub = "select * from REPORT where mPublisher=? and mDate between ? and ?";
    mPub = newQuery(inDataSource, pub, Types.NUMERIC, Types.DATE, Types.DATE);

    String cong = "select * from REPORT where mCongregation=? and mDate between ? and ?";
    mCong = newQuery(inDataSource, cong, Types.NUMERIC, Types.DATE, Types.DATE);
  }

  @Override
  public Db.Rows selectByUserAndMonthsAgo(long inUserId, int inMonthsAgo)
  {
    Date[] range = range(inMonthsAgo);
    return new Db.Rows(mPub.execute(inUserId, range[0], range[1]));
  }

  @Override
  public Db.Rows selectByCongAndMonthsAgo(long inUserId, int inMonthsAgo)
  {
    Date[] range = range(inMonthsAgo);
    return new Db.Rows(mCong.execute(inUserId, range[0], range[1]));
  }

  private static Date[] range(int inMonths)
  {
    Calendar to = Calendar.getInstance();
    Calendar from = Calendar.getInstance();

    to.set(Calendar.DAY_OF_MONTH, 1);

    from.set(Calendar.MONTH, -inMonths);
    from.set(Calendar.DAY_OF_MONTH, 1);

    return new Date[]
        {
            from.getTime(), to.getTime()
        };
  }
}