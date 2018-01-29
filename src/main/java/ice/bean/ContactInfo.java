package ice.bean;

public class ContactInfo {
	private String phone;
	private String imsi;
	private String imei;

	private String phoneDigest;
	private String imsiDigest;
	private String imeiDigest;
	
	/**
	 * 是否有手机号码
	 */
	private boolean hasPhone;
	/**
	 * 是否为座机电话
	 */
	private boolean isTelephone;

	public ContactInfo() {
		this.phone = "";
		this.imsi = "";
		this.imei = "";
		this.hasPhone = false;
		this.isTelephone = false;
	}

	public String getPhone() {
		return phone;
	}

	public ContactInfo setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getImsi() {
		return imsi;
	}

	public ContactInfo setImsi(String imsi) {
		this.imsi = imsi;
		return this;
	}

	public String getImei() {
		return imei;
	}

	public ContactInfo setImei(String imei) {
		this.imei = imei;
		return this;
	}

	public String getPhoneDigest() {
		return phoneDigest;
	}

	public ContactInfo setPhoneDigest(String phoneDigest) {
		this.phoneDigest = phoneDigest;
		return this;
	}

	public String getImsiDigest() {
		return imsiDigest;
	}

	public ContactInfo setImsiDigest(String imsiDigest) {
		this.imsiDigest = imsiDigest;
		return this;
	}

	public String getImeiDigest() {
		return imeiDigest;
	}

	public ContactInfo setImeiDigest(String imeiDigest) {
		this.imeiDigest = imeiDigest;
		return this;
	}

	public boolean isHasPhone() {
		return hasPhone;
	}

	public void setHasPhone(boolean hasPhone) {
		this.hasPhone = hasPhone;
	}

	public boolean isTelephone() {
		return isTelephone;
	}

	public void setTelephone(boolean isTelephone) {
		this.isTelephone = isTelephone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		result = prime * result + ((imsi == null) ? 0 : imsi.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactInfo other = (ContactInfo) obj;
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		if (imsi == null) {
			if (other.imsi != null)
				return false;
		} else if (!imsi.equals(other.imsi))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[phone=" + phone + ", imsi=" + imsi + ", imei=" + imei + "]";
	}

}
