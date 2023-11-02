package spring.project.groupware.academy.bus.dto.data;

import lombok.*;
import spring.project.groupware.academy.bus.entity.BusEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemList {

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
