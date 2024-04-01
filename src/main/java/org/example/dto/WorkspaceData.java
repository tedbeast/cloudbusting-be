package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WorkspaceData {
    public long id;
    public long userId;
    public String lab;
    public String url;
    public String containerName;
}
