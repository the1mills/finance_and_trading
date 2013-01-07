package exp;

import java.awt.Color;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ExpClassWithFields {

	private Integer markee = null;
	private Integer simpson = null;
	private Double realle = null;
	private Integer bonafide = null;
	private String xxx = null;
	private Date dateField = null;
	private JPanel jcp = null;
	private JComboBox jcb = null;

	public ExpClassWithFields(Integer markee, Integer simpson, Double realle,
			Integer bonafide) {
		super();
		this.markee = markee;
		this.simpson = simpson;
		this.realle = realle;
		this.bonafide = bonafide;
		this.xxx = "u the glove eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1";
		this.dateField = new Date();
		this.jcp = new JPanel();
		this.jcp.setBackground(Color.pink);
		this.jcb = new JComboBox();
	}
	
	public JPanel getJcp() {
		return jcp;
	}


	public void setJcp(JPanel jcp) {
		this.jcp = jcp;
	}


	public void setJcb(JComboBox jcb) {
		this.jcb = jcb;
	}

	public JPanel getJcb() {
		return jcp;
	}

	public void setJcb(JPanel jcb) {
		this.jcp = jcb;
	}
	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}
	
	public String getXxx() {
		return xxx;
	}

	public void setXxx(String xxx) {
		this.xxx = xxx;
	}

	public Integer getMarkee() {
		return markee;
	}

	public void setMarkee(Integer markee) {
		this.markee = markee;
	}

	public Integer getSimpson() {
		return simpson;
	}

	public void setSimpson(Integer simpson) {
		this.simpson = simpson;
	}

	public Double getRealle() {
		return realle;
	}

	public void setRealle(Double realle) {
		this.realle = realle;
	}

	public Integer getBonafide() {
		return bonafide;
	}

	public void setBonafide(Integer bonafide) {
		this.bonafide = bonafide;
	}

	
}
