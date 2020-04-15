package com.red.testframework.testconfiguration;
//
//        import java.io.FileNotFoundException;
//        import java.io.IOException;
//        import java.util.Arrays;
//        import java.util.HashMap;
//        import java.util.Hashtable;
//        import java.util.Iterator;
//        import java.util.List;
//        import java.util.Map;
//        import java.util.Random;
//
//        import org.apache.log4j.Logger;
//        import org.jdom.Element;
//
//
public class WebApp {
}
//    private ApiSession breezeSession;
//    //from user
//    public WebApp(String url) {
//        breezeSession = new ApiSession(url);
//
//    }
//    //from config reader
//    public WebApp() throws FileNotFoundException, IOException, Exception
//    {
//
//        iniObj=new INIFile();
//        breezeSession=new ApiSession(iniObj.getStringProperty("sut", "server"));
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //utilities
//    private String generateMeetingName()
//    {
//        return "TestMeeting"+Integer.toString((new Random()).nextInt(30000));
//    }
//    /**
//     * @author akajain
//     * @throws Failure
//     */
//    public void login() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("sut", "login"), iniObj.getStringProperty("sut", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public void adminLogin() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("adminuser", "login"), iniObj.getStringProperty("passwords", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public String createMeetingWithName(String meetingName) throws Exception
//    {
//        String meetingSco = createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//
//        String meetingURL = iniObj.getStringProperty("sut", "server");//"http://" + getDomainName();
//        return meetingURL + "/" + meetingName;
//    }
//    public String createMeetingWithNameScoReturn(String meetingName) throws Exception
//    {
//        String meetingSco = createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//
//        String meetingURL = iniObj.getStringProperty("sut", "server");//"http://" + getDomainName();
//        return meetingSco;
//    }
//    public void CommonUserLogin() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("sut", "login"), iniObj.getStringProperty("sut", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void logout() throws Failure
//    {
//        try
//        {
//            breezeSession.logout();
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Logout Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public String createProtectedMeeting() throws Exception
//    {
//
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//
//
//    private  String createSimpleMeeting(String meetingName) throws Exception
//    {
//        String scoId = null;
//        String tempId = null;
//        String meetingTemplate = null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//
//        org.jdom.Element resp = breezeSession.apiPost("sco-shortcuts", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco");
//        }
//        List<Element> scoList = resp.getChild("shortcuts").getChildren("sco");
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getAttribute("type").getValue().equalsIgnoreCase("shared-meeting-templates"))
//            {
//                tempId = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"shared-meeting-templates  Sco id:"+scoId);
//            }
//            if(sco1.getAttribute("type").getValue().equalsIgnoreCase("my-meetings"))
//            {
//                scoId = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"my-meetings folder Sco id:"+scoId);
//                break;
//            }
//        }
//
//        if(tempId==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Could not find template");
//        }
//        if(scoId==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Could not find my-meetings folder");
//        }
//
//        Map<String, String[]> updateParams1 = new HashMap<String, String[]>();
//        updateParams1.put("sco-id", new String[] {tempId});
//        resp = breezeSession.apiPost("sco-contents", updateParams1);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco-contents");
//        }
//
//        List<Element> content = resp.getContent();
//        org.jdom.Element scoContent = content.get(1);
//        scoList = scoContent.getContent();
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getChild("name").getValue().equalsIgnoreCase("Default Meeting Template"))
//            {
//                meetingTemplate = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+meetingTemplate);
//                break;
//            }
//        }
//        if(meetingTemplate==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Sco id for Default meeting template is null");
//        }
//
//        //Create Meeting
//
//
//
//        Map<String, String[]> updateParams2 = new HashMap<String, String[]>();
//        updateParams2.put("type", new String[] { "meeting" });
//        updateParams2.put("name", new String[] { meetingName });
//        updateParams2.put("folder-id", new String[] { scoId });
//        updateParams2.put("lang", new String[] { iniObj.getStringProperty("lang", "locale") });
//        if (meetingTemplate != "0") {
//            updateParams2.put("source-sco-id", new String[] { meetingTemplate });
//        }
//        updateParams2.put("url-path", new String[] { meetingName });
//        resp = breezeSession.apiPost("sco-update", updateParams2);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            System.out.println("Response : " + resp.getChild("status").getAttributeValue("code"));
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating meeting");
//        }
//
//        content = resp.getContent();
//        scoContent = content.get(1);
//        scoId = scoContent.getAttribute("sco-id").getValue();
//        log.info("Meeting ScoID"+scoId);
//        return scoId;
//    }
//
//
//
//
//    public boolean startRecording(String scoId) throws Exception
//    {
//
//
//
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"true"});
//        updateParams.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//    public boolean startRecording(String scoId,String recordingName) throws Exception {
//
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"true"});
//        updateParams.put("sco-id", new String[] {scoId});
//        updateParams.put("name", new String[] {recordingName});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//
//    public boolean EnableDisableMP4Encoder(String accountID, String enable) throws Exception {
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("account-id",new String[]{accountID});
//        updateParams.put("enable", new String[] {enable});
//        updateParams.put("feature-id", new String[] {"fid-mp4-encoder-disabled"});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-feature-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//
//    public boolean stopRecording(String scoId) throws Exception	{
//
//        //String scoid=getMeetingScoID(meetingURL);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"false"});
//        updateParams.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the recording");
//        }
//        return true;
//    }
//
//
//    public int getNumberOfTelephonyProfiles() throws Exception
//    {
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-list",null);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while retrieving telephony profiles");
//        }
//        return resp.getChild("telephony-profiles").getChildren("profile").size();
//
//    }
//
//    public String createPlainTelephonyProvider(String providerName) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("status", new String[] {"enabled"});
//        params.put("name", new String[] {providerName});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-provider-update",params);
//
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating telephony provider");
//        }
//        return  resp.getChild("telephony-provider").getAttributeValue("provider-id");
//    }
//
//    public String createPlainTelephonyProfile(String profileName, String providerId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("status", new String[] {"enabled"});
//        params.put("profile-name", new String[] {profileName});
//        params.put("provider-id", new String[] {providerId});
//
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-update",params);
//
//        System.out.println(resp.toString());
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        System.out.println(resp.getText());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating telephony profile");
//        }
//        return  resp.getChild("telephony-profile").getAttributeValue("profile-id");
//    }
//
//    public void deletePlainTelephonyProvider(String providerId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("provider-id", new String[] {providerId});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-provider-delete",params);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting telephony provider");
//        }
//    }
//    public void deletePlainTelephonyProfile(String profileId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("profile-id", new String[] {profileId});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-delete",params);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting telephony profile");
//        }
//    }
//
//    public String checkFortimeZoneInfoDisplayedUnderTrainingCatalogForVirtualClass(String scoIdOfFolder, String virtualClassName) throws Exception
//    {
//
//
//        String timeZoneId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoIdOfFolder});
//
//        updateParams.put("filter-icon", new String[] {"virtual-classroom"});
//
//        updateParams.put("trainingCatalog", new String[] {"true"});
//        org.jdom.Element resp = breezeSession.apiPost(
//                "sco-contents", updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure("Response error while checking the time zone info");
//
//        }
//
//
//
//        List<Element> scoList = resp.getChild("scos").getChildren("sco");
//
//        timeZoneId=scoList.toString();
//        Iterator<Element> scoIterator = scoList.iterator();
//        while (scoIterator.hasNext()) {
//            org.jdom.Element sco = scoIterator.next();
//            String hereName = sco.getChildText("name");
//            if (virtualClassName.equals(hereName)) {
//                timeZoneId = sco.getAttributeValue("time-zone-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//
//        if (timeZoneId == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + virtualClassName + " not found.");
//            throw new Failure("Asset" + timeZoneId + " not found.");
//        }
//
//        return timeZoneId;
//
//    }
//
//
//
//    public String getPrincipalIdOfUser(String logIn)throws Exception
//    {
//
//        String principalID=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] { logIn });
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-list",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error while displaying the principal-list");
//
//        }
//
//        List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//
//        Iterator<Element> prinIterator = principalList.iterator();
//
//        while (prinIterator.hasNext()) {
//            org.jdom.Element sco = prinIterator.next();
//            String logInXML = sco.getChildText("login");
//            if (logIn.equals(logInXML)) {
//                principalID = sco.getAttributeValue("principal-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//        if (principalID == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + logIn + " not found.");
//            throw new Failure("Asset" + principalID + " not found.");
//        }
//
//
//
//        return principalID;
//    }
//
//
//
//    private void makeUserHost(String meetingSco) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        String userId= resp.getChild("common").getChild("user").getAttribute("user-id").getValue();
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {userId});
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("permission-id", new String[] {"host"});
//        resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while making user the meeting host");
//        }
//    }
//    private void makeMeetingPublic(String meetingSco) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("principal-id", new String[] {"public-access"});
//        updateParams.put("permission-id", new String[] {"view-hidden"});
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating meeting");
//        }
//    }
//    public String createMeeting() throws Exception
//    {
//        return createOpenMeeting();
//    }
//    public String createOpenMeeting() throws Exception
//    {
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    public String createOpenMeetingWithName(String meetingName) throws Exception
//    {
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    private void attachTelephonyProfile(String meetingSco,String profileID) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("inherit-telephony-settings", new String[] {"true"});
//        updateParams.put("field-id", new String[] {"telephony-profile"});
//        updateParams.put("value", new String[] {profileID});
//        org.jdom.Element resp = breezeSession.apiPost("acl-field-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            System.out.println("Response : " + resp.getChild("status").getAttributeValue("code"));
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while attaching  profile");
//        }
//    }
//    public String getDomainName() throws Exception
//    {
//        org.jdom.Element resp = breezeSession.apiPost("report-my-meetings", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Exception("Response error while creating sco-contents");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success");
//        return resp.getChild("my-meetings").getChild("meeting").getChildText("domain-name");
//    }
//    public void deleteMeeting(String meetingURL) throws Exception
//    {
//        String scoid=getMeetingScoID(meetingURL);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteSco(String scoid) throws Exception
//    {
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //change manager of a user
//    public void changeTeamMembership(String userLogin,String managerLogin) throws Exception
//    {                String pricipalId=getUserPrincipalID(userLogin);
//        String managerPrincipalId=getUserPrincipalID(managerLogin);;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("manager-id", new String[] {managerPrincipalId});
//        updateParams.put("principal-id", new String[] {pricipalId});
//        org.jdom.Element resp = breezeSession.apiPost("team-membership-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while changing manager");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //change manager of a user
//    public void removeManager(String userLogin) throws Exception
//    {                String pricipalId=getUserPrincipalID(userLogin);
//        String managerPrincipalId=getAccountId();
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("manager-id", new String[] {managerPrincipalId});
//        updateParams.put("principal-id", new String[] {pricipalId});
//        org.jdom.Element resp = breezeSession.apiPost("team-membership-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while changing manager");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public void disableChatandQAPods() throws Exception
//    {
//        updateCompliance("fid-meeting-chat","false");
//        updateCompliance("fid-meeting-im","false");
//        updateCompliance("fid-meeting-questions","false");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void enableChatandQAPods() throws Exception
//    {
//        updateCompliance("fid-meeting-chat","true");
//        updateCompliance("fid-meeting-im","true");
//        updateCompliance("fid-meeting-questions","true");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private void updateCompliance(String feature_id,String state) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {iniObj.getStringProperty("sut", "accountid") });
//        updateParams.put("feature-id", new String[] { feature_id});
//        updateParams.put("enable", new String[] { state });
//        org.jdom.Element resp = breezeSession.apiPost("meeting-feature-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while updating meeting compliance feature");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private String getProfileID(String profileName) throws Exception
//    {
//        String profileID=null;
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-list", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Exception("Response error while finding telephony profile sco");
//        }
//        List<org.jdom.Element> profiles=resp.getChild("telephony-profiles").getChildren("profile");
//        Iterator<Element> profileIterator=profiles.iterator();
//        while(profileIterator.hasNext())
//        {
//            org.jdom.Element profile=profileIterator.next();
//            if(profileName.equals(profile.getChildText("profile-name")))
//            {
//                profileID=profile.getAttributeValue("profile-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(profileID==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure."+profileName+" not found");
//            throw new Failure(profileName+" not found!!!");
//        }
//        return profileID;
//    }
//    public String getMeetingScoID(String meetingURL) throws Exception
//    {
//        String scoid=null;
//        String meetingName=meetingURL.substring(meetingURL.lastIndexOf('/'))+"/";
//        org.jdom.Element resp = breezeSession.apiPost("report-my-meetings", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the meeting "+meetingName+" scoid");
//        }
//        List<Element> meetings=resp.getChild("my-meetings").getChildren("meeting");
//
//        Iterator<Element> meetingIterator=meetings.iterator();
//        while(meetingIterator.hasNext())
//        {
//            org.jdom.Element meeting=meetingIterator.next();
//            if(meetingName.equalsIgnoreCase(meeting.getChildText("url-path")))
//            {
//                scoid=meeting.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Meeting "+meetingName+" not found.");
//            throw new Failure("Meeting "+meetingName+" not found.");
//        }
//        return scoid;
//    }
//    public String getUserPrincipalID(String userLoginEmail) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] {userLoginEmail });
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting "+userLoginEmail+" PrincipalID");
//        }
//        return resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//    }
//    private String getGuestPrincipalID(String userLoginEmail) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] {userLoginEmail });
//        org.jdom.Element resp = breezeSession.apiPost("report-bulk-users", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting "+userLoginEmail+" PrincipalID");
//        }
//        return resp.getChild("report-bulk-users").getChild("row").getAttributeValue("principal-id");
//    }
//    public void deleteGuest(String userLoginEmail) throws Exception
//    {
//        String principalID=getGuestPrincipalID(userLoginEmail);
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {principalID });
//        org.jdom.Element resp = breezeSession.apiPost("principals-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting "+userLoginEmail);
//        }
//    }
//    public String createTelephonyMeeting(String profileName) throws Exception
//    {
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String profileID=getProfileID(profileName);
//        attachTelephonyProfile(meetingSco, profileID);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    public void deleteTelephonyProfile(String profileName) throws Exception
//    {
//        String profileID=getProfileID(profileName);
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("profile-id", new String[] {profileID });
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting profile");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private void addUserToSco(String scoId,String userLoginEmail,String role) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String principalID=getUserPrincipalID(userLoginEmail);
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {principalID});
//        updateParams.put("acl-id", new String[] {scoId});
//        updateParams.put("permission-id", new String[] {role});
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while adding "+userLoginEmail+"to the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @param url New URL to set the login page help link to point to (must include http://)
//     * @throws Exception
//     */
//    public void setCustomLoginPageHelpLink(String url) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-login-help-link"});
//        updateParams.put("value", new String[] {url});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to set custom url for login page help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @param url New URL to set the CPS webapp help links to point to (must include http://)
//     * @throws Exception
//     */
//    public void setCustomWebAppHelpLink(String url) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-webapp-help-link"});
//        updateParams.put("value", new String[] {url});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to set custom url for webapp help links.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomWebAppHelpLink() throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-webapp-help-link"});
//        updateParams.put("value", new String[] {""});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to clear webapp help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomLoginPageHelpLink() throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-login-help-link"});
//        updateParams.put("value", new String[] {""});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to clear login page help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomHelpLinks() throws Exception
//    {
//        clearCustomWebAppHelpLink();
//        clearCustomLoginPageHelpLink();
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsEnrolled(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "view";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsDenied(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "denied";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsPendingApproval(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "pending";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsHost(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsHost(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsPresenter(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsPresenter(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsParticipant(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsParticipant(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsPendingApproval(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "pending");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToMeetingAsHost(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "host");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addUserToMeetingAsPresenter(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "mini-host");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addUserToMeetingAsParticipant(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void denyScoAccessToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "denied");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    public void allowScoViewToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void allowScoPublishToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "publish");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void allowScoManageToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "manage");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void denyMeetingAccessToUser(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "remove");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addAsLearner(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public String getRecordingUrlPath(String meetingURL, String recordingName) throws Exception{
//        return getRecordingUrlPath(meetingURL, recordingName, 1);
//    }
//    private String getRecordingUrlPath(String meetingURL, String recordingName ,int tries) throws Exception
//    {
//        String scoId = getMeetingScoID(meetingURL);
//        String recordingURL =null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoId});
//        updateParams.put("filter-icon", new String[] {"archive"});
//        org.jdom.Element resp = breezeSession.apiPost("sco-contents", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting recordings of the meeting "+meetingURL);
//        }
//        List<Element> content = resp.getContent();
//        org.jdom.Element scos = content.get(1);
//        List<Element> scoList = scos.getContent();
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();) {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getChild("name").getValue().equalsIgnoreCase(recordingName))
//            {
//                if(sco1.getChild("date-end")==null&&tries<=10)
//                {
//                    Thread.sleep(1000);
//                    return getRecordingUrlPath(meetingURL, recordingName, tries+1);
//                }
//                else if(sco1.getChild("date-end")!=null)
//                {
//                    String recURL = sco1.getChild("url-path").getValue();
//                    recordingURL = "http://"+getDomainName()+recURL;
//                    return recordingURL;
//                }
//                else if(tries>10)
//                {
//                    log.error((new Exception().getStackTrace()[0].getMethodName())+"Failed Recording not prepared yet" );
//                    throw new Failure(recordingName+" Recording not prepared yet!!");
//                }
//            }
//        }
//        log.error((new Exception().getStackTrace()[0].getMethodName())+"Failed "+recordingName+"Recording not present" );
//        throw new Failure(recordingName+" Recording not present!!");
//    }
//
//    public String getScoID(String scoName) throws Exception
//    {
//        String scoid=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("query", new String[] {scoName});
//        org.jdom.Element resp = breezeSession.apiPost("sco-search-by-field", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the Asset  "+scoName+" scoid");
//        }
//        List<Element> scoList=resp.getChild("sco-search-by-field-info").getChildren("sco");
//        Iterator<Element> scoIterator=scoList.iterator();
//        while(scoIterator.hasNext())
//        {
//            org.jdom.Element sco=scoIterator.next();
//            String hereName=sco.getChildText("name");
//            if(scoName.equals(hereName))
//            {
//                scoid=sco.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Asset "+scoName+" not found.");
//            throw new Failure("Asset"+scoName+" not found.");
//        }
//
//        return scoid;
//    }
//
//
//
//
//
//    public String getServerDateTimeID() throws Exception
//    {
//        String dateTime=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common= resp.getChild("common");
//        org.jdom.Element date = common.getChild("date");
//        dateTime=date.getText();
//        return dateTime;
//    }
//    public String getAccountId() throws Exception
//    {
//        String accountId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common= resp.getChild("common");
//        org.jdom.Element accIdNode = common.getChild("account");
//        accountId=accIdNode.getAttributeValue("account-id");
//
//        return accountId;
//    }
//    public Map<String, String> getQuizDescription(String scoID) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoID});
//        org.jdom.Element resp = breezeSession.apiPost("report-asset-interaction-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the quizes for sco-id "+scoID);
//        }
//        List<Element> interactionList=resp.getChild("report-asset-interactions").getChildren("interaction");
//        Iterator<Element> interactionIterator=interactionList.iterator();
//        Map<String,String> interactionMap=new HashMap<String, String>();
//        while(interactionIterator.hasNext())
//        {
//            org.jdom.Element interaction=interactionIterator.next();
//            String interactionID=interaction.getAttributeValue("interaction-id");
//            String desciption=interaction.getChildText("description");
//            interactionMap.put(interactionID, desciption);
//        }
//        return interactionMap;
//    }
//
//    public String createGroup(String name,String accountId, String description) throws Exception
//    {
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("account-id", new String[] {accountId});
//        Params.put("name", new String[] {name});
//        Params.put("description", new String[] {description});
//        Params.put("has-children", new String[] {"1"});
//        Params.put("type", new String[] {"group"});
//
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            if(("duplicate".equalsIgnoreCase(resp.getChild("status").getChild("invalid").getAttributeValue("subcode"))))
//            {
//                log.info("Group already exists.");
//                return "duplicate";
//            }
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco: Creation of following group failed. ");
//        }else
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            log.info("Group succesfully created.");
//        }
//        return newUserPrincipalId;
//
//    }
//    public String createUser(String fname, String lname, String login, String email, String password,String ...sendMail) throws Exception
//    {
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("first-name", new String[] {fname});
//        Params.put("last-name", new String[] {lname});
//        Params.put("login", new String[] {login});
//        Params.put("password", new String[] {password});
//        Params.put("type", new String[] {"user"});
//        if(sendMail.length==0)
//            Params.put("send-email", new String[] { "false" });
//        else
//            Params.put("send-email", new String[] { sendMail[0]});
//        Params.put("has-children", new String[] {"0"});
//        Params.put("email", new String[] {email});
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            if(("duplicate".equalsIgnoreCase(resp.getChild("status").getChild("invalid").getAttributeValue("subcode"))))
//            {
//                log.info("User "+login+" already exists.");
//                return "duplicate";
//            }
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco: Creation of following "+login+"failed. ");
//        }else
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            log.info("User "+login+" succesfully created.");
//        }
//        return newUserPrincipalId;
//
//    }
//    public void makeUserAdmins(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting admin group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user admin permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned admin permission to user.");
//            }
//        }
//    }
//
//    public void makeUserAuthors(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"authors"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting author group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user author permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned author permission to user.");
//            }
//        }
//
//    }
//    public void makeUserTrainingManager(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"course-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Training Manager group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                //throw new Failure("Response error while creating giving user Training Manager permissions");
//                return;
//            }
//            else
//            {
//                log.info(" Succesfully assigned Training Manager permission to user.");
//            }
//        }
//
//    }
//    public void makeUserEventManager(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"event-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Event Manager group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Event Manager permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Event Manager permission to user.");
//            }
//        }
//
//    }
//    public void makeUserEventAdministrator(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"event-super-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Event Administrator group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Event Administrator permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Event Administrator permission to user.");
//            }
//        }
//
//    }
//    public void makeUserLearners(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"learners"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Learners group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Learners permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Learners permission to user.");
//            }
//        }
//
//    }
//    public void makeUserMeetingHost(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"live-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting meeting hosts group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user meeting hosts permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned meeting hosts permission to user.");
//            }
//        }
//
//    }
//
//    public boolean selfEnroll(
//            String scoOfTrgObj) throws Exception {
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] { scoOfTrgObj });
//
//
//
//
//        org.jdom.Element resp = breezeSession.apiPost("self-enroll",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error self enrolling");
//
//        }
//
//
//        return true;
//
//
//
//    }
//
//
//
//    public boolean selfUnEnroll(
//            String scoOfTrgObj) throws Exception {
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] { scoOfTrgObj });
//        updateParams.put("withdrawEnrollment", new String[] { "true" });
//
//
//
//
//
//        org.jdom.Element resp = breezeSession.apiPost("self-enroll",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error self un-enrolling");
//
//        }
//
//
//        return true;
//
//
//
//    }
//    public boolean changePermissionsVCMakeLearnerParticipant(String logIn,String scoIDOfTrg) throws Exception
//    {
//        boolean haschanged=false;
//        String principalID=getPrincipalIdOfUser(logIn);
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] { scoIDOfTrg});
//        updateParams.put("principal-id", new String[] { principalID});
//        updateParams.put("permission-id",new String[] { "view" });
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error changinng the permissions");
//        }
//
//        else
//            haschanged=true;
//
//
//        return haschanged;
//
//    }
//
//
//    public boolean changePermissionsVCDenyLearner(String logIn,String scoIDOfTrg) throws Exception
//    {
//        boolean hasdenied=false;
//        String principalID=getPrincipalIdOfUser(logIn);
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] { scoIDOfTrg});
//        updateParams.put("principal-id", new String[] { principalID});
//        updateParams.put("permission-id",new String[] { "denied" });
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error denying the learner");
//        }
//
//        else
//            hasdenied=true;
//
//
//        return hasdenied;
//
//    }
//
//
//
//    public String checkPermissionsOnASco(String logIn, String scoIdOfObject) throws Exception
//    {
//        String status=null;
//        //String principalid= getPrincipalIdOfUser(logIn);
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        updateParams.put("acl-id", new String[] { scoIdOfObject});
//        //updateParams.put("pricipal-id", new String[] { principalid});
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-info",
//                updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error fetching the permissions info");
//        }
//
//        List<Element> principalList = resp.getChild("permissions").getChildren("principal");
//
//        Iterator<Element> prinIterator = principalList.iterator();
//
//        while (prinIterator.hasNext()) {
//            org.jdom.Element sco = prinIterator.next();
//            String logInXML = sco.getChildText("login");
//            if (logIn.equals(logInXML)) {
//                status = sco.getAttributeValue("permission-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//
//
//
//        return status;
//
//
//
//
//    }
//
//
//
//
//
//
//    public int getNumberOfTrgsOfAName(String trgName) throws Exception
//    {
//        int number_of_trgs=0;
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        updateParams.put("filter-name", new String[] { trgName});
//        org.jdom.Element resp = breezeSession.apiPost("report-my-training",
//                updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error changinng the permissions");
//        }
//
//        List<Element> trgList = resp.getChild("report-my-training").getChildren("row");
//
//        Iterator<Element> trgIterator = trgList.iterator();
//
//        while (trgIterator.hasNext()) {
//            org.jdom.Element sco = trgIterator.next();
//            String NameInXML = sco.getChildText("name");
//            if (trgName.equals(NameInXML)) {
//
//                number_of_trgs=number_of_trgs+1;
//
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//
//
//            }
//        }
//
//        return number_of_trgs;
//    }
//
//
//    public void loginasEndUseronHosted(String userName, String password) throws Failure {
//        try {
//            breezeSession.login(userName, password);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//    }
//
//    public void makeUserSeminarHost(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"seminar-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting seminar hosts group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user seminar hosts permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned seminar hosts permission to user.");
//            }
//        }
//    }
//
//    public String createCustomField(String objectType,String perissionId, String accountId, String name, String comment,String filedType, String isRequired , String isPrimary) throws Exception{
//
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("object-type", new String[] {objectType});
//        Params.put("permission-id",new String[] {perissionId});
//        Params.put("account-id", new String[]{accountId});
//        Params.put("name", new String[]{name});
//        Params.put("comments", new String[]{comment});
//        Params.put("field-type", new String[]{filedType});
//        Params.put("is-required",new String[] {isRequired});
//        Params.put("is-primary",new String[] {isPrimary});
//        String customFieldId=null;
//
//        org.jdom.Element resp = breezeSession.apiPost("custom-field-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return null;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating custom-field, action failed. ");
//
//        }
//        else
//        {
//
//            log.info("custom field succesfully created.");
//            customFieldId=resp.getChild("field").getAttributeValue("field-id").toString();
//        }
//        return customFieldId;
//    }
//    public boolean deleteCustomFiled(String objectType,String fieldId) throws Exception{
//
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("object-type", new String[] {objectType});
//
//        Params.put("field-id",new String[] {fieldId});
//
//
//        org.jdom.Element resp = breezeSession.apiPost("custom-fields-delete", Params);
//        log.info(resp.toString());
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return false;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating custom-field, action failed. ");
//
//        }
//        else
//        {
//
//            log.info("custom field deleted created.");
//            return true;
//
//        }
//
//    }
//    public String getMp4RecordingJobStatus(String mp4RecordingName) throws Exception{
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("filter-name", new String[] {mp4RecordingName});
//
//        String jobStatus=null;
//
//        org.jdom.Element resp = breezeSession.apiPost("get-mp4-encoder-queue", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return null;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error , action failed. ");
//
//        }
//        else
//        {
//
//            log.info("succesfully .");
//            List<Element> recordingJobs=resp.getChild("mp4-encoder-queue").getChildren("recording-job");
//            for(int i=0;i<recordingJobs.size();i++){
//                if(recordingJobs.get(i).getChild("name").getValue().equals(mp4RecordingName)){
//                    return recordingJobs.get(i).getAttributeValue("job-status");
//                }
//            }
//        }
//        return null;
//    }
//
//    //	public String getMp4RecordingJobStatus(String mp4RecordingName) throws Exception{
//    //		Map<String, String[]> Params = new HashMap<String, String[]>();
//    //		String jobId=getMp4RecordingJobId(mp4RecordingName);
//    //		Params.put("job-id", new String[] {jobId});
//    //
//    //		String jobStatus=null;
//    //
//    //		org.jdom.Element resp = breezeSession.apiPost("get-recording-job", Params);
//    //		if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//    //		{
//    //				return null;
//    //		}
//    //		if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//    //		{
//    //
//    //			log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//    //			throw new Failure("Response error , action failed. ");
//    //
//    //		}
//    //		else
//    //    	{
//    //
//    //			log.info("api call succesfull");
//    //			jobStatus=resp.getChild("recording-job").getAttributeValue("job-status").toString();
//    //    	}
//    //		return jobStatus;
//    //	}
//    //
//
//    public void loginWithWrongPassword(String login, String password, String accountId, String codes, String subcode) throws Failure{
//        try{
//
//
//            Map<String, String[]> params = new HashMap<String, String[]>();
//            params.put("login", new String[]{login});
//            params.put("password", new String[]{password});
//            if(accountId!=null && !"".equals(accountId))
//                params.put("account-id", new String[]{accountId});
//            Element resp = breezeSession.apiPost("login", params);
//            System.out.println(resp.getChild("status").getAttributeValue("code"));
//            if(codes.equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))
//            {
//                System.out.println("Login failed!");
//            }
//            else if(codes.equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")) && subcode!=null)
//            {
//                System.out.println(resp.getChild("status").getAttributeValue("subcode"));
//                if(subcode.equalsIgnoreCase(resp.getChild("status").getAttributeValue("subcode")))
//                {
//                    System.out.println("User Suspended!");
//                }
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteUser(String login) throws Failure
//    {
//        try{
//
//
//            Map<String, String[]> params = new HashMap<String, String[]>();
//
//            String principalID = getPrincipalIdOfUser(login);
//            params.put("principal-id", new String[]{principalID});
//            Element resp = breezeSession.apiPost("principals-delete", params);
//            System.out.println(resp.getChild("status").getAttributeValue("code"));
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//
//            }
//
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not delete: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    //public void createCustomTemplate(String templateName,List<String> sequenceNumbers,List<String> headerIds,List<String> customLabels, String excludeHeader, String reportType) throws Failure
//    public void createCustomTemplate(String templateName, String reportType) throws Failure
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("template-name", new String[]{templateName});
//
//		/*String[] value = new String[sequenceNumbers.size()];
//			for (int i = 0; i < sequenceNumbers.size(); i++)
//			{
//				value[i] = sequenceNumbers.get(i);
//
//			}
//			params.put("column-sequence-number", value);
//
//			String[] value1 = new String[headerIds.size()];
//			for (int i = 0; i < headerIds.size(); i++)
//			{
//				value1[i] = headerIds.get(i);
//			}
//			params.put("header-id", value1);
//
//			String[] value2 = new String[customLabels.size()];
//			for (int i = 0; i < customLabels.size(); i++)
//			{
//				value2[i] = customLabels.get(i);
//			}
//			params.put("header-custom-name", value2);*/
//
//        params.put("report-type", new String[]{reportType});
//
//        //params.put("exclude-header",new String[] {excludeHeader});
//
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-template-update", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not create custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteTemplate(String templateName) throws Failure
//    {
//        String templateId=getTemplateId(templateName);
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("template-id", new String[]{templateId});
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-template-delete", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not delete custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    private String getTemplateId(String templateName) throws Failure
//    {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params = null;
//
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-templates-list", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//            else
//            {
//                if(resp.getChild("template-name").equals(templateName))
//                    return resp.getAttributeValue("template-id");
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not create custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//
//        return null;
//    }
//
//    /**
//     * @param enable
//     * @throws Failure
//     */
//    public void changeLoginPwPolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("change-login-pw-policy", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public void deleteUserPrincipalId(String principalId) throws Exception {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("principal-id", new String[] { principalId });
//        org.jdom.Element resp = breezeSession.apiPost("principals-delete",
//                Params);
//        if (("no-data".equalsIgnoreCase(resp.getChild("status")
//                .getAttributeValue("code")))) {
//            throw new Failure("No such id exists ");
//        }
//
//    }
//    public void changeCustomizePolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("customization", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public void changeModifyAddUserPolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("modify-current-users-groups", new String[]{"true"} );
//        params.put("add-users-groups-webui", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public String getPrincipalIdSystemGroup(String groupType)throws Failure{
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        String groupId = null;
//        INIFile objINI = new INIFile();
//        try {
//            Params.put("filter-type", new String[] {groupType});
//            org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//            if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                    "code")))) {
//
//                log.error("Response : "
//                        + resp.getChild("status").getAttributeValue("code"));
//
//                throw new Failure("Response error while getting group ID");
//            } else {
//                groupId = resp.getChild("principal-list").getChild("principal")
//                        .getAttributeValue("principal-id");
//            }
//        }catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return groupId;
//    }
//
//    public void addUserToGroup(String principalId,String groupId) throws Exception {
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//
//        updateGroupParams.put("group-id", new String[] { groupId });
//        updateGroupParams.put("principal-id", new String[] { principalId });
//        updateGroupParams.put("is-member", new String[] { "true" });
//
//        org.jdom.Element resp2 = breezeSession.apiPost(
//                "group-membership-update", updateGroupParams);
//        if (!("ok".equalsIgnoreCase(resp2.getChild("status")
//                .getAttributeValue("code")))) {
//
//            log.error("Response : "
//                    + resp2.getChild("status").getAttributeValue("code"));
//
//            throw new Failure("Response error while adding user to group!!");
//        } else {
//            log.info(" Succesfully added user to group.");
//        }
//    }
//
//
//    public void verifycreateUserNotAuthorized(String fname, String lname, String login, String email, String password) throws Exception
//    {
//
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("first-name", new String[] {fname});
//        Params.put("last-name", new String[] {lname});
//        Params.put("login", new String[] {login});
//        Params.put("password", new String[] {password});
//        Params.put("type", new String[] {"user"});
//        Params.put("send-email", new String[] {"false"});
//        Params.put("has-children", new String[] {"0"});
//        Params.put("email", new String[] {email});
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(!("no-access".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            deleteUser(newUserPrincipalId);
//            throw new Failure("User is authorized to create new users!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//
//    }
//    public void verifyDeleteUserNotAuthorized(String login) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//
//        String principalID = getPrincipalIdOfUser(login);
//        params.put("principal-id", new String[]{principalID});
//        Element resp = breezeSession.apiPost("principals-delete", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("no-access".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("User is authorized to delete users!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//
//
//    }
//
//    public String getGroupPrincipalIdAPI(String GroupName) throws Failure {
//        String principal_id = null;
//        INIFile objINI = new INIFile();
//        String sAction = "permissions-info";
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("acl-id", new String[]{objINI.getStringProperty("sut", "accountid")} );
//            hParams.put("filter-name", new String[]{GroupName} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else
//            {
//                principal_id = resp.getChild("permissions").getChild("principal").getAttributeValue("principal-id");
//            }
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return principal_id;
//    }
//
//    public void updateGroupMembershipOfUser(String addOrRemoveGroupBoolean, String sAdminGroupId, String sUserId) throws Failure
//    {
//        String sAction = "group-membership-update";
//        try
//        {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("group-id", new String[]{sAdminGroupId});
//            hParams.put("principal-id", new String[]{sUserId});
//            hParams.put("is-member", new String[]{addOrRemoveGroupBoolean});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Could not update group membership of user!!");
//            }
//        }
//        catch (Exception e)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())+ "Success");
//    }
//
//    public boolean verifyUserInGroup(String groupPrincipalID, String userPrincipalID) throws Failure
//    {
//        String sAction = "principal-list";
//        String principalID = null;
//        boolean exists = false;
//        try
//        {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.clear();
//            hParams.put("group-id", new String[]{groupPrincipalID});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Could not retrieve group list!!");
//            }
//            else
//            {
//                List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//                Iterator<Element> prinIterator = principalList.iterator();
//                while (prinIterator.hasNext())
//                {
//                    org.jdom.Element sco = prinIterator.next();
//                    principalID = sco.getAttributeValue("principal-id");
//                    if(principalID.equals(userPrincipalID))
//                    {
//                        if(sco.getChild("is-member").getValue().equals("true"))
//                        {
//                            exists = true;
//                            break;
//                        }
//                        else
//                            break;
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())+ "Success");
//        return exists;
//    }
//    public void updateLoginHelpLink(String customUrl) throws Exception{
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("account-id", new String[]{iniObj.getStringProperty("sut", "accountid")});
//        params.put("field-id",new String[]{"account-login-help-link"});
//        params.put("value",new String[]{customUrl});
//
//        Element resp = breezeSession.apiPost("account-custom-help-link-update", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("cannot update help link in login page!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public void updateWebAppHelpLink(String customUrl) throws Exception{
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("account-id", new String[]{iniObj.getStringProperty("sut", "accountid")});
//        params.put("field-id",new String[]{"account-webapp-help-link"});
//        params.put("value",new String[]{customUrl});
//
//        Element resp = breezeSession.apiPost("account-custom-help-link-update", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("cannot update help link in webapp!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public void deleteEvent(String eventName) throws Exception
//    {
//        String scoid=getEventScoID(eventName);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the event");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public String getEventsScoID(String eventName) throws Exception	{
//        String scoid=null;
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("get-unpublished", new String[]{"true"});
//        Element resp = breezeSession.apiPost("event-list", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            throw new Failure("event-list API call failed!");
//
//        @SuppressWarnings("unchecked")
//        List<Element> events=resp.getChild("event-list").getChildren("event");
//        Iterator<Element> eventIterator=events.iterator();
//        while(eventIterator.hasNext()) {
//            org.jdom.Element event=eventIterator.next();
//            if(eventName.equalsIgnoreCase(event.getChildText("name"))) {
//                scoid=event.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Event "+eventName+" not found.");
//            throw new Failure("Event "+eventName+" not found.");
//        }
//        return scoid;
//    }
//
//    public String getEventScoID(String eventName) throws Exception
//    {
//        String scoid=null;
//        org.jdom.Element resp = breezeSession.apiPost("report-my-events", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the meeting "+eventName+" scoid");
//        }
//
//        List<Element> events=resp.getChild("my-events").getChildren("event");
//
//        Iterator<Element> eventIterator=events.iterator();
//        while(eventIterator.hasNext())
//        {
//            org.jdom.Element event=eventIterator.next();
//            if(eventName.equalsIgnoreCase(event.getChildText("name")))
//            {
//                scoid=event.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Event "+eventName+" not found.");
//            throw new Failure("Event "+eventName+" not found.");
//        }
//        return scoid;
//    }
//
//    //Verify that AddinInfo.xml contains the information about minimum CEF versions and verify that the versions maintained are not less than minimum CEF version expected
//    public boolean verifyMinCEFVersions(String expectedMinimumCEFVersionForMAC,String expectedMinimumCEFVersionForWIN) throws Exception
//    {
//        String platform = null;
//        boolean MACVerified=false;
//        boolean WINVerified=false;
//        boolean verified=false;
//        String CEFValueMAC=null,CEFValueWIN=null;
//        //Store the expected CEF version value in ArrayList
//        List<String> minimumCEFVersionMACList = Arrays.asList(expectedMinimumCEFVersionForMAC.split(","));
//        List<String> minimumCEFVersionWINList = Arrays.asList(expectedMinimumCEFVersionForWIN.split(","));
//        List<String> CEFVersionList =null;
//        //Api post for addinInfo.xml
//        org.jdom.Element response = breezeSession.apiPostForAddinInfo();
//        //Get <m> id objects
//        List<Element> messages = response.getChildren("m");
//        for (Iterator<Element> iterator = messages.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element m1 = iterator.next();
//            //Check that <m> attribute contains CEFPackageVersion
//            if(m1.getAttribute("id").getValue().equalsIgnoreCase("CEFPackageVersion"))
//            {
//                //Check whether the platform is WIN/MAC
//                platform = m1.getAttributeValue("platform");
//
//                if(platform.equals("Windows"))
//                {
//                    CEFValueWIN=m1.getValue();
//                    //Store the retrieved CEF version value in ArrayList
//                    CEFVersionList = Arrays.asList(CEFValueWIN.split(","));
//                    for (int i=0; i<minimumCEFVersionWINList.size(); i++)
//                    {
//                        if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i))>0)
//                        {
//                            WINVerified=true;
//                            break;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i)) ==0)
//                        {
//                            if(i==minimumCEFVersionWINList.size()-1)
//                                WINVerified=true;
//                            else
//                                continue;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i))<0)
//                        {
//                            WINVerified=false;
//                            break;
//                        }
//                    }
//                }
//                else if(platform.equals("Mac OS 10"))
//                {
//                    CEFValueMAC=m1.getValue();
//                    //Store the retrieved CEF version value in ArrayList
//                    CEFVersionList = Arrays.asList(CEFValueMAC.split(","));
//                    for (int i=0; i<minimumCEFVersionWINList.size(); i++)
//                    {
//                        if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i))>0)
//                        {
//                            MACVerified=true;
//                            break;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i)) ==0)
//                        {
//                            if(i==minimumCEFVersionMACList.size()-1)
//                                MACVerified=true;
//                            else
//                                continue;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i))<0)
//                        {
//                            MACVerified=false;
//                            break;
//                        }
//                    }
//                }
//
//            }
//        }
//        if(WINVerified && MACVerified)
//            verified=true;
//        else if(!WINVerified)
//            throw new Failure("Verify Mimimum CEF Version for WIN failed !");
//        else if(!MACVerified)
//            throw new Failure("Verify Mimimum CEF Version for MAC failed !");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return verified;
//    }
//
//    public void verifyUser(String principalId) throws Exception {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", null);
//        Element principalList = (resp.getChild("principal-list"));
//        Boolean flag = false;
//        List contentList = principalList.getContent();
//        for (int i = 0; i < contentList.size(); i++) {
//            Element element = (Element) contentList.get(i);
//            List attrList = element.getAttributes();
//            for (int j = 0; j < attrList.size(); j++) {
//                org.jdom.Attribute id = (org.jdom.Attribute) attrList.get(j);
//                String value = id.getValue();
//                if (principalId.equals(value)) {
//                    flag = true;
//                }
//
//            }
//
//        }
//        if (flag == false) {
//            throw new Failure("User not created ");
//        }
//
//    }
//
//    public void login(String userName, String password) throws Failure {
//        try {
//            breezeSession.login(userName,password);
//        }
//        catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallNotificationList(String eventScoId)	throws Exception {
//
//        Map<String, String[]> targetAclIDs = new HashMap<String, String[]>();
//        targetAclIDs.put("target-acl-id", new String[]{eventScoId});
//        Element resp = breezeSession.apiPost("notification-list", targetAclIDs);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("notification-list API call failed!");
//        }
//
//        return resp;
//    }
//
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallEventNotificationStatusUpdate(String accountId, String eventScoId,
//                                                        String actionId, String isSelected) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("account-id", new String[]{accountId});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("action-id", new String[]{actionId});
//        requestParameters.put("is-selected", new String[]{isSelected});
//
//        return breezeSession.apiPost("event-notification-status-update", requestParameters);
//    }
//
//    // Updated by Marko D. 20/04/2016
//    public Map<String, String>  getDescriptionAndInteractionIdFromResponse(Element resp) {
//        Map<String, String> assetIdAndInteractionIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            assetIdAndInteractionIdMap.put(interactionElement.getChild("description").getValue(), interactionElement.getAttributeValue("interaction-id"));
//        }
//        return assetIdAndInteractionIdMap;
//    }
//
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallEventList(String getUnpublished)	throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("get-unpublished", new String[]{getUnpublished});
//        Element resp = breezeSession.apiPost("event-list", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("event-list API call failed!");
//        }
//        return resp;
//    }
//
//    // Updated by Marko D. 04/04/2016
//    public Element ApiCallAssetInteractionInfo(String eventScoId)	throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        Element resp = breezeSession.apiPost("asset-interaction-info", requestParameters);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("asset-interaction-info API call failed!");
//        }
//        return resp;
//    }
//
//    // Updated by Marko D. 04/04/2016
//    public Element ApiCallAssetInteractionDelete( String isSelected, String interactionId, String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("is-selected", new String[]{isSelected});
//        requestParameters.put("interaction-id", new String[]{interactionId});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("asset-interaction-delete", requestParameters);
//    }
//
//    // Created by Marko D. 27/05/2016
//    public Map<String, String>  getDescriptionAndAssetIdFromResponse(Element resp) {
//        Map<String, String> descriptionAndAssetIdFromResponseMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            descriptionAndAssetIdFromResponseMap.put(interactionElement.getChild("description").getValue(),interactionElement.getAttribute("asset-id").getValue());
//            System.out.println("Asset: " + interactionElement.getChild("description").getValue() + " - Asset ID: " + interactionElement.getAttribute("asset-id").getValue());
//        }
//        return descriptionAndAssetIdFromResponseMap;
//    }
//
//    public Map<String, String>  getAssetDescriptionAndDisplaySeqFromResponse(Element resp) {
//        Map<String, String> assetDescriptionAndDisplaySeqMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            assetDescriptionAndDisplaySeqMap.put(interactionElement.getChild("description").getValue(),interactionElement.getAttribute("display-seq").getValue());
//            System.out.println("Asset: " + interactionElement.getChild("description").getValue() + "- display seq: " + interactionElement.getAttribute("display-seq").getValue());
//        }
//        return assetDescriptionAndDisplaySeqMap;
//    }
//
//    // Shift registration question up (true) or down (false) - Marko D. 09/13/2016
//    public Element ApiCallAssetInteractionOrderUpdate(String up, String eventScoId, String assetId,String interactionId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("up", new String[]{up});
//        requestParameters.put("type", new String[]{"event"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("asset-id", new String[]{assetId});
//        requestParameters.put("interaction-id", new String[]{interactionId});
//
//        return breezeSession.apiPost("asset-interactions-order-update", requestParameters);
//    }
//
//
//    // Create Multiple Choice question with answers API - Marko D. 05/04/2016
//    public Element ApiCallAssetInteractionResponseUpdateChoice(String eventScoId,String questionDescription, String type, String firstAnswer,
//                                                               String secondAnswer, String thirdAnswer) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("interaction-type", new String[]{"choice"});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("is-required", new String[]{"true"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("csvResponse", new String[]{""});
//        requestParameters.put("csvChoicesEnabled", new String[]{"false"});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("response-display-seq", new String[]{"1","2","3"});
//        requestParameters.put("response-description", new String[]{firstAnswer,secondAnswer,thirdAnswer});
//        requestParameters.put("response", new String[]{" "}); // removed as mandatory parameter for version 9.5.4 and later based on CL#1313537 - Marko D.  5/20/2016
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Created by Marko D. 20/04/2016
//    public Map<String, String>  getActionIdAndDescriptionFromResponse(Element resp) {
//        Map<String, String> actionIdAndDescriptionMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("notification-list").getChildren("notification");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element notificationElement = iterator.next();
//            actionIdAndDescriptionMap.put(notificationElement.getChild("cq-template-name").getValue(), notificationElement.getAttribute("action-id").getValue());
//            System.out.println("Description: " + notificationElement.getChild("cq-template-name").getValue() + " - sco-id:" + notificationElement.getAttribute("action-id").getValue());
//        }
//        return actionIdAndDescriptionMap;
//    }
//
//    // Created by Marko D. 20/04/2016
//    public Map<String, String>  getInteractionIdAndDescriptionFromResponse(Element resp) {
//        Map<String, String> interactionIdAndDescriptionMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interaction").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            interactionIdAndDescriptionMap.put(interactionElement.getAttribute("interaction-id").getValue(), interactionElement.getChild("description").getValue());
//            System.out.println("Description: " + interactionElement.getAttribute("interaction-id").getValue() + "-" + interactionElement.getChild("description").getValue());
//        }
//        return interactionIdAndDescriptionMap;
//    }
//
//    // Created by Marko D. 05/04/2016
//    public Element ApiCallAccountInfo(String accountId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("account-id", new String[]{accountId});
//
//        return breezeSession.apiPost("account-info", requestParameters);
//    }
//
//    // Creating a question that needs a short answer API - Marko D. 05/24/2016
//    public Element ApiCallAssetInteractionResponseUpdateFillIn(String eventScoId, String questionDescription) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"fill-in"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//
//    // Creating a question which can be answered with either Yes or No API - Marko D. 05/24/2016
//    public Element ApiCallAssetInteractionResponseUpdateYesNo(String eventScoId, String questionDescription) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"true-false"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Creating Multiple Choice question with answers in the comma separated format API - Marko D. 05/25/2016
//    public Element ApiCallAssetInteractionResponseUpdateChoice2(String eventScoId,String questionDescription, String type,
//                                                                String firstAnswer, String secondAnswer, String thirdAnswer) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("csvChoicesEnabled", new String[]{"true"});
//        requestParameters.put("csvResponse", new String[]{firstAnswer+","+secondAnswer+","+thirdAnswer});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"choice"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Api call to retrieve sco shortcuts for the account - Added by Marko D. 06/03/2016
//    public Element ApiCallScoShortcuts() throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//
//        return breezeSession.apiPost("sco-shortcuts", requestParameters);
//    }
//
//    // Created by Marko D. 06/03/2016
//    public Map<String, String>  getTypeAndScoIdFromResponse(Element resp) {
//        Map<String, String> scoIdAndTypeMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("shortcuts").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndTypeMap.put(scoElement.getAttribute("type").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco type:" + scoElement.getAttribute("type").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndTypeMap;
//    }
//
//    // Api call to list contents for given sco - Added by Marko D. 06/03/2016
//    public Element ApiCallScoContents(String scoId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId});
//
//        return breezeSession.apiPost("sco-contents", requestParameters);
//    }
//
//    // Created by Marko D. 06/06/2016
//    public Map<String, String> getScoIdofContentFromResponse(Element resp) {
//        Map<String, String> scoIdAndContentMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("scos").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndContentMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Name: " + scoElement.getChild("name").getValue() + "- sco id: " + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndContentMap;
//    }
//
//    public String getLicenseSeminarScoID(String seminarLicenseName,String sharedSeminarScoId) {
//
//        String sAction = "sco-contents";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("sco-id", new String[]{sharedSeminarScoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("scos").getChildren();
//                for (Element el : scoId_list) {
//                    if ((el.getChild("name").getText()).contains(seminarLicenseName)){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public void moveMeetingFolderAPI( String folder_scoID, String destination_scoId) throws Failure {
//
//        String sAction = "sco-move";
//
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("folder-id", new String[]{destination_scoId} );
//            hParams.put("sco-id", new String[]{folder_scoID} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error !! ");
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public String getSharedMeetingsScoID(){
//
//        String sAction = "sco-shortcuts";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("shortcuts").getChildren();
//                for (Element el : scoId_list) {
//                    if (el.getAttributeValue("type").equals("meetings")){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public String getMeetingFolderScoID(String folderName,String sharedMeetingsScoId){
//        String sAction = "sco-contents";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("sco-id", new String[]{sharedMeetingsScoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("scos").getChildren();
//                for (Element el : scoId_list) {
//                    if ((el.getChild("name").getText()).equals(folderName)){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public String getSharedSeminarScoID() {
//        String sAction = "sco-shortcuts";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("shortcuts").getChildren();
//                for (Element el : scoId_list) {
//                    if (el.getAttributeValue("type").equals("seminars")) {
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public void moveSeminarRoomAPI( String source_scoId, String destination_scoId) throws Failure {
//
//        String sAction = "sco-seminar-move";
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("source-license-id", new String[]{source_scoId} );
//            hParams.put("destination-license-id", new String[]{destination_scoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//    }
//    @SuppressWarnings("unchecked")
//    public List<Element> getWebinarLicenseInfoAPI(String accountId, String principalId){
//
//        String sAction = "sco-user-webinar-license-info";
//        List<Element> resultList= null;
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("account-id", new String[]{accountId} );
//            hParams.put("principal-id", new String[]{principalId});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            resultList =  resp.getChildren();
//
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return resultList;
//    }
//
//    public Element ApiCallScoUserWebinarLicenseInfo(String accountId, String principalId) throws Exception{
//
//        String sAction = "sco-user-webinar-license-info";
//
//        Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//        hParams.put("account-id", new String[]{accountId} );
//        hParams.put("principal-id", new String[]{principalId});
//
//        return breezeSession.apiPost(sAction, hParams);
//    }
//
//
//    // Updated by Marko D. 20/04/2016
//    public Map<String, String>  getUserWebinarLicenseScoIdFromResponse(Element resp) {
//        Map<String, String> licenseScoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> userWebLicListElement = resp.getChildren("user-webinar-license");
//        for (Iterator<Element> iterator = userWebLicListElement.iterator(); iterator.hasNext();) {
//            Element UserWebLicElement = iterator.next();
//            licenseScoIdAndNameMap.put(UserWebLicElement.getAttribute("sco-id").getValue(), UserWebLicElement.getChild("name").getValue());
//            System.out.println("Name: " + UserWebLicElement.getChild("name").getValue() + "- sco-id:" + UserWebLicElement.getAttribute("sco-id").getValue());
//        }
//        return licenseScoIdAndNameMap;
//    }
//
//    // Api call to create/update meeting - Added by Marko D. 12/23/2016
//    public Element APICallMeetingScoUpdate(String name, String type, String folderId, String description) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"meeting"});
//        requestParameters.put("folder-id", new String[]{folderId});
//        requestParameters.put("description", new String[]{description});
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call to create/update event - Added by Marko D. 12/23/2016
//    // Extended Meeting sco update API call
//    public Element APICallEventScoUpdate(String name, String type, String folderId, String description,
//                                         String dateBegin, String dateEnd, String lang, String status,
//                                         String sourceScoId, String URLpath
//    ) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"event"});
//        requestParameters.put("folder-id", new String[]{folderId});
//        requestParameters.put("description", new String[]{description});
//        requestParameters.put("date-begin", new String[]{dateBegin});
//        requestParameters.put("date-end", new String[]{dateEnd});
//        requestParameters.put("lang", new String[]{lang});
//        requestParameters.put("status", new String[]{status});
//        requestParameters.put("source-sco-id", new String[]{sourceScoId});
//        requestParameters.put("url-path", new String[]{URLpath});
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call to obtain my cq templates list - Added by Marko D. 12/23/2016
//    public Element APICallReportMyCQTemplates (String type) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("template-type", new String[]{type});
//
//        return breezeSession.apiPost("report-my-cq-templates", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getMeetingScoId(Element resp) {
//
//        Map<String, String> meetingScoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> meetingsListElement = resp.getChild("my-meetings").getChildren("meeting");
//        for (Iterator<Element> iterator = meetingsListElement.iterator(); iterator.hasNext();) {
//            Element meetingElement = iterator.next();
//            meetingScoIdAndNameMap.put(meetingElement.getAttribute("sco-id").getValue(), meetingElement.getChild("name").getValue());
//            System.out.println("Meeting name: " + meetingElement.getChild("name").getValue() + "- sco-id:" + meetingElement.getAttribute("sco-id").getValue());
//        }
//        return meetingScoIdAndNameMap;
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getScoIdAndNameFromResponse(Element resp) {
//
//        Map<String, String> scoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("scos").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndNameMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco name:" +scoElement.getChild("name").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndNameMap;
//    }
//
//    // Api call to update principal sco - Added by Marko D. 12/23/2016
//    public Element APICallScoPrincipalUpdate(String scoId, String name, String type, String hasChildren, String isPrimary) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId} );
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{type});
//        requestParameters.put("has-children", new String[]{hasChildren});
//        requestParameters.put("is-primary", new String[]{isPrimary});
//
//        return breezeSession.apiPost("sco-principal-update", requestParameters);
//    }
//
//    // Api call to update event details - Added by Marko D. 12/23/2016
//    public Element APICallEventDetailsUpdate(String eventId, String speakerBriefOverview, String speakerName,
//                                             String speakerDetailedOverview, String isRegistrationLimitEnabled, String passwordBypass,
//                                             String showInCatalog, String eventCategory, String registrationType) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("event-id", new String[]{eventId} );
//        requestParameters.put("speaker-brief-overview", new String[]{speakerBriefOverview});
//        requestParameters.put("speaker-name", new String[]{speakerName});
//        requestParameters.put("speaker-detailed-overview", new String[]{speakerDetailedOverview});
//        requestParameters.put("is-registration-limit-enabled", new String[]{isRegistrationLimitEnabled});
//        requestParameters.put("password-bypass", new String[]{passwordBypass});
//        requestParameters.put("show-in-catalog", new String[]{showInCatalog});
//        requestParameters.put("event-category", new String[]{eventCategory});
//        requestParameters.put("registration-type", new String[]{registrationType});
//
//        return breezeSession.apiPost("event-details-update", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Element APICallEventNotificationUpdate(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-notification-update", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getTemplateNameAndIdFromResponse(Element resp) {
//
//        Map<String, String> templateNameAndIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> templateListElement = resp.getChild("cq-template-list").getChildren("cq-template");
//        for (Iterator<Element> iterator = templateListElement.iterator(); iterator.hasNext();) {
//            Element templateElement = iterator.next();
//            templateNameAndIdMap.put(templateElement.getChild("display-name").getValue(), templateElement.getAttribute("template-id").getValue());
//            System.out.println("Template name: " + templateElement.getChild("display-name").getValue()+ " - sco-id:" + templateElement.getAttribute("template-id").getValue());
//        }
//        return templateNameAndIdMap;
//    }
//
//    // Api call to update acl fields - Created by Marko D. 12/23/2016
//    public Element APICallAclFieldUpdate(String fieldId, String aclId, String value) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("field-id", new String[]{fieldId} );
//        requestParameters.put("acl-id", new String[]{aclId});
//        requestParameters.put("value", new String[]{value});
//
//        return breezeSession.apiPost("acl-field-update", requestParameters);
//    }
//
//    // API call permissions-update to add host to an event or
//    // to make event publicly accessible or only accessible if permitted - Created by Marko D. 12/26/2016
//    public Element APICallPermissionsUpdate(String permissionId, String aclId, String principalId) throws Exception	{
//
//        Map<String, String[]> updateParameters = new HashMap<String, String[]>();
//        updateParameters.put("permission-id", new String[] {permissionId});
//        updateParameters.put("acl-id", new String[] {aclId});
//        updateParameters.put("principal-id", new String[] {principalId});
//
//        return breezeSession.apiPost("permissions-update", updateParameters);
//    }
//
//    // Create metadata for an event using API call - Created by Marko D. 12/23/2016
//    public Element APICallScoUpload(String optional, String scoId, String build) throws Exception	{
//
//        Map<String, String[]> uploadParameters = new HashMap<String, String[]>();
//        uploadParameters.put("optional", new String[] {optional});
//        uploadParameters.put("sco-id", new String[] {scoId});
//        uploadParameters.put("build", new String[] {build});
//        return breezeSession.apiPost("sco-upload", uploadParameters);
//    }
//
//    // Publish an event using API call - Created by Marko D. 12/23/2016
//    public Element APICallEventPublish(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-publish", requestParameters);
//    }
//    // API call acl create - Created by Marko D. 01/17/2016
//    public Element callApiAclCreate(String parentScoId) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("parent-acl-id", new String[] {parentScoId});
//
//        if(!("ok".equalsIgnoreCase(breezeSession.apiPost("acl-create", updateParameters).getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + breezeSession.apiPost("acl-create", updateParameters).getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating Acl");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return breezeSession.apiPost("acl-create", updateParameters);
//    }
//    // Created by Marko D. 01/17/2016
//    public Map<String, String>  getParentAclIDAndAclIdFromResponse(Element resp) {
//        Map<String, String> parentAclIDAndAclIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> aclListElement = resp.getChildren("acl");
//        for (Iterator<Element> iterator = aclListElement.iterator(); iterator.hasNext();) {
//            Element aclElement = iterator.next();
//            parentAclIDAndAclIdMap.put(aclElement.getAttributeValue("parent-acl-id"), aclElement.getAttributeValue("acl-id"));
//            System.out.println(aclElement.getAttributeValue("parent-acl-id") + "  -  " + aclElement.getAttributeValue("acl-id"));
//        }
//        return parentAclIDAndAclIdMap;
//    }
//    // API call for seminar session create - Created by Marko D. 01/17/2016
//    public Element APICallseminarSessionScoUpdate( String scoId, String sourceScoId, String dateBegin, String dateEnd, String name) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("sco-id", new String[] {scoId});
//        updateParameters.put("source-sco-id", new String[] {sourceScoId});
//        updateParameters.put("type", new String[] {"seminarsession"});
//        updateParameters.put("date-begin", new String[] {dateBegin});
//        updateParameters.put("date-end", new String[] {dateEnd});
//        updateParameters.put("name", new String[] {name});
//
//        if(!("ok".equalsIgnoreCase(breezeSession.apiPost("seminar-session-sco-update", updateParameters).getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + breezeSession.apiPost("seminar-session-sco-update", updateParameters).getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating a session");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return breezeSession.apiPost("acl-create", updateParameters);
//    }
//
//    // Api call to create/update seminar room - Created by Marko D. 01/17/2017
//    public Element APICallSeminarScoUpdate(String name, String scoId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"meeting"});
//        requestParameters.put("sco-id", new String[]{scoId});
//        // sco-id when creating a room is acl-id from response of acl-create api call
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call sco delete - Created by Marko D. 01/27/2016
//    public void callApiScoDelete(String scoId) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    // Created by Marko D. 01/30/2016
//    public String getPrincipalId() throws Exception	{
//        String principalId=null;
//
//        Map<String, String[]> updateParameters = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParameters);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common = resp.getChild("common");
//        org.jdom.Element principalIdNode = common.getChild("user");
//        principalId=principalIdNode.getAttributeValue("user-id");
//        return principalId;
//    }
//
//    // Api call event info - Created by Marko D. 02/09/2017
//    public Element APICallEventInfo(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-info", requestParameters);
//    }
//
//    // Created by Marko D. 02/09/2017
//    public Map<String, String>  getNameAndScoIdFromResponse(Element resp) {
//
//        Map<String, String> nameAndScoIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            nameAndScoIdMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco name:" +scoElement.getChild("name").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return nameAndScoIdMap;
//    }
//
//    public void verifyUserRegisteredForEvent(String registeredUserLogin,String eventName, boolean checkFor) throws Exception, Failure
//    {
//        boolean flag = false;
//        String scoId = getScoID(eventName);
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId});
//        org.jdom.Element resp = breezeSession.apiPost("report-event-registration", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting event registrants!!");
//        }
//        org.jdom.Element common = resp.getChild("csv");
//        @SuppressWarnings("unchecked")
//        List<Element> rowListElement = common.getChildren("row");
//        for (Iterator<Element> iterator = rowListElement.iterator(); iterator.hasNext();)
//        {
//            Element loginElement = iterator.next();
//            String login = loginElement.getChild("login").getValue();
//            if(login.equals(registeredUserLogin))
//            {
//                String permissionId = loginElement.getAttribute("permission-id").getValue();
//                if(permissionId.equals("view") || permissionId.equals("host"))
//                {
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        if(flag!=checkFor)
//            throw new Failure("Failed to verify user registration for user: "+registeredUserLogin+"!! Expected: "+checkFor+" but got: "+flag+"!!");
//    }
//
//    public boolean verifyEventTemplateExistOrNot(String eventName) throws Exception	{
//
//        boolean flag=false;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("template-type",new String[]{"template-type-event"});
//
//        org.jdom.Element resp = breezeSession.apiPost("report-my-cq-templates", updateParams);
//
//
//        if(!("ok".equals(resp.getChild("status").getAttributeValue("code").toLowerCase())))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while fetching  the event template  list");
//        }
//        else{
//            resp=resp.getChild("cq-template-list");
//            List<Element> scoListElement = resp.getChildren("cq-template");
//            for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//                Element scoElement = iterator.next();
//
//                if(scoElement.getChild("display-name").getValue().equals(eventName)){
//                    flag=true;
//                }
//            }
//
//        }
//        return flag;
//    }
//
//    public boolean verifyEmailTemplateExistOrNot(String eventName) throws Exception	{
//
//        boolean flag=false;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("template-type",new String[]{"template-type-email"});
//
//        org.jdom.Element resp = breezeSession.apiPost("report-my-cq-templates", updateParams);
//
//
//        if(!("ok".equals(resp.getChild("status").getAttributeValue("code").toLowerCase())))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while fetching  the Email template  list");
//        }
//        else{
//            resp=resp.getChild("cq-template-list");
//            List<Element> scoListElement = resp.getChildren("cq-template");
//            for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//                Element scoElement = iterator.next();
//
//                if(scoElement.getChild("display-name").getValue().equals(eventName)){
//                    flag=true;
//                }
//            }
//
//        }
//        return flag;
//    }
//
//    public void verifyUserInMeeting(String meetingName,String userName) throws Exception
//    {
//        boolean exists = false;
//        Map<String, String[]> params=new HashMap<String, String[]>();
//        String scoId=getScoID(meetingName);
//        params.put("sco-id",  new String[] {scoId});
//        org.jdom.Element resp=breezeSession.apiPost("report-meeting-attendance",params);
//        if(("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            List users = resp.getChild("report-meeting-attendance").getChildren("row");
//            Iterator usersIterator = users.iterator();
//            while (usersIterator.hasNext())
//            {
//                org.jdom.Element principal = (org.jdom.Element) usersIterator.next();
//                Element list=principal.getChild("participant-name");
//                String name=list.getValue();
//                if((name.equals(userName)))
//                {
//                    exists = true;
//                    break;
//                }
//            }
//            if(!exists)
//                throw new Failure("User: "+userName+" not present in the meeting: "+meetingName+"!!");
//        }
//        else
//            throw new Failure("Verify user in meeting failed!!");
//
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//
//    public boolean verifyGuestUserInMeeting(String meetingName,String userName) throws Exception
//    {
//        boolean exists = false;
//        Map<String, String[]> params=new HashMap<String, String[]>();
//        String scoId=getScoID(meetingName);
//        params.put("sco-id",  new String[] {scoId});
//        org.jdom.Element resp=breezeSession.apiPost("report-meeting-attendance",params);
//        if(("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            List users = resp.getChild("report-meeting-attendance").getChildren("row");
//            Iterator usersIterator = users.iterator();
//            while (usersIterator.hasNext())
//            {
//                org.jdom.Element principal = (org.jdom.Element) usersIterator.next();
//                Element list=principal.getChild("session-name");
//                String name=list.getValue();
//                if((name.equals(userName)))
//                {
//                    exists = true;
//                    break;
//                }
//            }
//
//        }
//
//
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//        return exists;
//    }
//
//    // Delete group - Marko D. 18-March-2018
//    public void deleteGroup(String groupName) throws Failure {
//        try {
//            Map<String, String[]> params = new HashMap<String, String[]>();
//            String groupPrincipalID = getPrincipalIdOfGroup(groupName);
//            params.put("principal-id", new String[]{groupPrincipalID});
//            Element resp = breezeSession.apiPost("principals-delete", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + groupName + " could not be deleted.");
//            throw new Failure("Asset " + groupName + " could not be deleted. Failure.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    // Get principal-id of the group - Marko D. 18-March-2018
//    public String getPrincipalIdOfGroup(String groupName)throws Exception {
//
//        String groupPrincipalId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-name", new String[] { groupName });
//        org.jdom.Element resp = breezeSession.apiPost("principal-list",	updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure("Response error while displaying the principal-list");
//        }
//        @SuppressWarnings("unchecked")
//        List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//        Iterator<Element> principalIterator = principalList.iterator();
//        while (principalIterator.hasNext()) {
//            org.jdom.Element sco = principalIterator.next();
//            String nameXML = sco.getChildText("name");
//            if (groupName.equals(nameXML)) {
//                groupPrincipalId = sco.getAttributeValue("principal-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + " Success");
//                break;
//            }
//        }
//        if (groupPrincipalId == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + groupName + " not found.");
//            throw new Failure("Asset" + groupName + " not found.");
//        }
//        return groupPrincipalId;
//    }
//}package lib.api;
//
//        import java.io.FileNotFoundException;
//        import java.io.IOException;
//        import java.util.Arrays;
//        import java.util.HashMap;
//        import java.util.Hashtable;
//        import java.util.Iterator;
//        import java.util.List;
//        import java.util.Map;
//        import java.util.Random;
//
//        import org.apache.log4j.Logger;
//        import org.jdom.Element;
//
//        import lib.conf.INIFile;
//        import lib.exceptions.Failure;
//
//public class WebApp {
//
//    private ApiSession breezeSession;
//    private INIFile iniObj;
//    Logger log=Logger.getLogger(this.getClass().getSimpleName());
//    //from user
//    public WebApp(String url)
//    {
//        iniObj=new INIFile();
//        breezeSession = new ApiSession(url);
//
//    }
//    //from config reader
//    public WebApp() throws FileNotFoundException, IOException, Exception
//    {
//
//        iniObj=new INIFile();
//        breezeSession=new ApiSession(iniObj.getStringProperty("sut", "server"));
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //utilities
//    private String generateMeetingName()
//    {
//        return "TestMeeting"+Integer.toString((new Random()).nextInt(30000));
//    }
//    /**
//     * @author akajain
//     * @throws Failure
//     */
//    public void login() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("sut", "login"), iniObj.getStringProperty("sut", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public void adminLogin() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("adminuser", "login"), iniObj.getStringProperty("passwords", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public String createMeetingWithName(String meetingName) throws Exception
//    {
//        String meetingSco = createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//
//        String meetingURL = iniObj.getStringProperty("sut", "server");//"http://" + getDomainName();
//        return meetingURL + "/" + meetingName;
//    }
//    public String createMeetingWithNameScoReturn(String meetingName) throws Exception
//    {
//        String meetingSco = createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//
//        String meetingURL = iniObj.getStringProperty("sut", "server");//"http://" + getDomainName();
//        return meetingSco;
//    }
//    public void CommonUserLogin() throws Failure
//    {
//        try
//        {
//            breezeSession.login(iniObj.getStringProperty("sut", "login"), iniObj.getStringProperty("sut", "password"));
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void logout() throws Failure
//    {
//        try
//        {
//            breezeSession.logout();
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure("Logout Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public String createProtectedMeeting() throws Exception
//    {
//
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//
//
//    private  String createSimpleMeeting(String meetingName) throws Exception
//    {
//        String scoId = null;
//        String tempId = null;
//        String meetingTemplate = null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//
//        org.jdom.Element resp = breezeSession.apiPost("sco-shortcuts", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco");
//        }
//        List<Element> scoList = resp.getChild("shortcuts").getChildren("sco");
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getAttribute("type").getValue().equalsIgnoreCase("shared-meeting-templates"))
//            {
//                tempId = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"shared-meeting-templates  Sco id:"+scoId);
//            }
//            if(sco1.getAttribute("type").getValue().equalsIgnoreCase("my-meetings"))
//            {
//                scoId = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"my-meetings folder Sco id:"+scoId);
//                break;
//            }
//        }
//
//        if(tempId==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Could not find template");
//        }
//        if(scoId==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Could not find my-meetings folder");
//        }
//
//        Map<String, String[]> updateParams1 = new HashMap<String, String[]>();
//        updateParams1.put("sco-id", new String[] {tempId});
//        resp = breezeSession.apiPost("sco-contents", updateParams1);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco-contents");
//        }
//
//        List<Element> content = resp.getContent();
//        org.jdom.Element scoContent = content.get(1);
//        scoList = scoContent.getContent();
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getChild("name").getValue().equalsIgnoreCase("Default Meeting Template"))
//            {
//                meetingTemplate = sco1.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+meetingTemplate);
//                break;
//            }
//        }
//        if(meetingTemplate==null)
//        {
//            log.debug((new Exception().getStackTrace()[0].getMethodName())+"Sco id for Default meeting template is null");
//        }
//
//        //Create Meeting
//
//
//
//        Map<String, String[]> updateParams2 = new HashMap<String, String[]>();
//        updateParams2.put("type", new String[] { "meeting" });
//        updateParams2.put("name", new String[] { meetingName });
//        updateParams2.put("folder-id", new String[] { scoId });
//        updateParams2.put("lang", new String[] { iniObj.getStringProperty("lang", "locale") });
//        if (meetingTemplate != "0") {
//            updateParams2.put("source-sco-id", new String[] { meetingTemplate });
//        }
//        updateParams2.put("url-path", new String[] { meetingName });
//        resp = breezeSession.apiPost("sco-update", updateParams2);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            System.out.println("Response : " + resp.getChild("status").getAttributeValue("code"));
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating meeting");
//        }
//
//        content = resp.getContent();
//        scoContent = content.get(1);
//        scoId = scoContent.getAttribute("sco-id").getValue();
//        log.info("Meeting ScoID"+scoId);
//        return scoId;
//    }
//
//
//
//
//    public boolean startRecording(String scoId) throws Exception
//    {
//
//
//
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"true"});
//        updateParams.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//    public boolean startRecording(String scoId,String recordingName) throws Exception {
//
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"true"});
//        updateParams.put("sco-id", new String[] {scoId});
//        updateParams.put("name", new String[] {recordingName});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//
//    public boolean EnableDisableMP4Encoder(String accountID, String enable) throws Exception {
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("account-id",new String[]{accountID});
//        updateParams.put("enable", new String[] {enable});
//        updateParams.put("feature-id", new String[] {"fid-mp4-encoder-disabled"});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-feature-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating the meeting");
//        }
//        return true;
//    }
//
//    public boolean stopRecording(String scoId) throws Exception	{
//
//        //String scoid=getMeetingScoID(meetingURL);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("active",new String[]{"false"});
//        updateParams.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("meeting-recorder-activity-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the recording");
//        }
//        return true;
//    }
//
//
//    public int getNumberOfTelephonyProfiles() throws Exception
//    {
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-list",null);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while retrieving telephony profiles");
//        }
//        return resp.getChild("telephony-profiles").getChildren("profile").size();
//
//    }
//
//    public String createPlainTelephonyProvider(String providerName) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("status", new String[] {"enabled"});
//        params.put("name", new String[] {providerName});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-provider-update",params);
//
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating telephony provider");
//        }
//        return  resp.getChild("telephony-provider").getAttributeValue("provider-id");
//    }
//
//    public String createPlainTelephonyProfile(String profileName, String providerId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("status", new String[] {"enabled"});
//        params.put("profile-name", new String[] {profileName});
//        params.put("provider-id", new String[] {providerId});
//
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-update",params);
//
//        System.out.println(resp.toString());
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        System.out.println(resp.getText());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating telephony profile");
//        }
//        return  resp.getChild("telephony-profile").getAttributeValue("profile-id");
//    }
//
//    public void deletePlainTelephonyProvider(String providerId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("provider-id", new String[] {providerId});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-provider-delete",params);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting telephony provider");
//        }
//    }
//    public void deletePlainTelephonyProfile(String profileId) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("profile-id", new String[] {profileId});
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-delete",params);
//        System.out.println(resp.toString());
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting telephony profile");
//        }
//    }
//
//    public String checkFortimeZoneInfoDisplayedUnderTrainingCatalogForVirtualClass(String scoIdOfFolder, String virtualClassName) throws Exception
//    {
//
//
//        String timeZoneId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoIdOfFolder});
//
//        updateParams.put("filter-icon", new String[] {"virtual-classroom"});
//
//        updateParams.put("trainingCatalog", new String[] {"true"});
//        org.jdom.Element resp = breezeSession.apiPost(
//                "sco-contents", updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure("Response error while checking the time zone info");
//
//        }
//
//
//
//        List<Element> scoList = resp.getChild("scos").getChildren("sco");
//
//        timeZoneId=scoList.toString();
//        Iterator<Element> scoIterator = scoList.iterator();
//        while (scoIterator.hasNext()) {
//            org.jdom.Element sco = scoIterator.next();
//            String hereName = sco.getChildText("name");
//            if (virtualClassName.equals(hereName)) {
//                timeZoneId = sco.getAttributeValue("time-zone-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//
//        if (timeZoneId == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + virtualClassName + " not found.");
//            throw new Failure("Asset" + timeZoneId + " not found.");
//        }
//
//        return timeZoneId;
//
//    }
//
//
//
//    public String getPrincipalIdOfUser(String logIn)throws Exception
//    {
//
//        String principalID=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] { logIn });
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-list",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error while displaying the principal-list");
//
//        }
//
//        List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//
//        Iterator<Element> prinIterator = principalList.iterator();
//
//        while (prinIterator.hasNext()) {
//            org.jdom.Element sco = prinIterator.next();
//            String logInXML = sco.getChildText("login");
//            if (logIn.equals(logInXML)) {
//                principalID = sco.getAttributeValue("principal-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//        if (principalID == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + logIn + " not found.");
//            throw new Failure("Asset" + principalID + " not found.");
//        }
//
//
//
//        return principalID;
//    }
//
//
//
//    private void makeUserHost(String meetingSco) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        String userId= resp.getChild("common").getChild("user").getAttribute("user-id").getValue();
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {userId});
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("permission-id", new String[] {"host"});
//        resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while making user the meeting host");
//        }
//    }
//    private void makeMeetingPublic(String meetingSco) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("principal-id", new String[] {"public-access"});
//        updateParams.put("permission-id", new String[] {"view-hidden"});
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating meeting");
//        }
//    }
//    public String createMeeting() throws Exception
//    {
//        return createOpenMeeting();
//    }
//    public String createOpenMeeting() throws Exception
//    {
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    public String createOpenMeetingWithName(String meetingName) throws Exception
//    {
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    private void attachTelephonyProfile(String meetingSco,String profileID) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] {meetingSco});
//        updateParams.put("inherit-telephony-settings", new String[] {"true"});
//        updateParams.put("field-id", new String[] {"telephony-profile"});
//        updateParams.put("value", new String[] {profileID});
//        org.jdom.Element resp = breezeSession.apiPost("acl-field-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            System.out.println("Response : " + resp.getChild("status").getAttributeValue("code"));
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while attaching  profile");
//        }
//    }
//    public String getDomainName() throws Exception
//    {
//        org.jdom.Element resp = breezeSession.apiPost("report-my-meetings", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Exception("Response error while creating sco-contents");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success");
//        return resp.getChild("my-meetings").getChild("meeting").getChildText("domain-name");
//    }
//    public void deleteMeeting(String meetingURL) throws Exception
//    {
//        String scoid=getMeetingScoID(meetingURL);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteSco(String scoid) throws Exception
//    {
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //change manager of a user
//    public void changeTeamMembership(String userLogin,String managerLogin) throws Exception
//    {                String pricipalId=getUserPrincipalID(userLogin);
//        String managerPrincipalId=getUserPrincipalID(managerLogin);;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("manager-id", new String[] {managerPrincipalId});
//        updateParams.put("principal-id", new String[] {pricipalId});
//        org.jdom.Element resp = breezeSession.apiPost("team-membership-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while changing manager");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    //change manager of a user
//    public void removeManager(String userLogin) throws Exception
//    {                String pricipalId=getUserPrincipalID(userLogin);
//        String managerPrincipalId=getAccountId();
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("manager-id", new String[] {managerPrincipalId});
//        updateParams.put("principal-id", new String[] {pricipalId});
//        org.jdom.Element resp = breezeSession.apiPost("team-membership-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while changing manager");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    public void disableChatandQAPods() throws Exception
//    {
//        updateCompliance("fid-meeting-chat","false");
//        updateCompliance("fid-meeting-im","false");
//        updateCompliance("fid-meeting-questions","false");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void enableChatandQAPods() throws Exception
//    {
//        updateCompliance("fid-meeting-chat","true");
//        updateCompliance("fid-meeting-im","true");
//        updateCompliance("fid-meeting-questions","true");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private void updateCompliance(String feature_id,String state) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {iniObj.getStringProperty("sut", "accountid") });
//        updateParams.put("feature-id", new String[] { feature_id});
//        updateParams.put("enable", new String[] { state });
//        org.jdom.Element resp = breezeSession.apiPost("meeting-feature-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while updating meeting compliance feature");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private String getProfileID(String profileName) throws Exception
//    {
//        String profileID=null;
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-list", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.info((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Exception("Response error while finding telephony profile sco");
//        }
//        List<org.jdom.Element> profiles=resp.getChild("telephony-profiles").getChildren("profile");
//        Iterator<Element> profileIterator=profiles.iterator();
//        while(profileIterator.hasNext())
//        {
//            org.jdom.Element profile=profileIterator.next();
//            if(profileName.equals(profile.getChildText("profile-name")))
//            {
//                profileID=profile.getAttributeValue("profile-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(profileID==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure."+profileName+" not found");
//            throw new Failure(profileName+" not found!!!");
//        }
//        return profileID;
//    }
//    public String getMeetingScoID(String meetingURL) throws Exception
//    {
//        String scoid=null;
//        String meetingName=meetingURL.substring(meetingURL.lastIndexOf('/'))+"/";
//        org.jdom.Element resp = breezeSession.apiPost("report-my-meetings", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the meeting "+meetingName+" scoid");
//        }
//        List<Element> meetings=resp.getChild("my-meetings").getChildren("meeting");
//
//        Iterator<Element> meetingIterator=meetings.iterator();
//        while(meetingIterator.hasNext())
//        {
//            org.jdom.Element meeting=meetingIterator.next();
//            if(meetingName.equalsIgnoreCase(meeting.getChildText("url-path")))
//            {
//                scoid=meeting.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Meeting "+meetingName+" not found.");
//            throw new Failure("Meeting "+meetingName+" not found.");
//        }
//        return scoid;
//    }
//    public String getUserPrincipalID(String userLoginEmail) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] {userLoginEmail });
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting "+userLoginEmail+" PrincipalID");
//        }
//        return resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//    }
//    private String getGuestPrincipalID(String userLoginEmail) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-email", new String[] {userLoginEmail });
//        org.jdom.Element resp = breezeSession.apiPost("report-bulk-users", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting "+userLoginEmail+" PrincipalID");
//        }
//        return resp.getChild("report-bulk-users").getChild("row").getAttributeValue("principal-id");
//    }
//    public void deleteGuest(String userLoginEmail) throws Exception
//    {
//        String principalID=getGuestPrincipalID(userLoginEmail);
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {principalID });
//        org.jdom.Element resp = breezeSession.apiPost("principals-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting "+userLoginEmail);
//        }
//    }
//    public String createTelephonyMeeting(String profileName) throws Exception
//    {
//        String meetingName=generateMeetingName();
//        String meetingSco=createSimpleMeeting(meetingName);
//        makeUserHost(meetingSco);
//        makeMeetingPublic(meetingSco);
//        String profileID=getProfileID(profileName);
//        attachTelephonyProfile(meetingSco, profileID);
//        String meetingURL="http://"+getDomainName();
//        return meetingURL+"/"+meetingName;
//    }
//    public void deleteTelephonyProfile(String profileName) throws Exception
//    {
//        String profileID=getProfileID(profileName);
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("profile-id", new String[] {profileID });
//        org.jdom.Element resp = breezeSession.apiPost("telephony-profile-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting profile");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    private void addUserToSco(String scoId,String userLoginEmail,String role) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String principalID=getUserPrincipalID(userLoginEmail);
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("principal-id", new String[] {principalID});
//        updateParams.put("acl-id", new String[] {scoId});
//        updateParams.put("permission-id", new String[] {role});
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while adding "+userLoginEmail+"to the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @param url New URL to set the login page help link to point to (must include http://)
//     * @throws Exception
//     */
//    public void setCustomLoginPageHelpLink(String url) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-login-help-link"});
//        updateParams.put("value", new String[] {url});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to set custom url for login page help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @param url New URL to set the CPS webapp help links to point to (must include http://)
//     * @throws Exception
//     */
//    public void setCustomWebAppHelpLink(String url) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-webapp-help-link"});
//        updateParams.put("value", new String[] {url});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to set custom url for webapp help links.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomWebAppHelpLink() throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-webapp-help-link"});
//        updateParams.put("value", new String[] {""});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to clear webapp help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomLoginPageHelpLink() throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        String accountId = getAccountId();
//
//
//        updateParams = new HashMap<String, String[]>();
//        updateParams.put("account-id", new String[] {accountId});
//        updateParams.put("field-id", new String[] {"account-login-help-link"});
//        updateParams.put("value", new String[] {""});
//
//        org.jdom.Element resp = breezeSession.apiPost("account-custom-help-link-update", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while trying to clear login page help link.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    public void clearCustomHelpLinks() throws Exception
//    {
//        clearCustomWebAppHelpLink();
//        clearCustomLoginPageHelpLink();
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsEnrolled(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "view";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsDenied(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "denied";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param sTrainingName
//     * @param sUserLoginEmail
//     * @throws Exception
//     */
//    public void addUserToTrainingAsPendingApproval(String sTrainingName, String sUserLoginEmail) throws Exception
//    {
//
//        String sPermissionId = "pending";
//
//        log.debug("Adding " + sUserLoginEmail + " to " + sTrainingName + " with permission " + sPermissionId );
//        addUserToSco(getScoID(sTrainingName), sUserLoginEmail, sPermissionId);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsHost(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsHost(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsPresenter(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsPresenter(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsParticipant(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToMeetingAsParticipant(meetingURL,userLoginEmail);
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToVirtualClassroomAsPendingApproval(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "pending");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//
//    /**
//     *
//     * @param meetingURL
//     * @param userLoginEmail
//     * @throws Exception
//     */
//    public void addUserToMeetingAsHost(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "host");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addUserToMeetingAsPresenter(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "mini-host");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addUserToMeetingAsParticipant(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void denyScoAccessToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "denied");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//
//    public void allowScoViewToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void allowScoPublishToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "publish");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void allowScoManageToUser(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "manage");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void denyMeetingAccessToUser(String meetingURL,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getMeetingScoID(meetingURL), userLoginEmail, "remove");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public void addAsLearner(String scoName,String userLoginEmail) throws Exception
//    {
//        addUserToSco(getScoID(scoName), userLoginEmail, "view");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+" Success" );
//    }
//    public String getRecordingUrlPath(String meetingURL, String recordingName) throws Exception{
//        return getRecordingUrlPath(meetingURL, recordingName, 1);
//    }
//    private String getRecordingUrlPath(String meetingURL, String recordingName ,int tries) throws Exception
//    {
//        String scoId = getMeetingScoID(meetingURL);
//        String recordingURL =null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoId});
//        updateParams.put("filter-icon", new String[] {"archive"});
//        org.jdom.Element resp = breezeSession.apiPost("sco-contents", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting recordings of the meeting "+meetingURL);
//        }
//        List<Element> content = resp.getContent();
//        org.jdom.Element scos = content.get(1);
//        List<Element> scoList = scos.getContent();
//        for (Iterator<Element> iterator = scoList.iterator(); iterator.hasNext();) {
//            org.jdom.Element sco1 = iterator.next();
//            if(sco1.getChild("name").getValue().equalsIgnoreCase(recordingName))
//            {
//                if(sco1.getChild("date-end")==null&&tries<=10)
//                {
//                    Thread.sleep(1000);
//                    return getRecordingUrlPath(meetingURL, recordingName, tries+1);
//                }
//                else if(sco1.getChild("date-end")!=null)
//                {
//                    String recURL = sco1.getChild("url-path").getValue();
//                    recordingURL = "http://"+getDomainName()+recURL;
//                    return recordingURL;
//                }
//                else if(tries>10)
//                {
//                    log.error((new Exception().getStackTrace()[0].getMethodName())+"Failed Recording not prepared yet" );
//                    throw new Failure(recordingName+" Recording not prepared yet!!");
//                }
//            }
//        }
//        log.error((new Exception().getStackTrace()[0].getMethodName())+"Failed "+recordingName+"Recording not present" );
//        throw new Failure(recordingName+" Recording not present!!");
//    }
//
//    public String getScoID(String scoName) throws Exception
//    {
//        String scoid=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("query", new String[] {scoName});
//        org.jdom.Element resp = breezeSession.apiPost("sco-search-by-field", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the Asset  "+scoName+" scoid");
//        }
//        List<Element> scoList=resp.getChild("sco-search-by-field-info").getChildren("sco");
//        Iterator<Element> scoIterator=scoList.iterator();
//        while(scoIterator.hasNext())
//        {
//            org.jdom.Element sco=scoIterator.next();
//            String hereName=sco.getChildText("name");
//            if(scoName.equals(hereName))
//            {
//                scoid=sco.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Asset "+scoName+" not found.");
//            throw new Failure("Asset"+scoName+" not found.");
//        }
//
//        return scoid;
//    }
//
//
//
//
//
//    public String getServerDateTimeID() throws Exception
//    {
//        String dateTime=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common= resp.getChild("common");
//        org.jdom.Element date = common.getChild("date");
//        dateTime=date.getText();
//        return dateTime;
//    }
//    public String getAccountId() throws Exception
//    {
//        String accountId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common= resp.getChild("common");
//        org.jdom.Element accIdNode = common.getChild("account");
//        accountId=accIdNode.getAttributeValue("account-id");
//
//        return accountId;
//    }
//    public Map<String, String> getQuizDescription(String scoID) throws Exception
//    {
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoID});
//        org.jdom.Element resp = breezeSession.apiPost("report-asset-interaction-info", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the quizes for sco-id "+scoID);
//        }
//        List<Element> interactionList=resp.getChild("report-asset-interactions").getChildren("interaction");
//        Iterator<Element> interactionIterator=interactionList.iterator();
//        Map<String,String> interactionMap=new HashMap<String, String>();
//        while(interactionIterator.hasNext())
//        {
//            org.jdom.Element interaction=interactionIterator.next();
//            String interactionID=interaction.getAttributeValue("interaction-id");
//            String desciption=interaction.getChildText("description");
//            interactionMap.put(interactionID, desciption);
//        }
//        return interactionMap;
//    }
//
//    public String createGroup(String name,String accountId, String description) throws Exception
//    {
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("account-id", new String[] {accountId});
//        Params.put("name", new String[] {name});
//        Params.put("description", new String[] {description});
//        Params.put("has-children", new String[] {"1"});
//        Params.put("type", new String[] {"group"});
//
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            if(("duplicate".equalsIgnoreCase(resp.getChild("status").getChild("invalid").getAttributeValue("subcode"))))
//            {
//                log.info("Group already exists.");
//                return "duplicate";
//            }
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco: Creation of following group failed. ");
//        }else
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            log.info("Group succesfully created.");
//        }
//        return newUserPrincipalId;
//
//    }
//    public String createUser(String fname, String lname, String login, String email, String password,String ...sendMail) throws Exception
//    {
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("first-name", new String[] {fname});
//        Params.put("last-name", new String[] {lname});
//        Params.put("login", new String[] {login});
//        Params.put("password", new String[] {password});
//        Params.put("type", new String[] {"user"});
//        if(sendMail.length==0)
//            Params.put("send-email", new String[] { "false" });
//        else
//            Params.put("send-email", new String[] { sendMail[0]});
//        Params.put("has-children", new String[] {"0"});
//        Params.put("email", new String[] {email});
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            if(("duplicate".equalsIgnoreCase(resp.getChild("status").getChild("invalid").getAttributeValue("subcode"))))
//            {
//                log.info("User "+login+" already exists.");
//                return "duplicate";
//            }
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating sco: Creation of following "+login+"failed. ");
//        }else
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            log.info("User "+login+" succesfully created.");
//        }
//        return newUserPrincipalId;
//
//    }
//    public void makeUserAdmins(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting admin group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user admin permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned admin permission to user.");
//            }
//        }
//    }
//
//    public void makeUserAuthors(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"authors"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting author group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user author permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned author permission to user.");
//            }
//        }
//
//    }
//    public void makeUserTrainingManager(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"course-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Training Manager group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                //throw new Failure("Response error while creating giving user Training Manager permissions");
//                return;
//            }
//            else
//            {
//                log.info(" Succesfully assigned Training Manager permission to user.");
//            }
//        }
//
//    }
//    public void makeUserEventManager(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"event-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Event Manager group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Event Manager permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Event Manager permission to user.");
//            }
//        }
//
//    }
//    public void makeUserEventAdministrator(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"event-super-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Event Administrator group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Event Administrator permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Event Administrator permission to user.");
//            }
//        }
//
//    }
//    public void makeUserLearners(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"learners"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting Learners group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user Learners permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned Learners permission to user.");
//            }
//        }
//
//    }
//    public void makeUserMeetingHost(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"live-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting meeting hosts group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user meeting hosts permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned meeting hosts permission to user.");
//            }
//        }
//
//    }
//
//    public boolean selfEnroll(
//            String scoOfTrgObj) throws Exception {
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] { scoOfTrgObj });
//
//
//
//
//        org.jdom.Element resp = breezeSession.apiPost("self-enroll",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error self enrolling");
//
//        }
//
//
//        return true;
//
//
//
//    }
//
//
//
//    public boolean selfUnEnroll(
//            String scoOfTrgObj) throws Exception {
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] { scoOfTrgObj });
//        updateParams.put("withdrawEnrollment", new String[] { "true" });
//
//
//
//
//
//        org.jdom.Element resp = breezeSession.apiPost("self-enroll",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error self un-enrolling");
//
//        }
//
//
//        return true;
//
//
//
//    }
//    public boolean changePermissionsVCMakeLearnerParticipant(String logIn,String scoIDOfTrg) throws Exception
//    {
//        boolean haschanged=false;
//        String principalID=getPrincipalIdOfUser(logIn);
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] { scoIDOfTrg});
//        updateParams.put("principal-id", new String[] { principalID});
//        updateParams.put("permission-id",new String[] { "view" });
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error changinng the permissions");
//        }
//
//        else
//            haschanged=true;
//
//
//        return haschanged;
//
//    }
//
//
//    public boolean changePermissionsVCDenyLearner(String logIn,String scoIDOfTrg) throws Exception
//    {
//        boolean hasdenied=false;
//        String principalID=getPrincipalIdOfUser(logIn);
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("acl-id", new String[] { scoIDOfTrg});
//        updateParams.put("principal-id", new String[] { principalID});
//        updateParams.put("permission-id",new String[] { "denied" });
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-update",
//                updateParams);
//
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error denying the learner");
//        }
//
//        else
//            hasdenied=true;
//
//
//        return hasdenied;
//
//    }
//
//
//
//    public String checkPermissionsOnASco(String logIn, String scoIdOfObject) throws Exception
//    {
//        String status=null;
//        //String principalid= getPrincipalIdOfUser(logIn);
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        updateParams.put("acl-id", new String[] { scoIdOfObject});
//        //updateParams.put("pricipal-id", new String[] { principalid});
//
//        org.jdom.Element resp = breezeSession.apiPost("permissions-info",
//                updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error fetching the permissions info");
//        }
//
//        List<Element> principalList = resp.getChild("permissions").getChildren("principal");
//
//        Iterator<Element> prinIterator = principalList.iterator();
//
//        while (prinIterator.hasNext()) {
//            org.jdom.Element sco = prinIterator.next();
//            String logInXML = sco.getChildText("login");
//            if (logIn.equals(logInXML)) {
//                status = sco.getAttributeValue("permission-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//                break;
//            }
//        }
//
//
//
//
//        return status;
//
//
//
//
//    }
//
//
//
//
//
//
//    public int getNumberOfTrgsOfAName(String trgName) throws Exception
//    {
//        int number_of_trgs=0;
//
//
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//
//        updateParams.put("filter-name", new String[] { trgName});
//        org.jdom.Element resp = breezeSession.apiPost("report-my-training",
//                updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                "code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + "Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure(
//                    "Response error changinng the permissions");
//        }
//
//        List<Element> trgList = resp.getChild("report-my-training").getChildren("row");
//
//        Iterator<Element> trgIterator = trgList.iterator();
//
//        while (trgIterator.hasNext()) {
//            org.jdom.Element sco = trgIterator.next();
//            String NameInXML = sco.getChildText("name");
//            if (trgName.equals(NameInXML)) {
//
//                number_of_trgs=number_of_trgs+1;
//
//                log.info((new Exception().getStackTrace()[0].getMethodName())
//                        + "Success");
//
//
//            }
//        }
//
//        return number_of_trgs;
//    }
//
//
//    public void loginasEndUseronHosted(String userName, String password) throws Failure {
//        try {
//            breezeSession.login(userName, password);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//    }
//
//    public void makeUserSeminarHost(String principalId)throws Exception
//    {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//        String groupId = null;
//
//        Params.put("filter-type", new String[] {"seminar-admins"});
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            return;
//            //throw new Failure("Response error while getting seminar hosts group ID");
//        }else
//        {
//            groupId = resp.getChild("principal-list").getChild("principal").getAttributeValue("principal-id");
//            log.info("Got the Group Id");
//
//            updateGroupParams.put("group-id",new String[] {groupId});
//            updateGroupParams.put("principal-id",new String[] {principalId});
//            updateGroupParams.put("is-member",new String[] {"true"});
//
//            org.jdom.Element resp2= breezeSession.apiPost("group-membership-update", updateGroupParams);
//            if(!("ok".equalsIgnoreCase(resp2.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp2.getChild("status").getAttributeValue("code") );
//                return;
//                //throw new Failure("Response error while creating giving user seminar hosts permissions");
//            }
//            else
//            {
//                log.info(" Succesfully assigned seminar hosts permission to user.");
//            }
//        }
//    }
//
//    public String createCustomField(String objectType,String perissionId, String accountId, String name, String comment,String filedType, String isRequired , String isPrimary) throws Exception{
//
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("object-type", new String[] {objectType});
//        Params.put("permission-id",new String[] {perissionId});
//        Params.put("account-id", new String[]{accountId});
//        Params.put("name", new String[]{name});
//        Params.put("comments", new String[]{comment});
//        Params.put("field-type", new String[]{filedType});
//        Params.put("is-required",new String[] {isRequired});
//        Params.put("is-primary",new String[] {isPrimary});
//        String customFieldId=null;
//
//        org.jdom.Element resp = breezeSession.apiPost("custom-field-update", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return null;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating custom-field, action failed. ");
//
//        }
//        else
//        {
//
//            log.info("custom field succesfully created.");
//            customFieldId=resp.getChild("field").getAttributeValue("field-id").toString();
//        }
//        return customFieldId;
//    }
//    public boolean deleteCustomFiled(String objectType,String fieldId) throws Exception{
//
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("object-type", new String[] {objectType});
//
//        Params.put("field-id",new String[] {fieldId});
//
//
//        org.jdom.Element resp = breezeSession.apiPost("custom-fields-delete", Params);
//        log.info(resp.toString());
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return false;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating custom-field, action failed. ");
//
//        }
//        else
//        {
//
//            log.info("custom field deleted created.");
//            return true;
//
//        }
//
//    }
//    public String getMp4RecordingJobStatus(String mp4RecordingName) throws Exception{
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("filter-name", new String[] {mp4RecordingName});
//
//        String jobStatus=null;
//
//        org.jdom.Element resp = breezeSession.apiPost("get-mp4-encoder-queue", Params);
//        if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            return null;
//        }
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//
//            log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error , action failed. ");
//
//        }
//        else
//        {
//
//            log.info("succesfully .");
//            List<Element> recordingJobs=resp.getChild("mp4-encoder-queue").getChildren("recording-job");
//            for(int i=0;i<recordingJobs.size();i++){
//                if(recordingJobs.get(i).getChild("name").getValue().equals(mp4RecordingName)){
//                    return recordingJobs.get(i).getAttributeValue("job-status");
//                }
//            }
//        }
//        return null;
//    }
//
//    //	public String getMp4RecordingJobStatus(String mp4RecordingName) throws Exception{
//    //		Map<String, String[]> Params = new HashMap<String, String[]>();
//    //		String jobId=getMp4RecordingJobId(mp4RecordingName);
//    //		Params.put("job-id", new String[] {jobId});
//    //
//    //		String jobStatus=null;
//    //
//    //		org.jdom.Element resp = breezeSession.apiPost("get-recording-job", Params);
//    //		if(("invalid".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//    //		{
//    //				return null;
//    //		}
//    //		if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//    //		{
//    //
//    //			log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//    //			throw new Failure("Response error , action failed. ");
//    //
//    //		}
//    //		else
//    //    	{
//    //
//    //			log.info("api call succesfull");
//    //			jobStatus=resp.getChild("recording-job").getAttributeValue("job-status").toString();
//    //    	}
//    //		return jobStatus;
//    //	}
//    //
//
//    public void loginWithWrongPassword(String login, String password, String accountId, String codes, String subcode) throws Failure{
//        try{
//
//
//            Map<String, String[]> params = new HashMap<String, String[]>();
//            params.put("login", new String[]{login});
//            params.put("password", new String[]{password});
//            if(accountId!=null && !"".equals(accountId))
//                params.put("account-id", new String[]{accountId});
//            Element resp = breezeSession.apiPost("login", params);
//            System.out.println(resp.getChild("status").getAttributeValue("code"));
//            if(codes.equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))
//            {
//                System.out.println("Login failed!");
//            }
//            else if(codes.equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")) && subcode!=null)
//            {
//                System.out.println(resp.getChild("status").getAttributeValue("subcode"));
//                if(subcode.equalsIgnoreCase(resp.getChild("status").getAttributeValue("subcode")))
//                {
//                    System.out.println("User Suspended!");
//                }
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteUser(String login) throws Failure
//    {
//        try{
//
//
//            Map<String, String[]> params = new HashMap<String, String[]>();
//
//            String principalID = getPrincipalIdOfUser(login);
//            params.put("principal-id", new String[]{principalID});
//            Element resp = breezeSession.apiPost("principals-delete", params);
//            System.out.println(resp.getChild("status").getAttributeValue("code"));
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//
//            }
//
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not delete: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    //public void createCustomTemplate(String templateName,List<String> sequenceNumbers,List<String> headerIds,List<String> customLabels, String excludeHeader, String reportType) throws Failure
//    public void createCustomTemplate(String templateName, String reportType) throws Failure
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("template-name", new String[]{templateName});
//
//		/*String[] value = new String[sequenceNumbers.size()];
//			for (int i = 0; i < sequenceNumbers.size(); i++)
//			{
//				value[i] = sequenceNumbers.get(i);
//
//			}
//			params.put("column-sequence-number", value);
//
//			String[] value1 = new String[headerIds.size()];
//			for (int i = 0; i < headerIds.size(); i++)
//			{
//				value1[i] = headerIds.get(i);
//			}
//			params.put("header-id", value1);
//
//			String[] value2 = new String[customLabels.size()];
//			for (int i = 0; i < customLabels.size(); i++)
//			{
//				value2[i] = customLabels.get(i);
//			}
//			params.put("header-custom-name", value2);*/
//
//        params.put("report-type", new String[]{reportType});
//
//        //params.put("exclude-header",new String[] {excludeHeader});
//
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-template-update", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not create custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public void deleteTemplate(String templateName) throws Failure
//    {
//        String templateId=getTemplateId(templateName);
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("template-id", new String[]{templateId});
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-template-delete", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not delete custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    private String getTemplateId(String templateName) throws Failure
//    {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params = null;
//
//        try
//        {
//            Element resp = breezeSession.apiPost("report-export-templates-list", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//            else
//            {
//                if(resp.getChild("template-name").equals(templateName))
//                    return resp.getAttributeValue("template-id");
//            }
//        }
//
//        catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not create custom template: Failure");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//
//        return null;
//    }
//
//    /**
//     * @param enable
//     * @throws Failure
//     */
//    public void changeLoginPwPolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("change-login-pw-policy", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public void deleteUserPrincipalId(String principalId) throws Exception {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("principal-id", new String[] { principalId });
//        org.jdom.Element resp = breezeSession.apiPost("principals-delete",
//                Params);
//        if (("no-data".equalsIgnoreCase(resp.getChild("status")
//                .getAttributeValue("code")))) {
//            throw new Failure("No such id exists ");
//        }
//
//    }
//    public void changeCustomizePolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("customization", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public void changeModifyAddUserPolicyForLimtdAdmin(String enable) throws Failure {
//
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("modify-current-users-groups", new String[]{"true"} );
//        params.put("add-users-groups-webui", new String[]{enable} );
//        try
//        {
//            org.jdom.Element resp = breezeSession.apiPost("limited-administrator-permissions-update", params);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            }else
//            {
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//            }
//        }catch (Exception e)
//        {
//            log.error(e.getMessage(), e);
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+"Could not change login password policy for Limited Admin");
//        }
//
//    }
//
//    public String getPrincipalIdSystemGroup(String groupType)throws Failure{
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        String groupId = null;
//        INIFile objINI = new INIFile();
//        try {
//            Params.put("filter-type", new String[] {groupType});
//            org.jdom.Element resp = breezeSession.apiPost("principal-list", Params);
//            if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue(
//                    "code")))) {
//
//                log.error("Response : "
//                        + resp.getChild("status").getAttributeValue("code"));
//
//                throw new Failure("Response error while getting group ID");
//            } else {
//                groupId = resp.getChild("principal-list").getChild("principal")
//                        .getAttributeValue("principal-id");
//            }
//        }catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return groupId;
//    }
//
//    public void addUserToGroup(String principalId,String groupId) throws Exception {
//        Map<String, String[]> updateGroupParams = new HashMap<String, String[]>();
//
//        updateGroupParams.put("group-id", new String[] { groupId });
//        updateGroupParams.put("principal-id", new String[] { principalId });
//        updateGroupParams.put("is-member", new String[] { "true" });
//
//        org.jdom.Element resp2 = breezeSession.apiPost(
//                "group-membership-update", updateGroupParams);
//        if (!("ok".equalsIgnoreCase(resp2.getChild("status")
//                .getAttributeValue("code")))) {
//
//            log.error("Response : "
//                    + resp2.getChild("status").getAttributeValue("code"));
//
//            throw new Failure("Response error while adding user to group!!");
//        } else {
//            log.info(" Succesfully added user to group.");
//        }
//    }
//
//
//    public void verifycreateUserNotAuthorized(String fname, String lname, String login, String email, String password) throws Exception
//    {
//
//        String newUserPrincipalId=null;
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        Params.put("first-name", new String[] {fname});
//        Params.put("last-name", new String[] {lname});
//        Params.put("login", new String[] {login});
//        Params.put("password", new String[] {password});
//        Params.put("type", new String[] {"user"});
//        Params.put("send-email", new String[] {"false"});
//        Params.put("has-children", new String[] {"0"});
//        Params.put("email", new String[] {email});
//
//        org.jdom.Element resp = breezeSession.apiPost("principal-update", Params);
//        if(!("no-access".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            newUserPrincipalId = resp.getChild("principal").getAttributeValue("principal-id");
//            deleteUser(newUserPrincipalId);
//            throw new Failure("User is authorized to create new users!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//
//    }
//    public void verifyDeleteUserNotAuthorized(String login) throws Exception
//    {
//        Map<String, String[]> params = new HashMap<String, String[]>();
//
//        String principalID = getPrincipalIdOfUser(login);
//        params.put("principal-id", new String[]{principalID});
//        Element resp = breezeSession.apiPost("principals-delete", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("no-access".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("User is authorized to delete users!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//
//
//    }
//
//    public String getGroupPrincipalIdAPI(String GroupName) throws Failure {
//        String principal_id = null;
//        INIFile objINI = new INIFile();
//        String sAction = "permissions-info";
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("acl-id", new String[]{objINI.getStringProperty("sut", "accountid")} );
//            hParams.put("filter-name", new String[]{GroupName} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else
//            {
//                principal_id = resp.getChild("permissions").getChild("principal").getAttributeValue("principal-id");
//            }
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return principal_id;
//    }
//
//    public void updateGroupMembershipOfUser(String addOrRemoveGroupBoolean, String sAdminGroupId, String sUserId) throws Failure
//    {
//        String sAction = "group-membership-update";
//        try
//        {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("group-id", new String[]{sAdminGroupId});
//            hParams.put("principal-id", new String[]{sUserId});
//            hParams.put("is-member", new String[]{addOrRemoveGroupBoolean});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Could not update group membership of user!!");
//            }
//        }
//        catch (Exception e)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())+ "Success");
//    }
//
//    public boolean verifyUserInGroup(String groupPrincipalID, String userPrincipalID) throws Failure
//    {
//        String sAction = "principal-list";
//        String principalID = null;
//        boolean exists = false;
//        try
//        {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.clear();
//            hParams.put("group-id", new String[]{groupPrincipalID});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Could not retrieve group list!!");
//            }
//            else
//            {
//                List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//                Iterator<Element> prinIterator = principalList.iterator();
//                while (prinIterator.hasNext())
//                {
//                    org.jdom.Element sco = prinIterator.next();
//                    principalID = sco.getAttributeValue("principal-id");
//                    if(principalID.equals(userPrincipalID))
//                    {
//                        if(sco.getChild("is-member").getValue().equals("true"))
//                        {
//                            exists = true;
//                            break;
//                        }
//                        else
//                            break;
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName())+ " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())+ "Success");
//        return exists;
//    }
//    public void updateLoginHelpLink(String customUrl) throws Exception{
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("account-id", new String[]{iniObj.getStringProperty("sut", "accountid")});
//        params.put("field-id",new String[]{"account-login-help-link"});
//        params.put("value",new String[]{customUrl});
//
//        Element resp = breezeSession.apiPost("account-custom-help-link-update", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("cannot update help link in login page!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public void updateWebAppHelpLink(String customUrl) throws Exception{
//        Map<String, String[]> params = new HashMap<String, String[]>();
//        params.put("account-id", new String[]{iniObj.getStringProperty("sut", "accountid")});
//        params.put("field-id",new String[]{"account-webapp-help-link"});
//        params.put("value",new String[]{customUrl});
//
//        Element resp = breezeSession.apiPost("account-custom-help-link-update", params);
//        System.out.println(resp.getChild("status").getAttributeValue("code"));
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            throw new Failure("cannot update help link in webapp!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public void deleteEvent(String eventName) throws Exception
//    {
//        String scoid=getEventScoID(eventName);
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("sco-id", new String[] {scoid});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParams);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the event");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    public String getEventsScoID(String eventName) throws Exception	{
//        String scoid=null;
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("get-unpublished", new String[]{"true"});
//        Element resp = breezeSession.apiPost("event-list", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            throw new Failure("event-list API call failed!");
//
//        @SuppressWarnings("unchecked")
//        List<Element> events=resp.getChild("event-list").getChildren("event");
//        Iterator<Element> eventIterator=events.iterator();
//        while(eventIterator.hasNext()) {
//            org.jdom.Element event=eventIterator.next();
//            if(eventName.equalsIgnoreCase(event.getChildText("name"))) {
//                scoid=event.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Event "+eventName+" not found.");
//            throw new Failure("Event "+eventName+" not found.");
//        }
//        return scoid;
//    }
//
//    public String getEventScoID(String eventName) throws Exception
//    {
//        String scoid=null;
//        org.jdom.Element resp = breezeSession.apiPost("report-my-events", null);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting the meeting "+eventName+" scoid");
//        }
//
//        List<Element> events=resp.getChild("my-events").getChildren("event");
//
//        Iterator<Element> eventIterator=events.iterator();
//        while(eventIterator.hasNext())
//        {
//            org.jdom.Element event=eventIterator.next();
//            if(eventName.equalsIgnoreCase(event.getChildText("name")))
//            {
//                scoid=event.getAttributeValue("sco-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//                break;
//            }
//        }
//        if(scoid==null)
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+" Failure. Event "+eventName+" not found.");
//            throw new Failure("Event "+eventName+" not found.");
//        }
//        return scoid;
//    }
//
//    //Verify that AddinInfo.xml contains the information about minimum CEF versions and verify that the versions maintained are not less than minimum CEF version expected
//    public boolean verifyMinCEFVersions(String expectedMinimumCEFVersionForMAC,String expectedMinimumCEFVersionForWIN) throws Exception
//    {
//        String platform = null;
//        boolean MACVerified=false;
//        boolean WINVerified=false;
//        boolean verified=false;
//        String CEFValueMAC=null,CEFValueWIN=null;
//        //Store the expected CEF version value in ArrayList
//        List<String> minimumCEFVersionMACList = Arrays.asList(expectedMinimumCEFVersionForMAC.split(","));
//        List<String> minimumCEFVersionWINList = Arrays.asList(expectedMinimumCEFVersionForWIN.split(","));
//        List<String> CEFVersionList =null;
//        //Api post for addinInfo.xml
//        org.jdom.Element response = breezeSession.apiPostForAddinInfo();
//        //Get <m> id objects
//        List<Element> messages = response.getChildren("m");
//        for (Iterator<Element> iterator = messages.iterator(); iterator.hasNext();)
//        {
//            org.jdom.Element m1 = iterator.next();
//            //Check that <m> attribute contains CEFPackageVersion
//            if(m1.getAttribute("id").getValue().equalsIgnoreCase("CEFPackageVersion"))
//            {
//                //Check whether the platform is WIN/MAC
//                platform = m1.getAttributeValue("platform");
//
//                if(platform.equals("Windows"))
//                {
//                    CEFValueWIN=m1.getValue();
//                    //Store the retrieved CEF version value in ArrayList
//                    CEFVersionList = Arrays.asList(CEFValueWIN.split(","));
//                    for (int i=0; i<minimumCEFVersionWINList.size(); i++)
//                    {
//                        if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i))>0)
//                        {
//                            WINVerified=true;
//                            break;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i)) ==0)
//                        {
//                            if(i==minimumCEFVersionWINList.size()-1)
//                                WINVerified=true;
//                            else
//                                continue;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionWINList.get(i))<0)
//                        {
//                            WINVerified=false;
//                            break;
//                        }
//                    }
//                }
//                else if(platform.equals("Mac OS 10"))
//                {
//                    CEFValueMAC=m1.getValue();
//                    //Store the retrieved CEF version value in ArrayList
//                    CEFVersionList = Arrays.asList(CEFValueMAC.split(","));
//                    for (int i=0; i<minimumCEFVersionWINList.size(); i++)
//                    {
//                        if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i))>0)
//                        {
//                            MACVerified=true;
//                            break;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i)) ==0)
//                        {
//                            if(i==minimumCEFVersionMACList.size()-1)
//                                MACVerified=true;
//                            else
//                                continue;
//                        }
//                        else if(CEFVersionList.get(i).compareTo(minimumCEFVersionMACList.get(i))<0)
//                        {
//                            MACVerified=false;
//                            break;
//                        }
//                    }
//                }
//
//            }
//        }
//        if(WINVerified && MACVerified)
//            verified=true;
//        else if(!WINVerified)
//            throw new Failure("Verify Mimimum CEF Version for WIN failed !");
//        else if(!MACVerified)
//            throw new Failure("Verify Mimimum CEF Version for MAC failed !");
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return verified;
//    }
//
//    public void verifyUser(String principalId) throws Exception {
//        Map<String, String[]> Params = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("principal-list", null);
//        Element principalList = (resp.getChild("principal-list"));
//        Boolean flag = false;
//        List contentList = principalList.getContent();
//        for (int i = 0; i < contentList.size(); i++) {
//            Element element = (Element) contentList.get(i);
//            List attrList = element.getAttributes();
//            for (int j = 0; j < attrList.size(); j++) {
//                org.jdom.Attribute id = (org.jdom.Attribute) attrList.get(j);
//                String value = id.getValue();
//                if (principalId.equals(value)) {
//                    flag = true;
//                }
//
//            }
//
//        }
//        if (flag == false) {
//            throw new Failure("User not created ");
//        }
//
//    }
//
//    public void login(String userName, String password) throws Failure {
//        try {
//            breezeSession.login(userName,password);
//        }
//        catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new Failure("Login Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallNotificationList(String eventScoId)	throws Exception {
//
//        Map<String, String[]> targetAclIDs = new HashMap<String, String[]>();
//        targetAclIDs.put("target-acl-id", new String[]{eventScoId});
//        Element resp = breezeSession.apiPost("notification-list", targetAclIDs);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("notification-list API call failed!");
//        }
//
//        return resp;
//    }
//
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallEventNotificationStatusUpdate(String accountId, String eventScoId,
//                                                        String actionId, String isSelected) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("account-id", new String[]{accountId});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("action-id", new String[]{actionId});
//        requestParameters.put("is-selected", new String[]{isSelected});
//
//        return breezeSession.apiPost("event-notification-status-update", requestParameters);
//    }
//
//    // Updated by Marko D. 20/04/2016
//    public Map<String, String>  getDescriptionAndInteractionIdFromResponse(Element resp) {
//        Map<String, String> assetIdAndInteractionIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            assetIdAndInteractionIdMap.put(interactionElement.getChild("description").getValue(), interactionElement.getAttributeValue("interaction-id"));
//        }
//        return assetIdAndInteractionIdMap;
//    }
//
//    // Updated by Marko D. 01/04/2016
//    public Element ApiCallEventList(String getUnpublished)	throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("get-unpublished", new String[]{getUnpublished});
//        Element resp = breezeSession.apiPost("event-list", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("event-list API call failed!");
//        }
//        return resp;
//    }
//
//    // Updated by Marko D. 04/04/2016
//    public Element ApiCallAssetInteractionInfo(String eventScoId)	throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        Element resp = breezeSession.apiPost("asset-interaction-info", requestParameters);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))){
//            throw new Failure("asset-interaction-info API call failed!");
//        }
//        return resp;
//    }
//
//    // Updated by Marko D. 04/04/2016
//    public Element ApiCallAssetInteractionDelete( String isSelected, String interactionId, String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("is-selected", new String[]{isSelected});
//        requestParameters.put("interaction-id", new String[]{interactionId});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("asset-interaction-delete", requestParameters);
//    }
//
//    // Created by Marko D. 27/05/2016
//    public Map<String, String>  getDescriptionAndAssetIdFromResponse(Element resp) {
//        Map<String, String> descriptionAndAssetIdFromResponseMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            descriptionAndAssetIdFromResponseMap.put(interactionElement.getChild("description").getValue(),interactionElement.getAttribute("asset-id").getValue());
//            System.out.println("Asset: " + interactionElement.getChild("description").getValue() + " - Asset ID: " + interactionElement.getAttribute("asset-id").getValue());
//        }
//        return descriptionAndAssetIdFromResponseMap;
//    }
//
//    public Map<String, String>  getAssetDescriptionAndDisplaySeqFromResponse(Element resp) {
//        Map<String, String> assetDescriptionAndDisplaySeqMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interactions").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            assetDescriptionAndDisplaySeqMap.put(interactionElement.getChild("description").getValue(),interactionElement.getAttribute("display-seq").getValue());
//            System.out.println("Asset: " + interactionElement.getChild("description").getValue() + "- display seq: " + interactionElement.getAttribute("display-seq").getValue());
//        }
//        return assetDescriptionAndDisplaySeqMap;
//    }
//
//    // Shift registration question up (true) or down (false) - Marko D. 09/13/2016
//    public Element ApiCallAssetInteractionOrderUpdate(String up, String eventScoId, String assetId,String interactionId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("up", new String[]{up});
//        requestParameters.put("type", new String[]{"event"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("asset-id", new String[]{assetId});
//        requestParameters.put("interaction-id", new String[]{interactionId});
//
//        return breezeSession.apiPost("asset-interactions-order-update", requestParameters);
//    }
//
//
//    // Create Multiple Choice question with answers API - Marko D. 05/04/2016
//    public Element ApiCallAssetInteractionResponseUpdateChoice(String eventScoId,String questionDescription, String type, String firstAnswer,
//                                                               String secondAnswer, String thirdAnswer) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("interaction-type", new String[]{"choice"});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("is-required", new String[]{"true"});
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("csvResponse", new String[]{""});
//        requestParameters.put("csvChoicesEnabled", new String[]{"false"});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("response-display-seq", new String[]{"1","2","3"});
//        requestParameters.put("response-description", new String[]{firstAnswer,secondAnswer,thirdAnswer});
//        requestParameters.put("response", new String[]{" "}); // removed as mandatory parameter for version 9.5.4 and later based on CL#1313537 - Marko D.  5/20/2016
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Created by Marko D. 20/04/2016
//    public Map<String, String>  getActionIdAndDescriptionFromResponse(Element resp) {
//        Map<String, String> actionIdAndDescriptionMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("notification-list").getChildren("notification");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element notificationElement = iterator.next();
//            actionIdAndDescriptionMap.put(notificationElement.getChild("cq-template-name").getValue(), notificationElement.getAttribute("action-id").getValue());
//            System.out.println("Description: " + notificationElement.getChild("cq-template-name").getValue() + " - sco-id:" + notificationElement.getAttribute("action-id").getValue());
//        }
//        return actionIdAndDescriptionMap;
//    }
//
//    // Created by Marko D. 20/04/2016
//    public Map<String, String>  getInteractionIdAndDescriptionFromResponse(Element resp) {
//        Map<String, String> interactionIdAndDescriptionMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> interactionListElement = resp.getChild("asset-interaction").getChildren("interaction");
//        for (Iterator<Element> iterator = interactionListElement.iterator(); iterator.hasNext();) {
//            Element interactionElement = iterator.next();
//            interactionIdAndDescriptionMap.put(interactionElement.getAttribute("interaction-id").getValue(), interactionElement.getChild("description").getValue());
//            System.out.println("Description: " + interactionElement.getAttribute("interaction-id").getValue() + "-" + interactionElement.getChild("description").getValue());
//        }
//        return interactionIdAndDescriptionMap;
//    }
//
//    // Created by Marko D. 05/04/2016
//    public Element ApiCallAccountInfo(String accountId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("account-id", new String[]{accountId});
//
//        return breezeSession.apiPost("account-info", requestParameters);
//    }
//
//    // Creating a question that needs a short answer API - Marko D. 05/24/2016
//    public Element ApiCallAssetInteractionResponseUpdateFillIn(String eventScoId, String questionDescription) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"fill-in"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//
//    // Creating a question which can be answered with either Yes or No API - Marko D. 05/24/2016
//    public Element ApiCallAssetInteractionResponseUpdateYesNo(String eventScoId, String questionDescription) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"true-false"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Creating Multiple Choice question with answers in the comma separated format API - Marko D. 05/25/2016
//    public Element ApiCallAssetInteractionResponseUpdateChoice2(String eventScoId,String questionDescription, String type,
//                                                                String firstAnswer, String secondAnswer, String thirdAnswer) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//        requestParameters.put("description", new String[]{questionDescription});
//        requestParameters.put("csvChoicesEnabled", new String[]{"true"});
//        requestParameters.put("csvResponse", new String[]{firstAnswer+","+secondAnswer+","+thirdAnswer});
//        requestParameters.put("asset-type", new String[]{"output"});
//        requestParameters.put("interaction-type", new String[]{"choice"});
//        requestParameters.put("is-required", new String[]{"true"});
//
//        return breezeSession.apiPost("asset-interaction-response-update", requestParameters);
//    }
//
//    // Api call to retrieve sco shortcuts for the account - Added by Marko D. 06/03/2016
//    public Element ApiCallScoShortcuts() throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//
//        return breezeSession.apiPost("sco-shortcuts", requestParameters);
//    }
//
//    // Created by Marko D. 06/03/2016
//    public Map<String, String>  getTypeAndScoIdFromResponse(Element resp) {
//        Map<String, String> scoIdAndTypeMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("shortcuts").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndTypeMap.put(scoElement.getAttribute("type").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco type:" + scoElement.getAttribute("type").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndTypeMap;
//    }
//
//    // Api call to list contents for given sco - Added by Marko D. 06/03/2016
//    public Element ApiCallScoContents(String scoId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId});
//
//        return breezeSession.apiPost("sco-contents", requestParameters);
//    }
//
//    // Created by Marko D. 06/06/2016
//    public Map<String, String> getScoIdofContentFromResponse(Element resp) {
//        Map<String, String> scoIdAndContentMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("scos").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndContentMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Name: " + scoElement.getChild("name").getValue() + "- sco id: " + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndContentMap;
//    }
//
//    public String getLicenseSeminarScoID(String seminarLicenseName,String sharedSeminarScoId) {
//
//        String sAction = "sco-contents";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("sco-id", new String[]{sharedSeminarScoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("scos").getChildren();
//                for (Element el : scoId_list) {
//                    if ((el.getChild("name").getText()).contains(seminarLicenseName)){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public void moveMeetingFolderAPI( String folder_scoID, String destination_scoId) throws Failure {
//
//        String sAction = "sco-move";
//
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("folder-id", new String[]{destination_scoId} );
//            hParams.put("sco-id", new String[]{folder_scoID} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error !! ");
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//            throw new Failure((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//    public String getSharedMeetingsScoID(){
//
//        String sAction = "sco-shortcuts";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("shortcuts").getChildren();
//                for (Element el : scoId_list) {
//                    if (el.getAttributeValue("type").equals("meetings")){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public String getMeetingFolderScoID(String folderName,String sharedMeetingsScoId){
//        String sAction = "sco-contents";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("sco-id", new String[]{sharedMeetingsScoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("scos").getChildren();
//                for (Element el : scoId_list) {
//                    if ((el.getChild("name").getText()).equals(folderName)){
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public String getSharedSeminarScoID() {
//        String sAction = "sco-shortcuts";
//        String scoId = null;
//
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//            else {
//                @SuppressWarnings("unchecked")
//                List<Element> scoId_list = resp.getChild("shortcuts").getChildren();
//                for (Element el : scoId_list) {
//                    if (el.getAttributeValue("type").equals("seminars")) {
//                        scoId=el.getAttributeValue("sco-id");
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//        }
//        return scoId;
//    }
//
//    public void moveSeminarRoomAPI( String source_scoId, String destination_scoId) throws Failure {
//
//        String sAction = "sco-seminar-move";
//        try {
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("source-license-id", new String[]{source_scoId} );
//            hParams.put("destination-license-id", new String[]{destination_scoId} );
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//            {
//                log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error while getting accountId !! ");
//            }
//
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//            throw new Failure(
//                    (new Exception().getStackTrace()[0].getMethodName())
//                            + " Failed!!");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//    }
//    @SuppressWarnings("unchecked")
//    public List<Element> getWebinarLicenseInfoAPI(String accountId, String principalId){
//
//        String sAction = "sco-user-webinar-license-info";
//        List<Element> resultList= null;
//        try{
//            Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//            hParams.put("account-id", new String[]{accountId} );
//            hParams.put("principal-id", new String[]{principalId});
//            org.jdom.Element resp = breezeSession.apiPost(sAction, hParams);
//
//            resultList =  resp.getChildren();
//
//        } catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failed!!");
//        }
//
//        log.info((new Exception().getStackTrace()[0].getMethodName())
//                + "Success");
//        return resultList;
//    }
//
//    public Element ApiCallScoUserWebinarLicenseInfo(String accountId, String principalId) throws Exception{
//
//        String sAction = "sco-user-webinar-license-info";
//
//        Hashtable<String, String[]> hParams = new Hashtable<String, String[]>();
//        hParams.put("account-id", new String[]{accountId} );
//        hParams.put("principal-id", new String[]{principalId});
//
//        return breezeSession.apiPost(sAction, hParams);
//    }
//
//
//    // Updated by Marko D. 20/04/2016
//    public Map<String, String>  getUserWebinarLicenseScoIdFromResponse(Element resp) {
//        Map<String, String> licenseScoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> userWebLicListElement = resp.getChildren("user-webinar-license");
//        for (Iterator<Element> iterator = userWebLicListElement.iterator(); iterator.hasNext();) {
//            Element UserWebLicElement = iterator.next();
//            licenseScoIdAndNameMap.put(UserWebLicElement.getAttribute("sco-id").getValue(), UserWebLicElement.getChild("name").getValue());
//            System.out.println("Name: " + UserWebLicElement.getChild("name").getValue() + "- sco-id:" + UserWebLicElement.getAttribute("sco-id").getValue());
//        }
//        return licenseScoIdAndNameMap;
//    }
//
//    // Api call to create/update meeting - Added by Marko D. 12/23/2016
//    public Element APICallMeetingScoUpdate(String name, String type, String folderId, String description) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"meeting"});
//        requestParameters.put("folder-id", new String[]{folderId});
//        requestParameters.put("description", new String[]{description});
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call to create/update event - Added by Marko D. 12/23/2016
//    // Extended Meeting sco update API call
//    public Element APICallEventScoUpdate(String name, String type, String folderId, String description,
//                                         String dateBegin, String dateEnd, String lang, String status,
//                                         String sourceScoId, String URLpath
//    ) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"event"});
//        requestParameters.put("folder-id", new String[]{folderId});
//        requestParameters.put("description", new String[]{description});
//        requestParameters.put("date-begin", new String[]{dateBegin});
//        requestParameters.put("date-end", new String[]{dateEnd});
//        requestParameters.put("lang", new String[]{lang});
//        requestParameters.put("status", new String[]{status});
//        requestParameters.put("source-sco-id", new String[]{sourceScoId});
//        requestParameters.put("url-path", new String[]{URLpath});
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call to obtain my cq templates list - Added by Marko D. 12/23/2016
//    public Element APICallReportMyCQTemplates (String type) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("template-type", new String[]{type});
//
//        return breezeSession.apiPost("report-my-cq-templates", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getMeetingScoId(Element resp) {
//
//        Map<String, String> meetingScoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> meetingsListElement = resp.getChild("my-meetings").getChildren("meeting");
//        for (Iterator<Element> iterator = meetingsListElement.iterator(); iterator.hasNext();) {
//            Element meetingElement = iterator.next();
//            meetingScoIdAndNameMap.put(meetingElement.getAttribute("sco-id").getValue(), meetingElement.getChild("name").getValue());
//            System.out.println("Meeting name: " + meetingElement.getChild("name").getValue() + "- sco-id:" + meetingElement.getAttribute("sco-id").getValue());
//        }
//        return meetingScoIdAndNameMap;
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getScoIdAndNameFromResponse(Element resp) {
//
//        Map<String, String> scoIdAndNameMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChild("scos").getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            scoIdAndNameMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco name:" +scoElement.getChild("name").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return scoIdAndNameMap;
//    }
//
//    // Api call to update principal sco - Added by Marko D. 12/23/2016
//    public Element APICallScoPrincipalUpdate(String scoId, String name, String type, String hasChildren, String isPrimary) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId} );
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{type});
//        requestParameters.put("has-children", new String[]{hasChildren});
//        requestParameters.put("is-primary", new String[]{isPrimary});
//
//        return breezeSession.apiPost("sco-principal-update", requestParameters);
//    }
//
//    // Api call to update event details - Added by Marko D. 12/23/2016
//    public Element APICallEventDetailsUpdate(String eventId, String speakerBriefOverview, String speakerName,
//                                             String speakerDetailedOverview, String isRegistrationLimitEnabled, String passwordBypass,
//                                             String showInCatalog, String eventCategory, String registrationType) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("event-id", new String[]{eventId} );
//        requestParameters.put("speaker-brief-overview", new String[]{speakerBriefOverview});
//        requestParameters.put("speaker-name", new String[]{speakerName});
//        requestParameters.put("speaker-detailed-overview", new String[]{speakerDetailedOverview});
//        requestParameters.put("is-registration-limit-enabled", new String[]{isRegistrationLimitEnabled});
//        requestParameters.put("password-bypass", new String[]{passwordBypass});
//        requestParameters.put("show-in-catalog", new String[]{showInCatalog});
//        requestParameters.put("event-category", new String[]{eventCategory});
//        requestParameters.put("registration-type", new String[]{registrationType});
//
//        return breezeSession.apiPost("event-details-update", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Element APICallEventNotificationUpdate(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-notification-update", requestParameters);
//    }
//
//    // Created by Marko D. 12/23/2016
//    public Map<String, String>  getTemplateNameAndIdFromResponse(Element resp) {
//
//        Map<String, String> templateNameAndIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> templateListElement = resp.getChild("cq-template-list").getChildren("cq-template");
//        for (Iterator<Element> iterator = templateListElement.iterator(); iterator.hasNext();) {
//            Element templateElement = iterator.next();
//            templateNameAndIdMap.put(templateElement.getChild("display-name").getValue(), templateElement.getAttribute("template-id").getValue());
//            System.out.println("Template name: " + templateElement.getChild("display-name").getValue()+ " - sco-id:" + templateElement.getAttribute("template-id").getValue());
//        }
//        return templateNameAndIdMap;
//    }
//
//    // Api call to update acl fields - Created by Marko D. 12/23/2016
//    public Element APICallAclFieldUpdate(String fieldId, String aclId, String value) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("field-id", new String[]{fieldId} );
//        requestParameters.put("acl-id", new String[]{aclId});
//        requestParameters.put("value", new String[]{value});
//
//        return breezeSession.apiPost("acl-field-update", requestParameters);
//    }
//
//    // API call permissions-update to add host to an event or
//    // to make event publicly accessible or only accessible if permitted - Created by Marko D. 12/26/2016
//    public Element APICallPermissionsUpdate(String permissionId, String aclId, String principalId) throws Exception	{
//
//        Map<String, String[]> updateParameters = new HashMap<String, String[]>();
//        updateParameters.put("permission-id", new String[] {permissionId});
//        updateParameters.put("acl-id", new String[] {aclId});
//        updateParameters.put("principal-id", new String[] {principalId});
//
//        return breezeSession.apiPost("permissions-update", updateParameters);
//    }
//
//    // Create metadata for an event using API call - Created by Marko D. 12/23/2016
//    public Element APICallScoUpload(String optional, String scoId, String build) throws Exception	{
//
//        Map<String, String[]> uploadParameters = new HashMap<String, String[]>();
//        uploadParameters.put("optional", new String[] {optional});
//        uploadParameters.put("sco-id", new String[] {scoId});
//        uploadParameters.put("build", new String[] {build});
//        return breezeSession.apiPost("sco-upload", uploadParameters);
//    }
//
//    // Publish an event using API call - Created by Marko D. 12/23/2016
//    public Element APICallEventPublish(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-publish", requestParameters);
//    }
//    // API call acl create - Created by Marko D. 01/17/2016
//    public Element callApiAclCreate(String parentScoId) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("parent-acl-id", new String[] {parentScoId});
//
//        if(!("ok".equalsIgnoreCase(breezeSession.apiPost("acl-create", updateParameters).getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + breezeSession.apiPost("acl-create", updateParameters).getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating Acl");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return breezeSession.apiPost("acl-create", updateParameters);
//    }
//    // Created by Marko D. 01/17/2016
//    public Map<String, String>  getParentAclIDAndAclIdFromResponse(Element resp) {
//        Map<String, String> parentAclIDAndAclIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> aclListElement = resp.getChildren("acl");
//        for (Iterator<Element> iterator = aclListElement.iterator(); iterator.hasNext();) {
//            Element aclElement = iterator.next();
//            parentAclIDAndAclIdMap.put(aclElement.getAttributeValue("parent-acl-id"), aclElement.getAttributeValue("acl-id"));
//            System.out.println(aclElement.getAttributeValue("parent-acl-id") + "  -  " + aclElement.getAttributeValue("acl-id"));
//        }
//        return parentAclIDAndAclIdMap;
//    }
//    // API call for seminar session create - Created by Marko D. 01/17/2016
//    public Element APICallseminarSessionScoUpdate( String scoId, String sourceScoId, String dateBegin, String dateEnd, String name) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("sco-id", new String[] {scoId});
//        updateParameters.put("source-sco-id", new String[] {sourceScoId});
//        updateParameters.put("type", new String[] {"seminarsession"});
//        updateParameters.put("date-begin", new String[] {dateBegin});
//        updateParameters.put("date-end", new String[] {dateEnd});
//        updateParameters.put("name", new String[] {name});
//
//        if(!("ok".equalsIgnoreCase(breezeSession.apiPost("seminar-session-sco-update", updateParameters).getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + breezeSession.apiPost("seminar-session-sco-update", updateParameters).getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while creating a session");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//        return breezeSession.apiPost("acl-create", updateParameters);
//    }
//
//    // Api call to create/update seminar room - Created by Marko D. 01/17/2017
//    public Element APICallSeminarScoUpdate(String name, String scoId) throws Exception {
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("name", new String[]{name});
//        requestParameters.put("type", new String[]{"meeting"});
//        requestParameters.put("sco-id", new String[]{scoId});
//        // sco-id when creating a room is acl-id from response of acl-create api call
//
//        return breezeSession.apiPost("sco-update", requestParameters);
//    }
//
//    // Api call sco delete - Created by Marko D. 01/27/2016
//    public void callApiScoDelete(String scoId) throws Exception	{
//        Map<String, String[]> updateParameters= new HashMap<String, String[]>();
//        updateParameters.put("sco-id", new String[] {scoId});
//        org.jdom.Element resp = breezeSession.apiPost("sco-delete", updateParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while deleting the meeting");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    // Created by Marko D. 01/30/2016
//    public String getPrincipalId() throws Exception	{
//        String principalId=null;
//
//        Map<String, String[]> updateParameters = new HashMap<String, String[]>();
//        org.jdom.Element resp = breezeSession.apiPost("common-info", updateParameters);
//
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting common info");
//        }
//        org.jdom.Element common = resp.getChild("common");
//        org.jdom.Element principalIdNode = common.getChild("user");
//        principalId=principalIdNode.getAttributeValue("user-id");
//        return principalId;
//    }
//
//    // Api call event info - Created by Marko D. 02/09/2017
//    public Element APICallEventInfo(String eventScoId) throws Exception {
//
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{eventScoId});
//
//        return breezeSession.apiPost("event-info", requestParameters);
//    }
//
//    // Created by Marko D. 02/09/2017
//    public Map<String, String>  getNameAndScoIdFromResponse(Element resp) {
//
//        Map<String, String> nameAndScoIdMap = new HashMap<String, String>();
//        @SuppressWarnings("unchecked")
//        List<Element> scoListElement = resp.getChildren("sco");
//        for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//            Element scoElement = iterator.next();
//            nameAndScoIdMap.put(scoElement.getChild("name").getValue(), scoElement.getAttribute("sco-id").getValue());
//            System.out.println("Sco name:" +scoElement.getChild("name").getValue()+ " - sco-id:" + scoElement.getAttribute("sco-id").getValue());
//        }
//        return nameAndScoIdMap;
//    }
//
//    public void verifyUserRegisteredForEvent(String registeredUserLogin,String eventName, boolean checkFor) throws Exception, Failure
//    {
//        boolean flag = false;
//        String scoId = getScoID(eventName);
//        Map<String, String[]> requestParameters = new HashMap<String, String[]>();
//        requestParameters.put("sco-id", new String[]{scoId});
//        org.jdom.Element resp = breezeSession.apiPost("report-event-registration", requestParameters);
//        if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))	{
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while getting event registrants!!");
//        }
//        org.jdom.Element common = resp.getChild("csv");
//        @SuppressWarnings("unchecked")
//        List<Element> rowListElement = common.getChildren("row");
//        for (Iterator<Element> iterator = rowListElement.iterator(); iterator.hasNext();)
//        {
//            Element loginElement = iterator.next();
//            String login = loginElement.getChild("login").getValue();
//            if(login.equals(registeredUserLogin))
//            {
//                String permissionId = loginElement.getAttribute("permission-id").getValue();
//                if(permissionId.equals("view") || permissionId.equals("host"))
//                {
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        if(flag!=checkFor)
//            throw new Failure("Failed to verify user registration for user: "+registeredUserLogin+"!! Expected: "+checkFor+" but got: "+flag+"!!");
//    }
//
//    public boolean verifyEventTemplateExistOrNot(String eventName) throws Exception	{
//
//        boolean flag=false;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("template-type",new String[]{"template-type-event"});
//
//        org.jdom.Element resp = breezeSession.apiPost("report-my-cq-templates", updateParams);
//
//
//        if(!("ok".equals(resp.getChild("status").getAttributeValue("code").toLowerCase())))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while fetching  the event template  list");
//        }
//        else{
//            resp=resp.getChild("cq-template-list");
//            List<Element> scoListElement = resp.getChildren("cq-template");
//            for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//                Element scoElement = iterator.next();
//
//                if(scoElement.getChild("display-name").getValue().equals(eventName)){
//                    flag=true;
//                }
//            }
//
//        }
//        return flag;
//    }
//
//    public boolean verifyEmailTemplateExistOrNot(String eventName) throws Exception	{
//
//        boolean flag=false;
//        Map<String, String[]> updateParams= new HashMap<String, String[]>();
//        updateParams.put("template-type",new String[]{"template-type-email"});
//
//        org.jdom.Element resp = breezeSession.apiPost("report-my-cq-templates", updateParams);
//
//
//        if(!("ok".equals(resp.getChild("status").getAttributeValue("code").toLowerCase())))
//        {
//            log.error((new Exception().getStackTrace()[0].getMethodName())+"Response : " + resp.getChild("status").getAttributeValue("code") );
//            throw new Failure("Response error while fetching  the Email template  list");
//        }
//        else{
//            resp=resp.getChild("cq-template-list");
//            List<Element> scoListElement = resp.getChildren("cq-template");
//            for (Iterator<Element> iterator = scoListElement.iterator(); iterator.hasNext();) {
//                Element scoElement = iterator.next();
//
//                if(scoElement.getChild("display-name").getValue().equals(eventName)){
//                    flag=true;
//                }
//            }
//
//        }
//        return flag;
//    }
//
//    public void verifyUserInMeeting(String meetingName,String userName) throws Exception
//    {
//        boolean exists = false;
//        Map<String, String[]> params=new HashMap<String, String[]>();
//        String scoId=getScoID(meetingName);
//        params.put("sco-id",  new String[] {scoId});
//        org.jdom.Element resp=breezeSession.apiPost("report-meeting-attendance",params);
//        if(("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            List users = resp.getChild("report-meeting-attendance").getChildren("row");
//            Iterator usersIterator = users.iterator();
//            while (usersIterator.hasNext())
//            {
//                org.jdom.Element principal = (org.jdom.Element) usersIterator.next();
//                Element list=principal.getChild("participant-name");
//                String name=list.getValue();
//                if((name.equals(userName)))
//                {
//                    exists = true;
//                    break;
//                }
//            }
//            if(!exists)
//                throw new Failure("User: "+userName+" not present in the meeting: "+meetingName+"!!");
//        }
//        else
//            throw new Failure("Verify user in meeting failed!!");
//
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//    }
//
//
//    public boolean verifyGuestUserInMeeting(String meetingName,String userName) throws Exception
//    {
//        boolean exists = false;
//        Map<String, String[]> params=new HashMap<String, String[]>();
//        String scoId=getScoID(meetingName);
//        params.put("sco-id",  new String[] {scoId});
//        org.jdom.Element resp=breezeSession.apiPost("report-meeting-attendance",params);
//        if(("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code"))))
//        {
//            List users = resp.getChild("report-meeting-attendance").getChildren("row");
//            Iterator usersIterator = users.iterator();
//            while (usersIterator.hasNext())
//            {
//                org.jdom.Element principal = (org.jdom.Element) usersIterator.next();
//                Element list=principal.getChild("session-name");
//                String name=list.getValue();
//                if((name.equals(userName)))
//                {
//                    exists = true;
//                    break;
//                }
//            }
//
//        }
//
//
//        log.info((new Exception().getStackTrace()[0].getMethodName()) + "Success");
//        return exists;
//    }
//
//    // Delete group - Marko D. 18-March-2018
//    public void deleteGroup(String groupName) throws Failure {
//        try {
//            Map<String, String[]> params = new HashMap<String, String[]>();
//            String groupPrincipalID = getPrincipalIdOfGroup(groupName);
//            params.put("principal-id", new String[]{groupPrincipalID});
//            Element resp = breezeSession.apiPost("principals-delete", params);
//            if(!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//                log.error("Response : " + resp.getChild("status").getAttributeValue("code") );
//                throw new Failure("Response error , action failed. ");
//            }
//        }
//        catch (Exception e) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + groupName + " could not be deleted.");
//            throw new Failure("Asset " + groupName + " could not be deleted. Failure.");
//        }
//        log.info((new Exception().getStackTrace()[0].getMethodName())+"Success");
//    }
//
//    // Get principal-id of the group - Marko D. 18-March-2018
//    public String getPrincipalIdOfGroup(String groupName)throws Exception {
//
//        String groupPrincipalId=null;
//        Map<String, String[]> updateParams = new HashMap<String, String[]>();
//        updateParams.put("filter-name", new String[] { groupName });
//        org.jdom.Element resp = breezeSession.apiPost("principal-list",	updateParams);
//        if (!("ok".equalsIgnoreCase(resp.getChild("status").getAttributeValue("code")))) {
//            log.error((new Exception().getStackTrace()[0].getMethodName()) + " Response : "
//                    + resp.getChild("status").getAttributeValue("code"));
//            throw new Failure("Response error while displaying the principal-list");
//        }
//        @SuppressWarnings("unchecked")
//        List<Element> principalList = resp.getChild("principal-list").getChildren("principal");
//        Iterator<Element> principalIterator = principalList.iterator();
//        while (principalIterator.hasNext()) {
//            org.jdom.Element sco = principalIterator.next();
//            String nameXML = sco.getChildText("name");
//            if (groupName.equals(nameXML)) {
//                groupPrincipalId = sco.getAttributeValue("principal-id");
//                log.info((new Exception().getStackTrace()[0].getMethodName()) + " Success");
//                break;
//            }
//        }
//        if (groupPrincipalId == null) {
//            log.error((new Exception().getStackTrace()[0].getMethodName())
//                    + " Failure. Asset " + groupName + " not found.");
//            throw new Failure("Asset" + groupName + " not found.");
//        }
//        return groupPrincipalId;
//    }
//}