package treizieme.com.epinoish;

import java.util.Map;

public class ProjectFiles {
    private String type;
    private String slug;
    private String title;
    private Boolean secure;
    private Boolean synchro;
    private Boolean archive;
    private String language;
    private Integer size;
    private String ctime;
    private String mtime;
    private String mime;
    private Boolean isLeaf;
    private Boolean noFloder;
    private Map<String, Integer> rights;
    private Map<String, String> modifier;
    private String fullpath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean getSynchro() {
        return synchro;
    }

    public void setSynchro(Boolean synchro) {
        this.synchro = synchro;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Boolean getNoFloder() {
        return noFloder;
    }

    public void setNoFloder(Boolean noFloder) {
        this.noFloder = noFloder;
    }

    public Map<String, Integer> getRights() {
        return rights;
    }

    public void setRights(Map<String, Integer> rights) {
        this.rights = rights;
    }

    public Map<String, String> getModifier() {
        return modifier;
    }

    public void setModifier(Map<String, String> modifier) {
        this.modifier = modifier;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }
}
