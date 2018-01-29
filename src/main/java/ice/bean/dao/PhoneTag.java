package ice.bean.dao;

public class PhoneTag {
  private int id;

  private String tag;
  
  private String phone;

  private int hit;

  public int getId() {
    return id;
  }

  public PhoneTag setId(int id) {
    this.id = id;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public PhoneTag setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public int getHit() {
    return hit;
  }

  public PhoneTag setHit(int hit) {
    this.hit = hit;
    return this;
  }

  public String getTag() {
    return tag;
  }

  public PhoneTag setTag(String tag) {
    this.tag = tag;
    return this;
  }

  @Override public String toString() {
    return "PhoneTag{" + "id=" + id + ", tag='" + tag + '\'' + ", phone='" + phone + '\'' + ", hit=" + hit + '}';
  }
}
