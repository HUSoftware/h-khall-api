package h.dao.jdbc.sql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import h.model.khall.Assignment;
import h.model.khall.Curriculum;
import h.model.khall.Hall;
import h.model.khall.Part;
import h.model.khall.Report;
import h.model.khall.Roles.Role;
import h.model.khall.StudyPoint;
import h.util.NumberUtil;

//import net.husoftware._sec.shared.model.AssignmentWeekend;
//import net.husoftware._sec.shared.model.Attendance;
//import net.husoftware._sec.shared.model.Congregation;
//import net.husoftware._sec.shared.model.Congregation.OclmRpt;
//import net.husoftware._sec.shared.model.FieldServiceGroup;
//import net.husoftware._sec.shared.model.HowTo;
//import net.husoftware._sec.shared.model.Note;
//import net.husoftware._sec.shared.model.PublicTalk;
//import net.husoftware._sec.shared.model.Publisher;
//import net.husoftware._sec.shared.model.Publisher.Faith;
//import net.husoftware._sec.shared.model.Shepherding;
//import net.husoftware._sec.shared.model.SpeakerTalk;
//import net.husoftware._sec.shared.model.SpecialEvent;
//import net.husoftware._sec.shared.value.Access;
//import net.husoftware._sec.shared.value.School;
//import net.husoftware._sec.shared.value.Setting;

public class Mapping
{
  // public static void mapAbstractModel(ResultSet inRs, String inPrefix,
  // AbstractModel inModel)
  // throws SQLException
  // {
  // inModel.setId(NumberUtil.toLong(inRs.getBigDecimal(prefix(inPrefix) +
  // "mId")));
  // inModel.setCreated(inRs.getTimestamp(prefix(inPrefix) + "mCreated"));
  // inModel.setUpdated(inRs.getTimestamp(prefix(inPrefix) + "mUpdated"));
  // }
  //
  // public static Person mapPersonEncrypt(ResultSet inRs, String inPrefix)
  // throws SQLException
  // {
  // Person ret = new Person();
  // mapAbstractModel(inRs, inPrefix, ret);
  // mapPersonEncrypt(ret, inRs, inPrefix);
  // return ret;
  // }
  //
  // private static void mapPersonEncrypt(Person inPerson, ResultSet inRs,
  // String inPrefix)
  // throws SQLException
  // {
  // inPerson.setCongregation(inRs.getLong(prefix(inPrefix) + "mCongregation"));
  // inPerson.setHeadOfHouse(NumberUtil.toLong(inRs.getObject(prefix(inPrefix) +
  // "mHeadOfHouse")));
  // inPerson.setFamily(inRs.getString(prefix(inPrefix) + "mFamily"));
  // inPerson.setFirst(inRs.getString(prefix(inPrefix) + "mFirst"));
  // inPerson.setLast(inRs.getString(prefix(inPrefix) + "mLast"));
  // inPerson.setEmail(inRs.getString(prefix(inPrefix) + "mEmail"));
  // inPerson.setMiddle(inRs.getString(prefix(inPrefix) + "mMiddle"));
  // inPerson.setSuffix(inRs.getString(prefix(inPrefix) + "mSuffix"));
  // inPerson.setBirth(TimeUtil.toDate(inRs.getString(prefix(inPrefix) +
  // "mBirth")));
  // inPerson.setGender(valueOf(inRs.getString(prefix(inPrefix) + "mGender"),
  // Gender.values()));
  // inPerson.setAddress(inRs.getString(prefix(inPrefix) + "mAddress"));
  // inPerson.setPhone(inRs.getString(prefix(inPrefix) + "mPhone"));
  // inPerson.setMobile(inRs.getString(prefix(inPrefix) + "mMobile"));
  // inPerson.setChidren(inRs.getString(prefix(inPrefix) + "mChildren"));
  // inPerson.setEmergency(inRs.getString(prefix(inPrefix) + "mEmergency"));
  // inPerson.setPassword(inRs.getString(prefix(inPrefix) + "mPassword"));
  // }
  //
  // private static String prefix(String inPrefix)
  // {
  // return inPrefix == null ? "" : inPrefix + ".";
  // }
  //
  // public static Publisher mapPublisher(ResultSet inRs, String inPu, String
  // inFsg, String inC)
  // throws SQLException
  // {
  // Publisher ret = mapPublisher(inRs, inPu);
  // FieldServiceGroup fsg = mapFieldServiceGroup(inRs, inFsg, inC);
  // ret.setFieldServiceGroup(fsg);
  // ret.setFieldServiceGroupId(fsg != null ? fsg.getId() : null);
  // return ret;
  // }
  //
  // public static Publisher mapPublisher(ResultSet inRs, String inPrefix)
  // throws SQLException
  // {
  // Publisher ret = new Publisher();
  //
  // mapAbstractModel(inRs, inPrefix, ret);
  // ret.setStart(inRs.getDate(inPrefix + ".mStart"));
  // ret.setImmersed(inRs.getDate(inPrefix + ".mImersed"));
  // ret.setFaith(valueOf(inRs.getString(inPrefix + ".mFaith"),
  // Faith.values()));
  // ret.setTypes(EnumUtil.toList(inRs.getString(inPrefix + ".mTypes"),
  // Type.values()));
  // ret.setCategories(EnumUtil.toList(inRs.getString(inPrefix + ".mCategory"),
  // Category.values()));
  // ret.setFieldServiceGroupId(inRs.getLong(inPrefix + ".mFieldServiceGroup"));
  // ret.setStopMissingReportMessage(inRs.getBoolean(inPrefix +
  // ".mStopMissingReportMessage"));
  // return ret;
  // }
  //
  // public static Congregation mapCongregation(ResultSet inRs, String inPrefix)
  // throws SQLException
  // {
  // Congregation ret = new Congregation();
  //
  // mapAbstractModel(inRs, inPrefix, ret);
  // ret.setName(inRs.getString(inPrefix + ".mName"));
  // ret.setNumber(inRs.getString(inPrefix + ".mNumber"));
  // ret.setWeekend(inRs.getTimestamp("mWeekend"));
  // ret.setWeekmid(inRs.getTimestamp("mWeekmid"));
  // ret.setMaxSchool(School.get(inRs.getString("mMaxSchool")));
  // ret.setStyle(OclmRpt.get(inRs.getString("mOclmRptStyle")));
  // ret.setChairmanParts(EnumUtil.toList(inRs.getString("mChairmanParts"),
  // Part.values()));
  // return ret;
  // }

