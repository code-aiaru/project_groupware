package spring.project.groupware.academy.bus.dto.data;

import lombok.Data;

@Data
public class BusJson {

    private ComMsgHeader comMsgHeader;
    private MsgHeader msgHeader;
    private MsgBody msgBody;
}
