package uet.example.demoMS;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo {
    private String studentId;
    private String fullName;
    private String address;
    private String className;
    private String dob;
}
