package com.microservice.gnuplot;

import java.util.List;


public class Alternative {
	
	private List<Services> command;

	public List<Services> getCommand() {
		return command;
	}

	public void setCommand(List<Services> command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "Alternative [command=" + command + "]";
	}
}
