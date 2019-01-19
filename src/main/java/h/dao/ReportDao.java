package h.dao;

import h.model.Db;
import h.model.Db.Rows;
import h.model.khall.Report;

public interface ReportDao
{
  Db.Rows selectByUserAndMonthsAgo(long inUserId, int inMonths);

  Rows selectByCongAndMonthsAgo(long inCongId, int inMonths);

  int upsert(Report inReport);
}