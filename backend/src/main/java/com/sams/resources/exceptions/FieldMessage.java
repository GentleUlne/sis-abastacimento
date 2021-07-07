package com.sams.resources.exceptions;

public class FieldMessage {
 private String FieldName;
 private String Message;
      public FieldMessage() {
	  
     }
      
      
      
      
	public FieldMessage(String fieldName, String message) {
		
		FieldName = fieldName;
		Message = message;
	}




	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
      
      
      
}
