package com.smartcity.smartHouse.utils;

import com.mongodb.Mongo;
import com.smartcity.smartHouse.Enums.ConditionType;
import com.smartcity.smartHouse.Enums.UserPlanType;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_ACTION;
import com.smartcity.smartHouse.dataModel.Storage.SM_SCENARIO_CONDITION;
import com.smartcity.smartHouse.dataModel.Storage.SM_USER;
import com.smartcity.smartHouse.db.MongoDbProvider;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DefaultManager {

    public static void setupDefaultUsers() {

        if (MongoDbProvider.getUserWithLogin("kichkin") == null) {
            SM_USER kichkin = new SM_USER();
            kichkin.login = "kichkin";
            kichkin.password = "password";
            kichkin.name = "Кычкин Алексей Владимирович";
            kichkin.houseId = "5abd18279433ee27b68c5d11";
            kichkin.token = Utils.generateToken();
            kichkin.type = UserPlanType.BASE;
            MongoDbProvider.saveUser(kichkin);
        }

        if (MongoDbProvider.getUserWithLogin("vikentieva") == null) {
            SM_USER vikentieva = new SM_USER();
            vikentieva.login = "vikentieva";
            vikentieva.password = "password";
            vikentieva.name = "Викентьева Ольга Леонидовна";
            vikentieva.houseId = "5abd18279433ee27b68c5d11";
            vikentieva.token = Utils.generateToken();
            vikentieva.type = UserPlanType.BASE;
            MongoDbProvider.saveUser(vikentieva);
        }

        if (MongoDbProvider.getUserWithLogin("lebedev") == null) {
            SM_USER lebedev = new SM_USER();
            lebedev.login = "lebedev";
            lebedev.password = "password";
            lebedev.name = "Лебедев Виктор Валерьевич";
            lebedev.houseId = "5abd18279433ee27b68c5d11";
            lebedev.token = Utils.generateToken();
            lebedev.type = UserPlanType.BASE;
            MongoDbProvider.saveUser(lebedev);
        }

        if (MongoDbProvider.getUserWithLogin("korotun") == null) {
            SM_USER korotun = new SM_USER();
            korotun.login = "korotun";
            korotun.password = "password";
            korotun.name = "Коротун Василиса Павловна";
            korotun.houseId = "5abd18279433ee27b68c5d11";
            korotun.token = Utils.generateToken();
            korotun.type = UserPlanType.BASE;
            MongoDbProvider.saveUser(korotun);
        }
    }

    public static void setupDefaultScenario() {

        SM_SCENARIO_CONDITION condition = new SM_SCENARIO_CONDITION();
        condition.sensorId = "5abd49069433ee36debb84d5";
        condition.sensorValue = 150;
        condition.type = ConditionType.MORE;

        MongoDbProvider.saveScenarioCondition(condition);

        SM_SCENARIO_ACTION action = new SM_SCENARIO_ACTION();
        action.actorId = "5abd49079433ee36debb84df";
        action.actorValue = 100;

        MongoDbProvider.saveScenarioAction(action);

        SM_SCENARIO scenario = new SM_SCENARIO();
        scenario.name = "Сценарий 1";
        scenario.houseId = "5abd49069433ee36debb84d4";
        scenario.conditions = Arrays.asList(condition);
        scenario.actions = Arrays.asList(action);

        MongoDbProvider.saveScenario(scenario);
    }
}