  public static Report mapReport(ResultSet inRs) throws SQLException
  {
    Report ret = new Report();

    ret.setCongId(inRs.getInt("mCongregation"));
    ret.setPubId(inRs.getLong("mPublisher"));
    ret.setYear(inRs.getInt("mYear"));
    ret.setMonth(inRs.getInt("mMonth"));
    ret.setSendDate(inRs.getDate("mSendDate"));
    ret.setNoActivity(inRs.getBoolean("mNoActivity"));
    ret.setPlacements(inRs.getInt("mPlacements"));
    ret.setVideoShowings(inRs.getInt("mVideoShowings"));
    ret.setHours(inRs.getInt("mHours"));
    ret.setReturnVisits(inRs.getInt("mReturnVisits"));
    ret.setBibleStudies(inRs.getInt("mBibleStudies"));
    ret.setCreditHours(inRs.getInt("mRbcHours"));
    ret.setIncludeAllHours(inRs.getBoolean("mIncludeAllHours"));
    ret.setRemarks(inRs.getString("mRemarks"));
    ret.setType(AbstractSql.valueOf(inRs.getString("mType"), Role.values()));
    ret.setPartialHours(NumberUtil.toDouble(inRs.getBigDecimal("mPartialHours")));

    return ret;
  }

  // public static FieldServiceGroup mapFieldServiceGroup(ResultSet inRs, String
  // inFsg, String inC)
  // throws SQLException
  // {
  // FieldServiceGroup ret = new FieldServiceGroup();
  //
  // mapAbstractModel(inRs, inFsg, ret);
  // ret.setTitle(inRs.getString(inFsg + ".mTitle"));
  // ret.setLocation(inRs.getString(inFsg + ".mLocation"));
  // ret.setCongregation(mapCongregation(inRs, inC));
  // return ret;
  // }
  //
  // public static FieldServiceGroup mapFieldServiceGroupCount(ResultSet inRs,
  // String inFsg,
  // String inC) throws SQLException
  // {
  // FieldServiceGroup ret = new FieldServiceGroup();
  //
  // mapAbstractModel(inRs, inFsg, ret);
  // ret.setTitle(inRs.getString(inFsg + ".mTitle"));
  // ret.setLocation(inRs.getString(inFsg + ".mLocation"));
  // ret.setCongregation(mapCongregation(inRs, inC));
  // ret.setCount(inRs.getInt("mCount"));
  // return ret;
  // }
  //
  // public static Report mapReport(ResultSet inRs, String inReport, String
  // inPu, String inPe,
  // String inFsg, String inC) throws SQLException
  // {
  // Report ret = mapReport(inRs, inReport);
  //
  // ret.setPublisher(mapPublisher(inRs, inPu));
  // FieldServiceGroup fsg = mapFieldServiceGroup(inRs, inFsg, inC);
  // ret.getPublisher().setFieldServiceGroup(fsg);
  // ret.getPublisher().setFieldServiceGroupId(fsg != null ? fsg.getId() :
  // null);
  // ret.getPublisher().setPerson(mapPersonEncrypt(inRs, null));
  // return ret;
  // }
  //
  // public static Publisher mapPublisherEncrypt(ResultSet inRs, String inPu,
  // String inPe,
  // String inFsg, String inC) throws SQLException
  // {
  // Publisher ret = mapPublisher(inRs, inPu);
  //
  // ret.setPerson(mapPersonEncrypt(inRs, null));
  // FieldServiceGroup fsg = mapFieldServiceGroup(inRs, inFsg, inC);
  // ret.setFieldServiceGroup(fsg);
  // ret.setFieldServiceGroupId(fsg != null ? fsg.getId() : null);
  // return ret;
  // }
  //
  // public static Publisher mapPublisherEncrypt(ResultSet inRs, String inPu,
  // String inPe)
  // throws SQLException
  // {
  // Publisher ret = mapPublisher(inRs, inPu);
  // ret.setPerson(mapPersonEncrypt(inRs, inPe));
  // return ret;
  // }
  //
  // public static Shepherding mapShepherding(ResultSet inRs, String inS) throws
  // SQLException
  // {
  // Shepherding ret = new Shepherding();
  // mapAbstractModel(inRs, inS, ret);
  // ret.setPublisherId(inRs.getLong(inS + ".mPublisherId"));
  // ret.setElderId(inRs.getLong(inS + ".mElderId"));
  // ret.setStatus(valueOf(inRs.getString(inS + ".mStatus"),
  // Shepherding.Status.values()));
  // ret.setOn(inRs.getTimestamp(inS + ".mOn"));
  // ret.setNote(inRs.getString("Note"));
  // return ret;
  // }
  //
  // public static Note mapNote(ResultSet inRs, String inN, String inPe, String
  // inEpe)
  // throws SQLException
  // {
  // Note ret = new Note();
  // mapAbstractModel(inRs, inN, ret);
  // ret.setNote(inRs.getString("Note"));
  // ret.setPerson(inRs.getLong("mPerson"));
  // ret.setPersonEditBy(inRs.getLong("mPersonEditBy"));
  // ret.setPersonEditByName(inRs.getString("EName"));
  // ret.setType(valueOf(inRs.getString(inN + ".mType"), Note.Type.values()));
  // ret.setMeeting(NumberUtil.toLong(inRs.getBigDecimal(inN + ".mMeeting")));
  // ret.setCompleted(inRs.getDate(inN + ".mCompleted"));
  // return ret;
  // }
  //
  // public static Attendance mapAttendance(ResultSet inRs, String inA) throws
  // SQLException
  // {
  // Attendance ret = new Attendance();
  // mapAbstractModel(inRs, inA, ret);
  // ret.setType(valueOf(inRs.getString("mMeeting"), Attendance.Type.values()));
  // ret.setYear(inRs.getInt("mYear"));
  // ret.setMonth(inRs.getInt("mMonth"));
  // ret.setDate(inRs.getDate("mDate"));
  // ret.setCount(inRs.getInt("mCount"));
  // return ret;
  // }
  //
  // public static Profile mapProfile(ResultSet inRs, String inP) throws
  // SQLException
  // {
  // Profile ret = new Profile();
  // mapAbstractModel(inRs, inP, ret);
  // ret.setAccess(EnumUtil.toList(inRs.getString(inP + ".mAccess"),
  // Access.values()));
  // ret.setExpireOn(inRs.getDate(inP + ".mExpireOn"));
  // ret.setEmail(inRs.getString("Email"));
  // ret.setNumber(inRs.getString(inP + ".mNumber"));
  // ret.setName(inRs.getString("Name"));
  // ret.setPerson(inRs.getLong(inP + ".mPerson"));
  // return ret;
  // }
  //
  // public static HowTo mapHowTo(ResultSet inRs, String inH) throws
  // SQLException
  // {
  // HowTo ret = new HowTo();
  // mapAbstractModel(inRs, inH, ret);
  // ret.setName(inRs.getString(inH + ".mName"));
  // ret.setVideo(inRs.getString(inH + ".mVideo"));
  // ret.setHeight(inRs.getString(inH + ".mHeight"));
  // ret.setWidth(inRs.getString(inH + ".mWidth"));
  // ret.setActive(inRs.getBoolean(inH + ".mActive"));
  // return ret;
  // }
  //
  // public static Meeting mapMeeting(ResultSet inRs, String inM, String inC)
  // throws SQLException
  // {
  // Meeting ret = new Meeting();
  // mapAbstractModel(inRs, inM, ret);
  // ret.setCongregation(mapCongregation(inRs, inC));
  // ret.setOn(inRs.getDate(inM + ".mOn"));
  // ret.setTitle(inRs.getString(inM + ".mTitle"));
  // return ret;
  // }

