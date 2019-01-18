package h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import h.dao.jdbc.CongJdbc;
import h.model.Db;

@RestController
public class CongController
{
  @Autowired
  private CongJdbc mJdbc;

  @GetMapping("/cong")
  public Db.Rows selectAllCong()
  {
    return mJdbc.select();
  }

}