package h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import h.dao.ReportDao;
import h.model.Db;
import h.model.khall.Report;
import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = "Report", description = "API")
@RequestMapping("/report")
public class ReportController
{
  @Autowired
  private ReportDao mDao;

  @GetMapping("/userid/{userid}/months/{months}")
  public Db.Rows selectByUserAndMonthsAgo(@PathVariable("userid") long inUserId, @PathVariable("months") int inMonths)
  {
    return mDao.reportByUserAndMonthsAgo(inUserId, inMonths);
  }

  @GetMapping("/congid/{congid}/months/{months}")
  public Db.Rows selectByCongAndMonthsAgo(@PathVariable("congid") long inCongId, @PathVariable("months") int inMonths)
  {
    return mDao.reportByCongAndMonthsAgo(inCongId, inMonths);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createReport(@RequestBody Report inReport)
  {
    mDao.upsert(inReport);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void updateReport(@RequestBody Report inReport)
  {
    mDao.upsert(inReport);
  }
}