  public static Assignment mapSchedule(ResultSet inRs, String inC, String inS) throws SQLException
  {
    Assignment ret = new Assignment();
    ret.setCurriculum(mapCurriculum(inRs, inC));
    ret.setId(NumberUtil.toLong(inRs.getBigDecimal(inS + ".mId")));
    ret.setCongregation(inRs.getLong(inS + ".mCongregation"));
    ret.setSchool(AbstractSql.valueOf(inRs.getString(inS + ".mSchool"), Hall.values()));
    ret.setStudyPoint(StudyPoint.get(inRs.getString(inS + ".mStudyPoint")));
    ret.setParticipantId(NumberUtil.toLong(inRs.getObject(inS + ".mParticipant")));
    ret.setAssistantId(NumberUtil.toLong(inRs.getObject(inS + ".mAssistant")));
    return ret;
  }

  // public static Schedule mapSchedule(ResultSet inRs, String inS) throws
  // SQLException
  // {
  // Assignment ret = new Assignment();
  // // mapAbstractModel(inRs, inS, ret);
  // ret.setCongregation(inRs.getLong(inS + ".mCongregation"));
  // ret.setSchool(AbstractSql.valueOf(inRs.getString(inS + ".mSchool"),
  // Hall.values()));
  // // ret.setLevel(AbstractSql.valueOf(inRs.getString(inS + ".mLevel"),
  // // Level.values()));
  // return ret;
  // }

