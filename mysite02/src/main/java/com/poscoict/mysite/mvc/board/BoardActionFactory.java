package com.poscoict.mysite.mvc.board;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("view".equals(actionName)) {
			action = new ViewAction();
		} else if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("updateform".equals(actionName)) {
			action = new UpdateFormAction();
		} else if("update".equals(actionName)) {
			action = new UpdateAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("addform".equals(actionName)) {
			action = new AddFormAction();
		} else if("add".equals(actionName)) {
			action = new AddAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
