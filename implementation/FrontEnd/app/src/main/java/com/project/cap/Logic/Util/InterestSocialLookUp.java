package com.project.cap.Logic.Util;

import com.project.cap.Backend.retrofit.Responses.GetAllSocialsResponse;
import com.project.cap.Entity.Socials;
import com.project.cap.Logic.LogicController;

import java.util.HashMap;
import java.util.List;

public class InterestSocialLookUp {

    public static HashMap<Integer, String> interestDict = new HashMap<>();
    public static HashMap<Integer, String> socialDict = new HashMap<>();

    private InterestSocialLookUp() {
        try {
            LogicController.getInstance().getFutureGateway().getAllInterestsAsync().get()
                    .forEach(iData -> {
                        interestDict.put(iData.getId(), iData.getName());
                    });
            List<GetAllSocialsResponse> socials = LogicController.getInstance().getFutureGateway().getAllSocialsAsync().get();
            int hardcodeId = 1;
            for (int i = 0; i < socials.size(); i++) {
                socialDict.put(hardcodeId, socials.get(i).getName());
                hardcodeId++;
            }
            //LogicController.getInstance().getUserManagement().getAllSocialsAsync().get().forEach(sData -> {
              //  System.out.println(sData.getId());
                //        System.out.println(sData.getName());
                  //      socialDict.put(, sData.getName());
                    //});
        } catch (Exception e){

        }
    }

    public static void initInterestAndSocialLookUp() {
        new InterestSocialLookUp();
    }
}
