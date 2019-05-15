package ir.asta.training.contacts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="WISE_CONTACT")
public class ContactEntity {
	
	Long id;
//	@JsonIgnore
	String name;
	
    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}