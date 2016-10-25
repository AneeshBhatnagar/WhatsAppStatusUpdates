package com.slashcoding.whatsappstatus;

public class Categories {
	// Private variables
	int _id;
	String _name;

	public Categories() {
		// Empty Constructor
	}

	// Constructors
	public Categories(int id, String name) {
		this._id = id;
		this._name = name;
	}

	public Categories(String name) {
		this._name = name;
	}

	// Functions to access private variables

	public int getID() {
		return this._id;
	}

	public void setID(int id) {
		this._id = id;
	}

	public String getName() {
		return this._name;
	}

	public void setName(String name) {
		this._name = name;
	}
}
