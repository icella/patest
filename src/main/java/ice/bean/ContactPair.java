package ice.bean;

/**
 * Created by lla on 17-4-11.
 */
public class ContactPair {
    private String pa;
    private String sa;
    private String ea;
    
    private String pad;
    private String sad;
    private String ead;
    
    private String pb;
    private String sb;
    private String eb;
    
    private String pbd;
    private String sbd;
    private String ebd;

    public ContactPair() {
        this.pa = "";
        this.sa = "";
        this.ea = "";
        
        this.pb = "";
        this.sb = "";
        this.eb = "";
    }

    public String getPa() {
        return pa;
    }

    public ContactPair setPa(String pa) {
        this.pa = pa;
        return this;
    }

    public String getSa() {
        return sa;
    }

    public ContactPair setSa(String sa) {
        this.sa = sa;
        return this;
    }

    public String getEa() {
        return ea;
    }

    public ContactPair setEa(String ea) {
        this.ea = ea;
        return this;
    }

    public String getPad() {
        return pad;
    }

    public ContactPair setPad(String pad) {
        this.pad = pad;
        return this;
    }

    public String getSad() {
        return sad;
    }

    public ContactPair setSad(String sad) {
        this.sad = sad;
        return this;
    }

    public String getEad() {
        return ead;
    }

    public ContactPair setEad(String ead) {
        this.ead = ead;
        return this;
    }

    public String getPb() {
        return pb;
    }

    public ContactPair setPb(String pb) {
        this.pb = pb;
        return this;
    }

    public String getSb() {
        return sb;
    }

    public ContactPair setSb(String sb) {
        this.sb = sb;
        return this;
    }

    public String getEb() {
        return eb;
    }

    public ContactPair setEb(String eb) {
        this.eb = eb;
        return this;
    }

    public String getPbd() {
        return pbd;
    }

    public ContactPair setPbd(String pbd) {
        this.pbd = pbd;
        return this;
    }

    public String getSbd() {
        return sbd;
    }

    public ContactPair setSbd(String sbd) {
        this.sbd = sbd;
        return this;
    }

    public String getEbd() {
        return ebd;
    }

    public ContactPair setEbd(String ebd) {
        this.ebd = ebd;
        return this;
    }

    @Override public String toString() {
        return "pa='" + pa + '\'' + ", sa='" + sa + '\'' + ", ea='" + ea + '\'' + ", pad='" + pad
            + '\'' + ", sad='" + sad + '\'' + ", ead='" + ead + '\'' + ", pb='" + pb + '\'' + ", sb='" + sb + '\''
            + ", eb='" + eb + '\'' + ", pbd='" + pbd + '\'' + ", sbd='" + sbd + '\'' + ", ebd='" + ebd + '\'';
    }
}
