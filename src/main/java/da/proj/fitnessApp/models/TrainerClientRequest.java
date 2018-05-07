package da.proj.fitnessApp.models;

import java.util.Date;

import da.proj.fitnessApp.models.enums.RequestStatusEnum;

public class TrainerClientRequest {
	private Long id;
	private Long trainerId;
	private Long clientId;
	private RequestStatusEnum requestStatus;
	private Date requestSentDate;
	private Date requestAcceptedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public RequestStatusEnum getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(RequestStatusEnum requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Date getRequestSentDate() {
		return requestSentDate;
	}
	public void setRequestSentDate(Date requestSentDate) {
		this.requestSentDate = requestSentDate;
	}
	public Date getRequestAcceptedDate() {
		return requestAcceptedDate;
	}
	public void setRequestAcceptedDate(Date requestAcceptedDate) {
		this.requestAcceptedDate = requestAcceptedDate;
	}
	
	
}
