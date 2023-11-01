package spring.project.groupware.academy.bus.dto.data;

import lombok.Data;

@Data
public class MsgHeader {

    private String headerMsg;
    private String headerCd;
    private int itemCount;
}
