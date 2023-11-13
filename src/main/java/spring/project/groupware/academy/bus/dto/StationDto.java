package spring.project.groupware.academy.bus.dto;

import lombok.*;
import spring.project.groupware.academy.bus.entity.BusEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StationDto {

    private Long id;

//    private List<String> StationList;

    private String busRouteId;
    private String busRouteNm;
    private String seq;             //순번
    private String section;         //구간ID
    private String station;         //정류소 고유 ID
    private String stationNm;       //정류소명
    private String gpsX;            //좌표X (WGS84)
    private String gpsY;            //좌표Y (WGS84)
    private String direction;       //진행방향
    private String stationNo;       //정류소 번호
    private String routeType;
    private String beginTm;         //첫차시간
    private String lastTm;          //막차시간
    private String posX;            //좌표X (GRS80)
    private String posY;            //좌표Y (GRS80)
    private String arsId;           //정류소 번호
    private String transYn;         //(Y:회차, N:회차지아님)	transYn
    private String trnstnid;        //회차지 ID
    private String sectSpd;         //구간속도
    private String fullSectDist;    //구간거리

//    private BusEntity busEntity;


}
