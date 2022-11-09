package com.nexon.flow.web.request;

import com.nexon.flow.domain.dto.Status;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MemberRequest {

    @Data
    public static class Join {
        private String email;
        private String username;
        private String displayname;
        private String password;
        private String directory;
    }

    @Data
    public static class ModifiyMember {
        private Long id;
        private String email;
        private String username;
        private String password;
        private String displayname;
        private String directory;
        private Status status;
        private List<String> roles;
    }

    @Data
    public static class ModifiyPassword {
        private String password;
    }
}
