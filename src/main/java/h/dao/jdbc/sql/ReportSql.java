package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;
import static h.dao.jdbc.DbUtil.newUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
  private final MappingSqlQuery<Db.Row> mPub, mCong;

  private final SqlUpdate mUpsert;
  private final MapReport mSelect0;
  private final MapReport mSelect1;
  private final MapReport mSelect2;
  private final MapReport mSelect3;
  private final MapReport mSelect4;

  public ReportSql(DataSource inDataSource)
  {
    mStmts = getStatements("Report.json");

    Statement pub = mStmts.getStatement("SELECT_CPR");
    mPub = newQuery(inDataSource, pub.getSql(), pub.gTypes());

    Statement cong = mStmts.getStatement("SELECT_CR");
    mCong = newQuery(inDataSource, cong.getSql(), cong.gTypes());

    Statement upsert = mStmts.getStatement("UPSERT");
    mUpsert = newUpdate(inDataSource, upsert.getSql(), upsert.gTypes());

    mSelect0 = new MapReport(inDataSource, mStmts.getStatement("SELECT_C"));
    mSelect1 = new MapReport(inDataSource, mStmts.getStatement("SELECT_CP"));
    mSelect2 = new MapReport(inDataSource, mStmts.getStatement("SELECT_CR"));
    mSelect3 = new MapReport(inDataSource, mStmts.getStatement("SELECT_CPR"));
    mSelect4 = new MapReport(inDataSource, mStmts.getStatement("SELECT_CPYM"));
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

  @Override
  public List<Report> select(int inCongId)
  {
    return mSelect0.execute(params(inCongId));
  }

  @Override
  public List<Report> select(int inCongId, long inPubId)
  {
    return mSelect1.execute(params(inCongId, inPubId));
  }

  @Override
  public List<Report> select(int inCongId, Date inBegin, Date inEnd)
  {
    return mSelect2.execute(params(inCongId, inBegin, inEnd));
  }

  @Override
  public List<Report> select(int inCongId, int inPastMonths)
  {
    Calendar start = Calendar.getInstance();
    start.set(Calendar.MONTH, -inPastMonths);
    Calendar end = Calendar.getInstance();
    return select(inCongId, start.getTime(), end.getTime());
  }

  @Override
  public List<Report> select(int inCongId, Long inPubId, Date inBegin, Date inEnd)
  {
    return mSelect3.execute(params(inCongId, inPubId, inBegin, inEnd));
  }

  @Override
  public Report select(int inCongId, long inPubId, int inYear, int inMonth)
  {
    return only(mSelect4.execute(params(inCongId, inPubId, inYear, inMonth)));
  }

  private class MapReport extends MappingSqlEncrypt<Report>
  {
    public MapReport(DataSource inDataSource, Statement inStmt)
    {
      super(inDataSource, inStmt.getSql(), inStmt.gTypes());
    }

    @Override
    public Report mapRow(ResultSet inRs, int inRowNum) throws SQLException
    {
      return Mapping.mapReport(inRs);
    }
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