  // public static Student mapStudent(ResultSet inRs, String inS, String inP)
  // throws SQLException
  // {
  // Student ret = new Student();
  // mapAbstractModel(inRs, inS, ret);
  // ret.setId(inRs.getLong("Id"));
  // ret.setFsgId(inRs.getLong("FsgId"));
  // ret.setTypes(EnumUtil.toList(inRs.getString("Types"), Type.values()));
  // ret.setCategories(EnumUtil.toList(inRs.getString("Category"),
  // Category.values()));
  // ret.setActive(inRs.getBoolean(inS + ".mActive"));
  // ret.setSchool(AbstractSql.toValidList(inRs.getString(inS + ".mSchool"),
  // School.values()));
  // ret.setLevel(AbstractSql.toValidList(inRs.getString(inS + ".mLevel"),
  // Level.values()));
  // ret.setPart(AbstractSql.toValidList(inRs.getString(inS + ".mPart"),
  // Part.values()));
  // mapPersonEncrypt(ret, inRs, inP);
  // return ret;
  // }

  public static Curriculum mapCurriculum(ResultSet inRs, String inC) throws SQLException
  {
    Curriculum ret = new Curriculum();
    ret.setId(NumberUtil.toLong(inRs.getBigDecimal(inC + ".mId")));
    ret.setPart(AbstractSql.valueOf(inRs.getString(inC + ".mPart"), Part.values()));
    ret.setSource(inRs.getString(inC + ".mSource"));
    ret.setTheme(inRs.getString(inC + ".mTheme"));
    ret.setDate(inRs.getDate(inC + ".mDate"));
    ret.setDurationMinutes(toInteger(inRs.getObject(inC + ".mDurationMinutes")));
    ret.setSort(toInteger(inRs.getObject(inC + ".mSort")));
    ret.setStudyPoint(StudyPoint.get(inRs.getString(inC + ".mStudyPoint")));
    return ret;
  }

  // move
  public static Long toLong(Object inObject)
  {
    Long ret = null;

    if (inObject != null)
    {
      if (inObject instanceof Long)
      {
        ret = (Long) inObject;
      }
      else if (inObject instanceof BigDecimal)
      {
        ret = ((BigDecimal) inObject).longValue();
      }
      else if (inObject instanceof Integer)
      {
        ret = ((Integer) inObject).longValue();
      }
      else
      {
        throw new RuntimeException("object type not handled " + inObject.getClass().getName());
      }
    }

    return ret;
  }

