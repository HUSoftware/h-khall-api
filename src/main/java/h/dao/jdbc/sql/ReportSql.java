package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;
import static h.dao.jdbc.DbUtil.newUpdate;

import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

import h.dao.ReportDao;
import h.model.Db;
import h.model.khall.Report;

@Component
public class ReportSql extends AbstractSql implements ReportDao
{
  private MappingSqlQuery<Db.Row> mPub, mCong;
  private SqlUpdate mUpsert;

  public ReportSql(DataSource inDataSource)
  {
    mStmts = getStatements("Report.json");

    Statement pub = mStmts.getStatement("SELECT_CPR");
    mPub = newQuery(inDataSource, pub.getSql(), pub.gTypes());

    Statement cong = mStmts.getStatement("SELECT_CR");
    mCong = newQuery(inDataSource, cong.getSql(), cong.gTypes());

    Statement upsert = mStmts.getStatement("UPSERT");
    mUpsert = newUpdate(inDataSource, upsert.getSql(), upsert.gTypes());
  }

  @Override
  public int upsert(Report inReport)
  {
    return mUpsert.update(inReport.getCongId(), inReport.getPubId(), inReport.getYear(), inReport.getMonth(), inReport.gDate(), inReport.gSendDate(), inReport.getNoActivity(),
        inReport.getPlacements(), inReport.getVideoShowings(), inReport.getHours(), inReport.getReturnVisits(), inReport.getBibleStudies(), inReport.getCreditHours(), inReport.getIncludeAllHours(),
        inReport.getRemarks(), inReport.gType(), inReport.getPartialHours(), inReport.gSendDate(), inReport.getNoActivity(), inReport.getPlacements(), inReport.getVideoShowings(), inReport.getHours(),
        inReport.getReturnVisits(), inReport.getBibleStudies(), inReport.getCreditHours(), inReport.getIncludeAllHours(), inReport.getRemarks(), inReport.gType(), inReport.getPartialHours());
  }

  @Override
  public Db.Rows reportByUserAndMonthsAgo(long inCongId, long inUserId, int inMonthsAgo)
  {
    Date[] range = range(inMonthsAgo);
    return new Db.Rows(mPub.execute(inCongId, inUserId, range[0], range[1]));
  }

  @Override
  public Db.Rows reportByCongAndMonthsAgo(long inUserId, int inMonthsAgo)
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