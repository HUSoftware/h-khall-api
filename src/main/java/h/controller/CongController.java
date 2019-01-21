package h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import h.dao.CongDao;
import h.model.Db;
import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = "Cong", description = "API")
@RequestMapping("/cong")
public class CongController
{
  @Autowired
  private CongDao mDao;

  @GetMapping()
  public Db.Rows selectAllCong()
  {
    return mDao.select();
  }

  @GetMapping("/{number}")
  public Db.Rows selectByNumber(@PathVariable("number") String inNumber)
  {
    return mDao.select(inNumber);
  }
}