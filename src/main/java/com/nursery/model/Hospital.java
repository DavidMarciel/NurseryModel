package com.nursery.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nursery.number.format.CoordenatesMixer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
public class Hospital implements Serializable{
	
	public Hospital(String name, String telephone, String URL, double longitude, double latitude) {
		super();
		
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.telephone = telephone;
		this.URL = URL;
		
		this.coordenates = setCoordenates(longitude, latitude);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String name;

	@Column
	private double longitude;
	
	@Column
	private double latitude;
	
	//TODO use a method
	@Column
	private double coordenates;
		
	@Column
	private String telephone;

	@Column
	private String URL;
	
	private double setCoordenates(double longitude2, double latitude2) {
		CoordenatesMixer coordenatesMixer = new CoordenatesMixer();
		double coordinatesMix = coordenatesMixer.merger(longitude, latitude);
		
		return coordinatesMix;
	}
	
}
