package org.example.service;

import org.example.dto.WorkspaceData;
import org.example.entity.Workspace;
import org.example.exception.WorkspaceException;
import org.example.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WorkspaceService {
    CMDService cmdService;
    WorkspaceRepository workspaceRepository;

    int portCounter = 7000;

    @Autowired
    public WorkspaceService(CMDService cmdService, WorkspaceRepository workspaceRepository){
        this.cmdService = cmdService;
        this.workspaceRepository = workspaceRepository;
    }
    public WorkspaceData createWorkspace(WorkspaceData requestedWorkspace) throws WorkspaceException {
        if(portCounter == 8000){
            portCounter = 7000;
        }
        portCounter++;
        String[] cmd = new String[]{"docker", "run", "-d",
                "--name=workspace"+portCounter,"-e", "PUID=1000",
                "-e", "PGID=1000", "-e", "TZ=Etc/UTC", "-e",
                "DEFAULT_WORKSPACE=/config/workspace",
                "-p", portCounter + ":8443",
                "-e", "lab="+requestedWorkspace.getLab(),
                "-e", "product_key="+requestedWorkspace.getUserId(),
                "-e", "api_url="+"http://74.249.178.70:9000",
                "--restart", "unless-stopped",
                "exa-vsc:latest"};

        try {
            String out = cmdService.runCommandReturnOutput(cmd);
            System.out.println(out);
            Workspace workspace = new Workspace(requestedWorkspace.getUserId(), requestedWorkspace.getLab(),
                    "http://localhost:"+portCounter, "workspace"+portCounter);
            workspace = workspaceRepository.save(workspace);
            WorkspaceData dto = new WorkspaceData(workspace.getId(), workspace.getUserId(), workspace.getLab(),
                    workspace.getUrl(), workspace.getContainerName());
//          todo- replace this sleep with a more sensible check for container activity
            Thread.sleep(2000);
            out = cmdService.runCommandReturnOutput(new String[] {
                    "docker", "exec", workspace.getContainerName(), "java", "-jar", "labs.jar", "open", "lab"
            });
            System.out.println(out);
            return dto;
        }catch (IOException | InterruptedException e){
            throw new WorkspaceException("issue starting the workspace.");
        }
    }

    public void deleteWorkspace(String container) throws WorkspaceException {
        try{
            String[] cmd = new String[] {"docker", "kill", ""+container};
            cmdService.runCommandReturnOutput(cmd);
        } catch (IOException | InterruptedException e){
            throw new WorkspaceException("issue deleting the workspace");
        }
    }
}
