package spring.project.groupware.academy.bus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.bus.dto.data.ItemList;
import spring.project.groupware.academy.bus.entity.BusEntity;
import spring.project.groupware.academy.bus.repository.BusRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

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
}
