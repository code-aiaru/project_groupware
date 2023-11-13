package spring.project.groupware.academy.bus.dto.data;

import lombok.*;
import spring.project.groupware.academy.bus.entity.BusEntity;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemList {

    //버스 기본 정보 조회
    private Long Id;
    private String busRouteAbrv; // 버스번호
    private String busRouteId; // 버스경로 고유번호
    private String corpNm; // 운행회사 전화번호
    private String routeType; // 노선 유형
    private String stStationNm; // 출발역
    private String edStationNm; // 종착,회차역
    private String firstBusTm; // 첫차시간
    private String lastBusTm; // 막차시간
    private String term;
    private String lastBusYn;
    private String lastLowTm;
    private String firstLowTm;
    private String busRouteNm;
    private String length;

    //정류장 조회
//    private List<ItemList> StationList;

    //    private String busRouteId;
//    private String busRouteNm;
    private String seq;             //순번
    private String section;         //구간ID
    private String station;         //정류소 고유 ID
    private String stationNm;       //정류소명
    private String gpsX;            //좌표X (WGS84)
    private String gpsY;            //좌표Y (WGS84)
    private String direction;       //진행방향
    private String stationNo;       //정류소 번호
    //    private String routeType;
    private String beginTm;         //첫차시간
    private String lastTm;          //막차시간
    private String posX;            //좌표X (GRS80)
    private String posY;            //좌표Y (GRS80)
    private String arsId;           //정류소 번호
    private String transYn;         //(Y:회차, N:회차지아님)	transYn
    private String trnstnid;        //회차지 ID
    private String sectSpd;         //구간속도
    private String fullSectDist;    //구간거리


    // 버스 도착시간
    private String arrmsg1;
    private String arrmsg2;
    //    private String arsId;         //정류소 번호
    private String avgCf1;
    private String avgCf2;
    private String brdrde_Num1;
    private String brdrde_Num2;
    private String brerde_Div1;
    private String brerde_Div2;
    //    private String busRouteAbrv;  //노선명 (안내용 – 마을버스 제외)
//    private String busRouteId;    //노선ID
    private String busType1;
    private String busType2;
    private String deTourAt;
    private String dir;
    private String expCf1;
    private String expCf2;
    private String exps1;
    private String exps2;
    private String firstTm;
    private String full1;
    private String full2;
    private String goal1;
    private String goal2;
    private String isArrive1;
    private String isArrive2;
    private String isLast1;
    private String isLast2;
    private String kalCf1;
    private String kalCf2;
    private String kals1;
    private String kals2;
    //    private String lastTm;        //막차시간
    private String mkTm;
    private String namin2Sec1;
    private String namin2Sec2;
    private String neuCf1;
    private String neuCf2;
    private String neus1;
    private String neus2;
    private String nextBus;
    private String nmain2Ord1;
    private String nmain2Ord2;
    private String nmain2Stnid1;
    private String nmain2Stnid2;
    private String nmain3Ord1;
    private String nmain3Ord2;
    private String nmain3Sec1;
    private String nmain3Sec2;
    private String nmain3Stnid1;
    private String nmain3Stnid2;
    private String nmainOrd1;
    private String nmainOrd2;
    private String nmainSec1;
    private String nmainSec2;
    private String nmainStnid1;
    private String nmainStnid2;
    private String nstnId1;
    private String nstnId2;
    private String nstnOrd1;
    private String nstnOrd2;
    private String nstnSec1;
    private String nstnSec2;
    private String nstnSpd1;
    private String nstnSpd2;
    private String plainNo1;
    private String plainNo2;
    private String rerdie_Div1;     //reride_Num1 값의 의미 구분(0: 데이터 없음, 2: 재차인원, 4:혼잡도)
    private String rerdie_Div2;     //reride_Num2 값의 의미 구분(0: 데이터 없음, 2: 재차인원, 4:혼잡도)
    private String reride_Num1;     //재차구분 4일 때 혼잡도(0: 데이터없음, 3: 여유, 4: 보통, 5: 혼잡) 재차구분 2일 때 재차인원 또는 잔여좌석수(routeType = 6) 서울시 광역버스
    private String reride_Num2;     //재차구분 4일 때 혼잡도(0: 데이터없음, 3: 여유, 4: 보통, 5: 혼잡) 재차구분 2일 때 재차인원 또는 잔여좌석수(routeType = 6) 서울시 광역버스
    //    private String routeType;     //노선유형 (1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
    private String rtNm;
    private String sectOrd1;
    private String sectOrd2;
    private String stId;
    private String stNm;
    private String staOrd;          //요청정류소순번
    //    private String term;          //배차간격 (분)
    private String traSpd1;
    private String traSpd2;
    private String traTime1;
    private String traTime2;
    private String vehId1;          //첫번째도착예정버스ID
    private String vehId2;          //두번째도착예정버스ID

    public static ItemList toBusDto(BusEntity busEntityRs) {
        ItemList busDto = new ItemList();
        busDto.setId(busEntityRs.getId());
        busDto.setBusRouteAbrv(busEntityRs.getBusRouteAbrv());
        busDto.setBusRouteId(busEntityRs.getBusRouteId());
        busDto.setCorpNm(busEntityRs.getCorpNm());
        busDto.setStStationNm(busEntityRs.getStStationNm());
        busDto.setEdStationNm(busEntityRs.getEdStationNm());
        busDto.setFirstBusTm(busEntityRs.getFirstBusTm());
        busDto.setLastBusTm(busEntityRs.getLastBusTm());
        busDto.setTerm(busEntityRs.getTerm());
        busDto.setLastBusYn(busEntityRs.getLastBusYn());
        busDto.setLastLowTm(busEntityRs.getLastLowTm());
        busDto.setFirstLowTm(busEntityRs.getFirstLowTm());
        busDto.setBusRouteNm(busEntityRs.getBusRouteNm());
        busDto.setLength(busEntityRs.getLength());
        return busDto;
    }
}