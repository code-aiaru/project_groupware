package spring.project.groupware.academy.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrgUnit {
	private int domainId;
	private String orgUnitId;
	private String orgUnitExternalKey;
	private String orgUnitName;
	private OrgUnitI18nName[] i18nNames;
	private String email;
	private String description;
	private boolean visible;
	private String parentOrgUnitId;
	private String parentExternalKey;
	private int displayOrder;
	private int displayLevel;
	private String[] aliasEmails;
	private boolean canReceiveExternalMail;
	private boolean useMessage;
	private boolean useNote;
	private boolean useCalendar;
	private boolean useTask;
	private boolean useFolder;
	private boolean useServiceNotification;
	private OrgUnitAllowedMember[] membersAllowedToUseOrgUnitEmailAsRecipient;
	private OrgUnitAllowedMember[] membersAllowedToUseOrgUnitEmailAsSender;

}
