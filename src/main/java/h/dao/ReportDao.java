package h.dao;

import java.util.Date;
import java.util.List;

import h.model.Db;
import h.model.khall.Report;

public interface ReportDao
{
  Db.Rows reportByUserAndMonthsAgo(long inCongId, long inUserId, int inMonths);

  Db.Rows reportByCongAndMonthsAgo(long inCongId, int inMonths);

  int upsert(Report inReport);

  List<Report> select(int inCongId);

  List<Report> select(int inCongId, long inPubId);

  List<Report> select(int inCongId, Date inBegin, Date inEnd);

  List<Report> select(int inCongId, int inPastMonths);

  List<Report> select(int inCongId, Long inPubId, Date inBegin, Date inEnd);

  Report select(int inCongId, long inPubId, int inYear, int inMonth);
}