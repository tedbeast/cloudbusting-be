package org.example.controller;

import org.example.dto.WorkspaceData;
import org.example.exception.WorkspaceException;
import org.example.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

@RestController
@CrossOrigin
public class WorkspaceController {
    WorkspaceService workspaceService;
    @Autowired
    public WorkspaceController(WorkspaceService workspaceService){
        this.workspaceService = workspaceService;
    }
    /**@RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Object> cors(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "*");
        headers.add("Access-Control-Allow-Headers", "*");
        return ResponseEntity.ok()
                .headers(headers)
                .body(null);
    }**/
    @PostMapping("/workspace")
    public ResponseEntity<WorkspaceData> postWorkspace(@RequestBody WorkspaceData workspaceData){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://172.191.162.100:3000");
        headers.add("Access-Control-Allow-Methods", "*");
        headers.add("Access-Control-Allow-Headers", "*");
        try{
            WorkspaceData result = workspaceService.createWorkspace(workspaceData);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(result);
        } catch (WorkspaceException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/workspace/{name}")
    public ResponseEntity<WorkspaceData> deleteWorkspace(@PathVariable String name){
        try {
            workspaceService.deleteWorkspace(name);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (WorkspaceException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
