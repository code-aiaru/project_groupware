package spring.project.groupware.academy.bus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.bus.dto.data.ItemList;
import spring.project.groupware.academy.bus.entity.BusEntity;
import spring.project.groupware.academy.bus.entity.StationEntity;
import spring.project.groupware.academy.bus.repository.BusRepository;
import spring.project.groupware.academy.bus.repository.StationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final StationRepository stationRepository;

    @Transactional
    public Map<String, String> busSaveDBMap(List<ItemList> itemList) {
        Map<String, String> response = new HashMap<>();

        for (ItemList item : itemList) {
            Optional<BusEntity> optionalBusEntity = busRepository.findBybusRouteNm(item.getBusRouteNm());
            if(optionalBusEntity.isEmpty()) {
                Long busId = busRepository.save(
                        BusEntity.builder()
                                .busRouteAbrv(item.getBusRouteAbrv())
                                .busRouteId(item.getBusRouteId())
                                .corpNm(item.getCorpNm())
                                .routeType(item.getRouteType())
                                .stStationNm(item.getStStationNm())
                                .edStationNm(item.getEdStationNm())
                                .firstBusTm(item.getFirstBusTm())
                                .lastBusTm(item.getLastBusTm())
                                .term(item.getTerm())
                                .lastBusYn(item.getLastBusYn())
                                .lastLowTm(item.getLastLowTm())
                                .firstLowTm(item.getFirstLowTm())
                                .busRouteNm(item.getBusRouteNm())
                                .length(item.getLength())
                                .build()).getId();

                BusEntity busEntityRs = busRepository.findById(busId).orElseThrow(() -> {
                    throw new IllegalArgumentException("버스번호존재");
                });
                response.put("busId", String.valueOf(busEntityRs.getId()));
                response.put("status", "success");
            }
        }
        return response;
    }

    @Transactional
    public Map<String, String> staionSaveDBMap(List<ItemList> itemList) {
        Map<String, String> response = new HashMap<>();

        for (ItemList item : itemList) {
            // 버스와 정류장으로 확인, 이후에 추가 되거나 수정 하려면 문제가 있음
            Optional<StationEntity> optionalStationEntity = stationRepository.findBybusRouteNmAndSeq(item.getBusRouteNm(),item.getSeq());

            // 겹치는 버스 따라 반복문 계속 진행 ex)111,1113
            if(optionalStationEntity.isEmpty()) {

                Optional<BusEntity> optionalBusEntity = busRepository.findBybusRouteNm(item.getBusRouteNm());

                Long stationId = stationRepository.save(
                        StationEntity.builder()
                                .busRouteId(item.getBusRouteId())   // 버스경로 고유번호    ***
                                .busRouteNm(item.getBusRouteNm())   // 버스 이름    	     ***
                                .seq(item.getSeq())                 // 순번               ***
                                .section(item.getSection())         // 구간 Id
                                .stationNm(item.getStationNm())     // 정류소 명           ***
//                                .gpsX(item.getGpsX())
//                                .gpsY(item.getGpsY())
                                .direction(item.getDirection())
                                .stationNo(item.getStationNo())
                                .station(item.getStation())         // 정류소 고유 Id
//                                .routeType()
//                                .beginTm()
//                                .lastTm()
//                                .posX(item.getPosX())
//                                .posY(item.getPosY())
                                .arsId(item.getArsId())             // 정류소 번호
//                                .transYn()
//                                .trnstnid()
//                                .sectSpd()
//                                .fullSectDist()
//                                .busEntity(optionalBusEntity.get())
                                .build()).getId();

                StationEntity stationEntityRs = stationRepository.findById(stationId).orElseThrow(() -> {
                    throw new IllegalArgumentException("정류장번호존재");
                });
                response.put("StaionId", String.valueOf(stationEntityRs.getStation()));
                response.put("status", "success");
            }
        }
        return response;
    }

}
