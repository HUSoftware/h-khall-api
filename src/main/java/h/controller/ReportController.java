package h.controller;

import org.springframework.beans.factory.annotation.Autowired;

import h.dao.Dao;

//@RestController
//@Api(produces = "application/json", tags = "Reports", description = "API")
//@RequestMapping("/reports")
public class ReportController
{
  @Autowired
  Dao mDao;

  // @GetMapping
  // public List<Report> getAllLookups()
  // {
  // return mService.lookups();
  // }

  // @GetMapping("/{id}")
  // public Report getLookupById(@PathVariable("congid") int inCongId,
  // @PathVariable("pubid") long inPubId, @PathVariable("year") int inYear,
  // @PathVariable("month") int inMonth)
  // {
  // return mDao.selectReport(inCongId, inPubId, inYear, inMonth);
  // }
  //
  // @GetMapping("/type/{id}")
  // public List<Report> getByPersonId(@PathVariable("id") int inCongId, Date
  // inStart, Date inEnd)
  // {
  // return mDao.reports(inCongId, inStart, inEnd);
  // }
}