  public static Integer toInteger(Object inObject)
  {
    Integer ret = null;

    if (inObject != null)
    {
      if (inObject instanceof Long)
      {
        ret = ((Long) inObject).intValue();
      }
      else if (inObject instanceof BigDecimal)
      {
        ret = ((BigDecimal) inObject).intValue();
      }
      else if (inObject instanceof Integer)
      {
        ret = (Integer) inObject;
      }
      else
      {
        throw new RuntimeException("object type not handled " + inObject.getClass().getName());
      }
    }

    return ret;
  }

  // public static Assignment mapAssignment(ResultSet inRs, String inC, String
  // inS, String inA)
  // throws SQLException
  // {
  // Assignment ret = new Assignment();
  // mapAbstractModel(inRs, inA, ret);
  // ret.setId(NumberUtil.toLong(inRs.getBigDecimal(prefix(inA) + "mId")));
  // ret.setNote(inRs.getString(inA + ".mNote"));
  // ret.setStudyPoint(StudyPoint.get(inRs.getString(inA + ".mStudyPoint")));
  // ret.setSetting(Setting.get(inRs.getString(inA + ".mSetting")));
  // ret.setStudentId(NumberUtil.toLong(inRs.getObject(inA + ".mStudent")));
  // ret.setAssistant1Id(NumberUtil.toLong(inRs.getObject(inA +
  // ".mAssistant1")));
  // ret.setAssistant2Id(NumberUtil.toLong(inRs.getObject(inA +
  // ".mAssistant2")));
  // ret.setSchedule(mapSchedule(inRs, inC, inS));
  // return ret;
  // }
  //
  // public static SpeakerTalk mapSpeakerTalk(ResultSet inRs) throws
  // SQLException
  // {
  // SpeakerTalk ret = new SpeakerTalk();
  // ret.setEmail(inRs.getString("mEmail"));
  // ret.setTalkId(NumberUtil.toLong(inRs.getObject("mTalkId")));
  // ret.setRate(toInteger(inRs.getObject("mRate")));
  // ret.setCode(toInteger(inRs.getObject("mCode")));
  // ret.setTheme(inRs.getString("mTheme"));
  // return ret;
  // }
  //
  // public static AssignmentWeekend mapAssignmentWeekend(ResultSet inRs, String
  // inA)
  // throws SQLException
  // {
  // AssignmentWeekend ret = new AssignmentWeekend();
  // mapAbstractModel(inRs, inA, ret);
  // ret.setOn(inRs.getDate(inA + ".mOn"));
  // ret.setCongregationId(NumberUtil.toLong(inRs.getObject(inA +
  // ".mCongregationId")));
  // ret.setPart(AbstractSql.valueOf(inRs.getString(inA + ".mPart"),
  // Part.values()));
  // ret.setRate(toInteger(inRs.getObject(inA + ".mRate")));
  // ret.setAssignToId(NumberUtil.toLong(inRs.getObject(inA + ".mAssignToId")));
  // ret.setAssignToEmail(inRs.getString(inA + ".mAssignToEmail"));
  // ret.setAssignTo(inRs.getString(inA + ".mAssignTo"));
  // ret.setAssignAt(inRs.getString(inA + ".mAssignAt"));
  // ret.setTalkId(toInteger(inRs.getObject(inA + ".mTalkId")));
  // ret.setInfo(inRs.getString(inA + ".mInfo"));
  // return ret;
  // }
  //
  // public static PublicTalk mapPublicTalk(ResultSet inRs) throws SQLException
  // {
  // PublicTalk ret = new PublicTalk();
  // ret.setId(NumberUtil.toLong(inRs.getObject("mId")));
  // ret.setCode(toInteger(inRs.getObject("mCode")));
  // ret.setTheme(inRs.getString("mTheme"));
  // return ret;
  // }
  //
  // public static SpecialEvent mapSpecialEvent(ResultSet inRs, String inS)
  // throws SQLException
  // {
  // SpecialEvent ret = new SpecialEvent();
  // mapAbstractModel(inRs, inS, ret);
  // ret.setDate(inRs.getDate(inS + ".mDate"));
  // ret.setCongregationId(NumberUtil.toLong(inRs.getObject(inS +
  // ".mCongregationId")));
  // ret.setType(AbstractSql.valueOf(inRs.getString(inS + ".mType"),
  // SpecialEvent.Type.values()));
  // ret.setDescription(inRs.getString(inS + ".mDescription"));
  // return ret;
  // }
}