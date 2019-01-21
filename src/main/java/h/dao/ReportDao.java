package h.dao;

import h.model.Db;
import h.model.khall.Report;

public interface ReportDao
{
  Db.Rows reportByUserAndMonthsAgo(long inCongId, long inUserId, int inMonths);

  Db.Rows reportByCongAndMonthsAgo(long inCongId, int inMonths);

  int upsert(Report inReport);
}