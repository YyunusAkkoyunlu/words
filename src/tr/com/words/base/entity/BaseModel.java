package tr.com.words.base.entity;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public class BaseModel {

    private String oid;

    private String kayitTarihi;

    private String kayitYapan;

    private String guncellemeTarihi = "";

    private String guncellemeYapan = "";

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(String kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public String getKayitYapan() {
        return kayitYapan;
    }

    public void setKayitYapan(String kayitYapan) {
        this.kayitYapan = kayitYapan;
    }

    public String getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public void setGuncellemeTarihi(String guncellemeTarihi) {
        this.guncellemeTarihi = guncellemeTarihi;
    }

    public String getGuncellemeYapan() {
        return guncellemeYapan;
    }

    public void setGuncellemeYapan(String guncellemeYapan) {
        this.guncellemeYapan = guncellemeYapan;
    }
}
