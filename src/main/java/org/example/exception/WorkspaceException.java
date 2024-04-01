package org.example.exception;

import org.example.service.WorkspaceService;

public class WorkspaceException extends Exception{
    public WorkspaceException(String msg){
        super(msg);
    }
}
