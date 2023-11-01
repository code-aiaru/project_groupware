package spring.project.groupware.academy.bus.dto.data;

import lombok.Data;

@Data
public class ComMsgHeader {

    private String requestMsgID;
    private String responseMsgID;
    private String responseTime;
    private String returnCode;
    private String errMsg;
    private String successYN;
}
