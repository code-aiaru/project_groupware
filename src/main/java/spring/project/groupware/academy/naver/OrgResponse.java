package spring.project.groupware.academy.naver;

import lombok.Data;

import java.util.List;

@Data
public class OrgResponse {

	private List<OrgUnit> orgUnits;
	private ResponseMetaData responseMetaData;

}
