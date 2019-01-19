package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;
import static h.dao.jdbc.DbUtil.newUpdate;

import java.sql.Types;
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
public class ReportSql implements ReportDao
{
  private MappingSqlQuery<Db.Row> mPub, mCong;
  private SqlUpdate mUpsert;

  public ReportSql(DataSource inDataSource)
  {
    String pub = "select * from REPORT where mPublisher=? and mDate between ? and ?";
    mPub = newQuery(inDataSource, pub, Types.NUMERIC, Types.DATE, Types.DATE);

    String cong = "select * from REPORT where mCongregation=? and mDate between ? and ?";
    mCong = newQuery(inDataSource, cong, Types.NUMERIC, Types.DATE, Types.DATE);
    String upsert =
        "INSERT INTO REPORT(mCongregation,mPublisher,mYear,mMonth,mDate,mSendDate,mNoActivity,mPlacements,mVideoShowings,mHours,mReturnVisits,mBibleStudies,mRbcHours,mIncludeAllHours,mRemarks,mType,mPartialHours) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE mSendDate=?,mNoActivity=?,mPlacements=?,mVideoShowings=?,mHours=?,mReturnVisits=?,mBibleStudies=?,mRbcHours=?,mIncludeAllHours=?,mRemarks=?,mType=?,mPartialHours=?,mUpdated=CURRENT_TIMESTAMP";
    mUpsert = newUpdate(inDataSource, upsert, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC,
        Types.NUMERIC, Types.DATE, Types.DATE, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC,
        Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR,
        Types.VARCHAR, Types.NUMERIC, Types.DATE, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC,
        Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR,
        Types.VARCHAR, Types.NUMERIC);
  }

  @Override
  public int upsert(Report inReport)
  {
    return mUpsert.update(inReport.getCongId(), inReport.getPubId(), inReport.getYear(),
        inReport.getMonth(), inReport.gDate(), inReport.gSendDate(), inReport.getNoActivity(),
        inReport.getPlacements(), inReport.getVideoShowings(), inReport.getHours(),
        inReport.getReturnVisits(), inReport.getBibleStudies(), inReport.getCreditHours(),
        inReport.getIncludeAllHours(), inReport.getRemarks(), inReport.gType(),
        inReport.getPartialHours(), inReport.gSendDate(), inReport.getNoActivity(),
        inReport.getPlacements(), inReport.getVideoShowings(), inReport.getHours(),
        inReport.getReturnVisits(), inReport.getBibleStudies(), inReport.getCreditHours(),
        inReport.getIncludeAllHours(), inReport.getRemarks(), inReport.gType(),
        inReport.getPartialHours());
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