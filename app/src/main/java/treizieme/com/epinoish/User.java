package treizieme.com.epinoish;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Quach on 27/01/16.
 */
public class User {
    private String login;
    private String title;
    private String internal_email;
    private String lastname;
    private String firstname;
    private Map<String, ?> userinfo;
    private String referent_used;
    private String picture;
    private String picture_fun;
    private Integer promo;
    private Integer semester;
    private Integer uid;
    private Integer gid;
    private String location;
    private String documents;
    private String userdocs;
    private String shell;
    private Boolean close;
    private String ctime;
    private String mtime;
    private String id_promo;
    private String id_history;
    private String course_code;
    private String school_code;
    private String school_title;
    private String old_id_promo;
    private String old_id_location;
    private Boolean invited;
    private Integer studentyear;
    private Boolean admin;
    private Boolean editable;
    private ArrayList groups;
    private ArrayList events;
    private Integer credits;
    private ArrayList<Map<String, String>> gpa;
    private Map<String, String> spice;
    private Map<String, Double> nsstat;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInternal_email() {
        return internal_email;
    }

    public void setInternal_email(String internal_email) {
        this.internal_email = internal_email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFullName() {
        String uFirstname = Character.toUpperCase(this.firstname.charAt(0)) +
                this.firstname.substring(1);
        String uLastName = Character.toUpperCase(this.lastname.charAt(0)) +
                this.lastname.substring(1);

        return uFirstname + " " + uLastName;
    }

    public Map<String, ?> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Map<String, ?> userinfo) {
        this.userinfo = userinfo;
    }

    public String getReferent_used() {
        return referent_used;
    }

    public void setReferent_used(String referent_used) {
        this.referent_used = referent_used;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture_fun() {
        return picture_fun;
    }

    public void setPicture_fun(String picture_fun) {
        this.picture_fun = picture_fun;
    }

    public Integer getPromo() {
        return promo;
    }

    public void setPromo(Integer promo) {
        this.promo = promo;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getUserdocs() {
        return userdocs;
    }

    public void setUserdocs(String userdocs) {
        this.userdocs = userdocs;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    public Boolean getClose() {
        return close;
    }

    public void setClose(Boolean close) {
        this.close = close;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getId_promo() {
        return id_promo;
    }

    public void setId_promo(String id_promo) {
        this.id_promo = id_promo;
    }

    public String getId_history() {
        return id_history;
    }

    public void setId_history(String id_history) {
        this.id_history = id_history;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getSchool_title() {
        return school_title;
    }

    public void setSchool_title(String school_title) {
        this.school_title = school_title;
    }

    public String getOld_id_promo() {
        return old_id_promo;
    }

    public void setOld_id_promo(String old_id_promo) {
        this.old_id_promo = old_id_promo;
    }

    public String getOld_id_location() {
        return old_id_location;
    }

    public void setOld_id_location(String old_id_location) {
        this.old_id_location = old_id_location;
    }

    public Boolean getInvited() {
        return invited;
    }

    public void setInvited(Boolean invited) {
        this.invited = invited;
    }

    public Integer getStudentyear() {
        return studentyear;
    }

    public void setStudentyear(Integer studentyear) {
        this.studentyear = studentyear;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public ArrayList getGroups() {
        return groups;
    }

    public void setGroups(ArrayList groups) {
        this.groups = groups;
    }

    public ArrayList getEvents() {
        return events;
    }

    public void setEvents(ArrayList events) {
        this.events = events;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public ArrayList<Map<String, String>> getGpa() {
        return gpa;
    }

    public void setGpa(ArrayList<Map<String, String>> gpa) {
        this.gpa = gpa;
    }

    public Map<String, String> getSpice() {
        return spice;
    }

    public void setSpice(Map<String, String> spice) {
        this.spice = spice;
    }

    public Map<String, Double> getNsstat() {
        return nsstat;
    }

    public void setNsstat(Map<String, Double> nsstat) {
        this.nsstat = nsstat;
    }
}
