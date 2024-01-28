package com.item.tomatofarm;

public class ItemDTO {

	private String sorta;
	private String sortb;
	private String sortc;
	private String sortd;
	private int code;
	private String madeby;
	private String name;
	private int size;
	private String storagetype;
	private String packagetype;
	private int price;
	
	
	public String getSorta() {
		return sorta;
	}
	public void setSorta(String sorta) {
		this.sorta = sorta;
	}
	public String getSortb() {
		return sortb;
	}
	public void setSortb(String sortb) {
		this.sortb = sortb;
	}
	public String getSortc() {
		return sortc;
	}
	public void setSortc(String sortc) {
		this.sortc = sortc;
	}
	public String getSortd() {
		return sortd;
	}
	public void setSortd(String sortd) {
		this.sortd = sortd;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMadeby() {
		return madeby;
	}
	public void setMadeby(String madeby) {
		this.madeby = madeby;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getStoragetype() {
		return storagetype;
	}
	public void setStoragetype(String storagetype) {
		this.storagetype = storagetype;
	}
	public String getPackagetype() {
		return packagetype;
	}
	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "mealkitDTO [sorta=" + sorta + ", sortb=" + sortb + ", sortc=" + sortc + ", sortd=" + sortd + ", code="
				+ code + ", madeby=" + madeby + ", name=" + name + ", size=" + size + ", storagetype=" + storagetype
				+ ", packagetype=" + packagetype + ", price=" + price + "]";
	}
	
	
}
