package treizieme.com.epinoish;

public class Planning {

    private String codeacti;
    private int num_event;
    private boolean project;
    private boolean allow_token;
    private String type_title;
    private String start; // can be a DateTime
    private String type_code;
    private int nb_max_students_projet; // nullable
    private Professor prof_inst;
    private String end; // can be a DateTime
    private String dates; // nullable
    private int nb_group;
    private String codeinstance;
    private String title; // nullable
    private String codemodule;
    private int semester;
    private String allowed_planning_end; // datetime
    private boolean register_prof;
    private boolean allow_register;
    private String display;
    private String room; // nullable
    private String codeevent;
    private boolean register_month;
    private String is_rdv;
    private String instance_location;
    private boolean past;
    private boolean module_registered;
    private boolean in_more_than_one_month;
    private boolean event_registered;
    private String titlemodule;
    private String nb_hours;
    private boolean module_available;
    private boolean rdv_indiv_registered;
    private String scolaryear;
    private String allowed_planning_start; // datetime
    private int total_students_registered;
    private String rdv_group_registered; // nullable
    private boolean register_student;
    
    private String acti_title;

    public String getActi_title() {
        return acti_title;
    }

    public void setActi_title(String acti_title) {
        this.acti_title = acti_title;
    }

    public String getCodeacti() {
        return codeacti;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public int getNum_event() {
        return num_event;
    }

    public void setNum_event(int num_event) {
        this.num_event = num_event;
    }

    public boolean isProject() {
        return project;
    }

    public void setProject(boolean project) {
        this.project = project;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public boolean isAllow_token() {
        return allow_token;
    }

    public void setAllow_token(boolean allow_token) {
        this.allow_token = allow_token;
    }

    public int getNb_group() {
        return nb_group;
    }

    public void setNb_group(int nb_group) {
        this.nb_group = nb_group;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getNb_max_students_projet() {
        return nb_max_students_projet;
    }

    public void setNb_max_students_projet(int nb_max_students_projet) {
        this.nb_max_students_projet = nb_max_students_projet;
    }

    public Professor getProf_inst() {
        return prof_inst;
    }

    public void setProf_inst(Professor prof_inst) {
        this.prof_inst = prof_inst;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getAllowed_planning_end() {
        return allowed_planning_end;
    }

    public void setAllowed_planning_end(String allowed_planning_end) {
        this.allowed_planning_end = allowed_planning_end;
    }

    public boolean isAllow_register() {
        return allow_register;
    }

    public void setAllow_register(boolean allow_register) {
        this.allow_register = allow_register;
    }

    public boolean isRegister_prof() {
        return register_prof;
    }

    public void setRegister_prof(boolean register_prof) {
        this.register_prof = register_prof;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isRegister_month() {
        return register_month;
    }

    public void setRegister_month(boolean register_month) {
        this.register_month = register_month;
    }

    public String getCodeevent() {
        return codeevent;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

    public String getIs_rdv() {
        return is_rdv;
    }

    public void setIs_rdv(String is_rdv) {
        this.is_rdv = is_rdv;
    }

    public String getInstance_location() {
        return instance_location;
    }

    public void setInstance_location(String instance_location) {
        this.instance_location = instance_location;
    }

    public boolean isModule_registered() {
        return module_registered;
    }

    public void setModule_registered(boolean module_registered) {
        this.module_registered = module_registered;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public boolean isIn_more_than_one_month() {
        return in_more_than_one_month;
    }

    public void setIn_more_than_one_month(boolean in_more_than_one_month) {
        this.in_more_than_one_month = in_more_than_one_month;
    }

    public boolean isEvent_registered() {
        return event_registered;
    }

    public void setEvent_registered(boolean event_registered) {
        this.event_registered = event_registered;
    }

    public String getTitlemodule() {
        return titlemodule;
    }

    public void setTitlemodule(String titlemodule) {
        this.titlemodule = titlemodule;
    }

    public String getNb_hours() {
        return nb_hours;
    }

    public void setNb_hours(String nb_hours) {
        this.nb_hours = nb_hours;
    }

    public boolean isModule_available() {
        return module_available;
    }

    public void setModule_available(boolean module_available) {
        this.module_available = module_available;
    }

    public boolean isRdv_indiv_registered() {
        return rdv_indiv_registered;
    }

    public void setRdv_indiv_registered(boolean rdv_indiv_registered) {
        this.rdv_indiv_registered = rdv_indiv_registered;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getAllowed_planning_start() {
        return allowed_planning_start;
    }

    public void setAllowed_planning_start(String allowed_planning_start) {
        this.allowed_planning_start = allowed_planning_start;
    }

    public int getTotal_students_registered() {
        return total_students_registered;
    }

    public void setTotal_students_registered(int total_students_registered) {
        this.total_students_registered = total_students_registered;
    }

    public String getRdv_group_registered() {
        return rdv_group_registered;
    }

    public void setRdv_group_registered(String rdv_group_registered) {
        this.rdv_group_registered = rdv_group_registered;
    }

    public boolean isRegister_student() {
        return register_student;
    }

    public void setRegister_student(boolean register_student) {
        this.register_student = register_student;
    }

    private class Professor {
        private String title;
        private String picture;
        private String login;
        private String type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Professor(String title, String picture, String login, String type) {
            this.title = title;
            this.picture = picture;
            this.login = login;
            this.type = type;
        }


    }
}
