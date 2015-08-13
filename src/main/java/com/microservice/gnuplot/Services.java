package com.microservice.gnuplot;


class Services {
	private int id;
	private String description;
	private String service;
	private String params;
	private String template;
	private Alternative alternative;

	public Alternative getAlternative() {
		return alternative;
	}

	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getService() {
		return service;
	}

	public void setService(String servicename) {
		this.service = servicename;
	}

	@Override
	public String toString() {
		return "Services [id=" + id + ", description=" + description
				+ ", params=" + params + ", template=" + template + "]";
	}
}
